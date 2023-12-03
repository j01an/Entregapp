package com.example.entregapp.models

import java.io.Serializable

data class CompraModel(
    val address: String,
    val producto: ProductoModel?,
    val fecha:String,
    val status:String,
    val user_name:String,
    val user_uid:String
) : Serializable
