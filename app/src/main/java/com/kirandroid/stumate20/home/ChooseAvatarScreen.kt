package com.kirandroid.stumate20.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kirandroid.stumate20.navigation.Screen

@Composable
fun ChooseAvatarScreen(navController: NavController, studentName: String?) {

    Column(modifier = Modifier.padding(20.dp) ) {

        if (studentName != null) {
            Text(text = studentName,
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }

    }
}