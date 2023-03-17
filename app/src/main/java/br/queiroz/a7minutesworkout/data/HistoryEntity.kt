package br.queiroz.a7minutesworkout.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_tb")
data class HistoryEntity(
    @PrimaryKey
    val date: String,
)
