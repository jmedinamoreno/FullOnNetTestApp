package com.example.fullonnettestapp.data.model

import android.net.Uri
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val firstname:String,
    val lastname:String,
    val photo: Uri? = null
)