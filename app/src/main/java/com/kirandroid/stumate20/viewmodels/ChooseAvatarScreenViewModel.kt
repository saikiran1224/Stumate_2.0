package com.kirandroid.stumate20.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChooseAvatarScreenViewModel: ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun updateAvatarPreference(phone: String, genderSelected: String) = viewModelScope.launch {

        try {
            loadingState.emit(LoadingState.LOADING)

            // Updating the `avatar` variable in firestore
            val db = Firebase.firestore
            db.collection("students_data")
                .whereEqualTo("phoneNumber", phone)
                .get()
                .addOnSuccessListener {

                    if(it.documents.size > 0) {

                        val studentData = it.documents[0].toObject<StudentData>()!!

                        // Updating the record by taking the key from studentData
                        db.collection("students_data").document(studentData.documentID)
                            .update("avatarType",genderSelected)
                            .addOnSuccessListener {

                                // Data got updated so navigate him to next Dashboard screen
                               Log.d("DEBUG", "Avatar preference updated successfully!")

                            }

                    }

                }.await()

            loadingState.emit(LoadingState.success())
        } catch (e: Exception) {
            Log.d("DEBUG", "Failure occurred in Choose Avatar ${e.localizedMessage!!.toString()}")
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}