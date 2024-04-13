package com.study.government.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.study.government.R
import com.study.government.ui.theme.*

enum class NewsCategory(
    @DrawableRes
    val icon: Int,
    @StringRes
    val label: Int,
    val color: Color,
) {
    
    EDUCATION(
        icon = R.drawable.ic_education,
        color = EducationColor,
        label = R.string.education
    ),
    HEALTH(
        icon = R.drawable.ic_health,
        color = HealthColor,
        label = R.string.health
    ),
    ECOLOGY(
        icon = R.drawable.ic_ecology,
        color = EcologyColor,
        label = R.string.ecology
    ),
    CULTURE(
        icon = R.drawable.ic_culture,
        color = CultureColor,
        label = R.string.culture
    ),
    MANUFACTURE(
        icon = R.drawable.ic_money,
        color = ManufactureColor,
        label = R.string.manufacture
    ),
    TRANSPORT(
        icon = R.drawable.ic_transport,
        color = TransportColor,
        label = R.string.transport
    )
}