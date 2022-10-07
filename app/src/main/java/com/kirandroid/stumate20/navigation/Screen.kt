package com.kirandroid.stumate20.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object SignUpScreen: Screen("login_signup_screen")
    object LoginScreen: Screen("continue_with_other_email")
    object StudentDetails: Screen("take_student_details")
    object ChooseAvatarScreen: Screen("choose_avatar")

    // Bottom Navigation
    object HomeScreen: Screen("dashboard_screen")
    object ClassRoomScreen: Screen("classroom_screen")
    object PremiumScreen: Screen("premium_screen")
    object ProfileScreen: Screen("profile_screen")

    // Subjects Related
    object AllSubjectsScreen: Screen("all_subjects_screen")
    object ShowListOfSubjects: Screen("show_subjects_home_screen")
    object SubjectInfoScreen: Screen("subject_info_screen")


}
