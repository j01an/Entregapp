package com.example.entregapp.ui.slideshow

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.entregapp.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SlideshowViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()



    fun submitRatingAndComment(restaurantName: String, rating: Float, comment: String) {
        val user = firebaseAuth.currentUser

        if (user != null) {
            // Almacenar la calificación y el comentario en Cloud Firestore
            val commentData = hashMapOf(
                "userId" to user.uid,
                "restaurantName" to restaurantName,
                "rating" to rating,
                "comment" to comment
            )

            val collectionRef = db.collection("comentarios")
            collectionRef.add(commentData)
                .addOnSuccessListener {
                    //showToast(context, "¡Tu comentario se ha agregado exitosamente!")

                }
                .addOnFailureListener {
                    //showSnackbar(view, "Hubo un problema al agregar tu comentario. Inténtalo nuevamente.")
                }
        } else {
            //showSnackbar(view, "Debes iniciar sesión para agregar un comentario.")
        }

    }

    //private fun showToast(context: Context, message: String) {
    //   Toast.makeText( message, Toast.LENGTH_SHORT).show()
    //}


}