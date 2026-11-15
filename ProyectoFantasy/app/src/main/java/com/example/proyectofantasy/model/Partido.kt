package com.example.aplicacindepreguntas.model

import com.example.proyectofantasy.R
import java.time.LocalDate

data class Partido(
    val nombre: String,
    val equipo: String,
    val posiciones: List<String>,
    val puntos: Int,
    val equipoE: String,
    val fecha: LocalDate,
    val foto: Int = R.drawable.jugador_generico
) {
    val fotosJugadores = mapOf(
        "LeBron James" to R.drawable.lebron,
        "Stephen Curry" to R.drawable.curry,
        "Luka Doncic" to R.drawable.luka,
        "Kevin Durant" to R.drawable.durant,
        "Jayson Tatum" to R.drawable.tatum,
        "Jimmy Butler" to R.drawable.butler,
        "Nikola Jokic" to R.drawable.jokic,
        "Giannis Antetokounmpo" to R.drawable.giannis,
        "Anthony Edwards" to R.drawable.edwards,
        "Shai Gilgeous-Alexander" to R.drawable.shai
    )
    @Override
    override fun toString() : String{
        return "$nombre;$equipo;$posiciones;$puntos;$equipoE;$fecha;$foto"
    }
}