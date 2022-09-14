package com.kirandroid.stumate20.home

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.twotone.Notifications
import androidx.compose.material3.*
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.ui.theme.dashboardBgColor
import com.kirandroid.stumate20.ui.theme.subjectCardBgColor
import com.kirandroid.stumate20.ui.theme.uploadYourFilesColor

@Composable
fun DashboardScreen(navController: NavController, studentName: String) {


     Box(modifier = Modifier
         .fillMaxSize()
         .padding(16.dp)
         .background(color = dashboardBgColor)) {

         Column(modifier = Modifier.fillMaxSize()) {

             // For Welcome Text and Notification
             Row(modifier = Modifier
                 .fillMaxWidth()
                 .align(Alignment.CenterHorizontally)
                 .padding(top = 15.dp), verticalAlignment = Alignment.CenterVertically) {

                 // Text Icon
                 Text(text = buildAnnotatedString {
                     append("Welcome")
                     withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                         append(" ${studentName.split(" ")[0]}!")
                     }
                 }, textAlign = TextAlign.Center,
                     fontSize = 24.sp, fontFamily = Cabin, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                     // Notification Icon
                     Icon(imageVector = Icons.Outlined.Notifications, tint = MaterialTheme.colorScheme.primary,contentDescription = null, modifier = Modifier
                         .align(CenterVertically)
                         .size(35.dp)
                         .clickable { /*TODO*/ })
             }

             // Subject Card
             OutlinedCard(modifier = Modifier
                 .fillMaxWidth()
                 .padding(top = 30.dp)
                 .clickable {
                     /*TODO*/
                 }
                 .height(80.dp),
                 border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                 shape = RoundedCornerShape(15.dp),
                 colors = CardDefaults.cardColors(
                     containerColor = subjectCardBgColor
                 ),
                 content = {
                     
                     Row(modifier = Modifier
                         .fillMaxWidth()
                         .align(Alignment.CenterHorizontally).padding(25.dp), verticalAlignment = Alignment.CenterVertically) {
                         
                         Icon(imageVector = Icons.Outlined.Add, contentDescription = null, modifier = Modifier.padding(end = 8.dp).size(26.dp),
                                tint = MaterialTheme.colorScheme.primary)

                         Text(text = "Create your Subject", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.W500)

                         Spacer(modifier = Modifier.weight(1f))

                         Icon(imageVector = Icons.Outlined.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(22.dp),
                               tint = MaterialTheme.colorScheme.primary)
                         
                     }
                     
                 }
             )


             // Upload your Documents Card
             OutlinedCard(modifier = Modifier
                 .fillMaxWidth()
                 .padding(top = 17.dp)
                 .clickable {
                     /*TODO*/
                 }
                 .height(80.dp),
                 border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                 shape = RoundedCornerShape(15.dp),
                 colors = CardDefaults.cardColors(
                     containerColor = uploadYourFilesColor
                 ),
                 content = {

                     Row(modifier = Modifier
                         .fillMaxWidth()
                         .align(Alignment.CenterHorizontally).padding(25.dp), verticalAlignment = Alignment.CenterVertically) {

                         Icon(imageVector = Icons.Outlined.FileUpload, contentDescription = null, modifier = Modifier.padding(end = 8.dp).size(26.dp),
                             tint = MaterialTheme.colorScheme.primary)

                         Text(text = "Upload your Documents", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.W500)

                         Spacer(modifier = Modifier.weight(1f))

                         Icon(imageVector = Icons.Outlined.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(22.dp),
                             tint = MaterialTheme.colorScheme.primary)

                     }

                 }
             )


         }


     }


}