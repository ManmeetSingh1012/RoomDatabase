package com.example.roomdatabase

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.databinding.UpdateDialogBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val studentdao=(application as StudentApp).db.StudentDao()

        binding?.button?.setOnClickListener {
            insertdata(studentdao)
        }

        /**
         * we know every time if we make any change then we have to update reycle view
         * but using flow it will update automatically
         */
        lifecycleScope.launchWhenCreated {
            studentdao.fetchAlldata().collect {
                val list=ArrayList(it)
                settinguprecylerview(list,studentdao)
            }
        }

    }

    fun insertdata(studentdao: StudentDao)
    {
        // here we have taking data from input field
        val name=binding?.nameMain?.text.toString()
        val email=binding?.emailMain?.text.toString()

        // corutine bec we cant do this thing in main thread
        lifecycleScope.launch {
            if( name.isNotEmpty() and email.isNotEmpty())
            {
                // calling insert wich is fun of student dao and passing student entity
                studentdao.insert(StudentEntity(Name=name, RollNo = email))
                Toast.makeText(applicationContext,"Data Added",Toast.LENGTH_SHORT).show()

                binding?.nameMain?.text?.clear()
                binding?.emailMain?.text?.clear()

            }
            else
            {
                Toast.makeText(applicationContext,"Error occured ",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun settinguprecylerview(lst:ArrayList<StudentEntity>, studentdao:StudentDao)
    {
        /**
         * update and delete listner are passed to adapter
         * then in adapter these func are invoked when they nedded
         * they will be invoked by passing id in them in adapter
         * then here these are called and they will perform there task for which they are made
         */

        if(lst.isNotEmpty())
        {
            val studentAdapter=StduentAdapter(lst,{
                updateId ->
                updateOnclicklistner(updateId,studentdao)
            }, { deleteId ->
                deleteupdate(deleteId, studentdao)
            })

            binding?.recylerview?.layoutManager=LinearLayoutManager(this)
            binding?.recylerview?.adapter=studentAdapter
            binding?.noItems?.visibility= View.INVISIBLE
            binding?.recylerview?.visibility= View.VISIBLE


        }
        else
        {
            binding?.noItems?.visibility= View.VISIBLE
            binding?.recylerview?.visibility= View.INVISIBLE
        }
    }

    private fun updateOnclicklistner(id:Int,studentdao: StudentDao)
    {
        val dialog=Dialog(this,R.style.Theme_dialog)
        dialog.setCancelable(false)

        // here we have used the bindding because we have to change and the display the data in the dialog box
        // it will make our work easy
        val binding=UpdateDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        // here we have used courutines bec we are fetching the data and this thing cant be done on main thread
        lifecycleScope.launch {
            studentdao.fetchdatbyId(id).collect {
                binding.nameMain.setText(it.Name)
                binding.emailMain.setText(it.RollNo)
            }
        }


        binding.udpdate.setOnClickListener {

            val name=binding.nameMain.text.toString()
            val roll_no=binding.emailMain.text.toString()

            if(name.isNotEmpty() and roll_no.isNotEmpty())
            {

                lifecycleScope.launch {
                    studentdao.update(StudentEntity(id,name,roll_no))
                    Toast.makeText(applicationContext,"Data updated",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
            else
            {
                Toast.makeText(this,"Data field should not be empty",Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteupdate(id: Int,studentdao: StudentDao)
    {
        val dialog=AlertDialog.Builder(this)

        dialog.setTitle("Delete Recored")
        dialog.setIcon(android.R.drawable.ic_dialog_alert)

        dialog.setPositiveButton("yes"){dialogInterface,_->
            lifecycleScope.launch {
                studentdao.delete(StudentEntity(id))
                Toast.makeText(applicationContext,"Record Deleted",Toast.LENGTH_SHORT).show()

            }
            dialogInterface.dismiss()

        }

        dialog.setNegativeButton("No"){dialogInterface,_->
            dialogInterface.dismiss()
        }

        val alertdialog=dialog.create()
        alertdialog.show()
        alertdialog.setCancelable(false)
    }
}