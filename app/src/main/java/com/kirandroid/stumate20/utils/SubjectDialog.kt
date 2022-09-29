package com.kirandroid.stumate20.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kirandroid.stumate20.data.StudentData
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.ui.theme.textFieldHintColor
import com.kirandroid.stumate20.ui.theme.txtSubjectsColor
import com.kirandroid.stumate20.ui.theme.unfocusedDialogTextFieldColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    // Form Data
    var txtName by remember { mutableStateOf(TextFieldValue("")) }

    // Semester Drop-down related
    val semestersList = listOf("Choose Semester","1st Semester", "2nd Semester", "3rd Semester", "4th Semester",
        "5th Semester", "6th Semester", "7th Semester", "8th Semester")
    var selectedSemester by rememberSaveable { mutableStateOf(semestersList[0]) }

    // Unit Drop-down related
    val unitsList = listOf("Choose Unit","Unit - 1", "Unit - 2", "Unit - 3", "Unit - 4")
    var selectedUnit by rememberSaveable { mutableStateOf(unitsList[0]) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {

            Box(contentAlignment = Alignment.Center) {

                Column(modifier = Modifier.padding(20.dp)) {

                    Icon(imageVector = Icons.Outlined.Cancel, contentDescription = null,
                        tint = txtSubjectsColor,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                setShowDialog(false)
                            }
                            .align(Alignment.End))
                    
                    Text(text = "Create Subject",
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 0.dp),
                        color = MaterialTheme.colorScheme.primary)

                    Text(text = "helps to make your Files organized",
                         fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp, bottom = 15.dp).align(CenterHorizontally), textAlign = TextAlign.Center)


                    // Subject Name
                    OutlinedTextField(modifier = Modifier
                        .padding(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                        value = txtName,
                        textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
                        maxLines = 1,
                        shape = RoundedCornerShape(80.dp),
                        onValueChange = { newText ->
                            txtName = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = { Text("Subject Name", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp))},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = unfocusedDialogTextFieldColor,
                            unfocusedLabelColor = textFieldHintColor,
                            placeholderColor = MaterialTheme.colorScheme.secondary
                        ),
                        placeholder = { Text(text = "Subject Name")})


                    // Semester Drop down
                    var expanded_1 by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = expanded_1, onExpandedChange = { expanded_1 = !expanded_1 },
                        modifier = Modifier
                            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 15.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedSemester, readOnly = true,
                            textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                fontWeight = FontWeight.W300, fontSize = 15.sp),
                            singleLine = true, shape = RoundedCornerShape(80.dp),
                            modifier = Modifier.fillMaxWidth(), maxLines = 1,
                            onValueChange = { selectedSemester = it }, label = {  },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_1) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = unfocusedDialogTextFieldColor,
                                placeholderColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = textFieldHintColor,
                            ),
                        )
                        // filter options based on text field value
                        ExposedDropdownMenu(expanded = expanded_1, onDismissRequest = { expanded_1 = false },) {
                            semestersList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = { selectedSemester = selectionOption
                                        expanded_1 = false
                                    },
                                    text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })
                            }
                        }
                    }

                    // Unit Drop down
                    var expanded_2 by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = expanded_2, onExpandedChange = { expanded_2 = !expanded_2 },
                        modifier = Modifier
                            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 15.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedUnit, readOnly = true,
                            textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                fontWeight = FontWeight.W300, fontSize = 15.sp),
                            singleLine = true, shape = RoundedCornerShape(80.dp),
                            modifier = Modifier.fillMaxWidth(), maxLines = 1,
                            onValueChange = { selectedUnit = it }, label = { },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_2) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = unfocusedDialogTextFieldColor,
                                placeholderColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = textFieldHintColor,
                            ),
                        )
                        // filter options based on text field value
                        ExposedDropdownMenu(expanded = expanded_2, onDismissRequest = { expanded_2 = false },) {
                            unitsList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = { selectedUnit = selectionOption
                                        expanded_2 = false
                                    },
                                    text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })
                            }
                        }
                    }

                    // Continue Button
                    Button(
                        onClick = {

                            // TODO: Need to create Subject Name
                            // Need to use the viewModel to perform the Backend process

                            if(txtName.toString().isEmpty()) {
                                // TODO: Notify user to enter something
                                return@Button
                            }

                            // TODO: Pass all the values from the above data to `setValue`
                            setValue(txtName.toString())
                            setShowDialog(false)

                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 25.dp, top = 15.dp)
                            .fillMaxWidth()
                            .height(50.dp),
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

    }


}