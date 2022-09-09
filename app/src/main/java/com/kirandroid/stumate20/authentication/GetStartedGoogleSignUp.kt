package com.kirandroid.stumate20.authentication

import android.icu.number.Scale
import android.provider.CalendarContract
import android.text.Layout
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.navigation.Screen
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.utils.LoadingState
import com.kirandroid.stumate20.viewmodels.SignUpScreenViewModel


@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpScreenViewModel) {

    // TODO: In Dark Mode, make sure the images of Books and the Stumate logo should be transparent

    // Creating a variable for detecting whether its Email or Google Auth
    var authType by remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.loadingState.collectAsState()

    // Equivalent of onActivityResult
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            viewModel.signWithCredential(credential)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

    // To check the status whether user has successfully autheticated with Google
    if (state.status == LoadingState.Status.SUCCESS) {
        // once authenticated successfully
        // Since user clicked on Email we are changing the authType variable to Email
        authType = "Google"
        // Navigating to form page - Enabling Email ID and Password Composable
        navController.navigate("take_student_details/$authType")
    }

        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {

            // Loading Book Icon - Lottie File
            val composition by
            rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.girl_with_books_anim))
            val progress by
            animateLottieCompositionAsState(composition = composition, isPlaying = true)

            LottieAnimation(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 5.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(width = 450.dp, height = 280.dp),
                composition = composition,
                progress = progress
            )

            // Stumate Logo
            Image(painter = painterResource(id = R.drawable.stumate_text_logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 230.dp, height = 75.dp))

            Text(
                text = "Your's Student's Friendly Platform",
                fontFamily = Cabin,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 0.5.sp))

            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(width = 40.dp, height = 3.dp),)

            // Loading Continue with Email Button
            OutlinedButton(
                onClick = {
                    // Since user clicked on Email we are changing the authType variable to Email
                    authType = "Email"
                    // Navigating to form page - Enabling Email ID and Password Composable
                    navController.navigate("take_student_details/$authType")
                },
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 17.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .size(height = 50.dp, width = 350.dp),
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    disabledElevation = 2.dp
                ),
            border = BorderStroke(1.dp,MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.background)) {

                Icon(painter = painterResource(id = R.drawable.drafts_icon),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .size(width = 28.dp, height = 28.dp),
                    contentDescription = "Gmail")

                Text(text = "Continue with Email",
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.padding(start = 0.dp),
                    fontFamily = Cabin, fontSize = 18.sp, fontWeight = FontWeight.Medium )
            }


            val context = LocalContext.current
            val token = stringResource(R.string.default_web_client_id)

            // Loading Continue with Google Button
            Button(
                onClick = {

                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)

                },
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 13.dp, bottom = 10.dp)
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

            // Loading "Login Text"
            Text(text = buildAnnotatedString {
                append("Already have an account?")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                    append(" Login")
                }
            },
            style = TextStyle(fontFamily = Cabin, fontSize = 17.sp,fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        // Navigating to Continue with Other Email Screen
                        navController.navigate("continue_with_other_email")
                    })


        // Terms and Conditions and Privacy Policy Text
        Text(
            text = buildAnnotatedString {
                                        append("By continuing, you agree to Stumate's ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                    append("Terms and Conditions")
                }
                append(" and ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                    append("Privacy Policy",)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 30.dp, bottom = 35.dp, end = 30.dp, top = 32.dp)
                .clickable {
                    // TODO
                },
            color = MaterialTheme.colorScheme.secondaryContainer,
            fontFamily = Cabin,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            style = TextStyle(textAlign = TextAlign.Center)
        )


    }
   



}