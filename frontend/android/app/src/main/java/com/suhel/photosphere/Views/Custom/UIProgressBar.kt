package com.suhel.photosphere.Views.Custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import com.suhel.photosphere.R

class UIProgressBar : View {

    var max = 100.0f
        set(value) {
            field = value
            invalidate()
        }

    var progress = 0.0f
        set(value) {
            field = value
            invalidate()
        }

    @ColorInt var progressColor = 0xFF000000.toInt()
        set(value) {
            field = value
            invalidate()
        }

    private var barPaint: Paint = Paint()
    private val drawRect: RectF = RectF(0.0f, 0.0f, 0.0f, 0.0f)
    private val clipRect: Rect = Rect()

    init {
        barPaint.color = progressColor
        barPaint.style = Paint.Style.FILL
        barPaint.alpha = (this.alpha * 255.0f).toInt()
        barPaint.isAntiAlias = true
    }

    constructor(context: Context?) : super(context) {

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        val a = context?.theme?.obtainStyledAttributes(attrs, R.styleable.UIProgressBar, 0, 0)

        try {
            this.progress = a?.getFloat(R.styleable.UIProgressBar_progress, 0.0f) ?: 0.0f
            this.max = a?.getFloat(R.styleable.UIProgressBar_max, 100.0f) ?: 100.0f
            this.progressColor = a?.getColor(R.styleable.UIProgressBar_progressColor, 0xFF000000.toInt())
                    ?: 0xFF000000.toInt()
        } finally {
            a?.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            it.getClipBounds(this.clipRect)

            val radius = this.clipRect.height() / 2.0f

            val progressValue = Math.max(Math.min(this.clipRect.width().toFloat(),
                    (this.clipRect.width() * this.progress) / this.max), 0.0f)

            val scaledProgressValue = progressValue *
                    (1 - (radius * 2 / this.clipRect.width().toFloat()))

            this.drawRect.right = scaledProgressValue + (radius * 2.0f)
            this.drawRect.bottom = this.clipRect.bottom.toFloat()

            it.drawRoundRect(this.drawRect, radius, radius, this.barPaint)
        }

    }
}