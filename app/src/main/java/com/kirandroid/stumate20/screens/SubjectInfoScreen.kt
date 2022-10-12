package com.kirandroid.stumate20.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.*
import com.kirandroid.stumate20.utils.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectInfoScreen(navController: NavController, subjectName: String) {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),

                content = {

                    // Box to place text and Icon over Image
                    Box(modifier = Modifier
                        .fillMaxSize()) {

                        // Subject Info Background Image
                        Image(painter = painterResource(id = R.drawable.subject_bg),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Subject Background Image")

                        // Overlaying Back Icon and Subject Text
                        Column(modifier = Modifier.fillMaxWidth()) {

                            // Back Icon
                            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(top = 10.dp)) {
                                Icon(imageVector = Icons.Filled.ArrowBack ,
                                    contentDescription = "Back Icon",
                                    tint = unfocusedAvatarColor,
                                    modifier = Modifier.size(29.dp))
                            }

                            // Subject Text
                            Text(text = subjectName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                             fontWeight = FontWeight.Bold,
                            fontSize = 27.sp)
                        }
                    }

                    // Your Units Text
                    Text(text = "Your Units",
                    modifier = Modifier.padding(top = 35.dp, start = 20.dp, bottom = 0.dp),
                        textAlign = TextAlign.Start,
                    color = txtSubjectsColor,
                        fontWeight = FontWeight.Medium, fontSize = 19.sp)

                    // Row for creating two cards Unit - 1 and Unit - 2
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 27.dp)) {

                        // First Card
                        Card(modifier = Modifier
                            .size(190.dp, 63.dp)
                            .padding(start = 18.dp, end = 14.dp).clickable {
                                       // Pass Subject Name and Unit name to DisplayDocuments Screen
                                        val unitName = "Unit - 1"
                                       navController.navigate("display_documents_screen?subName=$subjectName&unitName=$unitName")
                            },
                             shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = redContainerColor
                            )
                            ) {

                            Row(modifier = Modifier.fillMaxSize()) {

                                Text(text = "UNIT - 1", textAlign = TextAlign.Start, fontSize = 17.sp,
                                color = unitTextColor,
                                    modifier = Modifier.padding(start = 20.dp).align(CenterVertically),
                                fontWeight = FontWeight.SemiBold)

                                Spacer(modifier = Modifier.weight(1.5f))

                                Icon(
                                    imageVector = Icons.Outlined.ArrowForwardIos,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .align(CenterVertically)
                                        .padding(top = 0.dp, end = 20.dp)
                                        .size(17.dp)
                                )



                            }


                        }

                        // Second Card
                        Card(modifier = Modifier
                            .size(180.dp, 63.dp)
                            .padding(end = 16.dp).clickable {
                                        // Pass Sub Name and Unit Name
                                val unitName = "Unit - 2"
                                navController.navigate("display_documents_screen?subName=$subjectName&unitName=$unitName")
                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = greenContainerColor
                            )) {

                            Row(modifier = Modifier.fillMaxSize()) {

                                Text(text = "UNIT - 2", textAlign = TextAlign.Start, fontSize = 17.sp,
                                    color = unitTextColor,
                                    modifier = Modifier.padding(start = 20.dp).align(CenterVertically),
                                    fontWeight = FontWeight.SemiBold)

                                Spacer(modifier = Modifier.weight(1.5f))

                                Icon(
                                    imageVector = Icons.Outlined.ArrowForwardIos,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .align(CenterVertically)
                                        .padding(top = 0.dp, end = 20.dp)
                                        .size(17.dp)
                                )



                            }


                        }


                    }

                    // Row for creating two cards Unit - 3 and Unit - 4
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {

                        // First Card
                        Card(modifier = Modifier
                            .size(190.dp, 63.dp)
                            .padding(start = 18.dp, end = 14.dp).clickable {
                                           // Pass Sub Name and Unit name
                                val unitName = "Unit - 3"
                                navController.navigate("display_documents_screen?subName=$subjectName&unitName=$unitName")
                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = violetContainerColor
                            )
                        ) {

                            Row(modifier = Modifier.fillMaxSize()) {

                                Text(text = "UNIT - 3", textAlign = TextAlign.Start, fontSize = 17.sp,
                                    color = unitTextColor,
                                    modifier = Modifier.padding(start = 20.dp).align(CenterVertically),
                                    fontWeight = FontWeight.SemiBold)

                                Spacer(modifier = Modifier.weight(1.5f))

                                Icon(
                                    imageVector = Icons.Outlined.ArrowForwardIos,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .align(CenterVertically)
                                        .padding(top = 0.dp, end = 20.dp)
                                        .size(17.dp)
                                )
                            }
                        }

                        // Second Card
                        Card(modifier = Modifier
                            .size(180.dp, 63.dp)
                            .padding(end = 16.dp).clickable {
                                        // Pass Sub Name and Unit Name
                                val unitName = "Unit - 4"
                                navController.navigate("display_documents_screen?subName=$subjectName&unitName=$unitName")
                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = yellowContainerColor
                            )) {

                            Row(modifier = Modifier.fillMaxSize()) {

                                Text(text = "UNIT - 4", textAlign = TextAlign.Start, fontSize = 17.sp,
                                    color = unitTextColor,
                                    modifier = Modifier.padding(start = 20.dp).align(CenterVertically),
                                    fontWeight = FontWeight.SemiBold)

                                Spacer(modifier = Modifier.weight(1.5f))

                                Icon(
                                    imageVector = Icons.Outlined.ArrowForwardIos,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .align(CenterVertically)
                                        .padding(top = 0.dp, end = 20.dp)
                                        .size(17.dp)
                                )
                            }
                        }
                    }

                    // Quote Text
                    Text(
                        text = "\"the secret of success is to do the common things uncommonly well\"",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF959595),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // Live it Up - Footer Card
                    Image(painter = painterResource(id = R.drawable.ending_board),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 80.dp).fillMaxWidth().height(150.dp))
                }
            )
        }
    )


}