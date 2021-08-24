package com.example.myapplication.todo

class plan(val content: String) {
}

class planlist() {
    val planlist = ArrayList<plan>()

    fun addPlan(plan: plan) {
        planlist.add(plan)
    }
}