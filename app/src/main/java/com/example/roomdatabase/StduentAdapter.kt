package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.StudentDataBinding

/**
 * Why we are using Student Entity rather than using differnt data class bec student entity is quite similar to data class so it will do our job
 * This class not going to implement the click listner thats why we are passing it
 */

class StduentAdapter(val list:ArrayList<StudentEntity>,private val updateListener:(id:Int)->Unit,
private val deleteListner:(id:Int)->Unit):RecyclerView.Adapter<StduentAdapter.ViewHolder>() {

    class ViewHolder(binding: StudentDataBinding):RecyclerView.ViewHolder(binding.root)
    {
        // here we are not taking the name of the file instead we are taking the id of the file
        val itemLL=binding.studentdtt
        val itemName=binding.name
        val itemRollno=binding.rollno
        val itemAdd=binding.add
        val itemDelete=binding.dlt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // why parent context - bec we cant use this because we are not in main context thats why
        return ViewHolder(StudentDataBinding.inflate(LayoutInflater.from(parent.context),parent ,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context=holder.itemView.context
        // here item will take all the stuff from the list at the specific position
        val item=list[position]

        holder.itemName.text=item.Name
        holder.itemRollno.text=item.RollNo

        // holder know wich btn is clicked and it will give id accordingly
        // And we will invoke update listner and delete listner to perform some task
        // and we will declare this function in main activity

        holder.itemAdd.setOnClickListener {
            updateListener.invoke(item.id)
        }

        holder.itemDelete.setOnClickListener {
            deleteListner.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}