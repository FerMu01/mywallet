package com.example.mywallet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si el usuario ya inició sesión
        val sharedPref = getSharedPreferences("MyWalletPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("USERNAME", null)
        if (savedUsername != null) {
            // Si ya existe un usuario guardado, redirigir a Home
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos del layout
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Guardar el nombre de usuario en SharedPreferences
                val editor = sharedPref.edit()
                editor.putString("USERNAME", username)
                editor.apply()

                Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()

                // Ir a la pantalla Home
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish() // Evita volver al Login al presionar "Atrás"
            } else {
                Toast.makeText(this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
