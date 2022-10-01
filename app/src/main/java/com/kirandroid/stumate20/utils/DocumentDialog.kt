package com.kirandroid.stumate20.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentDialog(subjectsData: List<SubjectData>?, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    // Form Data
    var txtDocumentName by remember { mutableStateOf(TextFieldValue("")) }

    // TODO: Dynamically load the subject names
    // Subjects Drop-down related
    val subjectsList = listOf<String>("Choose Subject")
    for (subject in subjectsData!!) {
        subjectsList.plus(subject.subjectName)
    }

   // val subjectsList = listOf("Choose Subject", )
    var selectedSubject by rememberSaveable { mutableStateOf(subjectsList[0]) }

    // Unit Drop-down related
    val unitsList = listOf("Choose Unit","Unit - 1", "Unit - 2", "Unit - 3", "Unit - 4")
    var selectedUnit by rememberSaveable { mutableStateOf(unitsList[0]) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {

            Box(contentAlignment = Alignment.Center, modifier = Modifier.verticalScroll(
                rememberScrollState())) {

                Column(modifier = Modifier.padding(20.dp)) {

                    Icon(imageVector = Icons.Outlined.Cancel, contentDescription = null,
                        tint = txtSubjectsColor,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                setShowDialog(false)
                            }
                            .align(Alignment.End))

                    Text(text = "Upload Document",
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 0.dp),
                        color = MaterialTheme.colorScheme.primary)

                    Text(text = "Add your documents in chapter-wise for more info",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 10.dp)
                            .align(Alignment.CenterHorizontally), textAlign = TextAlign.Center)


                    // Document Name
                    OutlinedTextField(modifier = Modifier
                        .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                        value = txtDocumentName,
                        textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp),
                        maxLines = 1,
                        shape = RoundedCornerShape(80.dp),
                        onValueChange = { newText ->
                            txtDocumentName = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = { Text("Document Name", style = TextStyle(color = textFieldHintColor, fontFamily = Cabin, fontWeight = FontWeight.W300, fontSize = 15.sp)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = unfocusedDialogTextFieldColor,
                            unfocusedLabelColor = textFieldHintColor,
                            placeholderColor = MaterialTheme.colorScheme.secondary
                        ),
                        placeholder = { Text(text = "Document Name") })


                    // Subjects Drop down
                    var expanded_1 by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = expanded_1, onExpandedChange = { expanded_1 = !expanded_1 },
                        modifier = Modifier
                            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 15.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedSubject, readOnly = true,
                            textStyle = TextStyle(color = textFieldHintColor, fontFamily = Cabin,
                                fontWeight = FontWeight.W300, fontSize = 15.sp),
                            singleLine = true, shape = RoundedCornerShape(80.dp),
                            modifier = Modifier.fillMaxWidth(), maxLines = 1,
                            onValueChange = { selectedSubject = it }, label = {  },
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
                            subjectsList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = { selectedSubject = selectionOption
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
                            )
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


                    // TODO: Browse your files to Upload option need to be given here
                    val stroke = Stroke(width = 2f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp, top = 15.dp, start = 5.dp, end = 5.dp)
                            .clickable {
                                   // TODO: Whole File upload operation

                            },contentAlignment = Alignment.Center){
                        Canvas(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)) {
                            drawRoundRect(color = primary,style = stroke, cornerRadius = CornerRadius(22.dp.toPx()))
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center), horizontalArrangement = Arrangement.Center) {
                            Icon(imageVector = Icons.Outlined.FileUpload, contentDescription = null,
                                modifier = Modifier.padding(end = 12.dp), tint = MaterialTheme.colorScheme.primary)
                            Text(textAlign = TextAlign.Center,text = "Browse your Files to Upload",
                                color = MaterialTheme.colorScheme.primary, fontSize = 13.5.sp, fontWeight = FontWeight.Medium)

                        }
                    }

                    // Continue Button
                    Button(
                        onClick = {

                            // TODO: Need to create Subject Name
                            // Need to use the viewModel to perform the Backend process

                            if(txtDocumentName.toString().isEmpty()) {
                                // TODO: Notify user to enter something
                                return@Button
                            }

                            // TODO: Pass all the values from the above data to `setValue`
                            setValue(txtDocumentName.toString())
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

                        Text(text = "Upload", textAlign = TextAlign.Center,fontFamily = Cabin, fontSize = 18.sp, )
                    }


                }

            }

        }

    }

}