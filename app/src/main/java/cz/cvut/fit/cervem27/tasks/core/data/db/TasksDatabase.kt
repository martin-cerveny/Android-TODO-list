package cz.cvut.fit.cervem27.tasks.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.cvut.fit.cervem27.tasks.features.category.data.db.DbCategory
import cz.cvut.fit.cervem27.tasks.features.task.data.db.Converters
import cz.cvut.fit.cervem27.tasks.features.task.data.db.DbTask

@Database(version = 1, entities = [DbCategory::class, DbTask::class])
//@TypeConverters(Converters::class)
@TypeConverters(Converters::class)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {
        fun newInstance(context: Context): TasksDatabase {
            return Room.databaseBuilder(context, TasksDatabase::class.java, "tasks.db").build()
        }
    }
}
