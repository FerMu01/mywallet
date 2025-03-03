package com.example.mywallet

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class Home : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

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
