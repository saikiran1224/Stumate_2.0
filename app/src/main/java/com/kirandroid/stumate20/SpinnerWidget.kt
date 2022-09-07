@file:OptIn(ExperimentalMaterial3Api::class)

package com.kirandroid.stumate20

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kirandroid.stumate20.ui.theme.Cabin
import com.kirandroid.stumate20.ui.theme.textFieldHintColor

@Composable
fun SpinnerWidget(list: List<String>, label: String) {


   /*var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = expanded.not() },
        modifier = Modifier.focusRequester(focusRequester).padding(start = 15.dp, top = 15.dp, end = 15.dp).fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOptionText,
            readOnly = true,
            singleLine = true,
            modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
            maxLines = 1,
            onValueChange = { selectedOptionText = it },
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                placeholderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = textFieldHintColor,
            ),
        )
        // filter options based on text field value

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    focusManager.clearFocus()
                },
            ) {
                list.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                            focusManager.clearFocus()
                        },
                        text = { Text(text = selectionOption, fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal) })

                }
            }

    } */

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(list[0]) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp).fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                Icon(icon, null)
            },
            label = { Text(text = label)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                placeholderColor = MaterialTheme.colorScheme.secondary,
            ),
                modifier = Modifier
                .onFocusChanged {
                    expanded = it.isFocused
                }.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            list.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label,fontSize = 16.sp, fontFamily = Cabin, fontWeight = FontWeight.Normal)},
                    onClick = {
                    selectedOption = label
                    expanded = false
                    focusManager.clearFocus()
                })
            }
        }
    }


}