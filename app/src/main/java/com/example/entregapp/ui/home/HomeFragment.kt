package com.example.entregapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregapp.MenuActivity
import com.example.entregapp.PedidoActivity
import com.example.entregapp.ProductoRegistrarActivity
import com.example.entregapp.R
import com.example.entregapp.adapters.ProductoAdapter
import com.example.entregapp.databinding.FragmentHomeBinding
import com.example.entregapp.models.ProductoModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_home, container, false)
        val rvProyectos: RecyclerView = view.findViewById(R.id.rvProductos)
        val db = FirebaseFirestore.getInstance()
        var lstProyectos: List<ProductoModel>
        val userModel = MySharedPreferences.getUserModel()
        db.collection("products")
            .addSnapshotListener{snap,e->
                if(e!=null){
                    Snackbar
                        .make(view
                            ,"Error al obtener la colección"
                            , Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                lstProyectos = snap!!.documents.map { documentSnapshot ->
                    ProductoModel(
                        documentSnapshot.id,
                        documentSnapshot["name"].toString(),
                        documentSnapshot["description"].toString(),
                        documentSnapshot["price"].toString().toFloat(),
                        documentSnapshot["stock"].toString().toFloat(),
                        documentSnapshot["user_uid"].toString(),
                    )
                }
                val adapter = ProductoAdapter(lstProyectos)
                rvProyectos.adapter = adapter
                rvProyectos.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickListener(object : ProductoAdapter.OnItemClickListener {
                    override fun onItemClick(producto: ProductoModel) {
                        val intent = Intent(context, PedidoActivity::class.java)
                        intent.putExtra("PRODUCTO", producto)
                        startActivity(intent)
                    }
                })
            }
        val fabAgregar: FloatingActionButton = view.findViewById(R.id.fabAgregarProducto)

        // Manejar clics en el botón flotante
        fabAgregar.setOnClickListener {
            val intent = Intent(activity, ProductoRegistrarActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}