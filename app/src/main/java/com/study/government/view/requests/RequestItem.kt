package com.study.government.view.requests

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.government.R
import com.study.government.model.Request
import com.study.government.ui.theme.PrimaryColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
@SuppressLint("SimpleDateFormat")
fun RequestItem(
    request: Request,
    onClick: () -> Unit,
) {
    Card(
        elevation = cardElevation(2.dp),
        colors = cardColors(Color.White),
        modifier = Modifier.height(Max),
        onClick = onClick
    ) {
        Row {
            Spacer(
                Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(request.status.color),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontWeight = SemiBold,
                                color = PrimaryColor
                            )
                        ) { append(stringResource(R.string.request)) }
                        append(" ")
                        append(request.id.toString())
                    },
                    fontWeight = Medium,
                    color = Color.DarkGray,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontWeight = Medium,
                                color = PrimaryColor
                            )
                        ) { append(stringResource(R.string.theme)) }
                        append(" ")
                        append(stringResource(request.theme.label))
                    },
                    fontWeight = Normal,
                    color = Color.DarkGray,
                    fontSize = 16.sp
                )

                val date by remember(request) {
                    mutableStateOf(
                        SimpleDateFormat("dd.MM.yyyy")
                            .format(Date(request.date))
                    )
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = Normal,
                    fontSize = 12.sp,
                    textAlign = End,
                    color = Gray,
                    text = date
                )
            }
        }
    }
}