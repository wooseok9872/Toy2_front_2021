package com.example.myapplication.todo

import java.io.Serializable

class plan(var content: String, var studyId:Long, var status : Boolean = false) : Serializable{
    override fun toString(): String {
        return content
    }
}

class planlist() {
    val Planlist = ArrayList<plan>()

    fun addPlan(plan: plan) {
        Planlist.add(plan)
    }
}

class todo(var content: String) : Serializable{

}

class toddlist(){
    val todolist = ArrayList<todo>()

    fun addtodo(todo: todo){
        todolist.add(todo)
    }
}

