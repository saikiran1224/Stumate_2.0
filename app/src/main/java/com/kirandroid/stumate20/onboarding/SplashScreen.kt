package com.kirandroid.stumate20

import android.annotation.SuppressLint
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kirandroid.stumate20.utils.UserPreferences
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    var _isLogin by remember { mutableStateOf(false) }
    val isLogin = dataStore.getIsLogin.collectAsState("").value.toString().toBoolean()
    _isLogin = isLogin

    Log.d("DEBUG", "In Splash Screen $isLogin")
    // student details
    var _studentName by remember { mutableStateOf("") }
    val studName = dataStore.getStudentName.collectAsState(initial = "").value.toString()
    _studentName = studName

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)

        // checking the condition and sending the user based on the login status
        Log.d("DEBUG"," In launched effect, $_isLogin and $studName")

        if (_isLogin) {
            navController.navigate("dashboard_screen")
        } else {
            navController.navigate("login_signup_screen")
        }

    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(width = 300.dp, height = 200.dp)
        )

    }
}
