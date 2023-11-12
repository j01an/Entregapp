package com.example.entregapp.models

import java.io.Serializable

data class ProductoModel(
    val id: String,
    val name: String,
    val description:String,
    val price:Float,
    val stock:Float,
    val user_uid:String
) : Serializable
