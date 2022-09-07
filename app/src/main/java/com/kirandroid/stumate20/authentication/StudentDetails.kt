@file:OptIn(ExperimentalMaterial3Api::class)

package com.kirandroid.stumate20.authentication

import android.widget.Spinner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.R
import com.kirandroid.stumate20.SpinnerWidget
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.ui.theme.textFieldHintColor

@Composable
fun StudentDetails(navController: NavController) {

   Scaffold(
       modifier = Modifier
           .fillMaxSize()
           .background(MaterialTheme.colorScheme.background),

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
               .verticalScroll(rememberScrollState())) {

               Column(modifier = Modifier.fillMaxSize()) {

                   // Text - Hey...
                   Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Hey! ")
                        }
                       append("We need more info about your class")},
                       modifier = Modifier
                           .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                           .align(Alignment.CenterHorizontally),
                       color = Color.Black,
                       fontSize = 22.sp,
                       fontWeight = FontWeight.W500,
                       textAlign = TextAlign.Center)

                  OutlinedCard(
                      modifier = Modifier
                          .padding(start = 20.dp, end = 20.dp, top = 15.dp)
                          .fillMaxSize()
                          .background(MaterialTheme.colorScheme.background),
                  border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.secondary),
                  colors = CardDefaults.cardColors(
                      containerColor = MaterialTheme.colorScheme.background
                  )) {

                      // Load Text Field - Name
                      var txtName by remember { mutableStateOf(TextFieldValue("")) }
                      OutlinedTextField(modifier = Modifier
                          .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                          .fillMaxWidth(),
                          value = txtName,
                          maxLines = 1,
                          onValueChange = { newText ->
                              txtName = newText
                          },
                          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                          label = { Text("Enter your Full Name")},
                          colors = TextFieldDefaults.outlinedTextFieldColors(
                              focusedBorderColor = MaterialTheme.colorScheme.primary,
                              unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                              unfocusedLabelColor = textFieldHintColor,
                              placeholderColor = MaterialTheme.colorScheme.secondary
                          ),
                          placeholder = { Text(text = "Enter your Name")})

                      // Load Text Field -  Phone Number
                      var txtPhone by remember { mutableStateOf(TextFieldValue("")) }
                      OutlinedTextField(modifier = Modifier
                          .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                          .fillMaxWidth(),
                          value = txtPhone,
                          maxLines = 1,
                          onValueChange = { newText ->
                              txtPhone = newText
                          },
                          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                          label = { Text("Enter your Phone Number")},
                          colors = TextFieldDefaults.outlinedTextFieldColors(
                              focusedBorderColor = MaterialTheme.colorScheme.primary,
                              unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                              placeholderColor = MaterialTheme.colorScheme.secondary,
                              unfocusedLabelColor = textFieldHintColor,
                          ),
                          placeholder = { Text(text = "Enter your Phone Number")})


                      // Admitted Batch - Drop down
                      val batchesList = listOf("2019 - 2023", "2020 - 2024", "2021 - 2025", "2022 - 2026")
                      SpinnerWidget(list = batchesList, label = "Choose your Batch")

                      // College Name - Drop Down
                      val collegeNames = listOf("GMR Institute of Technology")
                      SpinnerWidget(list = collegeNames, label = "Choose College Name")

                      // Department Name - Drop Down
                      val deptNames = listOf("IT", "CSE", "ECE", "EEE", "MECH", "CHEM","CIVIL")
                      SpinnerWidget(list = deptNames, label = "Choose Department")

                      // Section - Drop Down
                      val sectionNames = listOf("A Section", "B Section", "C Section", "Other")
                      SpinnerWidget(list = sectionNames, label = "Choose Section")

                      // Load Text Field -  Registration Number
                      var txtJNTUNo by remember { mutableStateOf(TextFieldValue("")) }
                      OutlinedTextField(modifier = Modifier
                          .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 20.dp)
                          .fillMaxWidth(),
                          value = txtJNTUNo,
                          maxLines = 1,
                          onValueChange = { newText ->
                              txtJNTUNo = newText
                          },
                          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                          label = { Text("Enter Registration Number")},
                          colors = TextFieldDefaults.outlinedTextFieldColors(
                              focusedBorderColor = MaterialTheme.colorScheme.primary,
                              unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                              placeholderColor = MaterialTheme.colorScheme.secondary,
                              unfocusedLabelColor = textFieldHintColor,
                          ),
                          placeholder = { Text(text = "Eg. 18341A1224")})






                  }

                   Button(
                       onClick = {

                           // Check whether user has entered email ID or not
                           navController.navigate("take_student_details")
                       },
                       modifier = Modifier
                           .align(Alignment.CenterHorizontally)
                           .padding(25.dp)
                           .fillMaxWidth()
                           .size(height = 50.dp, width = 350.dp),
                       shape = CircleShape,
                       elevation = ButtonDefaults.buttonElevation(
                           defaultElevation = 8.dp,
                           disabledElevation = 2.dp
                       ),
                       enabled = true) {

                       Text(text = "Continue", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )
                   }


               }




           }

       }
   )


    @Composable
    fun AdmittedBatch() {


    }

}