package com.example.roomdatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * it is type of interface which is  use to communicate with the data base
 * it is use to fectch retrive delete update and delete from the database
 * (a) And why we are using suspend function bec we will call these func from courtines and we can call only suspend func from them
 * (b) they can use to stop or resume corutines as well
 * Flow is just basically emit or store  values in large numerbs in runtime like :- getting data from database or network call
 */
@Dao
interface StudentDao {

    // use to insert data
    @Insert
    suspend fun insert(studententity:StudentEntity)

    // use to update the data
    @Update
    suspend fun update(studententity:StudentEntity)

    // use to delete the data
    @Delete
    suspend fun delete(studententity:StudentEntity)

    // use to reterive using sql query whole data
    @Query("SELECT*FROM'Student_info'")
    suspend fun fetchAlldata():Flow<List<StudentEntity>>

    // use to get data using id or for specific person using querry
    @Query("SELECT*FROM'Student_info' where id=:id")
    suspend fun fetchdatbyId(id:Int):Flow<StudentEntity>


}