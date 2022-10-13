package com.kirandroid.stumate20.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kirandroid.stumate20.data.DocumentData
import com.kirandroid.stumate20.ui.theme.dashboardBgColor
import com.kirandroid.stumate20.ui.theme.documentCircleBgColor
import com.kirandroid.stumate20.viewmodels.DocumentsViewModel

@Composable
fun LazyDocumentCard(documentData: DocumentData, navController: NavController,
                     documentsViewModel: DocumentsViewModel, studentBatchID: String, subjectName: String, unitName: String) {

    val context = LocalContext.current

    // For options purpose
    var menuExpanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 18.dp)
            .fillMaxWidth()
            .clickable {

                // Checking the content type of the file
                // If its of PDF, then we are going to create a chooser, else opening the custom tab
                if (documentData.documentContentType?.contains("image") == true)
                // Open the URL in the browser or from the app using Chrome Custom Tabs
                    ChromeCustomTab(context = context, URL = documentData.documentDownloadUrl)
                else {


                    // Creating a chooser where user can choose the PDF viewer
                    val intent = Intent(Intent.ACTION_VIEW)
                    // If the file type is PDF we are directly opening with PDF Viewer
                    if (documentData.documentContentType == "application/pdf")
                        intent.setDataAndType(
                            Uri.parse(documentData.documentDownloadUrl),
                            "application/pdf"
                        )
                    else
                        intent.setDataAndType(
                            Uri.parse(documentData.documentDownloadUrl),
                            "application/*"
                        )

                    // Setting the flag
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                    // Creating an new intent
                    // Creating an Intent
                    val newIntent = Intent.createChooser(intent, "Open File")
                    try {
                        context.startActivity(newIntent)
                    } catch (e: ActivityNotFoundException) {
                        // Instruct the user to install a PDF reader here, or something
                        Toast
                            .makeText(
                                context,
                                "Sorry, Your mobile doesn't have any supported app to open this file",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }

                }

            },
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        content = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(25.dp), verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = if (documentData.documentContentType!!.contains("application")) Icons.Filled.Description else Icons.Filled.Image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 25.dp)
                        .size(26.dp)
                        .drawBehind {
                            drawCircle(
                                color = documentCircleBgColor,
                                radius = this.size.maxDimension
                            )
                        },
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = documentData.documentName,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.W500
                )

                Spacer(modifier = Modifier.weight(1f))

                Box() {

                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = null,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                // Open the options menu where you want to show download and mark as inappropriate option
                                menuExpanded = true
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                  documentsViewModel.deleteDocument(documentData = documentData,
                                      studentBatchID = studentBatchID, subjectName = subjectName, unitName = unitName)

                                // Hiding the menu
                                menuExpanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text("Report") },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Report,
                                    contentDescription = null
                                )
                            })
                    }
                }



            }

        }
    )
    
    
}