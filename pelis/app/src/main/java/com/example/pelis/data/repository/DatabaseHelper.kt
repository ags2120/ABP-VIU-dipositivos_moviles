package com.example.pelis.data.repository

//package com.example.pelis.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pelis.domain.model.Movie

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "movies.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE movies (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                year INTEGER,
                rating INTEGER,
                director TEXT,
                synopsis TEXT,
                imageUrl TEXT,
                isWatched INTEGER,
                isFavorite INTEGER
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS movies")
        onCreate(db)
    }

    // 🔹 Convertir Cursor → Movie
    private fun cursorToMovie(cursor: Cursor): Movie {
        return Movie(
            id = cursor.getInt(0),
            title = cursor.getString(1),
            year = cursor.getInt(2),
            rating = cursor.getLong(3),
            director = cursor.getString(4),
            synopsis = cursor.getString(5),
            imageUrl = cursor.getString(6),
            isWatched = cursor.getInt(7) == 1,
            isFavorite = cursor.getInt(8) == 1
        )
    }

    // 🔹 Obtener todas las películas
    fun getAllMovies(): List<Movie> {
        val list = mutableListOf<Movie>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM movies", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToMovie(cursor))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    // 🔹 Insertar película
    fun insertMovie(movie: Movie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", movie.title)
            put("year", movie.year)
            put("rating", movie.rating)
            put("director", movie.director)
            put("synopsis", movie.synopsis)
            put("imageUrl", movie.imageUrl)
            put("isWatched", if (movie.isWatched) 1 else 0)
            put("isFavorite", if (movie.isFavorite) 1 else 0)
        }

        db.insert("movies", null, values)
    }

    // 🔹 Eliminar película
    fun deleteMovie(id: Int) {
        val db = writableDatabase
        db.delete("movies", "id = ?", arrayOf(id.toString()))
    }

    // 🔹 Actualizar favorito
    fun updateFavorite(id: Int, isFavorite: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("isFavorite", if (isFavorite) 1 else 0)
        }
        db.update("movies", values, "id = ?", arrayOf(id.toString()))
    }
}