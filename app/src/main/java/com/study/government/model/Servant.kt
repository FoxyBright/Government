package com.study.government.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servants")
data class Servant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val avatarUrl: String,
    val name: String,
    val post: String,
    val department: String,
    val description: String,
    val merits: String,
    val avatarPath: String = "",
) {
    constructor() : this(
        name = "",
        post = "",
        department = "",
        description = "",
        avatarUrl = "",
        merits = ""
    )
}
