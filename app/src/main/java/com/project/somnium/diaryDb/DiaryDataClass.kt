package com.project.somnium.diaryDb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "DiaryDB")
data class DiaryDataClass(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var date: String,
    var content: String,
    var title: String,
    var imgurl: String?
)
