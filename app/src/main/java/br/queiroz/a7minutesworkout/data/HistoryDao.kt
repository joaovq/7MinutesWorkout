package br.queiroz.a7minutesworkout.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistory(historyEntity: HistoryEntity)
    @Update
    suspend fun updateHistory(historyEntity: HistoryEntity)
    @Delete
    suspend fun deleteHistory(historyEntity: HistoryEntity)

    @Query("Select * from `history_tb`")
    fun fetchAllHistories(): Flow<List<HistoryEntity>>
    @Query("Select * from `history_tb` where date like :date")
    fun fetchAllHistoriesById(date: String):Flow<HistoryEntity>

}