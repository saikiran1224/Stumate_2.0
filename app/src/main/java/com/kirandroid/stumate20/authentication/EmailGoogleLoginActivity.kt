@file:OptIn(ExperimentalMaterial3Api::class)

package com.kirandroid.stumate20.authentication

import android.annotation.SuppressLint
import android.text.Layout
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.navigation.Screen
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.ui.theme.dividerDotsColor
import com.kirandroid.stumate20.ui.theme.textFieldHintColor
import com.kirandroid.stumate20.utils.LoadingState
import com.kirandroid.stumate20.utils.UserPreferences
import com.kirandroid.stumate20.viewmodels.LogInScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(navController: NavController, viewModel: LogInScreenViewModel) {

    val state by viewModel.loadingState.collectAsState()

    var txtEmailID by remember { mutableStateOf(TextFieldValue("")) }
    var txtPassword by remember { mutableStateOf(TextFieldValue("")) }

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // creating a custom object of studentData to hold data fetched from database
    var studentData = StudentData()

    // Google sign in related
    val context = LocalContext.current
    val token = stringResource(R.string.default_web_client_id)

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    var googleEmailID by remember { mutableStateOf("null@gmail.com") }

    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)


    // Equivalent of onActivityResult
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

        try {
            val account = task.getResult(ApiException::class.java)!!

            // getting the gmail which user selected
            googleEmailID = account.email.toString()

            // will be checking whether user has already registered or not with emailID
            val db = Firebase.firestore
            db.collection("students_data")
                .whereEqualTo("emailID", googleEmailID)
                .get()
                .addOnSuccessListener { it ->
                    // checking the size of the result if it's greater than one user is already registered
                    // allow him to go to Dashboard Screen and greet him
                    // TODO: Set App Preferences
                    Log.d("DEBUG", "${it.documents}")

                    if (it.documents.size > 0) {

                        // retrieving the student Data from Database
                        studentData = it.documents[0].toObject<StudentData>()!!

                        // checking whether student is registered with Google
                        if (studentData.authType == "Google") {

                            // making user sign in using Google
                            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                            // allows user to sign in to Google account
                            viewModel.signWithCredential(credential)

                            // TODO: Set the app preferences with the emailID
                            coroutineScope.launch {
                                dataStore.setIsLogin(true)
                                dataStore.setStudentID(studentData.documentID)
                                dataStore.setStudentName(studentData.name)
                                dataStore.setStudentEmail(studentData.emailID)
                                // Setting the student Academic Batch and Gender
                                dataStore.setStudentGender(studentData.avatarType)
                                dataStore.setStudentAcademicBatch(studentData.batch_ID)
                            }

                            // OF DASHBOARD SCREEN STARTS
                            navController.navigate("dashboard_screen")

                        } else {
                            googleSignInClient.signOut()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "You are already registered with Email. Please sign in using your email"
                                )
                            }
                        }
                    } else {

                        // If user is not found we are informing to "sign up to continue"
                        googleSignInClient.signOut()
                        // show Snackbar
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "Sorry you haven't registered with us! Please sign up to continue"
                            )
                        }
                    }
                }

        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

    // observing status of google sign in
    when(state.status) {

        LoadingState.Status.RUNNING -> {
            // show progress bar
        }

        LoadingState.Status.SUCCESS -> {

            // passing the Email to the Database to fetch the user details and setting the
            // App Preferences
            val db = Firebase.firestore
            db.collection("students_data")
                .whereEqualTo("emailID", txtEmailID.text.toString())
                .get()
                .addOnSuccessListener { it ->
                    // checking the size of the result if it's greater than one user is already registered
                    // allow him to go to Dashboard Screen and greet him
                    // TODO: Set App Preferences

                    if (it.documents.size > 0) {

                        // retrieving the student Data from Database
                        studentData = it.documents[0].toObject<StudentData>()!!

                        // checking whether student is registered with Google
                        if (studentData.authType == "Email") {

                            // TODO: Set the app preferences with the emailID
                            coroutineScope.launch {
                                dataStore.setIsLogin(true)
                                dataStore.setStudentID(studentData.documentID)
                                dataStore.setStudentName(studentData.name)
                                dataStore.setStudentEmail(studentData.emailID)
                                // Setting the academic batch and Gender
                                dataStore.setStudentGender(studentData.avatarType)
                                dataStore.setStudentAcademicBatch(studentData.batch_ID)
                            }

                            // OF DASHBOARD SCREEN STARTS
                            navController.navigate("dashboard_screen")

                        } else {

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "You are already registered with Google. Please google sign in"
                                )
                            }
                        }
                    }
                }
        }

        LoadingState.Status.FAILED -> {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    "Incorrect Email ID or Password!"
                )
            }
        }

        else -> {
            // Nothing
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        content = { innerPadding ->
            Column(modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())) {

                // Loading Stumate Logo
                Image(painter = painterResource(id = R.drawable.stumate_text_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(width = 130.dp, height = 58.dp)
                        .padding(top = 15.dp))

                Text(
                    text = "Your's Student's Friendly Platform",
                    fontFamily = Cabin,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 0.5.sp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 15.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(width = 25.dp, height = 2.dp),)


                // Login Text - Big
                Text(
                    text = "Login",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontFamily = Cabin, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 25.dp)
                )

                // New to Stumate Text
                Text(text = buildAnnotatedString {
                    append("New to Stumate? ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                        append("Sign up for free")
                    }
                },
                    style = TextStyle(fontFamily = Cabin, fontSize = 17.sp,fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .clickable {
                            // Navigating to Continue with Other Email Screen
                            navController.navigate("login_signup_screen")
                        }
                        .align(Alignment.CenterHorizontally))

                // Load Text Field - Email ID

                OutlinedTextField(modifier = Modifier
                    .padding(start = 25.dp, top = 32.dp, end = 25.dp)
                    .fillMaxWidth(),
                    value = txtEmailID,
                    textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
                    maxLines = 1,
                    shape = RoundedCornerShape(80.dp),
                    onValueChange = { newText ->
                        txtEmailID = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text("Enter your Email ID", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = textFieldHintColor,
                        placeholderColor = MaterialTheme.colorScheme.secondary
                    ),
                    placeholder = { Text(text = "Enter your Email ")})

                // Load Text Field - Password

                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(modifier = Modifier
                    .padding(start = 25.dp, top = 20.dp, end = 25.dp)
                    .fillMaxWidth(),
                    value = txtPassword,
                    textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
                    maxLines = 1,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(80.dp),
                    onValueChange = { newText ->
                        txtPassword = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text("Enter your Password", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = textFieldHintColor,
                        placeholderColor = MaterialTheme.colorScheme.secondary
                    ),
                    placeholder = { Text(text = "Enter your Password ")},
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        // Please provide localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    })


                // Forget your Password Text
                Text(
                    text = "Forgot your Password?",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontFamily = Cabin, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 40.dp, top = 10.dp))


                // Continue for EMail/Pwd Button
                Button(
                    onClick = {
                        viewModel.signInWithEmailAndPassword(email = txtEmailID.text.toString(), password = txtPassword.text.toString())

                              },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 25.dp, end = 25.dp, top = 50.dp, bottom = 25.dp)
                        .fillMaxWidth()
                        .size(height = 50.dp, width = 350.dp),
                    shape = CircleShape,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        disabledElevation = 0.dp
                    ),
                    enabled = txtEmailID.text.isNotEmpty() && txtPassword.text.isNotEmpty()) {

                    Text(text = "Continue", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )

                }

                // Loading ---or--
                Text(text = buildAnnotatedString {
                    append("------------------------------------")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 13.sp, fontWeight = FontWeight.Medium)) {
                        append(" or ")
                    }
                    append("------------------------------------")
                },
                    fontFamily = Cabin,
                    color = dividerDotsColor,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                // Loading Continue with Google Button
                Button(
                    onClick = {

                        launcher.launch(googleSignInClient.signInIntent)

                              },
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .size(height = 50.dp, width = 350.dp),
                    shape = CircleShape,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        disabledElevation = 2.dp
                    )) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_logo),
                        modifier = Modifier
                            .padding(start = 0.dp, end = 0.dp)
                            .size(width = 28.dp, height = 28.dp),
                        tint = Color.Unspecified,
                        contentDescription = "Google logo")
                    Text(text = "    Continue with Google", modifier = Modifier.padding(start = 0.dp), fontFamily = Cabin, fontSize = 18.sp, fontWeight = FontWeight.Medium )
                }
            }
        }
    )
}

