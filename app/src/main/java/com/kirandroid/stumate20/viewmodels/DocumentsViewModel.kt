package com.kirandroid.stumate20.viewmodels

import android.util.Log
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

class DocumentsViewModel: ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)


    var documents: MutableLiveData<List<DocumentData>> = MutableLiveData<List<DocumentData>>()

    // function to load documents from Database
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

    // function to delete document from Database
    fun deleteDocument(documentData: DocumentData, studentBatchID: String, subjectName: String, unitName: String) = viewModelScope.launch {


        try {

            loadingState.emit(LoadingState.LOADING)

            // We need to first delete the document from Cloud Firestore
            val collegeName = studentBatchID.split("_").toTypedArray()[1]

            val db = Firebase.firestore
            db.collection(collegeName)
                .document(studentBatchID)
                .collection("documents_data")
                .document(subjectName)
                .collection(unitName).document(documentData.docID)
                .delete() // Deleting the Document with the help of Doc ID
                .addOnSuccessListener {

                    // Since the document successfully deleted
                    // Now we are deleting from the Cloud Storage
                    Firebase.storage.getReferenceFromUrl(documentData.documentDownloadUrl.toString())
                        .delete()
                        .addOnSuccessListener{

                            // File got successfully deleted from Cloud Storage
                            Log.d("DEBUG","Successfully deleted from Cloud Storage and Firestore")

                        }.addOnFailureListener {

                            Log.d("DEBUG","Error in deleting file from Cloud Storage")
                        }
                }.addOnFailureListener {
                    Log.d("DEBUG","Error in deleting file from Firestore")
                }

            loadingState.emit(LoadingState.success(homeScreenDocType = "Document Deleted"))


        } catch (e: Exception) {
            Log.d("DEBUG", "Failure occurred in Deleting Document ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))

        }


    }

}