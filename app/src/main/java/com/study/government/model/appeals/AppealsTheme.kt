package com.study.government.model.appeals

import androidx.annotation.StringRes
import com.study.government.R

@Suppress("unused")
enum class AppealsTheme(
    @StringRes val label: Int,
) {
    QUESTION(R.string.question),
    DEDUCTIONS(R.string.deductions),
    ARRANGEMENT(R.string.arrangement),
    TAX(R.string.tax),
    FINE(R.string.fine)
}