package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_timer.*
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TimerActivity : AppCompatActivity() {
    // 핸들러사용
    val handler = Handler()
    var timeValue = 0
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        var now = System.currentTimeMillis()
        var date = Date(now)
        var format = SimpleDateFormat("yyyy.MM.dd")
        var todayDate = format.format(date)
        var strtime = "00:00:00"

        val sp = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sp.edit()
        today.text = todayDate
        btn_stop.visibility = View.GONE

        (application as MasterApplication).service.gettime().enqueue(object : Callback<Timer> {
            override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
                val timer = response.body()
                val time = timer!!.time!!
                all_time.text = time
            }

            override fun onFailure(call: Call<Timer>, t: Throwable) {
                Toast.makeText(this@TimerActivity, "서버 오류", Toast.LENGTH_SHORT).show()
            }
        })

        //핸들러 - 1초마다 실행되게 함
        val runnable = object : Runnable {
            override fun run() {
                timeValue ++
                //TextView 업데이트 하기
                timeToText(timeValue)?.let {
                    time.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        btn_start.setOnClickListener {
            it.visibility = View.GONE
            btn_stop.visibility = View.VISIBLE
            handler.post(runnable)

            (application as MasterApplication).service.status("true").enqueue(object : Callback<Timer> {
                override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(this@TimerActivity, "상태 오류", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Timer>, t: Throwable) {
                    Toast.makeText(this@TimerActivity, "서버 오류", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btn_stop.setOnClickListener {
            it.visibility = View.GONE
            btn_start.visibility = View.VISIBLE
            count++
            strtime = time.text.toString()
            recordView.text = recordView.text.toString()+ count.toString()+"회차 - " + strtime + "\n"
            time.text = ""
            timeValue = 0
            handler.removeCallbacks(runnable)
            timeToText()?.let {
                time.text = it
            }

            var timer = Timer(time=strtime)
            (application as MasterApplication).service.timer(timer).enqueue(object : Callback<Timer> {
                override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@TimerActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@TimerActivity, "저장 오류", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Timer>, t: Throwable) {
                    Toast.makeText(this@TimerActivity, "서버 오류", Toast.LENGTH_SHORT).show()
                }
            })

            (application as MasterApplication).service.status("false").enqueue(object : Callback<Timer> {
                override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(this@TimerActivity, "상태 오류", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Timer>, t: Throwable) {
                    Toast.makeText(this@TimerActivity, "서버 오류", Toast.LENGTH_SHORT).show()
                }
            })

            (application as MasterApplication).service.gettime().enqueue(object : Callback<Timer> {
                override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
                    val timer = response.body()
                    val time = timer!!.time!!
                    all_time.text = time
                }

                override fun onFailure(call: Call<Timer>, t: Throwable) {
                    Toast.makeText(this@TimerActivity, "서버 오류", Toast.LENGTH_SHORT).show()
                }
            })
        }

        mypage.setOnClickListener { startActivity(Intent(this@TimerActivity, MypageActivity::class.java)) }
    }

    private fun timeToText(time: Int = 0) : String{
        return if (time <= 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}