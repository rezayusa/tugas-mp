package com.reza.tugasmp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


    @Entity
    data class User(
        @PrimaryKey(autoGenerate = true) val uid: Int?,
        @ColumnInfo(name = "full_name") val fullName: String?,
        @ColumnInfo(name = "email") val email: String?,
        @ColumnInfo(name = "phone") val phone: String?
    )


