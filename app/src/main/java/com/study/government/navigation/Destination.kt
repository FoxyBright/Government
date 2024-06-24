package com.study.government.navigation

import com.study.government.navigation.DestinationArg.NEW_INFO_ARG
import com.study.government.navigation.DestinationArg.SERVANT_INFO_ARG

enum class Destination(
    val route: String,
    val showBottomBar: Boolean = false
) {
    SERVANTS_INFO("servant_info/{${SERVANT_INFO_ARG.arg}}"),
    NEW_INFO("new_info/{${NEW_INFO_ARG.arg}}"),
    APPEALS("appeals", true),
    SERVANTS("servants", true),
    NEWS("news", true)
}