package com.example.admin.homework_w6.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.example.admin.homework_w6.Entity.User
import com.example.admin.homework_w6.R
import kotlinx.android.synthetic.main.item_user_assign.view.*

class AdapterAssign(var listUser : ArrayList<User>) : BaseAdapter(), SpinnerAdapter {
    private lateinit var context : Context
    private lateinit var txtUserName : TextView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        context = parent?.context!!
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_user_assign, parent, false)
        txtUserName = view.txtUserName
        txtUserName.text = listUser[position].name
        return view
    }

    override fun getItem(position: Int): Any {
        return listUser[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listUser.size
    }
}
