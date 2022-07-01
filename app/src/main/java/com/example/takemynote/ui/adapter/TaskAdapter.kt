package com.example.takemynote.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.takemynote.R
import com.example.takemynote.databinding.ItemAdapterBinding
import com.example.takemynote.model.Task

class TaskAdapter (
                  private val context: Context,
                  private val taskList: List<Task>,
                  val taskSelected: (Task, Int) -> Unit)
    :RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    companion object{
        val SELECT_BACK: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_DETAILS: Int = 4
        val SELECT_NEXT: Int = 5

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(
           ItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false
       )
    )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]

        holder.binding.tvDescription.text = task.description

        holder.binding.tvRemover.setOnClickListener { taskSelected(task, SELECT_REMOVE) }
        holder.binding.tvEditar.setOnClickListener { taskSelected(task, SELECT_EDIT) }
        holder.binding.tvDetalhes.setOnClickListener { taskSelected(task, SELECT_DETAILS) }


        when(task.status){
            0 ->{
                holder.binding.ibVoltar.isVisible = false

                holder.binding.ibProximo.setColorFilter(ContextCompat.getColor(context, R.color.color_doing))

                holder.binding.ibProximo.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            1 -> {
                holder.binding.ibVoltar.setColorFilter(ContextCompat.getColor(context, R.color.color_todo))

                holder.binding.ibProximo.setColorFilter(ContextCompat.getColor(context, R.color.color_done))

                holder.binding.ibVoltar.setOnClickListener { taskSelected(task, SELECT_BACK) }
                holder.binding.ibProximo.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            else ->{
                holder.binding.ibProximo.isVisible = false

                holder.binding.ibVoltar.setColorFilter(ContextCompat.getColor(context, R.color.color_doing))

                holder.binding.ibVoltar.setOnClickListener { taskSelected(task, SELECT_BACK) }
            }



        }

    }

    override fun getItemCount() = taskList.size

    inner class MyViewHolder(val binding: ItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)

}