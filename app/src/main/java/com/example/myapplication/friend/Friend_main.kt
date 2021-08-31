package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.friend.*
import com.example.myapplication.todo.Todo_main

import kotlinx.android.synthetic.main.activity_todo_main.*
import kotlinx.android.synthetic.main.friend_list_item.*
import kotlinx.android.synthetic.main.friend_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Friend_main : AppCompatActivity() {
    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_main)

        val friend_add_button: ImageView = findViewById(R.id.friend_add_button) as ImageView

        val todolist_button: ImageView = findViewById(R.id.todolist_button) as ImageView
        val home_button: ImageView = findViewById(R.id.home_button) as ImageView

        val friendList: friendList = friendList()

        //친구 추가 버튼 누르면 추가화면으로 이동
        friend_add_button.setOnClickListener {
            val intent = Intent(this, Friend_add::class.java)
            startActivity(intent)
        }

        //todo list 화면으로 이동
        todolist_button.setOnClickListener{
            startActivity(Intent(this, Todo_main::class.java))
            finish()
        }
        //타이머 화면으로 이동
        home_button.setOnClickListener{
            startActivity(Intent(this, TimerActivity::class.java))
            finish()
        }


        val friendListview: friendListView = friendListView()

        // 친구 목록 데이터 받기
        api.get2_users().enqueue(object : Callback<CheckGetModel2> {
            override fun onResponse(call: Call<CheckGetModel2>, response: Response<CheckGetModel2>) {
                Log.d("log", response.toString())

                // <받은 유저정보 -닉네임, 달성률, 누적시간- 리사이클러뷰로 보이기>
                if(response.isSuccessful){
                    // 리스트로 출력이 안됨, 로그는 찍힘
                    for(i in response.body()!!.checkRoomList2.indices){
                        val name= response.body()!!.checkRoomList2[i].username
                        val achievementRate= response.body()!!.checkRoomList2[i].achievementRate
                        val accumulatedTime= response.body()!!.checkRoomList2[i].accumulatedTime
                        Log.d("log", name)
                        Log.d("log", achievementRate.toString())
                        Log.d("log", accumulatedTime)

                        // 리스트에 추가
                        friendListview.viewFriend(
                            friendview(
                                name,
                                achievementRate,
                                accumulatedTime
                            )
                        )
                    }
                }
            }
            override fun onFailure(call: Call<CheckGetModel2>, t: Throwable) {
                // 실패
                Log.d("log",t.message.toString())
                Log.d("log","fail")
            }
        })


        // 리싸이클러뷰 어댑터 설정
        friend_list_recyclerView.adapter = ViewAdapter(friendListview)
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = false
        manager.stackFromEnd = false
        friend_list_recyclerView.layoutManager = manager

    }

    class ViewAdapter(
        val friend_List: friendListView
    ) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val friend_name: TextView
            val friend_achievement_rate: TextView
            val friend_Cumulative_time: TextView
            //var friend_delete: ImageView

            init {
                friend_name = itemView.findViewById(R.id.friend_name)
                friend_achievement_rate = itemView.findViewById(R.id.friend_achievement_rate)
                friend_Cumulative_time = itemView.findViewById(R.id.friend_Cumulative_time)
                //friend_delete = itemView.findViewById(R.id.friend_delete)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return friend_List.FriendListview.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val friend = friend_List.FriendListview[position]

            holder.friend_name.text = friend.name
            holder.friend_achievement_rate.text = friend.achievementRate.toString()
            holder.friend_Cumulative_time.text = friend.accumulatedTime


        }
    }
}