package com.kirandroid.stumate20.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.data.DocumentData
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.ui.theme.*
import com.kirandroid.stumate20.utils.LazyDocumentCard
import com.kirandroid.stumate20.utils.LoadingState
import com.kirandroid.stumate20.utils.UserPreferences
import com.kirandroid.stumate20.viewmodels.DocumentsViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDocumentsScreen(navController: NavController, subjectName: String, unitName: String, documentsViewModel: DocumentsViewModel = viewModel()) {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // State from homeScreenViewModel
    val state by documentsViewModel.loadingState.collectAsState()

    var _studentBatchID by remember { mutableStateOf("") }
    val studBatchID = dataStore.getStudentAcademicBatch.collectAsState(initial = "").value.toString()
    _studentBatchID = studBatchID

    // Calling the method from ViewModel to load Documents
    documentsViewModel.loadDocuments(studentBatchID = _studentBatchID,
        subjectName = subjectName, unitName = unitName)

    // Listening to subjects from ViewModel
    val documents by documentsViewModel.documents.observeAsState(initial = emptyList())

    val searchText = remember { mutableStateOf(TextFieldValue("")) }



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
                        "Document Deleted" -> snackbarHostState.showSnackbar("Document Deleted successfully!")

                    }
                }
            }
        }

        LoadingState.Status.FAILED -> {
            // Show some error occured snackbar

            coroutineScope.launch {
               // snackbarHostState.showSnackbar("${state.msg}")
            }
        }

        else -> {}
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = {
            
            Column(modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            content = {

                Box(modifier = Modifier
                    .background(yellowBgDocumentsScreen)
                    .height(190.dp)
                    .fillMaxWidth()) {
                    Column(
                        content = {

                            // Toolbar - Overlaying Back Icon and Subject Text
                            Row(modifier = Modifier.fillMaxWidth()) {

                                // Back Icon
                                IconButton(onClick = { navController.popBackStack() },
                                    modifier = Modifier.padding(top = 10.dp, end = 10.dp)) {
                                    Icon(imageVector = Icons.Filled.ArrowBack ,
                                        contentDescription = "Back Icon",
                                        tint = unfocusedAvatarColor,
                                        modifier = Modifier.size(29.dp))
                                }

                                // Subject Text
                                Text(text = subjectName,
                                    modifier = Modifier.padding(top = 17.dp),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }

                            // Search Bar
                            OutlinedTextField(modifier = Modifier
                                .padding(top = 55.dp)
                                .size(300.dp, 56.dp)
                                .align(CenterHorizontally)
                                .padding(top = 0.dp, bottom = 0.dp),
                                value = searchText.value,
                                shape = RoundedCornerShape(80.dp),
                                singleLine = true,
                                leadingIcon = {
                                          Icon(imageVector = Icons.Filled.Search, tint = MaterialTheme.colorScheme.primary,contentDescription = "",
                                              modifier = Modifier.padding(top = 4.dp, start = 8.dp))
                                },
                                onValueChange = { newText ->
                                    searchText.value = newText
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedLabelColor = textFieldHintColor,
                                    placeholderColor = unfocusedAvatarColor,
                                    containerColor = Color.White,
                                    textColor = unfocusedAvatarColor
                                ),
                                placeholder = { Text(text = "Search your Documents")})

                        })
                }


                // Unit Name
                Text(
                    text = unitName,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp))

                // Your Documents Text
                Text(text = "Your Documents",
                    modifier = Modifier.padding(top = 6.dp, start = 16.dp, bottom = 0.dp),
                    textAlign = TextAlign.Start,
                    color = txtSubjectsColor,
                    fontWeight = FontWeight.Medium, fontSize = 19.sp)

                // Checking whether documents list is not Empty
                if (documents.isNotEmpty()) {

                    DisplayDocumentsList(documents = documents,
                        searchText = searchText, navController = navController,
                        documentsViewModel = documentsViewModel, studentBatchID = _studentBatchID, subjectName = subjectName, unitName = unitName)

                }
                Log.d("DEBUG", "Display Documents Screen: ${documents.toString()}")


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
                        .fillMaxWidth()
                        .padding(top = 50.dp))


            })
            
        }
        
        
    ) 
    
    
}

@Composable
fun DisplayDocumentsList(documents: List<DocumentData>, subjectName: String, unitName: String,
                         studentBatchID: String,searchText: MutableState<TextFieldValue>, navController: NavController, documentsViewModel: DocumentsViewModel) {

    Column(modifier = Modifier.fillMaxWidth().height(300.dp)) {

        ConstraintLayout(modifier = Modifier.verticalScroll(rememberScrollState())) {
            // Displaying list of documents retrieved from the Database

            // Creating an empty Documents List to store the searched ones
            var filteredDocumentsList: List<DocumentData>

            // Final result list once the text is entered
           // val resultList = remember { mutableStateListOf<DocumentData>() }

            LazyColumn(
                contentPadding = PaddingValues(top = 10.dp),
                modifier = Modifier.fillMaxWidth().height(300.dp)
            ) {

                // Logic for searching Documents
                val searchedText = searchText.value.text
                filteredDocumentsList = if (searchedText.isEmpty()) {
                    documents
                } else {
                    val resultList = ArrayList<DocumentData>()
                    for (document in documents) {
                        if (document.documentName.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault()))) {
                            resultList.add(document)
                        }
                    }
                    resultList
                }

                items(filteredDocumentsList) { filteredDocument ->
                    LazyDocumentCard(
                        documentData = filteredDocument,
                        navController = navController,
                        documentsViewModel = documentsViewModel,
                        studentBatchID = studentBatchID,
                        subjectName = subjectName,
                        unitName = unitName
                    )
                }
            }
        }
    }

}
