package com.example.tasklist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.model.Task

class TaskAdapter(
    private val onDeleteClick: (Task) -> Unit,
    private val onTaskClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks = emptyList<Task>()

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTask: TextView = itemView.findViewById(R.id.tvTask)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        
        holder.tvTask.text = currentTask.title
        holder.tvTask.setOnClickListener { onTaskClick(currentTask) }
        
        holder.btnDelete.setOnClickListener {
            onDeleteClick(currentTask)
        }
    }

    override fun getItemCount() = tasks.size

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}