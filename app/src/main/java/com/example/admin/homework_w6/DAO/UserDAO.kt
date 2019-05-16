package com.example.admin.homework_w6.DAO

import android.arch.persistence.room.*
import com.example.admin.homework_w6.Entity.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAll() : List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun findById(id : Int) : User

    @Query("SELECT * FROM user WHERE name LIKE :name")
    fun findByName(name : String) : User

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(obj : User) : Long

    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id : Int)
}
