package com.kirandroid.stumate20.viewmodels

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel: ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    // Creating a LiveData variable
    var subjects: MutableLiveData<List<SubjectData>> = MutableLiveData<List<SubjectData>>()

    fun listenToSubjects(studentBatchID: String)   {

        try {

          //  loadingState.emit(LoadingState.LOADING)
            // Getting the college name from the studentBatchID
            val collegeName = studentBatchID.split("_").toTypedArray()[1]

            val db = Firebase.firestore
            val subjectsRef = db.collection("subjects_data").document(collegeName).collection(studentBatchID)

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

                    Log.d("DEBUG", "In ViewModel: ${subjects.value}")
                }

            }

          //  loadingState.emit(LoadingState.success())

        } catch (e: Exception) {

            Log.d("DEBUG", "Failure occurred in Choose Avatar ${e.localizedMessage!!.toString()}")
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
            val newSubData = db.collection("subjects_data").document(collegeName).collection(studentBatchID).document()
            subjectData.documentID = newSubData.id

            newSubData.set(subjectData).await()

            loadingState.emit(LoadingState.success(homeScreenDocType = "Subject"))

        } catch (e: Exception) {

            Log.d("DEBUG", "Failure occurred in Choose Avatar ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))

        }

    }

    fun uploadDocumentToDB() = viewModelScope.launch {

        // TODO: This function is for uploading document to Database

    }

}