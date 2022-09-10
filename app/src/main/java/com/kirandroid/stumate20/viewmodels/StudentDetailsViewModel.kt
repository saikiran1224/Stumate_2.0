package com.kirandroid.stumate20.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StudentDetailsViewModel: ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun sendStudentDetailsToFirestore(studentData: StudentData) = viewModelScope.launch {

        try {

            loadingState.emit(LoadingState.LOADING)
            val db = Firebase.firestore
            // TODO: Try to create a sub collection of Students of College Wise
            db.collection("students_data")
                .add(studentData).await()
            loadingState.emit(LoadingState.LOADED)

        } catch (e: Exception) {

            loadingState.emit(LoadingState.error(e.localizedMessage))
            Log.d("DEBUG", "Fail ${e.localizedMessage}")
        }

    }

}