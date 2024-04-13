package com.study.government.model

import androidx.compose.ui.graphics.Color
import com.study.government.ui.theme.EcologyColor
import com.study.government.ui.theme.HealthColor
import com.study.government.ui.theme.TransportColor

enum class RequestStatus(val color: Color) {
    OPENED(TransportColor),
    CANCELED(HealthColor),
    CLOSED(EcologyColor)
}