package com.tiang.shapeview.shape

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.tiang.shapeview.R

class ShapeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var shapeHelper: ShapeHelper? = null
    private var selectedTextColor = 0
    private var normalTextColor = 0
    private var strokeColor = 0
    private var shapeSolidColor = 0
    private var startColor = 0
    private var endColor = 0
    private var gradientOrientation = 0
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShapeTextView,
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
        val radius = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_tv_radius, 0)
            .toFloat() // 圆角
        val radiusTopLeft =
            typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_tv_radius_top_left, 0)
                .toFloat() // 左上圆角
        val radiusTopRight =
            typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_tv_radius_top_right, 0)
                .toFloat() // 右上圆角
        val radiusBottomRight = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeTextView_tv_radius_bottom_right,
            0
        ).toFloat() // 右下圆角
        val radiusBottomLeft = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeTextView_tv_radius_bottom_left,
            0
        ).toFloat() // 左下圆角
        shapeSolidColor = typedArray.getColor(R.styleable.ShapeTextView_tv_solidColor, 0) // 填充色
        val strokeWidth = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeTextView_tv_strokeWidth,
            0
        ) // 描边宽度
        strokeColor = typedArray.getColor(R.styleable.ShapeTextView_tv_strokeColor, 0) // 描边色
        // 按压
        val pressSolidColor =
            typedArray.getColor(R.styleable.ShapeTextView_tv_pressSolidColor, 0) // 填充色
        // 选中
        val selectedSolidColor =
            typedArray.getColor(R.styleable.ShapeTextView_tv_selectedSolidColor, 0) // 填充色
        val selectedStrokeColor =
            typedArray.getColor(R.styleable.ShapeTextView_tv_selectedStrokeColor, 0) // 描边色

        startColor = typedArray.getColor(R.styleable.ShapeTextView_tv_startColor, 0)
        endColor = typedArray.getColor(R.styleable.ShapeTextView_tv_endColor, 0)
        if (startColor != 0 && endColor != 0) {
            gradientOrientation = commonType.getInt(R.styleable.ShapeView_gradientOrientation, 0)
        }

        gravity = Gravity.CENTER
        //字体颜色
        normalTextColor = currentTextColor
        selectedTextColor =
            typedArray.getColor(R.styleable.ShapeTextView_tv_selectedTextColor, 0)
        selectedTextColor = if (selectedTextColor == 0) normalTextColor else selectedTextColor
        typedArray.recycle()
        commonType.recycle()
        shapeHelper = ShapeHelper(this)
            .setRadius(
                floatArrayOf(
                    radiusTopLeft,
                    radiusTopLeft,
                    radiusTopRight,
                    radiusTopRight,
                    radiusBottomRight,
                    radiusBottomRight,
                    radiusBottomLeft,
                    radiusBottomLeft
                )
            )
            .setRadius(radius).setStrokeWidth(strokeWidth)
            .setNormalDrawable(
                strokeColor,
                shapeSolidColor,
                startColor,
                endColor,
                gradientOrientation
            )
            .setPressDrawable(0, pressSolidColor)
            .setSelectedDrawable(selectedStrokeColor, selectedSolidColor)
        shapeHelper!!.setNormalDrawable()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            shapeHelper!!.setSelectedDrawable()
            setTextColor(selectedTextColor)
        } else {
            shapeHelper!!.setNormalDrawable()
            setTextColor(normalTextColor)
        }
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        if (pressed) {
            shapeHelper!!.setPressDrawable()
        }
    }

    fun setShapeSolidColor(color: Int) {
        shapeSolidColor = color
        shapeHelper!!.setNormalDrawable(strokeColor, color)
    }

    fun setGradientColor(startColor: Int, endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
        shapeHelper!!.setNormalDrawable(
            strokeColor,
            shapeSolidColor,
            this.startColor,
            this.endColor,
            gradientOrientation
        )
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        shapeHelper!!.setNormalDrawable(color, solidColor)
    }

    fun setSelectedStrokeAndSolidColor(sColor: Int, soColor: Int) {
        strokeColor = sColor
        shapeSolidColor = soColor
        shapeHelper!!.setSelectedDrawable(sColor, soColor)
    }

    fun setStrokeWidth(width: Int) {
        shapeHelper!!.setStrokeWidth(width)
    }

    fun setRadius(radius: Float) {
        shapeHelper!!.setRadius(radius)
    }

    fun setStrokeAndSolidColor(sColor: Int, soColor: Int) {
        strokeColor = sColor
        shapeSolidColor = soColor
        shapeHelper!!.setNormalDrawable(sColor, soColor)
    }

    init {
        init(context, attrs, defStyleAttr)
    }
}