package com.kirandroid.stumate20

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {

         // TODO Scope of the Box - Display Stumate logo in center here
        val image: Painter = painterResource(id = R.drawable.splash_logo)
        Image(painter = image, contentDescription = "Splash Screen Image")
    }

}