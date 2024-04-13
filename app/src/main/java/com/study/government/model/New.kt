package com.study.government.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.study.government.GovernmentApp.Companion.curTime

@Entity(tableName = "news")
data class New(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val date: Long,
    val title: String,
    val imageUrl: String,
    val link: String,
    val text: String,
    val imagePath: String = "",
) {
    constructor() : this(
        date = curTime,
        imageUrl = "",
        title = "",
        link = "",
        text = ""
    )
}