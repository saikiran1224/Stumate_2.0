package com.kirandroid.stumate20.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.ui.theme.unfocusedAvatarColor
import com.kirandroid.stumate20.utils.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SubjectInfoScreen() {

    // Context
    val context = LocalContext.current
    // Instantiating User Preferences class
    val dataStore = UserPreferences(context = context)

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            // Null
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),

                content = {

                    // Box to place text and Icon over Image
                    Box(modifier = Modifier.fillMaxSize()) {

                        // Subject Info Background Image
                        Image(painter = painterResource(id = R.drawable.subject_bg),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Subject Background Image")

                        // Overlaying Back Icon and Subject Text
                        Column(modifier = Modifier.fillMaxWidth()) {

                            // Back Icon
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.ArrowBack ,
                                    contentDescription = "Back Icon",
                                    tint = unfocusedAvatarColor,
                                    modifier = Modifier.size(32.dp))
                            }

                            // Subject Text
                            Text(text = "Internet of Things",
                                modifier = Modifier.fillMaxWidth().align(CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                             fontWeight = FontWeight.Bold,
                            fontSize = 35.sp)


                        }


                    }




                }
            )


        }
    )


}