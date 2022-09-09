package com.kirandroid.stumate20.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object SignUpScreen: Screen("login_signup_screen")
    object LoginScreen: Screen("continue_with_other_email")
    object StudentDetails: Screen("take_student_details")
    object ChooseAvatarScreen: Screen("choose_avatar")

}
