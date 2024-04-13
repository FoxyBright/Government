package com.study.government.view.requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.government.R
import com.study.government.model.UserRole.USER
import com.study.government.tools.getViewModel
import com.study.government.viewmodel.MainViewModel

@Composable
fun RequestsPlaceholder(modifier: Modifier = Modifier) {
    val mainVm = getViewModel<MainViewModel>()

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_empty_requests),
            modifier = Modifier.size(120.dp),
            contentDescription = null,
            tint = LightGray
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = if (mainVm.user?.role == USER) {
                stringResource(R.string.empty_requests_user)
            } else {
                stringResource(R.string.empty_requests)
            },
            textAlign = Center,
            fontWeight = Medium,
            color = LightGray,
            fontSize = 16.sp
        )
    }
}