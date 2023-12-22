package com.example.healthtracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalcDao {
    /*
    - @Insert: Inserir
    - @Query: Buscar
    - @Delete: Deletar
    - @Update: Atualizar */

    @Insert
    fun insert(calc: Calc)

}