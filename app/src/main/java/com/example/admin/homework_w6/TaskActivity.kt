package com.example.admin.homework_w6

import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.admin.homework_w6.Adapter.AdapterTask
import com.example.admin.homework_w6.DAO.TaskDAO
import com.example.admin.homework_w6.Entity.Task
import kotlinx.android.synthetic.main.activity_task.*

class TaskActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var listTask : List<Task>
    private lateinit var taskDao : TaskDAO
    private lateinit var adapterTask: AdapterTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        supportActionBar?.setTitle(R.string.task)
        initRoomDatabase()

        listTask = taskDao.getAll() // get infor from DB

        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvTask.layoutManager = layoutManager
        setAdapter(listTask) // set adapter
        addEvent()
    }

    private fun addEvent() {
        btnAdd.setOnClickListener(this)
        btnListUser.setOnClickListener(this)
        swipe.setOnRefreshListener {
            listTask = taskDao.getAll()
            setAdapter(listTask)
        }
        rgFilter.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rbAll) {
                listTask = taskDao.getAll()
                setAdapter(listTask)
            }
            else if(checkedId == R.id.rbCompleted) {
                listTask = taskDao.getByCompleted(true)
                setAdapter(listTask)
            }
            else if (checkedId == R.id.rbUncompleted) {
                listTask = taskDao.getByCompleted(false)
                setAdapter(listTask)
            }
        }
    }

    private fun initRoomDatabase() {
        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries()
                .build()
        taskDao = db.taskDao()
    }

    private fun setAdapter(listTask : List<Task>) {
        adapterTask = AdapterTask(listTask)
        rvTask.adapter = adapterTask
        swipe.isRefreshing = false
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnAdd -> {
                val title = edTitle.text.toString()
                if(!title.isEmpty()) { // Add new task
                    val obj : Task = Task(null,title, false, null)
                    taskDao.insert(obj)
                    listTask = taskDao.getAll()
                    setAdapter(listTask)
                    edTitle.text = null
                }
                else {
                    Toast.makeText(this, "Please enter title!!!", Toast.LENGTH_LONG).show()
                }
            }
            R.id.btnListUser -> {
                val intent = Intent(this@TaskActivity, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
