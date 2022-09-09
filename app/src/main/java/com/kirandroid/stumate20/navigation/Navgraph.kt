package com.kirandroid.stumate20.navigation

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.SplashScreen

import com.kirandroid.stumate20.authentication.LoginScreen
import com.kirandroid.stumate20.authentication.SignUpScreen
import com.kirandroid.stumate20.authentication.StudentDetails
import com.kirandroid.stumate20.home.ChooseAvatarScreen
import com.kirandroid.stumate20.viewmodels.SignUpScreenViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController, 
        startDestination = Screen.Splash.route) {
        
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SignUpScreen.route) {

            val viewModel: SignUpScreenViewModel = SignUpScreenViewModel()
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.StudentDetails.route + "/{id}") {

            val viewModel: SignUpScreenViewModel = SignUpScreenViewModel()

            // Extracting the argument
            val authType = it.arguments?.getString("id")

            StudentDetails(navController = navController, authType = authType, viewModel = viewModel)
        }

        composable(route = Screen.ChooseAvatarScreen.route) {
            ChooseAvatarScreen(navController = navController)
        }

        
    }
    
}