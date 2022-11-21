package com.tiang.shapeview.shape

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.tiang.shapeview.R


class ShapeConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var shapeHelper: ShapeHelper? = null
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShapeConstraintLayout,
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
            typedArray.getDimensionPixelOffset(R.styleable.ShapeConstraintLayout_cl_radius, 0)
                .toFloat() // 圆角
        val radiusTopLeft = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeConstraintLayout_cl_radius_top_left,
            0
        ).toFloat() // 左上圆角
        val radiusTopRight = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeConstraintLayout_cl_radius_top_right,
            0
        ).toFloat() // 右上圆角
        val radiusBottomRight = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeConstraintLayout_cl_radius_bottom_right,
            0
        ).toFloat() // 右下圆角
        val radiusBottomLeft = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeConstraintLayout_cl_radius_bottom_left,
            0
        ).toFloat() // 左下圆角
        val solidColor =
            typedArray.getColor(R.styleable.ShapeConstraintLayout_cl_solidColor, 0) // 填充色
        val strokeWidth = typedArray.getDimensionPixelOffset(
            R.styleable.ShapeConstraintLayout_cl_strokeWidth,
            0
        ) // 描边宽度
        val strokeColor =
            typedArray.getColor(R.styleable.ShapeConstraintLayout_cl_strokeColor, 0) // 描边色
        // 按压
        val pressSolidColor =
            typedArray.getColor(R.styleable.ShapeConstraintLayout_cl_pressSolidColor, 0) // 填充色
        // 选中
        val selectedSolidColor = typedArray.getColor(
            R.styleable.ShapeConstraintLayout_cl_selectedSolidColor,
            0
        ) // 填充色
        val selectedStrokeColor = typedArray.getColor(
            R.styleable.ShapeConstraintLayout_cl_selectedStrokeColor,
            0
        ) // 描边色

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
            .setNormalDrawable(strokeColor, solidColor, startColor, endColor, gradientOrientation)
            .setPressDrawable(0, pressSolidColor)
            .setSelectedDrawable(selectedStrokeColor, selectedSolidColor)
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
        when {
            pressed -> {
                shapeHelper!!.setPressDrawable()
            }
            isSelected -> {
                shapeHelper!!.setSelectedDrawable()
            }
            else -> {
                shapeHelper!!.setNormalDrawable()
            }
        }
    }

    init {
        init(context, attrs, defStyleAttr)
    }
}