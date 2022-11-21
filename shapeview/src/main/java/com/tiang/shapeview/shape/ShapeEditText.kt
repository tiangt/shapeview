package com.tiang.shapeview.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.tiang.shapeview.R

class ShapeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    private var shapeHelper: ShapeHelper? = null
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShapeEditText,
            defStyleAttr,
            0
        )
        val commonType = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShapeView,
            defStyleAttr,
            0
        )
        // 公共
        val radius =
            typedArray.getDimensionPixelOffset(R.styleable.ShapeEditText_et_radius, 0) // 圆角
        val solidColor = typedArray.getColor(R.styleable.ShapeEditText_et_solidColor, 0) // 填充色
        val strokeWidth = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeEditText_et_strokeWidth,
            0
        ) // 描边宽度
        val strokeColor = typedArray.getColor(R.styleable.ShapeEditText_et_strokeColor, 0) // 描边色

        val startColor = typedArray.getColor(R.styleable.ShapeEditText_et_startColor, 0)
        val endColor = typedArray.getColor(R.styleable.ShapeEditText_et_endColor, 0)
        var gradientOrientation = 0
        if (startColor != 0 && endColor != 0) {
            gradientOrientation = commonType.getInt(R.styleable.ShapeView_gradientOrientation, 0)
        }
        typedArray.recycle()
        commonType.recycle()
        shapeHelper = ShapeHelper(this)
            .setRadius(radius.toFloat()).setStrokeWidth(strokeWidth)
            .setNormalDrawable(strokeColor, solidColor,startColor,endColor, gradientOrientation)
        shapeHelper!!.setNormalDrawable()
    }

    init {
        init(context, attrs, defStyleAttr)
    }
}