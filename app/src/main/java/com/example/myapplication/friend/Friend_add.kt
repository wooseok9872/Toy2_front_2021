package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView


class Friend_add : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_add)

        val friend_search_input: SearchView = findViewById<View>(R.id.friend_search_input) as SearchView
        val friend_search_button: ImageView = findViewById<View>(R.id.friend_search_button) as ImageView
        val friend_add_listView: ListView = findViewById<View>(R.id.friend_add_listView) as ListView
        val friend_add_button: Button = findViewById<View>(R.id.friend_add_button) as Button
        val back_button: ImageView = findViewById<View>(R.id.back_button) as ImageView






        friend_add_button.setOnClickListener {
            // <선택한 listView 추가하는 기능 추가해야함>
            finish()
        }

        back_button.setOnClickListener {
            finish()
        }

    }
}