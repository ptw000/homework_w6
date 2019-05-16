package com.example.admin.homework_w6

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.admin.homework_w6.Adapter.AdapterAssign
import com.example.admin.homework_w6.DAO.TaskDAO
import com.example.admin.homework_w6.DAO.UserDAO
import com.example.admin.homework_w6.Entity.Task
import com.example.admin.homework_w6.Entity.User
import kotlinx.android.synthetic.main.activity_detail_task.*
import kotlinx.android.synthetic.main.custom_actionbar_detail_task.*

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var taskDao : TaskDAO
    private lateinit var userDao : UserDAO
    private lateinit var db : AppDatabase
    private var id_task : Int = 0
    private lateinit var listUser : List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_detail_task)
        db = AppDatabase.invoke(this) // get Room database instance
        // get id_task
        val intent = intent
        id_task = intent.getIntExtra("id_task", 0)
        // get infor from DB
        val taskObj  = getDetailTask(id_task)
        listUser = getListUser()
        // Update UI
        updateUI(taskObj)
        addEvents()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addEvents() {
        imgDelete.setOnClickListener {
            delete(id_task)
            val intent = Intent(this@DetailTaskActivity, TaskActivity::class.java)
            startActivity(intent)
            finish()
        }
        checkboxStt.setOnCheckedChangeListener {
            buttonView, isChecked ->  if (isChecked) changeStt(true, id_task) else changeStt(false, id_task)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id_user = listUser[position].id
                val name = listUser[position].name
                txtAssignTo.text = name
                if(id_user == null) {   // unassigned
                    unAssigned(id_task)
                }
                else {  // assigned
                    assigned(id_task, id_user)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    private fun getDetailTask(id_task : Int) : Task{
        taskDao = db.taskDao()
        return taskDao.findById(id_task)
    }

    private fun getListUser() : List<User>{
        userDao = db.userDao()
        return userDao.getAll()
    }

    private fun updateUI(taskObj : Task) {
        txtTitle.text = taskObj.description // set title
        checkboxStt.isChecked = taskObj.completed   // set status completed?

        val arrUser : ArrayList<User> = listUser as ArrayList<User> // add item unassigned for unassign
        arrUser.add(User(null, "Unassigned"))
        val adapterAssign = AdapterAssign(arrUser)
        spinner.adapter = adapterAssign // set adapter for spinner
        if(taskObj.id_user != null) {   // set user name
            for(i in 0..arrUser.size-1) {
                if(arrUser[i].id == taskObj.id_user) {
                    txtAssignTo.text = arrUser[i].name
                    spinner.setSelection(i) // set index of spinner
                }
            }
        }
        else {
            spinner.setSelection(arrUser.size-1)    // set index of spinner
        }
    }

    private fun delete(id_task : Int) {
        taskDao.delete(id_task)
    }

    private fun changeStt(stt : Boolean, id_task : Int) {
        taskDao.updateStt(stt, id_task)
    }

    private fun assigned(id_task : Int, id_user : Int) {
        taskDao.assigned(id_task, id_user)
    }

    private fun unAssigned(id_task : Int) {
        taskDao.unAssigned(id_task)
    }
}
