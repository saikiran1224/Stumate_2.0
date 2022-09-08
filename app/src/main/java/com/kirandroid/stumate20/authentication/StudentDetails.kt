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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
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


                      // Load Text Field - Name
                      var txtName by remember { mutableStateOf(TextFieldValue("")) }
                      OutlinedTextField(modifier = Modifier
                          .padding(start = 18.dp, top = 25.dp, end = 18.dp)
                          .fillMaxWidth(),
                          value = txtName,
                          textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
                          maxLines = 1,
                          shape = RoundedCornerShape(80.dp),
                          onValueChange = { newText ->
                              txtName = newText
                          },
                          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                          label = { Text("Name of Student", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
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
                          .padding(start = 18.dp, top = 12.dp, end = 18.dp)
                          .fillMaxWidth(),
                          value = txtPhone,
                          maxLines = 1,
                          textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
                          shape = RoundedCornerShape(80.dp),
                          onValueChange = { newText ->
                              txtPhone = newText
                          },
                          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),

                          colors = TextFieldDefaults.outlinedTextFieldColors(
                              focusedBorderColor = MaterialTheme.colorScheme.primary,
                              unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                              placeholderColor = MaterialTheme.colorScheme.secondary,
                              unfocusedLabelColor = textFieldHintColor,
                          ),
                          leadingIcon = {

                              OutlinedCard(
                                  border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onTertiary),
                                  colors = CardDefaults.cardColors(
                                      containerColor = MaterialTheme.colorScheme.primaryContainer
                                  ),
                                  modifier = Modifier.padding(start = 10.dp, end = 8.dp, top = 0.dp, bottom = 0.dp),
                                  shape = RoundedCornerShape(80.dp),
                              ) {
                                  Text(text = "   + 91   ", fontSize = 15.sp,
                                      color = MaterialTheme.colorScheme.primary,
                                      fontFamily = Cabin, fontWeight = FontWeight.Bold,
                                      modifier = Modifier.padding(start = 10.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
                                  textAlign = TextAlign.Center)
                              }

                          },
                          label = { Text("Phone Number", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
                          placeholder = { Text(text = "Enter Phone No")})


                      // Admitted Batch - Drop down
                      val batchesList = listOf("Select your Batch","2019 - 2023", "2020 - 2024", "2021 - 2025", "2022 - 2026")
                      SpinnerWidget(list = batchesList, label = "Academic Batch")

                      // College Name - Drop Down
                      val collegeNames = listOf("Select College","GMR Institute of Technology")
                      SpinnerWidget(list = collegeNames, label = "College Name")

                      // Department Name - Drop Down
                      val deptNames = listOf("Select Department","IT", "CSE", "ECE", "EEE", "MECH", "CHEM","CIVIL")
                      SpinnerWidget(list = deptNames, label = "Department")

                      // Section - Drop Down
                      val sectionNames = listOf("Select Section","A Section", "B Section", "C Section", "Other")
                      SpinnerWidget(list = sectionNames, label = "Section")

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
}