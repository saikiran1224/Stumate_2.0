package com.kirandroid.stumate20.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kirandroid.stumate20.SplashScreen

import com.kirandroid.stumate20.authentication.LoginScreen
import com.kirandroid.stumate20.authentication.SignUpScreen
import com.kirandroid.stumate20.authentication.StudentDetails
import com.kirandroid.stumate20.screens.*
import com.kirandroid.stumate20.viewmodels.*

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

        // Class Room Screen
        composable(route = Screen.ClassRoomScreen.route) {
            ClassRoomScreen(navController = navController)
        }

        // Premium Screen
        composable(route = Screen.PremiumScreen.route) {
            PremiumScreen(navController = navController)
        }

        // Profile Screen
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
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

        composable(route = Screen.DisplayDocumentsScreen.route + "?subName={subName}&unitName={unitName}", arguments = listOf(

            navArgument("subName") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
            navArgument("unitName") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }

        )) {

            val subjectName = it.arguments?.getString("subName")
            val unitName = it.arguments?.getString("unitName")

            val documentsViewModel: DocumentsViewModel = viewModel()

            DisplayDocumentsScreen(navController = navController, subjectName = subjectName.toString(),
                unitName = unitName.toString(), documentsViewModel = documentsViewModel)

        }

        composable(route = Screen.HomeScreen.route) {

            // Used Earlier
            val studentName = it.arguments?.getString("studName")

            val homeScreenViewModel: HomeScreenViewModel = viewModel()

            DashboardScreen(navController = navController, homeScreenViewModel = homeScreenViewModel)
        }

        // Note: Passing an empty list as far as Navgraph considered
        composable(route = Screen.AllSubjectsScreen.route) {

            val homeScreenViewModel: HomeScreenViewModel = viewModel()

            AllSubjectsScreen(navController = navController, homeScreenViewModel = homeScreenViewModel)

        }

        composable(route = Screen.SubjectInfoScreen.route + "/{subName}") {

            // TODO: Need to pass Subject View Model
            val subjectName = it.arguments?.getString("subName")

            SubjectInfoScreen(navController = navController, subjectName = subjectName.toString())

        }



        
    }
    
}