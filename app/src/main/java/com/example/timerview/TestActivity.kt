package com.example.timerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.timerview.databinding.TestTimerViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*

class TestActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var uiScope: CoroutineScope
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job!!)
        var binding= TestTimerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        var time = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        var time:Long = calendar.get(Calendar.MILLISECOND).toLong()

        binding.timerView.startTimer(
            10000,
            uiScope = uiScope,
            onInterval = { time },
            onFinished = {}
        )

        Log.d("time", "$time")
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }


}