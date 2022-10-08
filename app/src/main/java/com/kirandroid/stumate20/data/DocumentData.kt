package com.kirandroid.stumate20.data

import android.net.Uri

data class DocumentData (
    var documentName: String = "",
    var subjectName: String = "",
    var unitName: String = "",
    var docID: String = "",
    var documentDownloadUrl: String = "",
    var documentUri: String? = null,

    // TODO: Need to update the fields in Database
    var submittedBy: String = "",
    var timeStamp:String = ""
)