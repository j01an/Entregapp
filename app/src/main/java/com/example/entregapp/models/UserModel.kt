package com.example.voluntapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val uid: String,
    val perfil: String,
): Parcelable