package com.study.government.model

import com.study.government.model.DestinationArg.NEW_INFO_ARG
import com.study.government.model.DestinationArg.REQUEST_INFO_ARG
import com.study.government.model.DestinationArg.SERVANT_INFO_ARG

enum class Destination(
    val route: String,
    val showBottomBar: Boolean = false
) {
    SERVANTS("servants", true),
    SERVANTS_INFO("servant_info/{${SERVANT_INFO_ARG.arg}}"),
    REQUESTS("requests", true),
    PROFILE("profile", true),
    NEWS("news", true),
    NEW_INFO("new_info/{${NEW_INFO_ARG.arg}}"),
    REQUEST_INFO("request_info/{${REQUEST_INFO_ARG.arg}}"),
    ADD_REQUEST("add_request/{${REQUEST_INFO_ARG.arg}}"),
    ADD_NEW("add_new/{${NEW_INFO_ARG.arg}}"),
    LOGIN("login"),
}