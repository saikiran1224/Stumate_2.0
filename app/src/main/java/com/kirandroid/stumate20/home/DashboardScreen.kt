package com.kirandroid.stumate20.home

import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.twotone.Notifications
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.*
import androidx.compose.material3.CardElevation as CardElevation1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, studentName: String) {

    val semList = listOf("Semester","Sem 1", "Sem 2", "Sem 3", "Sem 4", "Sem 5", "Sem 6", "Sem 7", "Sem 8")
    var selectedSem by rememberSaveable { mutableStateOf(semList[0]) }

     Box(modifier = Modifier
         .fillMaxSize()
         .padding()
         .background(color = dashboardBgColor)
         .verticalScroll(rememberScrollState())) {
         
         Column(modifier = Modifier.fillMaxSize()) {

             Box(modifier = Modifier.fillMaxWidth()) {

                 Box(modifier = Modifier
                     .background(dashboardTopBgColor)
                     .height(140.dp)
                     .fillMaxWidth())
                 Card(
                     border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                     shape = RoundedCornerShape(20.dp),
                     colors = CardDefaults.cardColors(
                         containerColor = MaterialTheme.colorScheme.primary
                     ),
                     modifier = Modifier.padding(start = 16.dp, end= 16.dp, top = 18.dp),
                     elevation = CardDefaults.cardElevation(
                         defaultElevation = 5.dp,
                         pressedElevation = 0.dp,
                         focusedElevation = 0.dp,
                         hoveredElevation = 0.dp
                     ),

                     ) {

                     // Welcome Text
                     Text(
                         text = "Hey $studentName !",
                         textAlign = TextAlign.Center,
                         fontSize = 24.sp,
                         fontFamily = Cabin,
                         color = light_onprimary,
                         fontWeight = FontWeight.Medium,
                         modifier = Modifier.padding(top = 18.dp, start = 18.dp)
                     )

                     /* // For Welcome Text and Notification
                 Row(modifier = Modifier
                     .fillMaxWidth()
                     .align(Alignment.CenterHorizontally)
                     .padding(top = 15.dp), verticalAlignment = Alignment.CenterVertically) {
    
    
                        Spacer(modifier = Modifier.weight(1f))
                         // Notification Icon
                         Icon(imageVector = Icons.Outlined.Notifications, tint = MaterialTheme.colorScheme.primary,contentDescription = null, modifier = Modifier
                             .align(CenterVertically)
                             .size(35.dp)
                             .clickable { *//*TODO*//* })
             }*/

                     // Subject Card
                     OutlinedCard(modifier = Modifier
                         .fillMaxWidth()
                         .padding(top = 28.dp, start = 15.dp, end = 15.dp)
                         .clickable {
                             /*TODO*/
                         }
                         .height(80.dp),
                         border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                         shape = RoundedCornerShape(15.dp),
                         colors = CardDefaults.cardColors(
                             containerColor = light_onprimary
                         ),
                         elevation = CardDefaults.cardElevation(
                             defaultElevation = 0.dp,
                             pressedElevation = 0.dp,
                             focusedElevation = 0.dp,
                             hoveredElevation = 0.dp

                         ),
                         content = {

                             Row(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .align(Alignment.CenterHorizontally)
                                     .padding(25.dp), verticalAlignment = Alignment.CenterVertically
                             ) {

                                 Icon(
                                     imageVector = Icons.Outlined.Add,
                                     contentDescription = null,
                                     modifier = Modifier
                                         .padding(end = 8.dp)
                                         .size(26.dp),
                                     tint = MaterialTheme.colorScheme.primary
                                 )

                                 Text(
                                     text = "Create your Subject",
                                     color = MaterialTheme.colorScheme.primary,
                                     fontWeight = FontWeight.W500
                                 )

                                 Spacer(modifier = Modifier.weight(1f))

                                 Icon(
                                     imageVector = Icons.Outlined.ArrowForwardIos,
                                     contentDescription = null,
                                     modifier = Modifier.size(22.dp),
                                     tint = MaterialTheme.colorScheme.primary
                                 )

                             }

                         }
                     )


                     // Upload your Documents Card
                     OutlinedCard(modifier = Modifier
                         .fillMaxWidth()
                         .padding(top = 13.dp, start = 15.dp, bottom = 18.dp, end = 15.dp)
                         .clickable {
                             /*TODO*/
                         }
                         .height(80.dp),
                         border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                         shape = RoundedCornerShape(15.dp),
                         colors = CardDefaults.cardColors(
                             containerColor = light_onprimary
                         ),
                         elevation = CardDefaults.cardElevation(
                             defaultElevation = 0.dp,
                             pressedElevation = 0.dp,
                             focusedElevation = 0.dp,
                             hoveredElevation = 0.dp
                         ),
                         content = {

                             Row(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .align(Alignment.CenterHorizontally)
                                     .padding(25.dp), verticalAlignment = Alignment.CenterVertically
                             ) {

                                 Icon(
                                     imageVector = Icons.Outlined.FileUpload,
                                     contentDescription = null,
                                     modifier = Modifier
                                         .padding(end = 8.dp)
                                         .size(26.dp),
                                     tint = MaterialTheme.colorScheme.primary
                                 )

                                 Text(
                                     text = "Upload your Documents",
                                     color = MaterialTheme.colorScheme.primary,
                                     fontWeight = FontWeight.W500
                                 )

                                 Spacer(modifier = Modifier.weight(1f))

                                 Icon(
                                     imageVector = Icons.Outlined.ArrowForwardIos,
                                     contentDescription = null,
                                     modifier = Modifier.size(22.dp),
                                     tint = MaterialTheme.colorScheme.primary
                                 )
                             }
                         }
                     )

                 }
             }


             // Subjects and Semesters Row
             Row(modifier = Modifier
                 .padding(top = 25.dp, start = 16.dp)
                 .fillMaxWidth()
                 .align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {

                 Text(text = "Subjects", fontSize = 22.sp, color = txtSubjectsColor,fontWeight = FontWeight.W500)

                 Spacer(modifier = Modifier.weight(1f))

                 Card(
                     modifier = Modifier.padding(end = 16.dp),
                     shape = RoundedCornerShape(15.dp),
                     colors = CardDefaults.cardColors(
                         containerColor = dashboardTopBgColor
                     )
                 ) {
                     Text(text = "Semester", fontSize = 12.5.sp, fontWeight = FontWeight.W500,
                         color = MaterialTheme.colorScheme.primary,
                         modifier = Modifier
                             .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 6.dp)
                             .align(CenterHorizontally))
                 }
             }

             // Browse Subjects here and Image Column
             OutlinedCard( modifier = Modifier
                 .padding(start = 16.dp, end = 16.dp, top = 15.dp)
                 .fillMaxWidth(),
                 border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.secondary),
                 colors = CardDefaults.cardColors(
                     containerColor = dashboardBgColor
                 )) {

                    // Image and Text
                  Image(painter = painterResource(id = R.drawable.subjects_icon),
                      modifier = Modifier.padding(top = 11.dp).align(CenterHorizontally),
                      contentDescription = null)
                 
                  Text(text = "Browse your Subjects",
                      modifier = Modifier.padding(top = 2.dp, bottom = 25.dp).align(CenterHorizontally),
                      fontSize = 17.sp,
                      fontWeight = FontWeight.W500,
                      color = txtBrowseYourSubs
                      )

             }

             // Your Community
             Text(text = "Your Community", fontSize = 22.sp,
                 color = txtSubjectsColor,
                 fontWeight = FontWeight.W500,
             modifier = Modifier.padding(top = 25.dp, start = 16.dp))


             // Community Card
             OutlinedCard(modifier = Modifier
                 .fillMaxWidth()
                 .padding(top = 18.dp, start = 15.dp, end = 15.dp)
                 .clickable {
                     /*TODO*/
                 }
                 .height(90.dp),
                 border = BorderStroke(1.dp, classMatesCardBgColor),
                 shape = RoundedCornerShape(28.dp),
                 colors = CardDefaults.cardColors(
                     containerColor = classMatesCardBgColor
                 ),
                 elevation = CardDefaults.cardElevation(
                     defaultElevation = 0.dp,
                     pressedElevation = 0.dp,
                     focusedElevation = 0.dp,
                     hoveredElevation = 0.dp

                 ),
                 content = {

                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .align(Alignment.CenterHorizontally)
                             .padding(18.dp), verticalAlignment = Alignment.CenterVertically
                     ) {

                         Column(modifier = Modifier) {

                             Text(
                                 text = "Check your class-mates here",
                                 color = MaterialTheme.colorScheme.primary,
                                 fontWeight = FontWeight.W500,
                                 fontSize = 18.sp
                             )

                             Text(
                                 text = "community with your class ",
                                 color = classMatesBottomText,
                                 fontWeight = FontWeight.W100,
                                 fontSize = 15.5.sp
                             )
                         }


                         Spacer(modifier = Modifier.weight(1.5f))

                         OutlinedCard(
                             onClick = {
                                 /* TODO */
                             },
                             shape = RoundedCornerShape(56.dp),
                             modifier = Modifier.size(60.dp).align(CenterVertically).padding(start = 2.dp),
                             border = BorderStroke(1.dp, classMatesIconColor),
                             colors = CardDefaults.cardColors(
                                 containerColor = classMatesIconColor
                             ),
                         ) {
                             Icon(
                                 imageVector = Icons.Outlined.ArrowForwardIos,
                                 contentDescription = null,
                                 tint = MaterialTheme.colorScheme.primary,
                                 modifier = Modifier.align(CenterHorizontally).padding(top = 14.dp)
                             )
                         }
                     }
                 }
             )
         }


     }


}