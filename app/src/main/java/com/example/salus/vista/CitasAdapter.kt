package com.example.salus.vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salus.R
import com.example.salus.modelo.Cita
class CitasAdapter(private var citas: List<Cita>) :
    RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
        val tvHora: TextView = view.findViewById(R.id.tvHora)
        val tvConsultorio: TextView = view.findViewById(R.id.tvConsultorio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.tvFecha.text = "Fecha: ${cita.fecha}"
        holder.tvHora.text = "Hora: ${cita.hora}"
        holder.tvConsultorio.text = "Consultorio: ${cita.consultorio}"
    }

    override fun getItemCount(): Int = citas.size

    fun actualizarCitas(nuevasCitas: List<Cita>) {
        citas = nuevasCitas
        notifyDataSetChanged()
    }
}