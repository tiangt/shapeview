package com.tiang.shapeview.shape

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.tiang.shapeview.R

class ShapeRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var shapeHelper: ShapeHelper? = null
    private var strokeColor = 0
    private var shapeSolidColor = 0
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShapeRelativeLayout,
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
            typedArray.getDimensionPixelOffset(R.styleable.ShapeRelativeLayout_rl_radius, 0)
                .toFloat() // 圆角
        val radiusTopLeft = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeRelativeLayout_rl_radius_top_left,
            0
        ).toFloat() // 左上圆角
        val radiusTopRight = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeRelativeLayout_rl_radius_top_right,
            0
        ).toFloat() // 右上圆角
        val radiusBottomRight = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeRelativeLayout_rl_radius_bottom_right,
            0
        ).toFloat() // 右下圆角
        val radiusBottomLeft = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeRelativeLayout_rl_radius_bottom_left,
            0
        ).toFloat() // 左下圆角
        shapeSolidColor =
            typedArray.getColor(R.styleable.ShapeRelativeLayout_rl_solidColor, 0) // 填充色
        val strokeWidth = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeRelativeLayout_rl_strokeWidth,
            0
        ) // 描边宽度
        strokeColor =
            typedArray.getColor(R.styleable.ShapeRelativeLayout_rl_strokeColor, 0) // 描边色
        // 按压
        val pressSolidColor =
            typedArray.getColor(R.styleable.ShapeRelativeLayout_rl_pressSolidColor, 0) // 填充色
        // 选中
        val selectedSolidColor =
            typedArray.getColor(R.styleable.ShapeRelativeLayout_rl_selectedSolidColor, 0) // 填充色
        val selectedStrokeColor =
            typedArray.getColor(R.styleable.ShapeRelativeLayout_rl_selectedStrokeColor, 0) // 描边色
        val startColor = typedArray.getColor(R.styleable.ShapeConstraintLayout_cl_startColor, 0)
        val endColor = typedArray.getColor(R.styleable.ShapeConstraintLayout_cl_endColor, 0)
        var gradientOrientation = 0
        if (startColor != 0 && endColor != 0) {
            gradientOrientation = commonType.getInt(R.styleable.ShapeView_gradientOrientation, 0)
        }
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
            .setNormalDrawable(strokeColor, shapeSolidColor,startColor, endColor, gradientOrientation)
            .setPressDrawable(0, pressSolidColor)
            .setSelectedDrawable(selectedStrokeColor, selectedSolidColor)
        shapeHelper!!.setNormalDrawable()
    }

    fun setRadius(radius: Int) {
        shapeHelper!!.setRadius(radius.toFloat())
        shapeHelper!!.setNormalDrawable()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            shapeHelper!!.setSelectedDrawable()
        } else {
            shapeHelper!!.setNormalDrawable()
        }
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        if (pressed) {
            shapeHelper!!.setPressDrawable()
        } else if (isSelected) {
            shapeHelper!!.setSelectedDrawable()
        } else {
            shapeHelper!!.setNormalDrawable()
        }
    }

    fun setShapeSolidColor(color: Int) {
        shapeSolidColor = color
        shapeHelper!!.setNormalDrawable(strokeColor, color)
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        shapeHelper!!.setNormalDrawable(color, solidColor)
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