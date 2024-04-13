package com.study.government.model

data class NavArgument(
    val argument: DestinationArg,
    val value: Any,
) {
    
    fun setInPath(path: String) = path.replace(
        oldValue = "{${argument.arg}}",
        newValue = "$value"
    )
}