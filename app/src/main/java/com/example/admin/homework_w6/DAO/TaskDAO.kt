package com.example.admin.homework_w6.DAO

import android.arch.persistence.room.*
import com.example.admin.homework_w6.Entity.Task

@Dao
interface TaskDAO {
    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAll() : List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    fun findById(id : Int) : Task

    @Query("SELECT * FROM task WHERE description LIKE :description")
    fun findByDescription(description : String) : Task

    @Query("SELECT * FROM task WHERE completed = :completed")
    fun findByCompledted(completed : Boolean) : Task

    @Query("SELECT * FROM task WHERE id_user = :id_user")
    fun findByIdUser(id_user : Int) : Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj : Task) : Long

    @Query("UPDATE task SET completed = :stt WHERE id = :id")
    fun updateStt(stt : Boolean, id : Int)

    @Query("UPDATE task SET id_user = :id_user WHERE id = :id_task")
    fun assigned(id_task : Int, id_user : Int)

    @Query("UPDATE task SET id_user = null WHERE id = :id_task")
    fun unAssigned(id_task : Int)

    @Query("UPDATE task SET id_user = null WHERE id_user = :id_user")
    fun unAssignedByIdUser(id_user : Int)

    @Query("DELETE FROM task WHERE id = :id")
    fun delete(id : Int)

    @Query("SELECT * FROM task WHERE completed = :stt")
    fun getByCompleted(stt : Boolean) : List<Task>

    @Query("SELECT COUNT(*) FROM task WHERE id_user = :id_user")
    fun getByIdUser(id_user: Int) : Int
}
