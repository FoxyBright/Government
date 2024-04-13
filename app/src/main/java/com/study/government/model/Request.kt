package com.study.government.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.study.government.GovernmentApp.Companion.curTime
import com.study.government.model.RequestStatus.OPENED
import com.study.government.model.RequestTheme.TAX

@Entity(tableName = "requests")
data class Request(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val theme: RequestTheme,
    val problem: String,
    val authorId: String,
    val authorName: String,
    val status: RequestStatus,
    val answer: String,
    val date: Long,
) {
    constructor(
        authorId: String,
        authorName: String
    ) : this(
        theme = TAX,
        problem = "",
        authorId = authorId,
        authorName = authorName,
        status = OPENED,
        answer = "",
        date = curTime
    )
}
