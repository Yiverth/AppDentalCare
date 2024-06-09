package com.yiverthdevs.dentalcare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import android.os.Vibrator
import android.os.VibrationEffect
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yiverthdevs.dentalcare.Adapter.RecyclerViewSliderImagenes

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewSliderImagenes
    private lateinit var vibrator: Vibrator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        val imagenes = listOf(
            R.drawable.imagen1,
            R.drawable.imagen2,
            R.drawable.imagen3,
            R.drawable.imagen4,
            R.drawable.imagen5,
            R.drawable.imagen6
        )

        adapter = RecyclerViewSliderImagenes(imagenes)

        vibrator = requireActivity().getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        // Se implementa la navegación de los botones desde Home
        openFragmentHome(view, R.id.card_citas, CitaFragment())
        openFragmentHome(view, R.id.card_historial_clinico, HistorialClinicoFragment())
        openFragmentHome(view, R.id.card_pagos, PagosFragment())

        openFragmentHome2(view, R.id.icon_especialistas, EspecialistasFragment())
        openFragmentHome2(view, R.id.icon_reportes, ReportesFragment())
        openFragmentHome2(view, R.id.icon_cancelar_cita, CancelarCitaFragment())



        return view
    }

    private fun openFragmentHome (view: View, buttonId: Int, fragment: Fragment) {
        val trasactionHome =  view.findViewById<CardView>(buttonId)
        trasactionHome.setOnClickListener {
            // Vibrar al precionar el botón
            vibrate()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmnet_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun openFragmentHome2 (view: View, buttonId: Int, fragment: Fragment) {
        val trasactionHome2 = view.findViewById<ImageView>(buttonId)
        trasactionHome2.setOnClickListener {
            // Vibrar al precionar el botón
            vibrate()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmnet_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun vibrate () {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50,VibrationEffect.DEFAULT_AMPLITUDE))
        }else {
            vibrator.vibrate(50)
        }
    }
}
