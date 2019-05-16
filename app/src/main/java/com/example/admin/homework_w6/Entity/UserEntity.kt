package com.example.admin.homework_w6.Entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey(autoGenerate = true)
        var id : Int? = null,
        var name : String
)
