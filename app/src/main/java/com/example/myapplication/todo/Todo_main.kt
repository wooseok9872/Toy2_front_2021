package com.example.myapplication.todo

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_todo_main.*
import java.time.LocalDate

class Todo_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_main)

        val date: LocalDate = LocalDate.now()
        val todaydate: String = date.toString()
        today_date.setText(todaydate)

        val planList: planlist = planlist()


        add_button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_dialong_view, null)
            val dialogtext = dialogView.findViewById<EditText>(R.id.edit_content)

            builder.setView(dialogView)
                .setPositiveButton("add") { dialogInterface, i ->
                    planList.addPlan(
                        plan(
                            dialogtext.text.toString()
                        )
                    )
                }
                .setNegativeButton("cancel") { dialogInterface, i ->
                }
                .show()
        }


        list_todo.adapter =
            planAdapter(planList, LayoutInflater.from(this), activity = this)
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = true
        manager.stackFromEnd = true
        list_todo.layoutManager = manager


    }

}

class planAdapter(
    val plan_list: planlist,
    val inflater: LayoutInflater,
    val activity: Activity
) : RecyclerView.Adapter<planAdapter.ViewHolder>() {

    inner class ViewHolder(todo_view: View) : RecyclerView.ViewHolder(todo_view) {
        val planContent: TextView

        init {
            planContent = todo_view.findViewById(R.id.plan_content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.todo_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plan_list.planlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.planContent.setText(plan_list.planlist.get(position).content)
    }
}