package com.magnus.boletim_covid19

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class Adapter(private val dados: List<Boletim>) : RecyclerView.Adapter<Adapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val vh = VH(v)
        v.setOnClickListener {
            var boletim = dados[vh.adapterPosition]
            var dados = dados[vh.adapterPosition]
            val enviarDados =
                Intent(v.context, BoletimActivity::class.java)
            enviarDados.putExtra("Suspeitos", dados.suspeitos.toString())
            enviarDados.putExtra("Confirmados", dados.confirmados.toString())
            enviarDados.putExtra("Data", dados.data)
            enviarDados.putExtra("Hora", dados.hora)
            enviarDados.putExtra("Descartados", dados.descartados.toString())
            enviarDados.putExtra("Curados", dados.curados.toString())
            enviarDados.putExtra("Monitoramento", dados.monitoramento.toString())
            enviarDados.putExtra("Sdomiciliar", dados.sDomiciliar.toString())
            enviarDados.putExtra("Shospitalar", dados.sHospitalar.toString())
            enviarDados.putExtra("mortes", dados.mortes.toString())
            v.context.startActivity(enviarDados)
        }
        return vh
    }

    override fun getItemCount(): Int {
        return dados.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var dados = dados[position]
        holder.txtConfirmados.text = dados.confirmados.toString()
        holder.txtData.text = dados.data
        holder.txtDescartados.text = dados.descartados.toString()
    }


    ///INNERCLASS
    class VH(itenView: View) : RecyclerView.ViewHolder(itenView) {
        var txtConfirmados: TextView = itenView.txt_confirmados
        var txtDescartados: TextView = itenView.txtDescartados
        var txtData: TextView = itenView.txtData
    }
}