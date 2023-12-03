package com.example.entregapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.entregapp.models.CompraModel
import com.example.entregapp.models.ProductoModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class PedidoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        MySharedPreferences.init(sharedPreferences)
        val userModel = MySharedPreferences.getUserModel()

        val producto: ProductoModel? = intent.getSerializableExtra("PRODUCTO") as? ProductoModel
        val btnGoHome: Button = findViewById(R.id.btnBackComprar)
        btnGoHome.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        val tvProductName: TextView = findViewById(R.id.tvProductName)
        val tvProductDescription: TextView = findViewById(R.id.tvProductDescription)
        val tvProductPrice: TextView = findViewById(R.id.tvProductPrice)
        tvProductName.text = producto?.name.toString()
        tvProductDescription.text = producto?.description.toString()
        tvProductPrice.text = producto?.price.toString()

        val etAddress: EditText = findViewById(R.id.etAddress)
        val etTarjetaNumero: EditText = findViewById(R.id.etTarjetaNumero)
        val etTarjetaFecha: EditText = findViewById(R.id.etTarjetaFecha)
        val etTarjetaCVV: EditText = findViewById(R.id.etTarjetaCVV)
        val etTarjetaName: EditText = findViewById(R.id.etTarjetaName)
        val btnComprar: Button = findViewById(R.id.btnComprar)

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("compras")

        btnComprar.setOnClickListener {
            if(etAddress.text.isNotEmpty() &&
                etTarjetaNumero.text.isNotEmpty() &&
                etTarjetaFecha.text.isNotEmpty() &&
                etTarjetaCVV.text.isNotEmpty() &&
                etTarjetaName.text.isNotEmpty()
                ){
                val formato = SimpleDateFormat("yyyy-MM-dd")
                val fechaActual = Date()

                val nuevaCompra = CompraModel(
                    etAddress.text.toString(),
                    producto!!,
                    formato.format(fechaActual),
                    "Creado",
                    userModel?.name ?: "",
                    userModel?.uid ?: ""
                )

                collectionRef.add(nuevaCompra)
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