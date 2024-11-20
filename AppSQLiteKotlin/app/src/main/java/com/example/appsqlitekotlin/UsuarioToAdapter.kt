package com.example.appsqlitekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioToAdapter (private val listaUsuarios: List<Usuario>):
    RecyclerView.Adapter<UsuarioToAdapter.UsuarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fila_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        holder.idTextView.text = usuario.id.toString()
        holder.nombreTextView.text = usuario.nombre
        holder.edadTextView.text = usuario.edad.toString()
    }

    override fun getItemCount(): Int = listaUsuarios.size

    // ViewHolder para cada item
    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idTextView: TextView = view.findViewById(R.id.textId)
        val nombreTextView: TextView = view.findViewById(R.id.textName)
        val edadTextView: TextView = view.findViewById(R.id.textAge)
    }
}