package com.example.salus.vista

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ArrayAdapter
import com.example.salus.R
import com.example.salus.modelo.Cita

class AdaptadorCitas(
    context: Context,
    private val lista: List<Cita>
) : ArrayAdapter<Cita>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vista = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_cita, parent, false
        )

        val cita = lista[position]

        vista.findViewById<TextView>(R.id.txtFecha).text = cita.fecha
        vista.findViewById<TextView>(R.id.txtHora).text = cita.hora
        vista.findViewById<TextView>(R.id.txtConsultorio).text = cita.consultorio
        vista.findViewById<TextView>(R.id.txtMotivo).text = cita.motivo

        return vista
    }
}
