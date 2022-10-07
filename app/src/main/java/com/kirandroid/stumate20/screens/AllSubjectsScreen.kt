package com.kirandroid.stumate20.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowColumn
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.ui.theme.*
import com.kirandroid.stumate20.utils.LazySubjectCard
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

    val firstSemChecked = remember { mutableStateOf(false) }
    val secondSemChecked = remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
                 // Null
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = {
         
            Column(modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {

                // For Background color purpose
                Box(modifier = Modifier
                    .background(dashboardTopBgColor)
                    .height(243.dp)
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
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                            .horizontalScroll(rememberScrollState())) {

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
                        } // Row End

                    }
                }

                // Created to keep negative padding for the below card
                val topPadding = 70.dp
                // Card to display Sems and Subjects
                OutlinedCard( modifier = Modifier
                    .offset(y = -topPadding)
                    .padding(start = 15.dp, end = 15.dp, top = 0.dp)
                    .fillMaxWidth(),
                    border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.secondary),
                    colors = CardDefaults.cardColors(
                        containerColor = dashboardBgColor
                    ), shape = RoundedCornerShape(15.dp)
                ) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .background(MaterialTheme.colorScheme.primary)) {

                        Row(modifier = Modifier.fillMaxWidth()) {

                            // Creating a toggle Button
                            IconToggleButton(checked = firstSemChecked.value, onCheckedChange = {
                                firstSemChecked.value = !firstSemChecked.value
                                secondSemChecked.value = false
                            }, modifier = Modifier
                                .padding(start = 20.dp, top = 13.dp)
                                .size(85.dp, 33.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = if (firstSemChecked.value) Color.White else unFocusedChipContainerColor)
                                .padding(top = 2.dp, bottom = 5.dp)
                                ) {

                                Text(text = "SEM - 1", fontSize = 15.sp,
                                    color = if (firstSemChecked.value) MaterialTheme.colorScheme.primary else unfocusedAvatarColor)

                            }

                            IconToggleButton(checked = secondSemChecked.value, onCheckedChange = {
                                secondSemChecked.value = !secondSemChecked.value

                                firstSemChecked.value = false

                            }, modifier = Modifier
                                .padding(start = 15.dp, top = 13.dp)
                                .size(85.dp, 33.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = if (secondSemChecked.value) Color.White else unFocusedChipContainerColor)
                                .padding(top = 2.dp, bottom = 5.dp)
                               ) {

                                Text(text = "SEM - 2", fontSize = 15.sp,
                                    color = if (secondSemChecked.value) MaterialTheme.colorScheme.primary else unfocusedAvatarColor)
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .background(Color.White)) {

                        val colors = listOf(Color(0xFFffd294), Color(0xFFb699ff), Color(0xFFff9090))

                        // TODO: Create a Grid Layout and display all the subjects

                        ConstraintLayout(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier
                                .height(350.dp)
                                .padding(top = 20.dp)

                            ) {
                                items(subjects.size) { index ->
                                    LazySubjectCard(subjectData = subjects[index], borderColor = colors.random(), navController = navController)
                                }
                            }


                            
                        }
                    }
                }

                val topPaddingForText = 40.dp
                // Quote Text
                Text(
                    text = "\"the secret of success is to do the common things uncommonly well\"",
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -topPaddingForText)
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF959595),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                // Live it Up - Footer Card
                Image(painter = painterResource(id = R.drawable.ending_board),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth())

                Log.d("DEBUG", subjects.toString())
            }
            
        })


}