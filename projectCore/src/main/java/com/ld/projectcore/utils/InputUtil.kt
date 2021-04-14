package com.ld.projectcore.utils

import android.content.Context
import com.ld.projectcore.base.application.BaseApplication

/**
 * Created by gongxueyong on 2018/3/13.
 * 表情输入框 相关 的工具类,
 */
object InputUtil {

    private var INIT = false
    private var STATUS_BAR_HEIGHT = 25.dp

    private const val STATUS_BAR_DEF_PACKAGE = "android"
    private const val STATUS_BAR_DEF_TYPE = "dimen"
    private const val STATUS_BAR_NAME = "status_bar_height"

    /**
     * 获取 状态栏高度
     */
    @Synchronized
    fun getStatusBarHeight(): Int {
        if (!INIT) {
            val context = BaseApplication.getsInstance() ?: return STATUS_BAR_HEIGHT
            val resourceId = context.resources.getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, STATUS_BAR_DEF_PACKAGE)
            if (resourceId > 0) {
                STATUS_BAR_HEIGHT = context.resources.getDimensionPixelSize(resourceId)
                INIT = true
            }
        }
        return STATUS_BAR_HEIGHT
    }


    // 最小的 输入面板高度
    private val minPanelHeight by lazy { DimensionUtils.dp2px(220F).toInt() }
    // 最大的 输入面板高度
    private val maxPanelHeight by lazy { DimensionUtils.dp2px(380F).toInt() }

    // 最小的键盘高度,  如果一个高度小于这个高度,  则表示不是弹出然键盘,
    // 因为某些手机(华为) 会有一个底部的虚拟键盘...  弹出的时候也会到时 布局重新绘制, 也会造成一个高度差...
    val minKeyBoardHeight by lazy { DimensionUtils.dp2px(80F).toInt() }

    // 获取输入面板的高度,  根据 村粗的 键盘高度 和最小的 和最大的面板的高度比较,  取合适的高度
    fun getValidPanelHeight(): Int {
        var height = getKeyBoardHeight()
//        height = Math.max(minPanelHeight, height)
        height = Math.min(maxPanelHeight, height)
        return height
    }


    private var LAST_SAVE_KEYBOARD_HEIGHT = 0
    private const val KEY_KEYBOARD_HEIGHT = "key_keyboard_height"

    // 获取键盘高度, 根据布局变化后获取的高度, 然后存储到本地
    private fun getKeyBoardHeight(): Int {
        if (LAST_SAVE_KEYBOARD_HEIGHT == 0) {
            LAST_SAVE_KEYBOARD_HEIGHT = SPUtil.getInstance().getInt(KEY_KEYBOARD_HEIGHT, -2)
        }
        return LAST_SAVE_KEYBOARD_HEIGHT
    }

    // 保存键盘高度, 返回 true 表示保存陈功, 和原始高度不一样, 需要重新对 输入面板的高度布局
    fun saveKeyBoardHeight(height: Int): Boolean {
        if (LAST_SAVE_KEYBOARD_HEIGHT == height) return false

        if (height < 0) return false

        LAST_SAVE_KEYBOARD_HEIGHT = height

        return SPUtil.getInstance().putInt(KEY_KEYBOARD_HEIGHT, height).commit()
    }
}