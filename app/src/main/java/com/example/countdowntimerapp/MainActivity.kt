package com.example.countdowntimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var Start_Time_In_Mills : Long = 25 * 60 * 1000
    var remain_time : Long = Start_Time_In_Mills
    var timer : CountDownTimer? = null
    var isTimerRunning = false
    val REMINING_TIME = "Remaining Time"

    lateinit var titleTv : TextView
    lateinit var timeTv : TextView
    lateinit var startBtn : Button
    lateinit var resetTv : TextView
    lateinit var pb: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializations()

        startBtn.setOnClickListener {
            if(!isTimerRunning) {
                timer(Start_Time_In_Mills)
                titleTv.text = resources.getText(R.string.keep_going)
            }

    }

        resetTv.setOnClickListener(){
            resetTimer()
        }
    }




// All Functions Of This App

    private fun initializations() {
        titleTv = findViewById(R.id.Tile_tv)
        timeTv = findViewById(R.id.time_tv)
        startBtn = findViewById(R.id.Start_btn)
        resetTv = findViewById(R.id.Reset_tv)
        pb = findViewById(R.id.progressBar)
    }

    private fun timer(starttime : Long) {
         timer = object : CountDownTimer(starttime, 1 * 1000) {
            override fun onTick(timeleft: Long) {
                remain_time = timeleft
                UpdateTimerText()
                pb.progress = remain_time.toDouble().div(Start_Time_In_Mills.toDouble()).times(100).toInt()
            }

            override fun onFinish() {

                isTimerRunning = false
            }
        }.start()
        isTimerRunning = true
    }

    private fun resetTimer(){
        timer?.cancel()
        remain_time = Start_Time_In_Mills
        UpdateTimerText()
        titleTv.text = resources.getText(R.string.take_pomodoro)
        isTimerRunning = false
        pb.progress = 100
    }

    private fun UpdateTimerText(){
        val minutes = remain_time.div(1000).div(60)
        val seconds = remain_time.div(1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        timeTv.text = formattedTime
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putLong(REMINING_TIME, remain_time)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedTime = savedInstanceState.getLong(REMINING_TIME)

        if(savedTime != Start_Time_In_Mills){
        timer(savedTime)}
    }
}