package com.kirandroid.stumate20.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.ui.theme.*
import com.kirandroid.stumate20.utils.*
import com.kirandroid.stumate20.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.Subject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "MutableCollectionMutableState", "SimpleDateFormat"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, homeScreenViewModel: HomeScreenViewModel = viewModel()) {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)

    var _studentBatchID by remember { mutableStateOf("") }
    val studBatchID = dataStore.getStudentAcademicBatch.collectAsState(initial = "").value.toString()
    _studentBatchID = studBatchID

    // State from homeScreenViewModel
    val state by homeScreenViewModel.loadingState.collectAsState()

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // student details
    var _studentName by remember { mutableStateOf("") }
    val studName = dataStore.getStudentName.collectAsState(initial = "").value.toString()
    _studentName = studName

    // For Time Stamp
    val currentTimeStamp = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())

    // Showing Snackbar based on the status received from the ViewModel
    when(state.status) {

        LoadingState.Status.RUNNING -> {
            // show loading progress bar
        }

        LoadingState.Status.SUCCESS -> {

               // show snackbar based on the submission of the document
               // make sures executes only one time
                LaunchedEffect(Unit) {
                    coroutineScope.launch {

                        when (state.homeScreenDocType) {
                            "Subject" -> snackbarHostState.showSnackbar("Subject created successfully!")
                            "Document" -> snackbarHostState.showSnackbar("Document uploaded successfully!")
                            "Loading Subs" -> {}
                            else -> snackbarHostState.showSnackbar("Some Error Occurred!")
                        }
                    }
                }
        }

        LoadingState.Status.FAILED -> {
            // Show some error occured snackbar

            coroutineScope.launch {
                snackbarHostState.showSnackbar("${state.msg}")
            }
        }

        else -> {}
    }

    // For Subject Dialog
    val showSubjectDialog = remember { mutableStateOf(false) }
    if(showSubjectDialog.value)
        SubjectDialog(value = "", setShowDialog = { showSubjectDialog.value = it }) {
            // Getting the batch ID from datastore and sent to ViewModel
            // initiate view model here to send data to Database

            // Setting some additional parameters like Timestamp and Submitted by

            it.timeStamp = currentTimeStamp.toString()
            it.submittedBy = _studentName.toString()

            homeScreenViewModel.sendSubjectData(subjectData = it, studentBatchID = _studentBatchID)
        }


    // calling the method to listen to subjects
    homeScreenViewModel.listenToSubjects(_studentBatchID)

    // Listening to subjects from ViewModel
    val subjects by homeScreenViewModel.subjects.observeAsState(initial = emptyList())

    // For Upload Document dialog
    val showDocumentDialog = remember { mutableStateOf(false) }

    if(showDocumentDialog.value) {

        // Since user wants to open document dialog we need to load the subjects

        DocumentDialog(subjectsData = subjects, setShowDialog = { showDocumentDialog.value = it }) {
            // Here we will get the data from Document dialog, so we need to call the viewmodel
            // to upload the file and the document details

            // Passing the documentData to ViewModel to upload the file to storage and subsequently to send data to
            // Cloud Firestore

            // Setting paramters like TimeStamp and Submitted By
            it.timeStamp = currentTimeStamp.toString()
            it.submittedBy = _studentName.toString()

            homeScreenViewModel.uploadDocumentToCloudStorage(documentData = it, studentBatchID = _studentBatchID)

            Log.d("DEBUG", "Document Dialog: ${it}")
        }


    }

    Scaffold(

        topBar = {
                 // NULL
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        content = {

            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = dashboardBgColor)
                .verticalScroll(rememberScrollState())) {

                Column(modifier = Modifier.fillMaxSize()) {

                    // Box for Create Subject and Documents Buttons
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
                                text = "Hey $_studentName !",
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
                                    // Showing Create Subject Dialog
                                    showSubjectDialog.value = true

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
                                            .padding(25.dp), verticalAlignment = Alignment.CenterVertically) {

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
                                    showDocumentDialog.value = true
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

                    // Outlined Card - Browse Subjects here and Image Column
                    OutlinedCard( modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 15.dp)
                        .fillMaxWidth(),
                        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.secondary),
                        colors = CardDefaults.cardColors(
                            containerColor = dashboardBgColor
                        )) {

                        // Show Browse your Subjects if there are no subjects
                        // else showing list of subjects
                        if (subjects.isNotEmpty())
                            ShowListOfSubjects(subjects = subjects, navController = navController)
                        else
                            ShowBrowseYourSubjects()

                    }

                    // Your Community
                    Text(text = "Your Community", fontSize = 22.sp,
                        color = txtSubjectsColor,
                        fontWeight = FontWeight.W700,
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
                                    modifier = Modifier
                                        .size(60.dp)
                                        .align(CenterVertically)
                                        .padding(start = 2.dp),
                                    border = BorderStroke(1.dp, classMatesIconColor),
                                    colors = CardDefaults.cardColors(
                                        containerColor = classMatesIconColor
                                    ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowForwardIos,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .align(CenterHorizontally)
                                            .padding(top = 14.dp)
                                    )
                                }
                            }
                        }
                    )

                    // Stumate advertisement
                    Image(painter = painterResource(id = R.drawable.stumate_ad),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp, start = 15.dp, end = 15.dp, bottom = 25.dp)
                            .clickable {
                                // TODO: Redirect to website

                            })


                    // Final Card
                    Image(painter = painterResource(id = R.drawable.ending_board),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 80.dp))
                }

            }
        },
        bottomBar = { BottomNavigationBar(navController = navController, "Home") }
    )
}

@Composable
fun ShowListOfSubjects(subjects: List<SubjectData>, navController: NavController) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Center)) {

        // Three individual columns of Subjects [ Subject Circle, Name of Subject ]

        val colors = listOf(Color(0xFFffd294), Color(0xFFb699ff), Color(0xFFff9090))

        // Horizontal Recycler View
        LazyRow(contentPadding = PaddingValues(start = 15.dp, end = 20.dp, top = 15.dp, bottom = 10.dp), ) {

         items(subjects.take(3)) {
                LazySubjectCard(subjectData = it, borderColor = colors.random(), navController = navController)
            }
        }

        // Show More Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, bottom = 15.dp, top = 0.dp).wrapContentSize(
                    Center).clickable {
                    // TODO: Open Subjects Screen
                    navController.navigate("all_subjects_screen")

                },
            colors = CardDefaults.cardColors(
                containerColor = unfocusedDialogTextFieldColor
            ),
            shape = RoundedCornerShape(21.dp)
        ) {

            Row(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Center)) {

                // Show More Text
                Text(text = "Show More", color = MaterialTheme.colorScheme.primary,
                fontSize = 15.sp, modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp), textAlign = TextAlign.Center)

                Icon(
                    imageVector = Icons.Outlined.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 0.dp, top = 5.5.dp, bottom = 5.dp)
                )


            }

        }

    }



}

@Composable
fun ShowBrowseYourSubjects() {

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Center)) {

        Image(
            painter = painterResource(id = R.drawable.subjects_icon),
            modifier = Modifier
                .padding(top = 11.dp)
                .align(CenterHorizontally),
            contentDescription = null
        )

        Text(
            text = "Browse your Subjects",
            modifier = Modifier
                .padding(top = 2.dp, bottom = 25.dp)
                .align(CenterHorizontally),
            fontSize = 17.sp,
            fontWeight = FontWeight.W500,
            color = txtBrowseYourSubs
        )

    }
}


