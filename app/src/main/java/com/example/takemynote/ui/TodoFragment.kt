package com.example.takemynote.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.takemynote.R
import com.example.takemynote.databinding.FragmentTodoBinding
import com.example.takemynote.helper.FirebaseHelper
import com.example.takemynote.model.Task
import com.example.takemynote.ui.adapter.TaskAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener



class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val taskList = mutableListOf<Task>()

   // private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

        getTasks()
    }

    private fun initClicks(){

        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }
    }
    private fun getTasks() {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        taskList.clear()
                        for(snap in snapshot.children){
                            val task = snap.getValue(Task::class.java) as Task

                            if(task.status == 0) taskList.add(task)
                        }
                        binding.textInfo.text = ""

                        taskList.reverse()
                        initAdapter()

                    }else{
                        binding.textInfo.text = "Nenhuma tarefa cadastrada."
                    }

                    binding.progressBar.isVisible = false
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun initAdapter(){
        binding.rvTarefa.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTarefa.setHasFixedSize(true)
        taskAdapter = TaskAdapter(requireContext(), taskList){task, select ->
         optionSelect(task, select)
        }

        binding.rvTarefa.adapter = taskAdapter
    }

    private fun optionSelect(task: Task, select: Int){
        when(select){
            TaskAdapter.SELECT_REMOVE ->{
            deleteTask(task)
            }
            TaskAdapter.SELECT_EDIT ->{
                val action = HomeFragmentDirections
                    .actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }
        }
    }

    private fun deleteTask(task: Task){
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .removeValue()

        taskList.remove(task)
        taskAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}