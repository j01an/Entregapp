package com.example.entregapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.voluntapp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        MySharedPreferences.init(sharedPreferences)

        setContentView(R.layout.activity_login)

        val userModel = MySharedPreferences.getUserModel()


        if (userModel != null) {
            println("valorperfil"+userModel.perfil)
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val btnGoRegister: Button = findViewById(R.id.btnGoRegister)
        btnGoRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        var auth = FirebaseAuth.getInstance();
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val txtEmail: EditText = findViewById(R.id.etEmailLogin)
        val txtPassword: EditText = findViewById(R.id.etPasswordLogin)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val correo = txtEmail.text.toString();
            val clave = txtPassword.text.toString();
            if(correo.isNotEmpty() && clave.isNotEmpty()){
                auth.signInWithEmailAndPassword(correo,clave)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            val uid = auth.currentUser?.uid
                            usersCollection.whereEqualTo("uid", uid)
                                .get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful && task.result != null) {
                                        val snapshot = task.result
                                        for (document in snapshot.documents) {
                                            val userModel = UserModel(
                                                document.get("name") as String,
                                                document.get("email") as String,
                                                document.get("uid") as String,
                                                document.get("perfil") as String,
                                            )
                                            MySharedPreferences.saveUserModel(userModel)

                                        }
                                        val intent = Intent(this, MenuActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        // Ocurrió un error al obtener los datos
                                    }
                                }

                        }else{
                            Snackbar.make(findViewById(android.R.id.content),"Las credenciales son inválidas",
                                Snackbar.LENGTH_LONG).show()
                        }
                    }
            }else{
                Snackbar.make(findViewById(android.R.id.content),"Ingrese correo y contraseña.",
                    Snackbar.LENGTH_LONG).show()
            }

        }
    }
}