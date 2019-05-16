package com.example.admin.homework_w6.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.homework_w6.DetailTaskActivity
import com.example.admin.homework_w6.Entity.Task
import com.example.admin.homework_w6.Interface.IListenerItemTaskClick
import com.example.admin.homework_w6.R
import kotlinx.android.synthetic.main.item_task.view.*

class AdapterTask(val listTask : List<Task>) : RecyclerView.Adapter<ViewHolderTask>() {
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolderTask {
        context = parent?.context!!
        return ViewHolderTask(LayoutInflater.from(parent?.context).inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: ViewHolderTask, position: Int) {
        holder.txtTitle.text = listTask[position].description
        if(listTask[position].id_user == null) {
            holder.txtAssign.setText(R.string.unassigned)
        }
        else {
            holder.txtAssign.setText(R.string.assigned)
        }
        holder.setItemTaskClick(object : IListenerItemTaskClick {
            override fun onClick(position: Int) {
                val id_task = listTask[position].id
                Log.i("id_task", ""+id_task)
                val intent = Intent(context, DetailTaskActivity::class.java)
                intent.putExtra("id_task", id_task)
                context?.startActivity(intent)
            }
        })
    }
}
class ViewHolderTask(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iListenerItemTaskClick : IListenerItemTaskClick? = null
    var txtTitle = itemView.txtTitle
    var txtAssign = itemView.txtAssign
    fun setItemTaskClick(iListenerItemTaskClick: IListenerItemTaskClick) {
        this.iListenerItemTaskClick = iListenerItemTaskClick
    }
    init {
        itemView.setOnClickListener {
            iListenerItemTaskClick?.onClick(position)
        }
    }
}