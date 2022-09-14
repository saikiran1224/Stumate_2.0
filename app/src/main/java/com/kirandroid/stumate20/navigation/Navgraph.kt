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
import com.kirandroid.stumate20.home.DashboardScreen
import com.kirandroid.stumate20.viewmodels.ChooseAvatarScreenViewModel
import com.kirandroid.stumate20.viewmodels.LogInScreenViewModel
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

            val viewModel:LogInScreenViewModel = LogInScreenViewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
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

        composable(route = Screen.ChooseAvatarScreen.route + "?stuName={stuName}&stuPhone={phone}", arguments = listOf(
            navArgument("stuName") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }, navArgument("stuPhone") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }
        )) {

            val studentName = it.arguments?.getString("stuName")
            val phone = it.arguments?.getString("phone")

            val chooseAvatarScreenViewModel: ChooseAvatarScreenViewModel = ChooseAvatarScreenViewModel()

            ChooseAvatarScreen(navController = navController, studentName = studentName, phone = phone, viewModel = chooseAvatarScreenViewModel)
        }

        composable(route = Screen.DashboardScreen.route + "/{studName}") {

            val studentName = it.arguments?.getString("studName")
            DashboardScreen(navController = navController, studentName = studentName.toString())
        }

        
    }
    
}