package com.kirandroid.stumate20.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.navigation.Screen
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.utils.ImageItem
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSnapperApi::class)
@Composable
fun ChooseAvatarScreen(navController: NavController, studentName: String?) {

    // For Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick =
                    { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack , contentDescription = "Back Icon")
                    }
                },
            )
        },
        content = { innerPadding ->

            Box(modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()) {

                Column(modifier = Modifier
                    .fillMaxSize() ) {

                    // Text - Hi $studentName
                    Text(text = buildAnnotatedString {

                        append("Hi!")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(" $studentName")
                        }
                        append(", Personalize your look")},
                        modifier = Modifier
                            .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center)

                    // Text - Choose from fun avatars
                    Text(text = "Choose from fun avatars",
                    fontWeight = FontWeight.Thin,
                    fontFamily = Cabin,
                    fontSize = 19.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 90.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )


                    LazyRow(
                        state = lazyListState,
                        flingBehavior = rememberSnapperFlingBehavior(lazyListState),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // content
                        items(5) { index ->
                            ImageItem(
                                text = "${index+1}",
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(80.dp)
                            )
                        }
                    }



                }

                // Button - Continue
                Button(
                    onClick = {

                        /* TODO */

                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(25.dp)
                        .fillMaxWidth()
                        .size(height = 50.dp, width = 350.dp),
                    shape = CircleShape,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        disabledElevation = 2.dp
                    ), // TODO: Enable the button after doing appropriate validation
                    enabled = true) {

                    Text(text = "Continue", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )
                }

            }

        }

    )


}