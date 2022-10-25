package com.example.timerview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.countdown_timer_view.utils.dp
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
        var binding = TestTimerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        var time = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        var time: Long = calendar.get(Calendar.MILLISECOND).toLong()

        binding.timerView.startTimer(
            20000,
            uiScope = uiScope,
            onTimerInterval = { time },
            onTimerFinished = {}
        )
        binding.ivPause.setOnClickListener {
            binding.timerView.pauseTimer()
            binding.ivPause.isVisible = false
            binding.ivPlay.isVisible = true
            binding.timerView.updateTimerUIState(
                ContextCompat.getColor(this, R.color.grey),
                ContextCompat.getColor(this, R.color.black_transparent_50),
                4.dp,
                10.dp
            )
        }
        binding.ivPlay.setOnClickListener {
            binding.timerView.resumeTimer()
            binding.ivPause.isVisible = true
            binding.ivPlay.isVisible = false
            binding.timerView.updateTimerUIState(
                ContextCompat.getColor(this, R.color.green_33009F6B),
                ContextCompat.getColor(this, R.color.green_99009F6B),
                4.dp,
                10.dp
            )
        }
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }


}