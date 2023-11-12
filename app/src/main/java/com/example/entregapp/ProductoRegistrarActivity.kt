package com.example.entregapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.entregapp.models.ProductoModel
import com.example.voluntapp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class ProductoRegistrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        MySharedPreferences.init(sharedPreferences)
        setContentView(R.layout.activity_producto_registrar)

        val userModel = MySharedPreferences.getUserModel()

        val btngoMenu: Button = findViewById(R.id.btnProductoCancelar)
        btngoMenu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        val btnRegisterProducto: Button = findViewById(R.id.btnProductoRegistrar)
        val etProductoName: EditText = findViewById(R.id.etProductoName)
        val etProductoDescription: EditText = findViewById(R.id.etProductoDescription)
        val etProductoPrecio: EditText = findViewById(R.id.etProductoPrecio)
        val etProductoStock: EditText = findViewById(R.id.etProductoStock)

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("products")

        btnRegisterProducto.setOnClickListener {
            if(etProductoName.text.isNotEmpty() && etProductoDescription.text.isNotEmpty() && etProductoPrecio.text.isNotEmpty() && etProductoStock.text.isNotEmpty()){
                val nuevoProducto = ProductoModel(
                    "",
                    etProductoName.text.toString(),
                    etProductoDescription.text.toString(),
                    etProductoPrecio.text.toString().toFloat(),
                    etProductoStock.text.toString().toFloat(),
                    userModel?.uid.toString()
                )
                collectionRef.add(nuevoProducto)
                    .addOnSuccessListener { documentReference ->
                        Snackbar
                            .make(findViewById(android.R.id.content)
                                ,"Registro exitoso ID: ${documentReference.id}"
                                , Snackbar.LENGTH_LONG).show()
                        startActivity(Intent(this,MenuActivity::class.java))
                    }
                    .addOnFailureListener{ error ->
                        Snackbar
                            .make(findViewById(android.R.id.content)
                                ,"Ocurrió un error: $error"
                                , Snackbar.LENGTH_LONG).show()
                    }
            }else{
                Snackbar.make(findViewById(android.R.id.content),"Por favor completa los campos",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }
}