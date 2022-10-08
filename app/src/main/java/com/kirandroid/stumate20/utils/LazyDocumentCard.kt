package com.kirandroid.stumate20.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kirandroid.stumate20.data.DocumentData
import com.kirandroid.stumate20.ui.theme.dashboardBgColor
import com.kirandroid.stumate20.ui.theme.documentCircleBgColor

@Composable
fun LazyDocumentCard(documentData: DocumentData, navController: NavController) {
    
    OutlinedCard(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 18.dp)
            .fillMaxWidth().clickable {
                      //TODO: Open the URL in the browser or from the app
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
                    imageVector = Icons.Outlined.Description,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 25.dp)
                        .size(26.dp)
                        .drawBehind {
                                drawCircle(color = documentCircleBgColor,
                                radius = this.size.maxDimension)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = documentData.documentName,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.W500
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp).clickable {
                                  // TODO: Open the document Download Url from the app browser
                    },
                    tint = MaterialTheme.colorScheme.primary
                )

            }

        }
    )
    
    
}