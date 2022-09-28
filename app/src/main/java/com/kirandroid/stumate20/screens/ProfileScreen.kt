package com.kirandroid.stumate20.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kirandroid.stumate20.utils.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    Scaffold(
        content = {

            Row(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {

                Text(text = "Hello Profile Screen")

            }

        },
        bottomBar = {
            BottomNavigationBar(navController = navController, "Profile")
        }
    )

}