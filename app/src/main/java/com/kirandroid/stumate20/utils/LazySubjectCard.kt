package com.kirandroid.stumate20.utils

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kirandroid.stumate20.data.SubjectData
import com.kirandroid.stumate20.ui.theme.light_onprimary
import com.kirandroid.stumate20.ui.theme.primary
import com.kirandroid.stumate20.ui.theme.unfocusedDialogTextFieldColor

@Composable
// TODO: Add Subject Data as parameter over here
fun LazySubjectCard(subjectData: SubjectData, borderColor: Color) {

    // Creating an object to store Container Color
    var containerColor: Color? = null

    // Based on the border color we are mapping the Corresponding container color
    when(borderColor) {

        Color(0xFFffd294) -> containerColor = Color(0xFFFBE8CE)
        Color(0xFFb699ff) -> containerColor = Color(0xFFE1d5ff)
        Color(0xFFff9090) -> containerColor = Color(0xFFFFc4c4)
        else -> {
            // Null
        }
    }

    Column(modifier = Modifier
        .clickable {
            // TODO: Open the subject Info screen with Subject Name
        }
        .width(100.dp)
        .padding(start = 11.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)) {


        Text(
            text = subjectData.subjectName[0].toString().trim(),
            fontSize = 24.sp,

            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
                .drawBehind {

                    // Border Color
                    drawCircle(
                        radius = this.size.maxDimension,
                        color = borderColor,
                        style = Stroke(2.5f)
                    )

                    // Container Color
                    containerColor?.let {
                        drawCircle(
                            radius = this.size.maxDimension,
                            color = it
                        )
                    }

                }
                .align(CenterHorizontally),
            color = borderColor
        )

        Text(text = subjectData.subjectName.toString(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(top = 10.dp)
            .align(CenterHorizontally),
        color = Color.Black,
        fontSize = 15.sp, textAlign = TextAlign.Center)



    }

}