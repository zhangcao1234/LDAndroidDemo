package com.ld.projectcore.utils

import android.view.View
import java.math.BigDecimal
import java.text.NumberFormat


/**
 * Created by gongxueyong on 2018/3/16.
 * kotlin 的扩展文件 ,
 */


val Int.dp: Int
    get() {
        return DimensionUtils.dp2px(this.toFloat()).toInt()
    }

val Int.sp: Int
    get() {
        return DimensionUtils.sp2px(this.toFloat()).toInt()
    }

val Float.dp: Float
    get() {
        return DimensionUtils.dp2px(this)
    }

fun setGone(gone: Boolean, vararg views: View) {
    views.forEach {
        if (gone) {
            if (it.visibility != View.GONE) it.visibility = View.GONE
        } else {
            if (it.visibility != View.VISIBLE) it.visibility = View.VISIBLE
        }
    }
}

fun setInVisible(inVisible: Boolean, vararg views: View) {
    views.forEach {
        if (inVisible) {
            if (it.visibility != View.INVISIBLE) it.visibility = View.INVISIBLE
        } else {
            if (it.visibility != View.VISIBLE) it.visibility = View.VISIBLE
        }
    }
}

//转化为百分比形式，digits为保留小数点多少位
fun Float.toPercent(digits: Int): String {
    val nt = NumberFormat.getPercentInstance()
    nt.setMinimumFractionDigits(digits)
    return nt.format(this)
}

//scale为保留小数点多少位
fun Float.format(scale: Int): Float {
    val roundingMode = 4//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
    var bd = BigDecimal((this.toDouble()))
    bd = bd.setScale(scale, roundingMode)
    return bd.toFloat()
}
//scale为保留小数点多少位
fun Double.format(scale: Int): Double {
    val roundingMode = 4//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
    var bd = BigDecimal(this)
    bd = bd.setScale(scale, roundingMode)
    return bd.toDouble()
}
