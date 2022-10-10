package com.kirandroid.stumate20.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
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

    // Predicting the Year of Study of Student
    // Getting the Academic Batch, based on the admitted year
    // and making +1 with Current Year will be getting the current Academic year
    // E.g 2019 - 2023 Batch Consider [Current Year] - [Admitted Year] = 2022 - 2019 = (3+1) = 4th Year
    // For the next Year, 2023 - 2019 will be 4th Year

    // Getting the student Admitted Batch
    val admittedBatch = _studentBatchID.split("_").toTypedArray()[0]
    val yearOfAdmission = admittedBatch.split(" ").toTypedArray()[0]

    // calling the method to listen to subjects
    homeScreenViewModel.listenToSubjects(_studentBatchID)

    // Listening to subjects from ViewModel
    val subjects by homeScreenViewModel.subjects.observeAsState(initial = emptyList())

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Year of Study
    var isFirstYrEnabled by remember { mutableStateOf(false) }
    var isSecondYrEnabled by remember { mutableStateOf(false) }
    var isThirdYrEnabled by remember { mutableStateOf(false) }
    var isFourthYrEnabled by remember { mutableStateOf(false) }

    var textSelectedYear by remember { mutableStateOf("") }

    // Setting the year based on admitted Batch
    when(yearOfAdmission) {
        "2019" -> {
            isFourthYrEnabled = true
            textSelectedYear = "4th Year"
        }

        "2020" -> {
            isThirdYrEnabled = true
            textSelectedYear = "3rd Year"
        }
        "2021" -> {
            isSecondYrEnabled = true
            textSelectedYear = "2nd Year"
        }
        "2022" -> {
            isFirstYrEnabled = true
            textSelectedYear = "1st Year"
        }
    }

    // When User clicks on Sem Wise Buttons,
    // Below array list gets filtered
    var modifiedSemWiseSubjects = remember { mutableStateListOf<SubjectData>() }
    // Below is a boolean variable used to refresh the DisplaySubjects composable
    var refreshSubjectsGrid by remember { mutableStateOf(false) }

   // Calling the method to refresh the subjects list

    Scaffold(
        topBar = {
                 // Null
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { it ->

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
                                        isFirstYrEnabled = true

                                        // Disabling all other Buttons
                                        isSecondYrEnabled = false
                                        isThirdYrEnabled = false
                                        isFourthYrEnabled = false

                                        // storing the value in var
                                        textSelectedYear = "1st Year"
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
                                        isSecondYrEnabled = true

                                        // Disabling all other Buttons
                                        isFirstYrEnabled = false
                                        isThirdYrEnabled = false
                                        isFourthYrEnabled = false

                                        textSelectedYear = "2nd Year"

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
                                        isThirdYrEnabled = true

                                        // Disabling all other Buttons
                                        isSecondYrEnabled = false
                                        isFirstYrEnabled = false
                                        isFourthYrEnabled = false

                                        textSelectedYear = "3rd Year"

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
                                        isFourthYrEnabled = true

                                        // Disabling all other Buttons
                                        isSecondYrEnabled = false
                                        isThirdYrEnabled = false
                                        isFirstYrEnabled = false

                                        textSelectedYear = "4th Year"

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

                // Semesters List
                val firstSemChecked = remember { mutableStateOf(false) }
                val secondSemChecked = remember { mutableStateOf(false) }

                val textFirstSem = remember { mutableStateOf("Sem - 1") }
                val textSecondSem = remember { mutableStateOf("Sem - 2") }


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
                                firstSemChecked.value = true
                                secondSemChecked.value = false

                                modifiedSemWiseSubjects.clear()

                                modifiedSemWiseSubjects = subjects.filter {
                                    it.selectedSem.toString().contains(textFirstSem.value.toString(), ignoreCase = true)
                                }.toMutableStateList()

                                // Intimating to refresh the subjects list globally
                               refreshSubjectsGrid = true

                                Log.d("DEBUG", "In IconToggleButton First Sem: ${modifiedSemWiseSubjects.toString()}")

                            }, modifier = Modifier
                                .padding(start = 20.dp, top = 13.dp)
                                .size(85.dp, 33.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = if (firstSemChecked.value) Color.White else unFocusedChipContainerColor)
                                .padding(top = 2.dp, bottom = 5.dp)
                                ) {

                                Text(text = textFirstSem.value.toString(), fontSize = 15.sp,
                                    color = if (firstSemChecked.value) MaterialTheme.colorScheme.primary else unfocusedAvatarColor)

                            }

                            IconToggleButton(checked = secondSemChecked.value, onCheckedChange = {
                                secondSemChecked.value = true
                                firstSemChecked.value = false

                                modifiedSemWiseSubjects.clear()

                                modifiedSemWiseSubjects = subjects.filter {
                                    it.selectedSem.contains(textSecondSem.value.toString(), ignoreCase = true)
                                }.toMutableStateList()

                                // Intimating to refresh the subjects list globally
                                refreshSubjectsGrid = true

                                Log.d("DEBUG", "In IconToggleButton Second Sem: ${modifiedSemWiseSubjects.toString()}")

                            }, modifier = Modifier
                                .padding(start = 15.dp, top = 13.dp)
                                .size(85.dp, 33.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = if (secondSemChecked.value) Color.White else unFocusedChipContainerColor)
                                .padding(top = 2.dp, bottom = 5.dp)) {

                                Text(text = textSecondSem.value.toString(), fontSize = 15.sp,
                                    color = if (secondSemChecked.value) MaterialTheme.colorScheme.primary else unfocusedAvatarColor)
                            }
                        }
                    }

                  /*  if (refreshSubjectsGrid) {

                        Log.d("DEBUG", "Called in If condition: $modifiedSemWiseSubjects")

                        DisplayAllSubjects(subjects = modifiedSemWiseSubjects, navController = navController)
                    }*/


                    when(textSelectedYear) {

                        "1st Year" -> {
                            textFirstSem.value = "1st Sem"
                            textSecondSem.value = "2nd Sem"


                            // refresh the subjects list based on 1st Year
                            modifiedSemWiseSubjects = subjects.filter { subject ->
                                subject.selectedSem == "1st Semester" || subject.selectedSem == "2nd Semester"
                            }.toMutableStateList()

                            // Calling method to load all subjects
                          // DisplayAllSubjects(subjects = modifiedSemWiseSubjects, navController = navController)
                            refreshSubjectsGrid = true

                        }

                        "2nd Year" -> {
                            textFirstSem.value = "3rd Sem"
                            textSecondSem.value = "4th Sem"

                            // refresh the subjects list based on 1st Year
                            modifiedSemWiseSubjects = subjects.filter { subject ->
                                subject.selectedSem == "3rd Semester" || subject.selectedSem == "4th Semester"
                            }.toMutableStateList()

                            // Calling method to load all subjects
                          // DisplayAllSubjects(subjects = modifiedSemWiseSubjects, navController = navController)
                            refreshSubjectsGrid = true

                        }

                        "3rd Year" -> {
                            textFirstSem.value = "5th Sem"
                            textSecondSem.value = "6th Sem"

                            // refresh the subjects list based on 1st Year
                            modifiedSemWiseSubjects = subjects.filter { subject ->
                                subject.selectedSem == "5th Semester" || subject.selectedSem == "6th Semester"
                            }.toMutableStateList()

                            // Calling method to load all subjects
                          // DisplayAllSubjects(subjects = modifiedSemWiseSubjects, navController = navController)
                            refreshSubjectsGrid = true

                        }

                        "4th Year" -> {
                            textFirstSem.value = "7th Sem"
                            textSecondSem.value = "8th Sem"

                            // refresh the subjects list based on 1st Year
                            modifiedSemWiseSubjects = subjects.filter { subject ->
                                subject.selectedSem == "7th Semester" || subject.selectedSem == "8th Semester"
                            }.toMutableStateList()

                            // Calling method to load all subjects
                          // DisplayAllSubjects(subjects = modifiedSemWiseSubjects, navController = navController)
                            refreshSubjectsGrid = true

                        }
                    }

                    DisplayAllSubjects(subjects = modifiedSemWiseSubjects, navController = navController)


                    if (refreshSubjectsGrid) {

                        Log.d("DEBUG", "Called in If condition: $modifiedSemWiseSubjects")

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

            }
            
        })
}

@Composable
fun DisplayAllSubjects(subjects: List<SubjectData>, navController: NavController) {

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