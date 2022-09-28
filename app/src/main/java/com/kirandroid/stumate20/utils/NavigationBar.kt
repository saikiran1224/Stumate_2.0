package com.kirandroid.stumate20.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HotelClass
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.navigation.Screen
import com.kirandroid.stumate20.screens.DashboardScreen
import com.kirandroid.stumate20.ui.theme.navBarBgColor
import com.kirandroid.stumate20.ui.theme.navContentColor
import com.kirandroid.stumate20.ui.theme.navIndicatorColor
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BottomNavigationBar(navController: NavController, destinationName: String) {

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Class Room", "Premium", "Profile")

    val isHomeEnabled by rememberSaveable { mutableStateOf(destinationName == "Home") }
    val isClassRoomEnabled by rememberSaveable { mutableStateOf(destinationName == "Class Room") }
    val isPremiumEnabled by rememberSaveable { mutableStateOf(destinationName == "Premium") }
    val isProfileEnabled by rememberSaveable { mutableStateOf(destinationName == "Profile") }

    val images = listOf(R.drawable.home_icon, R.drawable.classroom_icon, R.drawable.crown_icon, R.drawable.profile_icon)

   NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
       containerColor = Color(0xFFEBE4FF),
       contentColor = Color.Gray
    ) {

            // Home Icon
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = R.drawable.home_icon), contentDescription = null) },
                label = { Text("Home") },
                selected = isHomeEnabled,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = navContentColor,
                    unselectedTextColor = navContentColor,
                    selectedIconColor =  MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = navIndicatorColor
                ),
                onClick = { navController.navigate("dashboard_screen")})

       // Class Room Icon
       NavigationBarItem(
           icon = { Icon(painter = painterResource(id = R.drawable.classroom_icon), contentDescription = null) },
           label = { Text("Class Room") },
           selected = isClassRoomEnabled,
           colors = NavigationBarItemDefaults.colors(
               unselectedIconColor = navContentColor,
               unselectedTextColor = navContentColor,
               selectedIconColor =  MaterialTheme.colorScheme.primary,
               selectedTextColor = MaterialTheme.colorScheme.primary,
               indicatorColor = navIndicatorColor
           ),
           onClick = { navController.navigate("classroom_screen")})

       // Home Icon
       NavigationBarItem(
           icon = { Icon(painter = painterResource(id = R.drawable.crown_icon), contentDescription = null) },
           label = { Text("Premium") },
           selected = isPremiumEnabled,
           colors = NavigationBarItemDefaults.colors(
               unselectedIconColor = navContentColor,
               unselectedTextColor = navContentColor,
               selectedIconColor =  MaterialTheme.colorScheme.primary,
               selectedTextColor = MaterialTheme.colorScheme.primary,
               indicatorColor = navIndicatorColor
           ),
           onClick = { navController.navigate("premium_screen")})

       // Home Icon
       NavigationBarItem(
           icon = { Icon(painter = painterResource(id = R.drawable.profile_icon), contentDescription = null) },
           label = { Text("Profile") },
           selected = isProfileEnabled,
           colors = NavigationBarItemDefaults.colors(
               unselectedIconColor = navContentColor,
               unselectedTextColor = navContentColor,
               selectedIconColor =  MaterialTheme.colorScheme.primary,
               selectedTextColor = MaterialTheme.colorScheme.primary,
               indicatorColor = navIndicatorColor
           ),
           onClick = { navController.navigate("profile_screen")})

    }




        // Navigate to a screen based on the index
        /**/
}