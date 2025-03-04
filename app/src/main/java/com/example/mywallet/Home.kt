package com.example.mywallet

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class Home : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textBienvenida: TextView
    private lateinit var btnLogout: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        // Recuperar el nombre de usuario desde SharedPreferences
        val sharedPref = getSharedPreferences("MyWalletPrefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("USERNAME", null)

        // Si no hay usuario guardado, redirigir al Login
        if (username == null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Mostrar "Bienvenido <USUARIO>" en el TextView
        textBienvenida = findViewById(R.id.textBienvenida)
        textBienvenida.text = "Bienvenido $username"

        // Configurar el botón de logout
        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            // Borrar el usuario guardado en SharedPreferences
            val editor = sharedPref.edit()
            editor.remove("USERNAME")
            editor.apply()

            // Volver a la pantalla Login
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el RecyclerView y obtener datos de Bitcoin
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        FetchBitcoinData().execute("https://mindicador.cl/api/bitcoin")
    }

    private inner class FetchBitcoinData : AsyncTask<String, Void, BitcoinResponse?>() {
        override fun doInBackground(vararg params: String?): BitcoinResponse? {
            val urlString = params[0] ?: return null
            return try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseStream = connection.inputStream.bufferedReader().use { it.readText() }
                Gson().fromJson(responseStream, BitcoinResponse::class.java)
            } catch (e: Exception) {
                Log.e("Error", "Error fetching data", e)
                null
            }
        }

        override fun onPostExecute(result: BitcoinResponse?) {
            result?.let {
                recyclerView.adapter = BitcoinAdapter(it.serie)
            }
        }
    }
}
