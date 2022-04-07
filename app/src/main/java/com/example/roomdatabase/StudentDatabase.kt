package com.example.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * here entites contains the Student entity (tables information) that will convert into a table in database
 * version will tell how many time data base is updated
 */
@Database(entities = [StudentEntity::class],version=1)
abstract class StudentDatabase:RoomDatabase() {

    // to connect Dao to the database
    abstract fun StudentDao():StudentDao


    /**
     * Define a companion object, this allows us to add functions on the EmployeeDatabase class.
     *
     * For example, classes can call `EMPLOYEEDatabase.getInstance(context)` to instantiate
     * a new EmployeeDatabase.
     */
    companion object{

        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private  var INSTANCE:StudentDatabase?=null

        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         * To learn more about Singleton read the wikipedia article:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */

        fun getInstance(context:Context):StudentDatabase{
            
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            // here this synchronized return database if empty created new one otherwise return the old one

            synchronized(this){

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance= INSTANCE

                // If instance is `null` make a new database instance.
                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,StudentDatabase::class.java,"Student_info"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration().build()

                    // Assign INSTANCE to the newly created database.
                    // it instance is not empty then it will give the old one
                    INSTANCE=instance

                }
                // Return instance; smart cast to be non-null.
                // returning the database
                return instance
            }
        }


    }
}