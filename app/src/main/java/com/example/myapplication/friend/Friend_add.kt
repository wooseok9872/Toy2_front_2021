package com.example.myapplication

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.friend.*
import kotlinx.android.synthetic.main.friend_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Friend_add : AppCompatActivity() {
    val api = APIS.create()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_add)

        // 검색어를 입력할 Input 창
        val friend_search_input : EditText= findViewById(R.id.friend_search_input) as EditText
        // 검색어 검색 버튼
        val friend_search_button : Button= findViewById(R.id.friend_search_button) as Button
        // 친구 추가 버튼
        val friend_add_button:Button = findViewById(R.id.friend_add_button) as Button

        val back_button: ImageView = findViewById(R.id.back_button) as ImageView



        val friendaddlist: friendaddList = friendaddList()


        // 추가할 친구 검색 기능 구현
        friend_search_button.setOnClickListener {
            api.get_users(friend_search_input.text.toString()).enqueue(object : Callback<CheckGetModel> {
                override fun onResponse(call: Call<CheckGetModel>, response: Response<CheckGetModel>) {
                    Log.d("log", response.toString())

                    // <받은 유저정보 -닉네임, 이메일- 리사이클러뷰로 보이기>
                    if(response.isSuccessful){
                        // 리스트로 출력이 안됨, 로그는 찍힘
                        for(i in response.body()!!.checkRoomList.indices){
                            val name= response.body()!!.checkRoomList[i].username
                            val email= response.body()!!.checkRoomList[i].email
                            Log.d("log", name)
                            Log.d("log", email)

                            // 리스트에 추가
                            friendaddlist.addFriend(
                                addlist(
                                    name,
                                    email
                                )
                            )

                        }

                    }
                    else{
                        Toast.makeText(this@Friend_add, "존재하지 않는 닉네임입니다", Toast.LENGTH_LONG).show()
                    }

                }
                override fun onFailure(call: Call<CheckGetModel>, t: Throwable) {
                    // 실패
                    Log.d("log",t.message.toString())
                    Log.d("log","fail")
                }
            })
        }


        friend_add_button.setOnClickListener {
//            api.post_users(name.toString()).enqueue(object : Callback<PostModel> {
//                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
//                    Log.d("log", response.toString())
//                    // 현재 code가 계속 400으로 뜸 (존재하지 않는 계정으로)!!!!!!
//
//                    if(response.body()!!.code == 201){
//                        Toast.makeText(this@Friend_add, response.body()!!.message, Toast.LENGTH_LONG).show()
//
//                        // <선택한 listView 추가하는 기능 추가해야함>
//
//
//
//                        finish()
//                    }
//                    else if(response.body()!!.code == 400){
//                        Toast.makeText(this@Friend_add, response.body()!!.message, Toast.LENGTH_LONG).show()
//                    }
//                    else if(response.body()!!.code == 401){
//                        Toast.makeText(this@Friend_add, response.body()!!.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//                override fun onFailure(call: Call<PostModel>, t: Throwable) {
//                    // 실패
//                    Log.d("log",t.message.toString())
//                    Log.d("log","fail")
//                }
//            })
        }


        back_button.setOnClickListener {
            finish()
        }


        // 리싸이클러뷰 어댑터 설정
        friend_add_recyclerView.adapter = Friend_Adapter(friendaddlist)
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = false
        manager.stackFromEnd = false
        friend_add_recyclerView.layoutManager = manager


//        val adapter = Friend_Adapter(friendaddlist)
//        adapter.setItemClickListener(object : Friend_Adapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                val item = friendaddlist.FriendaddList[position]
//
//                Toast.makeText(v.context, "Activity\n${item.name}\n${item.email}", Toast.LENGTH_SHORT).show()
//
//                adapter.notifyDataSetChanged()
//            }
//        })
//        friend_add_recyclerView.adapter = adapter
//        val manager = LinearLayoutManager(this)
//        manager.reverseLayout = false
//        manager.stackFromEnd = false
//        friend_add_recyclerView.layoutManager = manager

    }


    class Friend_Adapter(
        val friend_addList: friendaddList
    ) : RecyclerView.Adapter<Friend_Adapter.ViewHolder>() {


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val friend_name: TextView
            val friend_Email: TextView
            init {
                friend_name = itemView.findViewById(R.id.friend_name_item)
                friend_Email = itemView.findViewById(R.id.friend_Email_item)
            }




        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_add_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return friend_addList.FriendaddList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val friend = friend_addList.FriendaddList[position]

            holder.friend_name.text = friend.name
            holder.friend_Email.text = friend.email



        }

//        //ClickListener
//        interface OnItemClickListener {
//            fun onClick(v: View, data: ProfileData, position: Int)
//        }
//        private lateinit var itemClickListener : OnItemClickListener
//        fun setItemClickListener(itemClickListener: OnItemClickListener) {
//            this.itemClickListener = itemClickListener
//        }
    }

}