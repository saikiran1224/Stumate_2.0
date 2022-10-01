package com.kirandroid.stumate20.utils

import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.data.SubjectData

data class LoadingState private constructor(val status: Status, val msg: String? = null, val studentID: String?= null,
                                            val studentEmailID: String? = null, val subjectsData: List<SubjectData>? = null,
                                            val homeScreenDocType: String? = null) {
    companion object {
        fun success(studentID: String? = null, studentEmailID: String? = null,
                    homeScreenDocType: String? = null, subjectsData: List<SubjectData>? = null) =
            LoadingState(Status.SUCCESS, studentID = studentID, studentEmailID = studentEmailID,
                homeScreenDocType = homeScreenDocType, subjectsData = subjectsData)

        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE,
    }
}

