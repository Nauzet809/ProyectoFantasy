package com.example.aplicacindepreguntas.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.aplicacindepreguntas.model.Partido
import com.example.proyectofantasy.R
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.time.LocalDate

val ficheroJugadores = "jugadores.txt"

val listaEquipos = listOf<String>(
    "Los Angeles Lakers",
    "Golden State Warriors",
    "Dallas Mavericks",
    "Phoenix Suns",
    "Boston Celtics",
    "Miami Heat",
    "Denver Nuggets",
    "Milwaukee Bucks",
    "Minnesota Timberwolves",
    "Oklahoma City Thunder"
)

val listaJugadores = listOf<String>(
    "LeBron James",
    "Stephen Curry",
    "Luka Doncic",
    "Kevin Durant",
    "Jayson Tatum",
    "Jimmy Butler",
    "Nikola Jokic",
    "Giannis Antetokounmpo",
    "Anthony Edwards",
    "Shai Gilgeous-Alexander"
)

@RequiresApi(Build.VERSION_CODES.O)
val listaPartidos = listOf(
    Partido("LeBron James", "Los Angeles Lakers", listOf("SF", "PF"), 32, "Golden State Warriors", LocalDate.of(2025, 1, 25), R.drawable.lebron),
    Partido("Stephen Curry", "Golden State Warriors", listOf("PG"), 28, "Los Angeles Lakers", LocalDate.of(2025, 1, 25), R.drawable.curry),
    Partido("Luka Doncic", "Dallas Mavericks", listOf("PG"), 35, "Phoenix Suns", LocalDate.of(2025, 2, 10), R.drawable.luka),
    Partido("Kevin Durant", "Phoenix Suns", listOf("SF", "PF"), 27, "Dallas Mavericks", LocalDate.of(2025, 2, 10), R.drawable.durant),
    Partido("Jayson Tatum", "Boston Celtics", listOf("SF", "PF"), 31, "Miami Heat", LocalDate.of(2025, 3, 5), R.drawable.tatum),
    Partido("Jimmy Butler", "Miami Heat", listOf("SF", "SG"), 24, "Boston Celtics", LocalDate.of(2025, 3, 5), R.drawable.butler),
    Partido("Nikola Jokic", "Denver Nuggets", listOf("C"), 30, "Milwaukee Bucks", LocalDate.of(2025, 3, 12), R.drawable.jokic),
    Partido("Giannis Antetokounmpo", "Milwaukee Bucks", listOf("PF"), 34, "Denver Nuggets", LocalDate.of(2025, 3, 12), R.drawable.giannis),
    Partido("Anthony Edwards", "Minnesota Timberwolves", listOf("SG"), 29, "Oklahoma City Thunder", LocalDate.of(2025, 4, 2), R.drawable.edwards),
    Partido("Shai Gilgeous-Alexander", "Oklahoma City Thunder", listOf("PG", "SG"), 33, "Minnesota Timberwolves", LocalDate.of(2025, 4, 2), R.drawable.shai)
)

@RequiresApi(Build.VERSION_CODES.O)
fun inicializarYCargar(contexto : Context) : List<Partido> {
    var ficheroPreguntas = File(contexto.filesDir, ficheroJugadores)

    if(!ficheroPreguntas.exists()){
        guardarAFichero(contexto, listaPartidos)
    }

    return FileInputStream(ficheroPreguntas).bufferedReader().useLines { lineas ->
        lineas
            .filter { it.isNotBlank() }
            .map { linea ->
                val partes = linea.split(";")
                Partido(
                    nombre = partes[0],
                    equipo = partes[1],
                    posiciones = partes[2]
                        .removeSurrounding("[", "]")
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() },
                    puntos = partes[3].toInt(),
                    equipoE = partes[4],
                    fecha = LocalDate.parse(partes[5]),
                    foto = partes[6].toInt()
                )
            }.toList()
    }
}

fun guardarAFichero(contexto : Context, listaJugadores : List<Partido>){
    var ficheroPreguntas = File(contexto.filesDir, ficheroJugadores)
    val bw = BufferedWriter(FileWriter(ficheroPreguntas))
    for (pregunta in listaJugadores){
        bw.write(pregunta.toString())
        bw.newLine()
    }
    bw.close()
}

fun guardarPartidoAFichero(contexto : Context, pregunta : Partido){
    var ficheroPreguntas = File(contexto.filesDir, ficheroJugadores)
    var fw = FileWriter(ficheroPreguntas, true)
    fw.write(pregunta.toString())
    fw.close()
}
