package com.magnus.boletim_covid19

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    var lista = arrayListOf<Boletim>()
    var adapter = Adapter(lista)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Covid-19 Torres-RS"
        readJson(this)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        rvDados.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        // val layoutManager= GridLayoutManager(this,2)
        rvDados.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readJson(context: Context) {
        var json: String? = null
        val listaBoletins = mutableListOf<Boletim>()
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
            // txtValue.text=json.toString()
            var jsonArray = JSONArray(json)
            for (i in 0..jsonArray.length() - 1) {
                var js = jsonArray.getJSONObject(i)
                var confirmados = js.getString("Confirmados")
                var curados = js.getString("Curados")
                var descartados = js.getString("Descartados")
                var suspeitos = js.getString("Suspeitos")
                var monitoramento = js.getString("Monitoramento")
                var sDomiciliar = js.getString("Sdomiciliar")
                var sHospitalar = js.getString("Shopitalar")
                var mortes = js.getString("mortes")
                var hora = js.getString("boletim")
                val dia = formatarData(js.getString("boletim").substring(0, 10))
                var boletim = Boletim(
                    suspeitos.toInt(),
                    confirmados.toInt(),
                    descartados.toInt(),
                    monitoramento.toInt(),
                    curados.toInt(),
                    sDomiciliar.toInt(),
                    sHospitalar.toInt(),
                    mortes.toInt(),
                    dia,
                    hora
                )
                lista.add(boletim)
            }
        } catch (e: IOException) {
            Log.e("Erro", "Impossivel ler JSON")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatarData(data: String): String {
        val diaString = data
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date = LocalDate.parse(diaString)
        var formattedDate = date.format(formatter)
        return formattedDate
    }

}
