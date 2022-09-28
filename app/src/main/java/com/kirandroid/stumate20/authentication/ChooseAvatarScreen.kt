package com.kirandroid.stumate20.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.*
import com.kirandroid.stumate20.utils.LoadingState
import com.kirandroid.stumate20.utils.UserPreferences
import com.kirandroid.stumate20.viewmodels.ChooseAvatarScreenViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSnapperApi::class)
@Composable
fun ChooseAvatarScreen(navController: NavController, studentName: String?, phone: String?, viewModel: ChooseAvatarScreenViewModel) {

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    // Storing the state returned from viewmodel
    val state by viewModel.loadingState.collectAsState()

    //context
    val context = LocalContext.current

    // we instantiate the saveEmail class
    val dataStore = UserPreferences(context)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick =
                    { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack , contentDescription = "Back Icon")
                    }
                },
            )
        },
        content = { innerPadding ->

            // Handling ViewModel State
            when(state.status) {

                LoadingState.Status.RUNNING -> {
                    // show loading progress bar
                }

                LoadingState.Status.SUCCESS -> {

                    // navigate to the Dashboard Screen
                    // Data got updated so navigate him to next Dashboard screen
                    navController.navigate("dashboard_screen")

                }

                LoadingState.Status.FAILED -> {

                    // Show some error ocurred
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "Some Error Ocurred!"
                        )
                    }

                }

                else -> {}
            }

            val maleCheckedState = remember { mutableStateOf(false) }
            val femaleCheckedState = remember { mutableStateOf(false) }

            Box(modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()) {

                Column(modifier = Modifier
                    .fillMaxSize() ) {

                    // Text - Hi $studentName
                    Text(
                        text = buildAnnotatedString {

                            append("Hi!")
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(" ${dataStore.getStudentName.collectAsState("").value}")
                            }
                            append(", Personalize your look")
                        },
                        modifier = Modifier
                            .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    // Text - Choose from fun avatars
                    Text(
                        text = "Choose from fun avatars",
                        fontWeight = FontWeight.Thin,
                        fontFamily = Cabin,
                        fontSize = 19.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 90.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Row(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 35.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        content = {
                        // Displaying two images

                            // Male
                            Column(modifier = Modifier.padding(end = 30.dp), verticalArrangement = Arrangement.Center) {
                                IconToggleButton(checked = maleCheckedState.value, onCheckedChange = {
                                    maleCheckedState.value = !maleCheckedState.value
                                  //  maleBorderColor.value = seed

                                    femaleCheckedState.value = false
                                   // femaleBorderColor.value = unfocusedAvatarColor
                                }, modifier = Modifier
                                    .size(115.dp, 115.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        if (maleCheckedState.value) seed else unfocusedAvatarColor,
                                        CircleShape
                                    )
                                    .padding(5.dp) ) {

                                    Image(painter = painterResource(id = R.drawable.male_avatar),
                                        contentDescription = null )

                                }

                                Text(text = "Male", color = if (maleCheckedState.value)
                                    MaterialTheme.colorScheme.primary else unfocusedAvatarColor,
                                    textAlign = TextAlign.Center, fontWeight = FontWeight.Thin,
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .align(Alignment.CenterHorizontally)
                                    )
                            }


                            // Female
                            Column(modifier = Modifier.padding(), verticalArrangement = Arrangement.Center) {

                                IconToggleButton(checked = femaleCheckedState.value, onCheckedChange = {
                                    femaleCheckedState.value = !femaleCheckedState.value
                                   // femaleBorderColor.value = seed

                                    maleCheckedState.value = false
                                   // maleBorderColor.value = unfocusedAvatarColor
                                }, modifier = Modifier
                                    .size(115.dp, 115.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        if (femaleCheckedState.value) seed else unfocusedAvatarColor,
                                        CircleShape
                                    )
                                    .padding(5.dp)) {

                                    Image(painter = painterResource(id = R.drawable.female_avatar ), contentDescription = null )


                                }

                                Text(text = "Female", modifier = Modifier
                                    .padding(top = 10.dp)
                                    .align(Alignment.CenterHorizontally),color = if (femaleCheckedState.value)
                                    MaterialTheme.colorScheme.primary else unfocusedAvatarColor,
                                    fontWeight = FontWeight.Thin, textAlign = TextAlign.Center )
                            }

                    })

                }

                // Creating a variable to hold the selected gender
                var genderSelected by rememberSaveable { mutableStateOf("") }

                // Button - Continue
                Button(
                    onClick = {

                        // need to update the firestore record with help of phone Number
                        // Based on the avatar selected we need to set the gender

                        // Getting the selected gender
                        genderSelected = if (maleCheckedState.value) "Male" else "Female"

                        viewModel.updateAvatarPreference(phone = phone.toString(), genderSelected = genderSelected)

                       /* val db = Firebase.firestore
                        db.collection("students_data")
                            .whereEqualTo("phoneNumber", phone)
                            .get()
                            .addOnSuccessListener {

                                if(it.documents.size > 0) {

                                    val studentData = it.documents[0].toObject<StudentData>()!!

                                    // Updating the record by taking the key from studentData
                                    db.collection("students_data").document(studentData.documentID)
                                        .update("avatarType",genderSelected)
                                        .addOnSuccessListener {



                                        }.addOnFailureListener {
                                            Log.d("DEBUG","Some Error Occurred! ${it.localizedMessage}")
                                            return@addOnFailureListener
                                        }

                                }

                            }*/


                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(25.dp)
                        .fillMaxWidth()
                        .size(height = 50.dp, width = 350.dp),
                    shape = CircleShape,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        disabledElevation = 0.dp
                    ), // TODO: Enable the button after doing appropriate validation
                    enabled = maleCheckedState.value || femaleCheckedState.value) {

                    Text(text = "Continue", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )
                }

            }

        }

    )


}