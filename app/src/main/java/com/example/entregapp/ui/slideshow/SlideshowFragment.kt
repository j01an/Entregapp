package com.example.entregapp.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.entregapp.R
import com.example.entregapp.databinding.FragmentSlideshowBinding
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SlideshowFragment : Fragment() {


    private lateinit var etRestaurantName: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var etComment: EditText
    private lateinit var btnSubmit: Button

    private lateinit var ratingViewModel: SlideshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_slideshow, container, false)

        etRestaurantName = rootView.findViewById(R.id.etRestaurantName)
        ratingBar = rootView.findViewById(R.id.ratingBar)
        etComment = rootView.findViewById(R.id.etComment)
        btnSubmit = rootView.findViewById(R.id.btnSubmit)

        ratingViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)

        btnSubmit.setOnClickListener {
            val restaurantName = etRestaurantName.text.toString()
            val rating = ratingBar.rating
            val comment = etComment.text.toString()

            // Utilizar el ViewModel para manejar la lógica de la calificación y el comentario
            ratingViewModel.submitRatingAndComment(restaurantName, rating, comment)

            limpiarDatosDelFormulario()
        }

        return rootView
    }

    private fun limpiarDatosDelFormulario() {

        etRestaurantName.text.clear()
        ratingBar.rating = 0.0f
        etComment.text.clear()
    }

}