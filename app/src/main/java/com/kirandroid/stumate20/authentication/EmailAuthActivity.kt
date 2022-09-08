@file:OptIn(ExperimentalMaterial3Api::class)

package com.kirandroid.stumate20.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.Cabin

@Composable
fun OtherEmailAuth(navController: NavController) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        Column(modifier = Modifier
            .fillMaxSize()) {

            // Loading Stumate Logo
            Image(painter = painterResource(id = R.drawable.stumate_logo_sign), contentDescription = "Stumate Logo",
                modifier = Modifier
                    .paddingFromBaseline(35.dp)
                    .size(width = 250.dp, height = 200.dp)
                    .align(Alignment.CenterHorizontally))

            // Load Text Field
            var text by remember { mutableStateOf(TextFieldValue("")) }

            TextField(modifier = Modifier
                .padding(start = 20.dp, top = 50.dp, end = 20.dp)
                .fillMaxWidth(),
                value = text, 
                onValueChange = { newText ->
                text = newText
            },
            placeholder = { Text(text = "Enter your Email ID")})

        }


        Button(
            onClick = {

                // Check whether user has entered email ID or not
                      navController.navigate("take_student_details")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(25.dp)
                .fillMaxWidth()
                .size(height = 50.dp, width = 350.dp),
            shape = CircleShape,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                disabledElevation = 2.dp
            ),
        enabled = true) {

            Text(text = "Continue", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )
        }




    }

}