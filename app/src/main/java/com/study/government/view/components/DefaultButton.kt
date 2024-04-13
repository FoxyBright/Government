package com.study.government.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.government.ui.theme.PrimaryColor

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = PrimaryColor,
    textColor: Color = White,
    onClick: () -> Unit,
) {
    Card(
        elevation = cardElevation(2.dp),
        colors = cardColors(color),
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            fontWeight = Medium,
            textAlign = Center,
            color = textColor,
            fontSize = 16.sp,
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp, 10.dp)
        )
    }
}