package com.kirandroid.stumate20.viewmodels

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kirandroid.stumate20.data.DocumentData
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/*

Overall Schema
==============
College Name -> Batch ID -> { subjects_data, documents_data }


Schema for Subjects
=================
(subjects_data -> docID -> Data)


Schema for Documents
===================
(documents_data -> Sub Name -> Unit Name -> file)*/

class HomeScreenViewModel: ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    // Creating a LiveData variable
    var subjects: MutableLiveData<List<SubjectData>> = MutableLiveData<List<SubjectData>>()

    fun listenToSubjects(studentBatchID: String) = viewModelScope.launch  {

        try {

           loadingState.emit(LoadingState.LOADING)
            // Getting the college name from the studentBatchID
            val collegeName = studentBatchID.split("_").toTypedArray()[1]

            val db = Firebase.firestore
            // val subjectsRef = db.collection("subjects_data").document(collegeName).collection(studentBatchID)
            val subjectsRef = db.collection(collegeName).document(studentBatchID).collection("subjects_data")

            subjectsRef.get().addOnSuccessListener {

                // initialising an empty subjects List
                val subjectsList = ArrayList<SubjectData>()

                // loading all the documents to subjects list
                // below code block executes only when the `QuerySnapshot` is not null
                it?.let {
                    // iterating over all the documents
                    for (doc in it.documents) {
                            doc.toObject<SubjectData>()?.let { subject ->
                                // adding document which is not null
                                subjectsList.add(subject)
                            }
                    }

                    // passing the above retrieved subjects into LiveData object
                    subjects.value = subjectsList

                   // Log.d("DEBUG", "Loaded Subjects in ViewModel: ${subjects.value}")
                }

            }

          // loadingState.emit(LoadingState.success(homeScreenDocType = "Loading Subs"))

        } catch (e: Exception) {

            Log.d("DEBUG", "Failure occurred in Listening Subjects ${e.localizedMessage!!.toString()}")
           // loadingState.emit(LoadingState.error("Sorry, Unable to load. Please try again!"))

        }

    }

    fun sendSubjectData(subjectData: SubjectData, studentBatchID: String) = viewModelScope.launch {

        try {

            loadingState.emit(LoadingState.LOADING)

            // Getting the college name from the studentBatchID
            val collegeName = studentBatchID.split("_").toTypedArray()[1]

            val db = Firebase.firestore
            // We are creating a sub collection
           // val newSubData = db.collection("subject_and_docs_data").document(collegeName).collection(studentBatchID).document()
           val newSubData = db.collection(collegeName).document(studentBatchID).collection("subjects_data").document()
            subjectData.documentID = newSubData.id

            newSubData.set(subjectData).await()

            loadingState.emit(LoadingState.success(homeScreenDocType = "Subject"))

        } catch (e: Exception) {

            Log.d("DEBUG", "Failure occurred in Sending Subject Data ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))

        }
    }

    fun uploadDocumentToCloudStorage(documentData: DocumentData, studentBatchID: String) = viewModelScope.launch {

        // This function is for uploading document to Database
        // Here we need to perform two steps
        // Step 1: First we need to upload the file to Cloud Storage and obtain the `downloadUrl`
        // Step 2: Using the downloadUrl we need to send DocumentData to Firestore

        try {

            loadingState.emit(LoadingState.LOADING)

            // Implementation of Step 1 as follows
            val storage = Firebase.storage
            val documentDownloadUrl =
                documentData.documentUri?.let {
                    storage.reference.child(studentBatchID).child(documentData.subjectName).child(documentData.documentName)
                        .putFile(it.toUri()).await()
                }?.storage?.downloadUrl?.await()
            // Step - 1 Process Completed Obtained the corresponding file`s Download URL

            // Implementation of Step 2 as follows

            if(documentDownloadUrl.toString().isNotEmpty()) {
                // Getting the college name from the studentBatchID
                val collegeName = studentBatchID.split("_").toTypedArray()[1]
                val db = Firebase.firestore
                // We are creating a sub collection
                val newDocData =
                    db.collection(collegeName).document(studentBatchID).collection("documents_data")
                        .document(documentData.subjectName).collection(documentData.unitName)
                        .document()

                documentData.documentDownloadUrl = documentDownloadUrl.toString()
                documentData.docID = newDocData.id

                newDocData.set(documentData).await()

                Log.d("DEBUG", "Download Url for file: $documentDownloadUrl")

                loadingState.emit(LoadingState.success(homeScreenDocType = "Document"))
            }


        } catch (e: Exception) {
            Log.d("DEBUG", "Failure occurred in Choose Avatar ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }





    }

}