package com.example.admin.homework_w6.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.homework_w6.Entity.User
import com.example.admin.homework_w6.Interface.IListenerDeleteUser
import com.example.admin.homework_w6.R
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterUser(val arrUser : List<User>, val arrCountAssign : ArrayList<Int>, val iListenerDeleteUser: IListenerDeleteUser) : RecyclerView.Adapter<ViewHolderUser>() {
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolderUser {
        context = parent?.context!!
        return ViewHolderUser(LayoutInflater.from(parent?.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return arrUser.size
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {

        holder.txtUserName.text = arrUser[position].name
        holder.txtCountAssign.text = arrCountAssign[position].toString() + " assign"
        holder.imgBin.setOnClickListener {
            iListenerDeleteUser.deleteUser(arrUser[position].id!!)
        }
    }

}
class ViewHolderUser(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtUserName = itemView.txtUserName
    var txtCountAssign = itemView.txtCountAssign
    var imgBin = itemView.imgBin
}