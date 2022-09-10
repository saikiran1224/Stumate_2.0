package com.kirandroid.stumate20.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kirandroid.stumate20.SplashScreen

import com.kirandroid.stumate20.authentication.LoginScreen
import com.kirandroid.stumate20.authentication.SignUpScreen
import com.kirandroid.stumate20.authentication.StudentDetails
import com.kirandroid.stumate20.home.ChooseAvatarScreen
import com.kirandroid.stumate20.viewmodels.SignUpScreenViewModel
import com.kirandroid.stumate20.viewmodels.StudentDetailsViewModel

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

        composable(route = Screen.StudentDetails.route + "?authType={authType}&gmailID={gmailID}", arguments = listOf(
            navArgument("authType") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }, navArgument("gmailID") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }
        )) {

            val viewModel: SignUpScreenViewModel = SignUpScreenViewModel()

            val studentDetailsViewModel: StudentDetailsViewModel = StudentDetailsViewModel()

            // Extracting the argument
            val authType = it.arguments?.getString("authType")
            val googleEmailID = it.arguments?.getString("gmailID")

            StudentDetails(navController = navController,
                authType = authType,
                googleEmailID = googleEmailID,
                viewModel = viewModel,
                studentDetailsViewModel = studentDetailsViewModel)
        }

        composable(route = Screen.ChooseAvatarScreen.route + "/{studName}") {

            val studentName = it.arguments?.getString("studName")

            ChooseAvatarScreen(navController = navController, studentName = studentName)
        }

        
    }
    
}