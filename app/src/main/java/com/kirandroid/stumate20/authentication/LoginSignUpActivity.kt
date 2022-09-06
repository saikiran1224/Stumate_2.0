package com.kirandroid.stumate20.authentication

import android.icu.number.Scale
import android.provider.CalendarContract
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.Cabin


@Composable
fun LoginSignUpActivity(navController: NavController) {

    // TODO: In Dark Mode, make sure the images of Books and the Stumate logo should be transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
        
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            // Loading Book Icon
            Image(painter = painterResource(id = R.drawable.book_icon),
                contentDescription = "book Image",
                modifier = Modifier
                    .paddingFromBaseline(35.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(width = 450.dp, height = 280.dp))

            // Loading Stumate logo
            Image(painter = painterResource(id = R.drawable.stumate_logo_sign), contentDescription = "Stumate Logo",
                modifier = Modifier
                    .size(width = 280.dp, height = 200.dp)
                    .align(Alignment.CenterHorizontally))

            // Loading Button
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .size(height = 50.dp, width = 350.dp),
                shape = CircleShape,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                disabledElevation = 2.dp
            )) {

                Icon(painter = painterResource(id = R.drawable.google_logo), modifier = Modifier.size(width = 25.dp, height = 25.dp), contentDescription = "Gmail")

                Text(text = "Continue with Google", modifier = Modifier.padding(start = 15.dp), fontFamily = Cabin, fontSize = 18.sp, )
            }
        }

        // Continue with other Email Text
        Text(
            text = "Continue with other Email",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 45.dp)
                .clickable {
                    // Navigating to Continue with Other Email Screen
                    navController.navigate("continue_with_other_email")
                },
            color = MaterialTheme.colorScheme.primary,
            fontFamily = Cabin,
            fontSize = 19.sp,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline
        )


    }
   



}