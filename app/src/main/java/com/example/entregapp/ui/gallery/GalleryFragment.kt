package com.example.entregapp.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregapp.MenuActivity
import com.example.entregapp.R
import com.example.entregapp.adapters.CompraAdapter
import com.example.entregapp.databinding.FragmentGalleryBinding
import com.example.entregapp.models.CompraModel
import com.example.entregapp.models.ProductoModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_gallery, container, false)
        val rvCompras: RecyclerView = view.findViewById(R.id.rvCompras)
        val db = FirebaseFirestore.getInstance()
        var lstCompras: List<CompraModel>

        val btnGoHome: Button = view.findViewById(R.id.btnGoHome)
        btnGoHome.setOnClickListener {
            val intent = Intent(context, MenuActivity::class.java)
            startActivity(intent)
        }
        db.collection("compras")
            .addSnapshotListener{snap,e->
                if(e!=null){
                    Snackbar
                        .make(view
                            ,"Error al obtener la colección"
                            , Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                lstCompras = snap!!.documents.map { documentSnapshot ->
                    val productoData = documentSnapshot["producto"] as? Map<*, *>

                    val producto: ProductoModel? = productoData?.let { data ->
                        val nombre = data["bane"] as? String ?: ""
                        val precio = data["price"].toString().toFloat()
                        val descripcion = data["description"] as? String ?: ""
                        val stock = data["stock"].toString().toFloat()
                        val user_uid = data["user_id"] as? String ?: ""

                        ProductoModel("",nombre,descripcion,  precio,stock, user_uid)
                    }

                    CompraModel(
                        documentSnapshot["address"].toString(),
                        producto,
                        documentSnapshot["fecha"].toString(),
                        documentSnapshot["status"].toString(),
                        documentSnapshot["user_name"].toString(),
                        documentSnapshot["user_uid"].toString()
                    )
                }

                val adapter = CompraAdapter(lstCompras)
                rvCompras.adapter = adapter
                rvCompras.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickListener(object : CompraAdapter.OnItemClickListener {
                    override fun onItemClick(producto: CompraModel) {
//                        val intent = Intent(this, PedidoActivity::class.java)
//                        intent.putExtra("PRODUCTO", producto)
//                        startActivity(intent)
                    }
                })
            }
        return view
//        val galleryViewModel =
//            ViewModelProvider(this).get(GalleryViewModel::class.java)

//        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}