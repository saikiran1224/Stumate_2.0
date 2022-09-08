package com.kirandroid.stumate20.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object LoginSignUp: Screen("login_signup_screen")
    object EmailAuthentication: Screen("continue_with_other_email")
    object StudentDetails: Screen("take_student_details")

}
