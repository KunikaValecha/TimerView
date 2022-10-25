package com.example.countdown_timer_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.example.countdown_timer_view.databinding.TimerViewBinding
import com.example.countdown_timer_view.utils.countDownTimer
import com.example.countdown_timer_view.utils.milliSecondsToCountDown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * CountDown Timer view with outline animation
 * **/
class TimerView : ConstraintLayout {

    //Binding
    private lateinit var binding: TimerViewBinding

    //Reference to existing timer job
    private var countDownJob: Job? = null

    //Cell Outline Radius
    private var outlineRadius: Int? = null

    private var isPaused = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(attributeSet)
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attributeSet, defStyleAttr) {
        init(attributeSet)
    }


    private fun init() {
        binding = TimerViewBinding.inflate(LayoutInflater.from(context), this, false)
        this.addView(binding.root)
    }

    private fun init(attributeSet: AttributeSet?) {
        init()
        val typedArray =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.CustomTimerView, 0, 0)

        setOutlineUI(typedArray)

        setCellTextSize(typedArray)

        setCellTextColor(typedArray)

        setCellLayout(typedArray)

        updateHeaderVisibility(typedArray)

        typedArray.recycle()
    }

    /**
     * Function to update timer view outline UI
     * **/
    private fun setOutlineUI(typedArray: TypedArray) {

        //Cell Background Color
        val cellBackgroundColor =
            typedArray.getColor(R.styleable.CustomTimerView_timer_backgroundColor, Color.WHITE)

        //Cell Background Outline Color
        val outlineBaseColor =
            typedArray.getColor(
                R.styleable.CustomTimerView_timer_backgroundOutlineColor,
                Color.BLACK
            )

        //Cell Background Outline Width
        val outlineWidth =
            typedArray.getDimensionPixelSize(
                R.styleable.CustomTimerView_timer_backgroundOutlineWidth,
                1
            )

        //Cell Background Outline Radius
        outlineRadius =
            typedArray.getDimensionPixelSize(
                R.styleable.CustomTimerView_timer_backgroundOutlineRadius,
                4
            )

        val outline = GradientDrawable()
        outline.setStroke(outlineWidth, outlineBaseColor)
        outline.setColor(cellBackgroundColor)
        outline.cornerRadius = outlineRadius!!.toFloat()

        //Hours Cell Outline
        binding.tvHr1.background = outline
        binding.tvHr2.background = outline

        //Minutes Cell Outline
        binding.tvMin1.background = outline
        binding.tvMin2.background = outline

        //Seconds Cell Outline
        binding.tvSec1.background = outline
        binding.tvSec2.background = outline
    }

    /**
     * Function to update text size
     * **/
    private fun setCellTextSize(typedArray: TypedArray) {
        val cellTextSize =
            typedArray.getDimensionPixelSize(R.styleable.CustomTimerView_timer_textViewSize, 14)
                .toFloat()

        //Hours Text Size
        binding.tvHr1.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
        binding.tvHr2.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)

        //Minutes Text Size
        binding.tvMin1.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
        binding.tvMin2.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)

        //Seconds Text Size
        binding.tvSec1.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
        binding.tvSec2.setTextSize(TypedValue.COMPLEX_UNIT_PX, cellTextSize)
    }

    /**
     * Function to update text color
     * **/
    private fun setCellTextColor(typedArray: TypedArray) {
        val cellTextColor =
            typedArray.getColor(R.styleable.CustomTimerView_timer_textViewColor, Color.WHITE)

        //Hours Text Color
        binding.tvHr1.setTextColor(cellTextColor)
        binding.tvHr2.setTextColor(cellTextColor)
        binding.tvSeparatorHr.setTextColor(cellTextColor)

        //Minutes Text Color
        binding.tvMin1.setTextColor(cellTextColor)
        binding.tvMin2.setTextColor(cellTextColor)
        binding.tvSeparatorMin.setTextColor(cellTextColor)

        //Seconds Text Color
        binding.tvSec1.setTextColor(cellTextColor)
        binding.tvSec2.setTextColor(cellTextColor)
    }

    /**
     * Function to update width and height of single cell of entire view
     * **/
    private fun setCellLayout(typedArray: TypedArray) {
        val cellHeight =
            typedArray.getDimension(R.styleable.CustomTimerView_timer_cellHeight, 18f)

        val cellWidth =
            typedArray.getDimension(R.styleable.CustomTimerView_timer_cellWidth, 18f)

        //Hours Height & Width
        binding.tvHr1.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvHr2.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }

        //Minutes Height & Width
        binding.tvMin1.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvMin2.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }

        //Seconds Height & Width
        binding.tvSec1.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
        binding.tvSec2.updateLayoutParams {
            height = cellHeight.toInt()
            width = cellWidth.toInt()
        }
    }

    /**
     * Function to show/hide visibility of header viz. Hour, Min, Sec
     * **/
    private fun updateHeaderVisibility(typedArray: TypedArray) {
        val shouldShowHeaders =
            typedArray.getBoolean(R.styleable.CustomTimerView_timer_shouldShowHeaders, false)

        //Hours Title Display
        binding.tvHourTitle.isVisible = shouldShowHeaders

        //Minutes Title Display
        binding.tvMinTitle.isVisible = shouldShowHeaders

        //Seconds Title Display
        binding.tvSecTitle.isVisible = shouldShowHeaders
    }

    /**
     * Function to set countdown in milliseconds
     * **/
    fun startTimer(
        durationInMillis: Long,
        uiScope: CoroutineScope,
        onTimerFinished: () -> Unit,
        onTimerInterval: () -> Unit
    ) {
        countDownJob?.cancel()
        countDownJob = uiScope.countDownTimer(
            durationInMillis,
            onInterval = {
                val values = it.milliSecondsToCountDown()
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

                onTimerInterval.invoke()
            },
            onFinished = {
                binding.tvHr1.text = "0"
                binding.tvHr2.text = "0"
                binding.tvMin1.text = "0"
                binding.tvMin2.text = "0"
                binding.tvSec1.text = "0"
                binding.tvSec2.text = "0"

                onTimerFinished.invoke()
            },
            onPaused = {
                isPaused
            }
        )
    }

    /**
     * Function to pause countdown
     * **/
    fun pauseTimer() {
        isPaused = true
    }

    /**
     * Function to resume countdown
     * **/
    fun resumeTimer() {
        isPaused = false
    }

    /**
     * Function to change state of timer-view
     * **/
    fun updateTimerUIState(
        bgColor: Int,
        outlineColor: Int,
        strokeWidth: Int,
        cornerRadius: Int
    ) {
        val outline = GradientDrawable()
        outline.setStroke(strokeWidth, outlineColor)
        outline.setColor(bgColor)
        outline.cornerRadius = cornerRadius.toFloat()

        //Hours Cell Outline
        binding.tvHr1.background = outline
        binding.tvHr2.background = outline

        //Minutes Cell Outline
        binding.tvMin1.background = outline
        binding.tvMin2.background = outline

        //Seconds Cell Outline
        binding.tvSec1.background = outline
        binding.tvSec2.background = outline
    }

    /**
    * Timer gets cancel automatically when scope gets destroyed
    * But to explicitly cancel timer you below function can be used
    * **/
    fun cancelTimer() {
        countDownJob?.cancel()
    }
}