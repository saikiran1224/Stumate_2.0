package com.kirandroid.stumate20.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.kirandroid.stumate20.R

fun ChromeCustomTab(context: Context, URL: String) {

    val package_name = "com.android.chrome"

    val activity = (context as? Activity)

    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)
    builder.setShowTitle(true)

    // builder.setToolbarColor(ContextCompat.getColor(context, ))

    val customBuilder = builder.build()
    if (package_name != null) {
        // on below line if package name is not null
        // we are setting package name for our intent.
        customBuilder.intent.setPackage(package_name)

        // on below line we are calling launch url method
        // and passing url to it on below line.
        customBuilder.launchUrl(context, Uri.parse(URL))
    } else {
        // this method will be called if the
        // chrome is not present in user device.
        // in this case we are simply passing URL
        // within intent to open it.
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(URL))

        // on below line we are calling start
        // activity to start the activity.
        activity?.startActivity(i)
    }




}