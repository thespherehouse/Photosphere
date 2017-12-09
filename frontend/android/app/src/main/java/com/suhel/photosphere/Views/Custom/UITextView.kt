package com.suhel.photosphere.Views.Custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.suhel.photosphere.Helper.fontName
import com.suhel.photosphere.R

class UITextView : TextView {

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context, attrs)
    }

    fun initView(context: Context?, attrs: AttributeSet?) {
        val a = context?.theme?.obtainStyledAttributes(attrs, R.styleable.UITextView, 0, 0)

        try {
            this.setTypeface(fontName(a?.getInt(R.styleable.UITextView_font, -1)))
        } finally {
            a?.recycle()
        }
    }

    fun setTypeface(path: String?) {
        path?.let {
            this.typeface = Typeface.createFromAsset(context.assets, path)
        }
    }
}