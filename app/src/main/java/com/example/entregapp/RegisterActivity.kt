package com.example.entregapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.voluntapp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnGoLogin: Button = findViewById(R.id.btnGoLogin)
        btnGoLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val etName: EditText = findViewById(R.id.etName)
        val etEmailLogin: EditText = findViewById(R.id.etEmailLogin)
        val etPasswordLogin: EditText = findViewById(R.id.etPasswordLogin)
        val rgUser: RadioGroup = findViewById(R.id.rgTipoUser)
        val btnRegister: Button = findViewById(R.id.btnLogin)

        var tipoUser = ""
        rgUser.setOnCheckedChangeListener { group, checkedId ->
            // Imprimir el valor seleccionado en el RadioGroup
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            tipoUser = selectedRadioButton.text.toString()
            Log.d("RGUser", "Tipo de usuario seleccionado: ${tipoUser}")
        }
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")
        btnRegister.setOnClickListener {
            Log.d("BTNRegister", "etName: ${etName.text}")
            Log.d("BTNRegister", "tipoUser: ${tipoUser}")
            auth
                .createUserWithEmailAndPassword(etEmailLogin.text.toString(),etPasswordLogin.text.toString())
                .addOnSuccessListener { obj ->
                    Snackbar.make(findViewById(android.R.id.content),"Usuario creado"+ (obj.user?.uid
                        ?: "-"),
                        Snackbar.LENGTH_LONG).show()
                    val nuevoUser = UserModel(
                        etName.text.toString(),
                        etEmailLogin.text.toString(),
                        (obj.user?.uid ?: "-"),
                        tipoUser,
                    )
                    collectionRef.add(nuevoUser)
                        .addOnSuccessListener { documentReference ->
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Registro exitoso ID: ${documentReference.id}"
                                    , Snackbar.LENGTH_LONG).show()
                            startActivity(Intent(this,LoginActivity::class.java))
                        }
                        .addOnFailureListener{ error ->
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Ocurrió un error: $error"
                                    , Snackbar.LENGTH_LONG).show()
                        }

                }.addOnFailureListener{error->
                    Snackbar.make(findViewById(android.R.id.content),"Error al crear usuario",
                        Snackbar.LENGTH_LONG).show()
                }
        }

    }
}