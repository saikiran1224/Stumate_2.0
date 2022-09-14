package com.kirandroid.stumate20.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.exception.DuplicateUserFoundException
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.RuntimeException
import java.lang.reflect.InvocationTargetException

/*class SignUpScreenViewModelFactory(private val studentData: StudentData) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SignUpScreenViewModel(studentData) as T
}*/

// For Email Auth Data Insertion
class SignUpScreenViewModel(): ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun createUserWithEmailAndPassword(email: String, password: String, studentData: StudentData) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)

            var documentID: String = ""

            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

                // Before registering the user, we need to check whether the user is already registered
                // with `Gmail` or his data is already there
                // We will sending the email to db and will be checking for the data
                val db = Firebase.firestore

                db.collection("students_data")
                    .whereEqualTo("emailID", email)
                    .get()
                    .addOnSuccessListener {

                        // checking the size of the result if it's greater than one
                        // That means user is already registered or registered with some provider
                        if(it.documents.size == 0) {

                            // Since it's a fresh user we will be proceeding
                            // We will be sending his details to Cloud Firestore
                            // TODO: Try to create a sub collection of Students of College Wise
                            val newStudentData = db.collection("students_data").document()
                            studentData.documentID = newStudentData.id

                            // Storing the same document ID in the `documentID` variable and sending back to composable
                            documentID = newStudentData.id
                            Log.d("DEBUG","In Sign up scren view model $documentID")

                            newStudentData.set(studentData).addOnSuccessListener {

                                sendDataToComposable(documentID, email)

                                Log.d("DEBUG", "Email Authentication and Data Insertion Successful")
                            }

                        } else {

                            throw DuplicateUserFoundException("Duplicate EmailAuth User found")
                            // Showing a snackbar that user is already registered and ask to Login
                            // navigate him back to Login Page
                            Log.d("DEBUG", "User is already registered with some other provider")

                        }
                    }

            }.await()

        } catch (duplicate: DuplicateUserFoundException) {
            loadingState.emit(LoadingState.error(duplicate.localizedMessage))
        }
        catch (e: Exception) {
            Log.d("DEBUG", "Auth Fail ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {

            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.success())

        } catch (e: Throwable) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    private fun sendDataToComposable(documentID: String, email:String) = viewModelScope.launch {

        Log.d("DEBUG","In out of signup screen viewmodel $documentID")
        loadingState.emit(LoadingState.success(studentID = documentID, studentEmailID = email))

    }
}