package com.kirandroid.stumate20.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.buildSpannedString
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.*
import com.kirandroid.stumate20.utils.BottomNavigationBar
import com.kirandroid.stumate20.utils.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)


    // TODO: Need to dynamically load from Shared Preferences
    var studentName by remember { mutableStateOf("") }
    val studName = dataStore.getStudentName.collectAsState(initial = "").value.toString()
    studentName = studName

    var studentGender by remember { mutableStateOf("") }
    val studGender = dataStore.getStudentGender.collectAsState(initial = "").value.toString()
    studentGender = studGender

    val studentEmailID by remember { mutableStateOf("") }

    // List of avatars
    // TODO: Need to make the background of the avatars Transparent
    var studentAvatar by remember { mutableStateOf(0) }

    Scaffold(
        content = {

              Column(modifier = Modifier
                  .fillMaxSize()
                  .padding(it)
                  .verticalScroll(
                      rememberScrollState()
                  )) {

                  // Profile Card Related
                  Box(modifier = Modifier.fillMaxSize()) {

                      // Profile Card Background Image
                      Image(painter = painterResource(id = com.kirandroid.stumate20.R.drawable.profile_bg),
                          modifier = Modifier.fillMaxSize(),
                          contentDescription = "Profile Background Image")

                      // Column to hold the profile pic, student name and stucard link
                      Column(modifier = Modifier.fillMaxWidth()) {

                          // Avatar Card
                          // Here we are selecting the student Avatar based on the Student Gender what we receive

                          studentAvatar = if (studentGender == "Male") R.drawable.male_avatar else R.drawable.female_avatar

                          Image(painter = painterResource(id = studentAvatar),
                              modifier = Modifier
                                  .align(Alignment.CenterHorizontally)
                                  .padding(top = 45.dp)
                                  .size(80.dp)
                                  .clip(
                                      CircleShape
                                  )
                                  .border(5.dp, Color.White, CircleShape),
                              contentDescription = null )

                          // Student Name
                          Text(text = studentName,
                              modifier = Modifier
                                  .align(Alignment.CenterHorizontally)
                                  .padding(top = 8.dp),
                              fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)

                          // Check Stucard link
                          Text(text = buildAnnotatedString {
                              withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                  append("check your stucard")
                              }
                              append("  >")
                          },
                          modifier = Modifier
                              .align(Alignment.CenterHorizontally)
                              .padding(top = 6.dp)
                              .clickable {
                                  // TODO: Navigate to Stucard Screen
                              }, fontSize = 15.sp, color = Color(0xFFFECB5B)
                          )
                      }
                  }

                  // Menu
                  // Premium Icon
                  Row(modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.CenterHorizontally)
                      .padding(top = 45.dp, start = 16.dp, end = 15.dp, bottom = 22.dp)
                      .clickable {
                          // TODO
                      }, verticalAlignment = Alignment.CenterVertically) {

                      Icon(painter = painterResource(id = R.drawable.premium_icon),
                          contentDescription = null,
                          modifier = Modifier
                              .padding(end = 15.dp),
                      tint = Color.Unspecified)

                      Text(
                          text = "Premium",
                          color = textFieldHintColor,
                          fontWeight = FontWeight.W200
                      )

                      Spacer(modifier = Modifier.weight(1f))

                      Icon(
                          imageVector = Icons.Outlined.ArrowForwardIos,
                          contentDescription = null,
                          modifier = Modifier.size(18.dp),
                          tint = MaterialTheme.colorScheme.primary
                      )

                  }

                  // Invite Friends Icon
                  Row(modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.CenterHorizontally)
                      .padding(top = 0.dp, start = 16.dp, end = 15.dp, bottom = 22.dp)
                      .clickable {
                          // TODO
                      }, verticalAlignment = Alignment.CenterVertically) {

                      Icon(painter = painterResource(id = R.drawable.invite_friends_icon),
                          contentDescription = null,
                          modifier = Modifier
                              .padding(end = 15.dp),
                          tint = Color.Unspecified)

                      Text(
                          text = "Invite Friends",
                          color = textFieldHintColor,
                          fontWeight = FontWeight.W200
                      )

                      Spacer(modifier = Modifier.weight(1f))

                      Icon(
                          imageVector = Icons.Outlined.ArrowForwardIos,
                          contentDescription = null,
                          modifier = Modifier.size(18.dp),
                          tint = MaterialTheme.colorScheme.primary
                      )

                  }

                  // Help Center Icon
                  Row(modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.CenterHorizontally)
                      .padding(top = 0.dp, start = 16.dp, end = 15.dp, bottom = 22.dp)
                      .clickable {
                          // TODO
                      }, verticalAlignment = Alignment.CenterVertically) {

                      Icon(painter = painterResource(id = R.drawable.helpcenter_icon),
                          contentDescription = null,
                          modifier = Modifier
                              .padding(end = 15.dp),
                          tint = Color.Unspecified)

                      Text(
                          text = "Help Center",
                          color = textFieldHintColor,
                          fontWeight = FontWeight.W200
                      )

                      Spacer(modifier = Modifier.weight(1f))

                      Icon(
                          imageVector = Icons.Outlined.ArrowForwardIos,
                          contentDescription = null,
                          modifier = Modifier.size(18.dp),
                          tint = MaterialTheme.colorScheme.primary
                      )

                  }

                  // About Us Icon
                  Row(modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.CenterHorizontally)
                      .padding(top = 0.dp, start = 16.dp, end = 15.dp, bottom = 22.dp)
                      .clickable {
                          // TODO
                      }, verticalAlignment = Alignment.CenterVertically) {

                      Icon(painter = painterResource(id = R.drawable.about_us_icon),
                          contentDescription = null,
                          modifier = Modifier
                              .padding(end = 15.dp),
                          tint = Color.Unspecified)

                      Text(
                          text = "About Us",
                          color = textFieldHintColor,
                          fontWeight = FontWeight.W200
                      )

                      Spacer(modifier = Modifier.weight(1f))

                      Icon(
                          imageVector = Icons.Outlined.ArrowForwardIos,
                          contentDescription = null,
                          modifier = Modifier.size(18.dp),
                          tint = MaterialTheme.colorScheme.primary
                      )

                  }

                  // Logout Icon
                  Row(modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.CenterHorizontally)
                      .padding(top = 0.dp, start = 16.dp, end = 15.dp, bottom = 22.dp)
                      .clickable {
                          // TODO
                      }, verticalAlignment = Alignment.CenterVertically) {

                      Icon(painter = painterResource(id = R.drawable.logout_icon),
                          contentDescription = null,
                          modifier = Modifier
                              .padding(end = 15.dp),
                          tint = Color.Unspecified)

                      Text(
                          text = "Logout",
                          color = textFieldHintColor,
                          fontWeight = FontWeight.W200
                      )

                      Spacer(modifier = Modifier.weight(1f))
                  }

                  // Instagram Advt
                  OutlinedCard(modifier = Modifier
                      .fillMaxWidth()
                      .padding(top = 18.dp, start = 12.dp, end = 12.dp)
                      .clickable {
                          /*TODO*/
                      }
                      .height(73.dp),
                      border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                      shape = RoundedCornerShape(17.dp),
                      colors = CardDefaults.cardColors(
                          containerColor = MaterialTheme.colorScheme.primaryContainer
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
                                  .padding(start = 18.dp, end = 18.dp, top = 11.5.dp, bottom = 0.dp), verticalAlignment = Alignment.CenterVertically
                          ) {

                              Column(modifier = Modifier) {

                                  Text(
                                      text = "Join our official community",
                                      color = MaterialTheme.colorScheme.primary,
                                      fontWeight = FontWeight.W600,
                                      fontSize = 17.sp
                                  )

                                  Text(
                                      text = "Follow us for latest new and updates",
                                      color = txtSubjectsColor,
                                      fontSize = 15.sp
                                  )
                              }

                              Spacer(modifier = Modifier.weight(1.5f))
                              
                              Image(painter = painterResource(id = R.drawable.insta_logo),
                                  contentDescription = null, modifier = Modifier.size(36.5.dp))

                          }
                      }
                  )
              }

        },
        bottomBar = {
            BottomNavigationBar(navController = navController, "Profile")
        }
    )

}