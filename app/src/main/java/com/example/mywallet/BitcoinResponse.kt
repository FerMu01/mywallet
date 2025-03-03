package com.example.mywallet

import com.google.gson.annotations.SerializedName

data class BitcoinResponse(
    @SerializedName("codigo") val codigo: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("unidad_medida") val unidadMedida: String,
    @SerializedName("serie") val serie: List<BitcoinData>
)

data class BitcoinData(
    @SerializedName("fecha") val fecha: String,
    @SerializedName("valor") val valor: Double
)
