@file:OptIn(ExperimentalMaterial3Api::class)

package com.kirandroid.stumate20.authentication


import android.util.Log
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.ui.theme.textFieldHintColor
import com.kirandroid.stumate20.utils.LoadingState
import com.kirandroid.stumate20.viewmodels.SignUpScreenViewModel
import com.kirandroid.stumate20.viewmodels.StudentDetailsViewModel


@Composable
fun StudentDetails(navController: NavController, authType: String?, googleEmailID: String?,
                   viewModel: SignUpScreenViewModel, studentDetailsViewModel: StudentDetailsViewModel) {

    val state by viewModel.loadingState.collectAsState()
    val student_viewmodel_state by studentDetailsViewModel.loadingState.collectAsState()

    // Lists Data
    val batchesList = listOf("Select your Batch","2019 - 2023", "2020 - 2024", "2021 - 2025", "2022 - 2026")
    val collegeNames = listOf("Select College","GMR Institute of Technology")
    val deptNames = listOf("Select Department","IT", "CSE", "ECE", "EEE", "MECH", "CHEM","CIVIL")
    val sectionNames = listOf("Select Section","A Section", "B Section", "C Section", "Other")

    // Form Data
    var txtName by remember { mutableStateOf(TextFieldValue("")) }

    var emailID by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var txtPhone by remember { mutableStateOf(TextFieldValue("")) }
    var selectedBatch by rememberSaveable { mutableStateOf(batchesList[0]) }
    var selectedCollege by rememberSaveable { mutableStateOf(collegeNames[0])}
    var selectedDepartment by rememberSaveable { mutableStateOf(deptNames[0])}
    var selectedSection by rememberSaveable { mutableStateOf(sectionNames[0])}


    // To check the status whether user has successfully authenticated with Email
    if (state.status == LoadingState.Status.SUCCESS) {



        // once authenticated successfully we need to extract the values from `OutlinedTextField` and
        // need to send it to Cloud Firestore
        val studentData = StudentData(
            name = txtName.text,
            emailID = emailID,
            authType = authType.toString(),
            phoneNumber = txtPhone.text,
            academicBatch = selectedBatch,
            collegeName = selectedCollege,
            deptName = selectedDepartment,
            sectionName = selectedSection
        )

        // passing the Data object to StudentDetailsViewModel for sending Data
        studentDetailsViewModel.sendStudentDetailsToFirestore(studentData)
    }

    // TO check whether the details are successfully sent to Cloud Firestore
    if(student_viewmodel_state.status == LoadingState.Status.SUCCESS) {

        Log.d("DEBUG", "Called now")
        // sending user to select Avatar
        // Passing paramter of student Name for the purpose of greeting
        navController.navigate("choose_avatar/${txtName.text}")
    }

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
                       append("We need more info about you")},
                       modifier = Modifier
                           .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                           .align(Alignment.CenterHorizontally),
                       color = Color.Black,
                       fontSize = 22.sp,
                       fontWeight = FontWeight.W500,
                       textAlign = TextAlign.Center)

                      // Load Text Field - Name
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

                      // Displaying both the Email and Password based on the condition
                      if (authType == "Email")
                          DisplayEmailIDAndPassword(emailID = emailID, onEmailChange = { emailID = it},
                          password = password, onPasswordChange = { password = it})

                      // Load Text Field -  Phone Number
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
                       var expanded_1 by remember { mutableStateOf(false) }
                       ExposedDropdownMenuBox(expanded = expanded_1, onExpandedChange = { expanded_1 = !expanded_1 },
                           modifier = Modifier
                               .padding(start = 18.dp, top = 17.dp, end = 18.dp)
                               .fillMaxWidth()
                       ) {
                           OutlinedTextField(
                               value = selectedBatch, readOnly = true,
                               textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                   fontWeight = FontWeight.W300, fontSize = 15.sp),
                               singleLine = true, shape = RoundedCornerShape(80.dp),
                               modifier = Modifier.fillMaxWidth(), maxLines = 1,
                               onValueChange = { selectedBatch = it }, label = { Text(text = "Admitted Batch") },
                               trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_1) },
                               colors = TextFieldDefaults.outlinedTextFieldColors(
                                   focusedBorderColor = MaterialTheme.colorScheme.primary,
                                   unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                   placeholderColor = MaterialTheme.colorScheme.secondary,
                                   unfocusedLabelColor = textFieldHintColor,
                               ),
                           )
                           // filter options based on text field value

                           ExposedDropdownMenu(expanded = expanded_1, onDismissRequest = { expanded_1 = false },) {
                               batchesList.forEach { selectionOption ->
                                   DropdownMenuItem(
                                       onClick = { selectedBatch = selectionOption
                                           expanded_1 = false
                                       },
                                       text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })
                               }
                           }
                       }

                      // College Name - Drop Down
                      var expanded_2 by remember { mutableStateOf(false) }
                       ExposedDropdownMenuBox(expanded = expanded_2, onExpandedChange = { expanded_2 = !expanded_2 },
                           modifier = Modifier
                               .padding(start = 18.dp, top = 17.dp, end = 18.dp)
                               .fillMaxWidth()
                       ) {
                           OutlinedTextField(
                               value = selectedCollege, readOnly = true,
                               textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                   fontWeight = FontWeight.W300, fontSize = 15.sp),
                               singleLine = true, shape = RoundedCornerShape(80.dp),
                               modifier = Modifier.fillMaxWidth(), maxLines = 1,
                               onValueChange = { selectedCollege = it }, label = { Text(text = "College Name") },
                               trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_2) },
                               colors = TextFieldDefaults.outlinedTextFieldColors(
                                   focusedBorderColor = MaterialTheme.colorScheme.primary,
                                   unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                   placeholderColor = MaterialTheme.colorScheme.secondary,
                                   unfocusedLabelColor = textFieldHintColor,
                               ),
                           )
                           // filter options based on text field value

                           ExposedDropdownMenu(expanded = expanded_2, onDismissRequest = { expanded_2 = false },) {
                               collegeNames.forEach { selectionOption ->
                                   DropdownMenuItem(
                                       onClick = { selectedCollege = selectionOption
                                           expanded_2 = false
                                       },
                                       text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })
                               }
                           }
                       }

                      // Department Name - Drop Down
                      var expanded_3 by remember { mutableStateOf(false)}
                       ExposedDropdownMenuBox(expanded = expanded_3, onExpandedChange = { expanded_3 = !expanded_3 },
                           modifier = Modifier
                               .padding(start = 18.dp, top = 17.dp, end = 18.dp)
                               .fillMaxWidth()
                       ) {
                           OutlinedTextField(
                               value = selectedDepartment, readOnly = true,
                               textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                   fontWeight = FontWeight.W300, fontSize = 15.sp),
                               singleLine = true, shape = RoundedCornerShape(80.dp),
                               modifier = Modifier.fillMaxWidth(), maxLines = 1,
                               onValueChange = { selectedDepartment = it }, label = { Text(text = "Department") },
                               trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_3) },
                               colors = TextFieldDefaults.outlinedTextFieldColors(
                                   focusedBorderColor = MaterialTheme.colorScheme.primary,
                                   unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                   placeholderColor = MaterialTheme.colorScheme.secondary,
                                   unfocusedLabelColor = textFieldHintColor,
                               ),
                           )
                           // filter options based on text field value

                           ExposedDropdownMenu(expanded = expanded_3, onDismissRequest = { expanded_3 = false },) {
                               deptNames.forEach { selectionOption ->
                                   DropdownMenuItem(
                                       onClick = { selectedDepartment = selectionOption
                                           expanded_3 = false
                                       },
                                       text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })
                               }
                           }
                       }

                      // Section - Drop Down
                      var expanded_4 by remember { mutableStateOf(false) }
                       ExposedDropdownMenuBox(expanded = expanded_4, onExpandedChange = { expanded_4 = !expanded_4 },
                           modifier = Modifier
                               .padding(start = 18.dp, top = 17.dp, end = 18.dp)
                               .fillMaxWidth()
                       ) {
                           OutlinedTextField(
                               value = selectedSection, readOnly = true,
                               textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                   fontWeight = FontWeight.W300, fontSize = 15.sp),
                               singleLine = true, shape = RoundedCornerShape(80.dp),
                               modifier = Modifier.fillMaxWidth(), maxLines = 1,
                               onValueChange = { selectedSection = it }, label = { Text(text = "Section") },
                               trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_4) },
                               colors = TextFieldDefaults.outlinedTextFieldColors(
                                   focusedBorderColor = MaterialTheme.colorScheme.primary,
                                   unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                   placeholderColor = MaterialTheme.colorScheme.secondary,
                                   unfocusedLabelColor = textFieldHintColor,
                               ),
                           )
                           // filter options based on text field value

                           ExposedDropdownMenu(expanded = expanded_4, onDismissRequest = { expanded_4 = false },) {
                               sectionNames.forEach { selectionOption ->
                                   DropdownMenuItem(
                                       onClick = { selectedSection = selectionOption
                                           expanded_4 = false
                                       },
                                       text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })
                               }
                           }
                       }


                   Button(
                       onClick = {

                         // TODO: Check the type of authType variable based on that perform the signUp process and
                         // TODO: sending data to Cloud Firestore

                         Log.d("DEBUG", "$selectedBatch is selected $selectedCollege and $selectedDepartment and $selectedSection")
                         // Need to use the viewModel to perform the Sign Up Process

                         if (authType != "Google") {
                             viewModel.createUserWithEmailAndPassword(
                                 email = emailID,
                                 password = password
                             )
                         } else {
                             // directly proceed to send the details to the Firestore

                             // once authenticated successfully we need to extract the values from `OutlinedTextField` and
                             // need to send it to Cloud Firestore
                             val studentData = StudentData(
                                 name = txtName.text,
                                 emailID = googleEmailID.toString(),
                                 authType = authType.toString(),
                                 phoneNumber = txtPhone.text,
                                 academicBatch = selectedBatch,
                                 collegeName = selectedCollege,
                                 deptName = selectedDepartment,
                                 sectionName = selectedSection
                             )

                             // passing the Data object to StudentDetailsViewModel for sending Data
                             studentDetailsViewModel.sendStudentDetailsToFirestore(studentData)
                         }

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
                       ), // TODO: Enable the button after doing appropriate validation
                       enabled = true) {

                       Text(text = "Continue", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )
                   }
                  }
               }
       }
   )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayEmailIDAndPassword(emailID: String,
                              onEmailChange: (String) -> Unit,
                              password: String,
                              onPasswordChange: (String) -> Unit) {

    // Load Text Field - Email ID

    OutlinedTextField(modifier = Modifier
        .padding(start = 18.dp, top = 12.dp, end = 18.dp)
        .fillMaxWidth(),
        value = emailID,
        textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
        maxLines = 1,
        shape = RoundedCornerShape(80.dp),
        onValueChange = onEmailChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text("Enter your Email ID", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
        colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                placeholderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = textFieldHintColor,

        ),
        placeholder = { Text(text = "Enter your Email ")})


    // Load Text Field - Password

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(modifier = Modifier
        .padding(start = 18.dp, top = 12.dp, end = 18.dp)
        .fillMaxWidth(),
        value = password,
        textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
        maxLines = 1,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(80.dp),
        onValueChange = onPasswordChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text("Enter your Password", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            placeholderColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = textFieldHintColor,
        ),
        placeholder = { Text(text = "Enter your Password ")},
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        })
}
