package com.kirandroid.stumate20.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/*class SignUpScreenViewModelFactory(private val studentData: StudentData) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SignUpScreenViewModel(studentData) as T
}*/

class SignUpScreenViewModel(): ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun createUserWithEmailAndPassword(email: String, password: String, studentData: StudentData) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)

            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                // Since user got successfully authenticated we will be sending his details to
                // Cloud Firestore
                val db = Firebase.firestore
                // TODO: Try to create a sub collection of Students of College Wise
                db.collection("students_data").add(studentData).addOnSuccessListener {
                    Log.d("DEBUG", "Email Authentication and Data Insertion Successful")
                }
            }.await()

            loadingState.emit(LoadingState.LOADED)

        } catch (e: Exception) {
            Log.d("DEBUG", "Auth Fail ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

}