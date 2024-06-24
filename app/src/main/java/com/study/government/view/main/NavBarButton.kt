package com.study.government.view.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.study.government.R
import com.study.government.navigation.Destination

enum class NavBarButton(
    @DrawableRes val icon: Int = 0,
    @StringRes val label: Int = 0,
    val dest: Destination,
) {
    NEWS_BT(
        icon = R.drawable.ic_news,
        label = R.string.news,
        dest = Destination.NEWS
    ),
    REQUESTS_BT(
        icon = R.drawable.ic_file,
        label = R.string.appeals,
        dest = Destination.APPEALS
    ),
    SERVANTS_BT(
        icon = R.drawable.ic_people,
        label = R.string.servants,
        dest = Destination.SERVANTS
    )
}