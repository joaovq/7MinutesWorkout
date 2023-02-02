package br.queiroz.a7minutesworkout.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class WorkOutDatabase:RoomDatabase() {

    abstract fun historyDao():HistoryDao

    companion object{

        @Volatile
        var INSTANCE:WorkOutDatabase? = null

        fun getInstance
                    (context: Context):WorkOutDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                   instance = Room.databaseBuilder(
                       context = context.applicationContext,
                       WorkOutDatabase::class.java, "seven_minutes_workout"
                   ).fallbackToDestructiveMigration().build()
                   INSTANCE = instance
               }
                return instance
            }

        }
    }
}