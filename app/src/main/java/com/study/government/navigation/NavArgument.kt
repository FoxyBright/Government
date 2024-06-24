package com.study.government.navigation

data class NavArgument(
    val argument: DestinationArg,
    val value: Any,
) {
    fun setInPath(path: String) = path.replace(
        oldValue = "{${argument.arg}}",
        newValue = "$value"
    )
}