package com.example.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * here entity will create table in data base
 * This is just simple data class similarly that we  make in recylerview and we will calling in main class
 */


@Entity(tableName="Student_info")
data class StudentEntity(

    //here we are creating colomns for our data in table
    // in every table there is only one primary key like here it is id
    // it use to get data of the particular id or student and it is different for different people
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,


    var Name:String="",
    
    // here info will set colmn name what we will provide to him
    // if we will not provide any name then it will take colmn name as variable name

    @ColumnInfo(name = "Registration_No")
    var RollNo: String = "",
)


