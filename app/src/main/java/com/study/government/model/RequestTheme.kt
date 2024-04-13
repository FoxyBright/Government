package com.study.government.model

import androidx.annotation.StringRes
import com.study.government.R

enum class RequestTheme(
    @StringRes
    val label: Int,
) {
    
    TAX(R.string.tax),
    QUESTION(R.string.question),
    ARRANGEMENT(R.string.arrangement),
    DEDUCTIONS(R.string.deductions),
    FINE(R.string.fine)
}