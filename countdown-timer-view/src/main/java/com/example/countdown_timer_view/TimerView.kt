package com.example.countdown_timer_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.example.countdown_timer_view.databinding.TimerViewBinding
import com.example.countdown_timer_view.utils.countDownTimer
import com.example.countdown_timer_view.utils.secondsToCountDown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class TimerView @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var binding: TimerViewBinding

    private var countDownJob: Job? = null

    init {
        binding = TimerViewBinding.inflate(LayoutInflater.from(context), this, true)
        renderView()
    }

    fun renderView() {
        if (attributeSet != null) {
            val typedArray =
                context.theme.obtainStyledAttributes(
                    attributeSet,
                    R.styleable.CustomTimerView,
                    defStyleAttr,
                    0
                )

            val backgroundColor = typedArray.getColor(
                R.styleable.CustomTimerView_timer_backgroundColor,
                Color.WHITE
            )

            val cellHeight =
                typedArray.getDimension(R.styleable.CustomTimerView_timer_cellHeight, 18f)

            val cellWidth =
                typedArray.getDimension(R.styleable.CustomTimerView_timer_cellWidth, 18f)

            val cellTextSize =
                typedArray.getDimensionPixelSize(R.styleable.CustomTimerView_timer_textViewSize, 14)

            val cellTextColor =
                typedArray.getColor(R.styleable.CustomTimerView_timer_textViewColor, Color.WHITE)

            val shouldShowHeaders =
                typedArray.getBoolean(R.styleable.CustomTimerView_timer_shouldShowHeaders, false)

            setCellBackgroundColor(backgroundColor)

            setCellTextSize(cellTextSize.toFloat())

            setCellTextColor(cellTextColor)

            setCellLayout(cellHeight, cellWidth)

            updateHeaderVisibility(shouldShowHeaders)
        }
    }

    fun setCellBackgroundColor(backgroundColor: Int) {
        binding.tvHr1.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        binding.tvHr2.backgroundTintList = ColorStateList.valueOf(backgroundColor)

        binding.tvMin1.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        binding.tvMin2.backgroundTintList = ColorStateList.valueOf(backgroundColor)

        binding.tvSec1.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        binding.tvSec2.backgroundTintList = ColorStateList.valueOf(backgroundColor)

    }

    fun setCellTextSize(cellTextSize: Float) {
        binding.tvHr1.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
        binding.tvHr2.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)

        binding.tvMin1.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
        binding.tvMin2.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)

        binding.tvSec1.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
        binding.tvSec2.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
    }

    fun setCellTextColor(cellTextColor: Int) {
        binding.tvHr1.setTextColor(cellTextColor)
        binding.tvHr2.setTextColor(cellTextColor)
        binding.tvSeparatorHr.setTextColor(cellTextColor)

        binding.tvMin1.setTextColor(cellTextColor)
        binding.tvMin2.setTextColor(cellTextColor)
        binding.tvSeparatorMin.setTextColor(cellTextColor)

        binding.tvSec1.setTextColor(cellTextColor)
        binding.tvSec2.setTextColor(cellTextColor)
    }

    fun setCellLayout(cellHeight: Float, cellWidth: Float) {
        binding.tvHr1.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvHr2.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvMin1.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvMin2.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvSec1.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvSec2.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
    }

    fun updateHeaderVisibility(shouldShowHeaders: Boolean) {
        binding.tvHourTitle.isVisible = shouldShowHeaders
        binding.tvMinTitle.isVisible = shouldShowHeaders
        binding.tvSecTitle.isVisible = shouldShowHeaders
    }


    fun startTimer(
        durationInMillis: Long,
        uiScope: CoroutineScope,
        onFinished: () -> Unit,
        onInterval: () -> Unit
    ) {
        countDownJob?.cancel()
        countDownJob = uiScope.countDownTimer(
            durationInMillis,
            onInterval = {
                val values = it.secondsToCountDown()
                binding.tvHr1.text =
                    values.first.takeIf { it > 9L }?.toString()?.first()?.toString() ?: "0"
                binding.tvHr2.text =
                    values.first.takeIf { it > 0L }?.toString()?.last()?.toString() ?: "0"
                binding.tvMin1.text =
                    values.second.takeIf { it > 9L }?.toString()?.first()?.toString() ?: "0"
                binding.tvMin2.text =
                    values.second.takeIf { it > 0L }?.toString()?.last()?.toString() ?: "0"
                binding.tvSec1.text =
                    values.third.takeIf { it > 9L }?.toString()?.first()?.toString() ?: "0"
                binding.tvSec2.text =
                    values.third.takeIf { it > 0L }?.toString()?.last()?.toString() ?: "0"

                onInterval.invoke()
            },
            onFinished = {
                onFinished.invoke()
            }
        )
    }

    fun cancelTimer() {
        countDownJob?.cancel()
    }
}