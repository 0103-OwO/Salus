package com.example.salus.vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salus.R
import com.example.salus.modelo.CitaMedico

class CitasMedicoAdapter(private var citas: List<CitaMedico>) :
    RecyclerView.Adapter<CitasMedicoAdapter.CitaMedicoViewHolder>() {

    class CitaMedicoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
        val tvHora: TextView = view.findViewById(R.id.tvHora)
        val tvPaciente: TextView = view.findViewById(R.id.tvPaciente)
        val tvConsultorio: TextView = view.findViewById(R.id.tvConsultorio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaMedicoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita_medico, parent, false)
        return CitaMedicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaMedicoViewHolder, position: Int) {
        val cita = citas[position]
        holder.tvFecha.text = "Fecha: ${cita.fecha}"
        holder.tvHora.text = "Hora: ${cita.hora}"
        holder.tvPaciente.text = "Paciente: ${cita.nombre_paciente}"
        holder.tvConsultorio.text = "Consultorio: ${cita.consultorio}"
    }

    override fun getItemCount(): Int = citas.size

    fun actualizarCitas(nuevasCitas: List<CitaMedico>) {
        citas = nuevasCitas
        notifyDataSetChanged()
    }
}