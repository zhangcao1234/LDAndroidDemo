package com.ld.projectcore.utils;

/**
 * @author Bryce
 * @Namel
 * @Description: 检查用户快速点击，防止频繁发送网络请求
 * @date 2015/10/30
 * @time 9:13
 */
public class FastClickUtils {

    private static long time;

    private static class SingletonHolder {
        private static final FastClickUtils INSTANCE = new FastClickUtils();
    }

    private FastClickUtils() {
    }

    public static final FastClickUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        if (curTime - time < 400) {
            time = curTime;
            return true;
        } else {
            time = curTime;
            return false;
        }

    }

    /**
     * 自定义时间
     *
     * @param millisecond 毫秒值
     * @return true表示在millisecond内
     */
    public boolean isFastClick(long millisecond) {
        long curTime = System.currentTimeMillis();
        if (curTime - time < millisecond) {
            time = curTime;
            return true;
        } else {
            time = curTime;
            return false;
        }

    }

}
