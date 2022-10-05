package com.kirandroid.stumate20.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.ui.theme.dashboardTopBgColor
import com.kirandroid.stumate20.ui.theme.txtSubjectsColor
import com.kirandroid.stumate20.ui.theme.unFocusedChipContainerColor
import com.kirandroid.stumate20.ui.theme.unfocusedAvatarColor
import com.kirandroid.stumate20.utils.UserPreferences
import com.kirandroid.stumate20.viewmodels.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllSubjectsScreen(navController: NavController, homeScreenViewModel: HomeScreenViewModel) {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var _studentBatchID by remember { mutableStateOf("") }
    val studBatchID = dataStore.getStudentAcademicBatch.collectAsState(initial = "").value.toString()
    _studentBatchID = studBatchID

    // calling the method to listen to subjects
    homeScreenViewModel.listenToSubjects(_studentBatchID)

    // Listening to subjects from ViewModel
    val subjects by homeScreenViewModel.subjects.observeAsState(initial = emptyList())

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 // Null
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = {
         
            Column(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {

                // For Background color purpose
                Box(modifier = Modifier
                    .background(dashboardTopBgColor)
                    .height(220.dp)
                    .fillMaxWidth()) {

                    Column(modifier = Modifier.fillMaxSize()) {

                        // Subjects Big Text
                        Text(text = "Subjects", fontWeight = FontWeight.SemiBold,
                            color = txtSubjectsColor,
                            fontSize = 36.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, top = 45.dp), textAlign = TextAlign.Start)

                        var isFirstYrEnabled by remember { mutableStateOf(false) }
                        var isSecondYrEnabled by remember { mutableStateOf(false) }
                        var isThirdYrEnabled by remember { mutableStateOf(false) }
                        var isFourthYrEnabled by remember { mutableStateOf(false) }

                        // Chips Design
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp).horizontalScroll(
                            rememberScrollState())) {

                            // creating Utils for Card
                            val focusedContainerColor = MaterialTheme.colorScheme.primary
                            val focusedTextColor = Color.White

                            val unfocusedTextColor = unfocusedAvatarColor
                            val unfocusedContainerColor = unFocusedChipContainerColor


                            // 1st Year
                            Card(
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 15.dp)
                                    .size(90.dp, height = 32.dp)
                                    .padding(start = 0.dp, end = 5.dp)
                                    .clickable {
                                        isFirstYrEnabled = !isFirstYrEnabled
                                    },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isFirstYrEnabled) focusedContainerColor else unfocusedContainerColor
                                ),
                                elevation = CardDefaults.cardElevation(
                                    focusedElevation = 0.dp,
                                    defaultElevation = 0.dp
                                ),

                            ) {

                                //Text inside Card
                                Text(
                                    text = " 1st Year ",
                                    fontSize = 17.sp,
                                    modifier = Modifier
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 2.dp,
                                            bottom = 5.dp
                                        )
                                        .wrapContentSize(Center)
                                        .fillMaxSize()
                                        .padding(bottom = 5.dp),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isFirstYrEnabled) focusedTextColor else unfocusedTextColor
                                )

                            }


                            // 2nd Year
                            Card(
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 15.dp)
                                    .size(90.dp, height = 32.dp)
                                    .padding(start = 0.dp, end = 5.dp)
                                    .clickable {
                                        isSecondYrEnabled = !isSecondYrEnabled
                                    },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSecondYrEnabled) focusedContainerColor else unfocusedContainerColor
                                ),
                                elevation = CardDefaults.cardElevation(
                                    focusedElevation = 0.dp,
                                    defaultElevation = 0.dp
                                ),

                                ) {

                                //Text inside Card
                                Text(
                                    text = " 2nd Year ",
                                    fontSize = 17.sp,
                                    modifier = Modifier
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 2.dp,
                                            bottom = 5.dp
                                        )
                                        .wrapContentSize(Center)
                                        .fillMaxSize()
                                        .padding(bottom = 5.dp),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSecondYrEnabled) focusedTextColor else unfocusedTextColor
                                )

                            }

                            // 3rd Year
                            Card(
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 15.dp)
                                    .size(90.dp, height = 32.dp)
                                    .padding(start = 0.dp, end = 5.dp)
                                    .clickable {
                                        isThirdYrEnabled = !isThirdYrEnabled
                                    },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isThirdYrEnabled) focusedContainerColor else unfocusedContainerColor
                                ),
                                elevation = CardDefaults.cardElevation(
                                    focusedElevation = 0.dp,
                                    defaultElevation = 0.dp
                                ),

                                ) {

                                //Text inside Card
                                Text(
                                    text = " 3rd Year ",
                                    fontSize = 17.sp,
                                    modifier = Modifier
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 2.dp,
                                            bottom = 5.dp
                                        )
                                        .wrapContentSize(Center)
                                        .fillMaxSize()
                                        .padding(bottom = 5.dp),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isThirdYrEnabled) focusedTextColor else unfocusedTextColor
                                )

                            }


                            // 4th Year
                            Card(
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 15.dp)
                                    .size(90.dp, height = 32.dp)
                                    .padding(start = 0.dp, end = 5.dp)
                                    .clickable {
                                        isFourthYrEnabled = !isFourthYrEnabled
                                    },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isFourthYrEnabled) focusedContainerColor else unfocusedContainerColor
                                ),
                                elevation = CardDefaults.cardElevation(
                                    focusedElevation = 0.dp,
                                    defaultElevation = 0.dp
                                ),

                                ) {

                                //Text inside Card
                                Text(
                                    text = " 4th Year ",
                                    fontSize = 17.sp,
                                    modifier = Modifier
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 2.dp,
                                            bottom = 5.dp
                                        )
                                        .wrapContentSize(Center)
                                        .fillMaxSize()
                                        .padding(bottom = 5.dp),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isFourthYrEnabled) focusedTextColor else unfocusedTextColor
                                )

                            }



                        }


                    }





                }



                Log.d("DEBUG", subjects.toString())
                
            }
            
        })


}