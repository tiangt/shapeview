package com.tiang.shapeview.shape

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View

class ShapeHelper(private val shapeView: View) {
    private var normalDrawable: Drawable? = null
    private var pressDrawable: Drawable? = null
    private var selectedDrawable: Drawable? = null
    var radius = 0f
        private set
    var radiusArray: FloatArray? = null
        private set
    var solidColor = 0
        private set
    var strokeWidth = 0
        private set
    var strokeColor = 0
        private set

    private fun setBackground(drawable: Drawable?) {
        if (drawable != null) shapeView.background = drawable
    }

    /**
     * 根据xml属性设置各种状态下的
     *
     * @param drawable
     * @param strokeColor
     * @param solidColor
     * @return
     */
    private fun setDrawable(
        draw: Drawable?,
        strokeColor: Int,
        solidColor: Int,
        startColor: Int = 0,
        endColor: Int = 0,
        gradientOrientation: Int = 0
    ): Drawable? {
        var strokeColor = strokeColor
        var solidColor = solidColor
        if (solidColor == 0 && strokeColor == 0 && startColor == 0 && endColor == 0) return draw
        val drawable: GradientDrawable = (draw as? GradientDrawable) ?: GradientDrawable()

        if (radius > 0) {
            drawable.cornerRadius = radius
        } else if (radiusArray != null && radiusArray!!.size == 8) {
            drawable.cornerRadii = radiusArray
        }

        if (startColor != 0 && endColor != 0) {
            drawable.colors = intArrayOf(startColor, endColor)
            drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            drawable.orientation =
                if (gradientOrientation == 0) GradientDrawable.Orientation.LEFT_RIGHT
                else GradientDrawable.Orientation.TOP_BOTTOM
        }else{
            solidColor = if (solidColor == 0) this.solidColor else solidColor
            drawable.setColor(solidColor)
        }
        strokeColor = if (strokeColor == 0) this.strokeColor else strokeColor
        drawable.setStroke(strokeWidth, strokeColor)
        return drawable
    }

    fun setNormalDrawable(gradientDrawable: GradientDrawable?): ShapeHelper {
        normalDrawable = gradientDrawable
        return this
    }

    fun setNormalDrawable(
        strokeColor: Int,
        solidColor: Int,
        startColor: Int = 0,
        endColor: Int = 0,
        gradientOrientation: Int = 0
    ): ShapeHelper {
        this.strokeColor = strokeColor
        this.solidColor = solidColor
        normalDrawable = setDrawable(
            normalDrawable,
            strokeColor,
            solidColor,
            startColor,
            endColor,
            gradientOrientation
        )
        return this
    }

    fun setPressDrawable(strokeColor: Int, solidColor: Int): ShapeHelper {
        pressDrawable = setDrawable(pressDrawable, strokeColor, solidColor)
        return this
    }

    fun setSelectedDrawable(strokeColor: Int, solidColor: Int): ShapeHelper {
        selectedDrawable = setDrawable(selectedDrawable, strokeColor, solidColor)
        return this
    }

    fun setNormalDrawable(drawable: Drawable?) {
        if (drawable == null) return
        normalDrawable = drawable
        setBackground(normalDrawable)
    }

    fun setPressDrawable(drawable: Drawable?) {
        if (drawable == null) return
        pressDrawable = drawable
        setBackground(pressDrawable)
    }

    fun setSelectedDrawable(drawable: Drawable?) {
        if (drawable == null) return
        selectedDrawable = drawable
        setBackground(selectedDrawable)
    }

    fun setNormalDrawable() {
        setNormalDrawable(normalDrawable)
    }

    fun setPressDrawable() {
        setPressDrawable(pressDrawable)
    }

    fun setSelectedDrawable() {
        setSelectedDrawable(selectedDrawable)
    }

    fun getNormalDrawable(): Drawable? {
        return normalDrawable
    }

    fun getPressDrawable(): Drawable? {
        return pressDrawable
    }

    fun getSelectedDrawable(): Drawable? {
        return selectedDrawable
    }

    fun setRadius(radius: Float): ShapeHelper {
        this.radius = radius
        return this
    }

    fun setRadius(radiusArray: FloatArray?): ShapeHelper {
        this.radiusArray = radiusArray
        return this
    }

    fun setSolidColor(solidColor: Int): ShapeHelper {
        this.solidColor = solidColor
        return this
    }

    fun setStrokeWidth(strokeWidth: Int): ShapeHelper {
        this.strokeWidth = strokeWidth
        return this
    }

    fun setStrokeColor(strokeColor: Int): ShapeHelper {
        this.strokeColor = strokeColor
        return this
    }

    companion object {
        /**
         * dp 转 px
         *
         * @param dpValue dp 值
         * @return px 值
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}