package com.kirandroid.stumate20.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences(private val context: Context) {

    // creating an instance of datastore
    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("stumate_20")

        // creating keys
        val IS_LOGIN = booleanPreferencesKey("is_login")
        val STUD_NAME = stringPreferencesKey("stud_name")
        val STUD_ID = stringPreferencesKey("stud_id")
        val STUD_EMAIL_KEY = stringPreferencesKey("stud_email")
        val STUD_GENDER = stringPreferencesKey("stud_gender")
    }

    // Email Getter and Setter
    val getIsLogin: Flow<Boolean?> = context.dataStoree.data
        .map { preferences ->
            preferences[IS_LOGIN]?: false
        }

    suspend fun setIsLogin(isLogin: Boolean) {
        context.dataStoree.edit { preferences ->
            preferences[IS_LOGIN] = isLogin
        }
    }


    // Student Name getter and setter
    val getStudentName: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[STUD_NAME] ?: "Student Name"
        }

    suspend fun setStudentName(studName: String) {
        context.dataStoree.edit { preferences ->
            preferences[STUD_NAME] = studName
        }
    }

    // Student ID getter and setter
    val getStudentID: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[STUD_ID] ?: "Student ID"
        }

    suspend fun setStudentID(studID: String) {
        context.dataStoree.edit { preferences ->
            preferences[STUD_ID] = studID
        }
    }

    // Student Email Getter and Setter
    val getStudentEmailID: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[STUD_EMAIL_KEY] ?: "Student Email ID"
        }

    suspend fun setStudentEmail(studEmailID: String) {
        context.dataStoree.edit { preferences ->
            preferences[STUD_EMAIL_KEY] = studEmailID
        }
    }

    // Student Gender Setter and Getter
    val getStudentGender: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[STUD_GENDER] ?: "Student Gender"
        }

    suspend fun setStudentGender(studentGender: String) {
        context.dataStoree.edit { preferences ->
            preferences[STUD_GENDER] = studentGender
        }
    }

}