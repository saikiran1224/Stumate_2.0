package com.kirandroid.stumate20.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.data.DocumentData
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DocumentsViewModel: ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    var documents: MutableLiveData<List<DocumentData>> = MutableLiveData<List<DocumentData>>()

    fun loadDocuments(studentBatchID: String, subjectName: String, unitName: String) = viewModelScope.launch {

        try {

            loadingState.emit(LoadingState.LOADING)

            // Getting the college name from the studentBatchID
            val collegeName = studentBatchID.split("_").toTypedArray()[1]

            val db = Firebase.firestore

            val documentsRef = db.collection(collegeName)
                .document(studentBatchID)
                .collection("documents_data")
                .document(subjectName)
                .collection(unitName)

            documentsRef.get().addOnSuccessListener {

                // initialising an empty documents list
                val documentsList = ArrayList<DocumentData>()

                // loading all the documents to subjects list
                // below code block executes only when the `QuerySnapshot` is not null
                it?.let {
                    // iterating over all the documents
                    for (doc in it.documents) {
                        doc.toObject<DocumentData>()?.let { document ->
                            // adding document which is not null
                            documentsList.add(document)
                        }
                    }

                    // passing the above retrieved subjects into LiveData object
                    documents.value = documentsList

                    Log.d("DEBUG", "In ViewModel: ${documentsList}")
                }
            }

            loadingState.emit(LoadingState.success())

        } catch (e: Exception) {

            Log.d("DEBUG", "Failure occurred in Retrieving Documents for Subject ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))

        }


    }

}