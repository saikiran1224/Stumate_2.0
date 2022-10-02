package com.kirandroid.stumate20.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
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
import javax.security.auth.Subject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "MutableCollectionMutableState"
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


    // calling the method to listen to subjects
    homeScreenViewModel.listenToSubjects(_studentBatchID)

    // Listening to subjects from ViewModel
    val subjects by homeScreenViewModel.subjects.observeAsState(initial = emptyList())


    // State from homeScreenViewModel
    val state by homeScreenViewModel.loadingState.collectAsState()



    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // student details
    var _studentName by remember { mutableStateOf("") }
    val studName = dataStore.getStudentName.collectAsState(initial = "").value.toString()
    _studentName = studName



   /* // Loading all the subjects from the Database and storing it into the Datastore
    // This will gets called everytime when this composable is called
    // Getting the college name from the studentBatchID
    val collegeName = _studentBatchID.split("_").toTypedArray()[1]
    // initialising an empty subjects List
    val subjectsList by remember { mutableStateOf(ArrayList<SubjectData>()) }

    val db = Firebase.firestore
    val subjectsRef = db.collection("subjects_data").document(collegeName).collection(_studentBatchID)

    subjectsRef.get().addOnSuccessListener {

        // loading all the documents to subjects list
        for (doc in it.documents) {
            subjectsList.add(doc.toObject<SubjectData>()!!)
        }

        // setting the subjectsList into Datastore
        coroutineScope.launch {
            dataStore.setStudentGender("Hi")
        }

        Log.d("DEBUG", "Data: ${subjectsList.toString()}")
    }*/


    // Showing Snackbar based on the status received from the ViewModel
    when(state.status) {

        LoadingState.Status.RUNNING -> {
            // show loading progress bar
        }

        LoadingState.Status.SUCCESS -> {
                // TODO: Check that SUCCESS is emitted from ether Subject or Document Creation
                if(state.homeScreenDocType == "Loaded Subjects") {

                    Log.d("DEBUG", state.subjectsData.toString())

                } else {

                   // show snackbar based on the submission of the document
                    coroutineScope.launch {

                        when (state.homeScreenDocType) {
                            "Subject" -> snackbarHostState.showSnackbar("Subject created successfully!")
                            "Document" -> snackbarHostState.showSnackbar("Document uploaded successfully!")
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
            homeScreenViewModel.sendSubjectData(subjectData = it, studentBatchID = _studentBatchID)
        }


    // For Upload Document dialog
    val showDocumentDialog = remember { mutableStateOf(false) }

    if(showDocumentDialog.value) {



        // Since user wants to open document dialog we need to load the subjects

        Log.d("DEBUG", "Home Screen: ${subjects.toString()}")

        DocumentDialog(subjectsData = subjects, setShowDialog = { showDocumentDialog.value = it }) {

            // Here we will get the data from Document dialog, so we need to call the viewmodel
            // to upload the file and the document details

            Log.d("DEBUG", "Document Dialog: $it")
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
                            modifier = Modifier
                                .padding(top = 11.dp)
                                .align(CenterHorizontally),
                            contentDescription = null)

                        Text(text = "Browse your Subjects",
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 25.dp)
                                .align(CenterHorizontally),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W500,
                            color = txtBrowseYourSubs
                        )

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
                            .padding(bottom = 85.dp))
                }

            }
        },
        bottomBar = { BottomNavigationBar(navController = navController, "Home") }
    )
}

@Composable
fun ShowDialogWithSubjects(retrievedsubjectsList: List<SubjectData>, showDocumentDialogValue: Boolean) {


}


