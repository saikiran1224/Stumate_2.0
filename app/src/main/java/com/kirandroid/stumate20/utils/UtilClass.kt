package com.kirandroid.stumate20.utils

import com.kirandroid.stumate20.data.StudentData

data class LoadingState private constructor(val status: Status, val msg: String? = null, val studentID: String?= null, val studentEmailID: String? = null) {
    companion object {
        fun success(studentID: String? = null, studentEmailID: String? = null) = LoadingState(Status.SUCCESS, studentID = studentID, studentEmailID = studentEmailID)
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

