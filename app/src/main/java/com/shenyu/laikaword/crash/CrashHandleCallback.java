package com.shenyu.laikaword.crash;

import java.util.Map;

/**
 * Created by shenyu_zxjCode on 2017/11/3 0003.
 */

public abstract class CrashHandleCallback {

    public static final int CRASHTYPE_JAVA_CRASH = 0; // Java crash
    public static final int CRASHTYPE_JAVA_CATCH = 1; // Java caught exception
    public static final int CRASHTYPE_NATIVE = 2; // Native crash
    public static final int CRASHTYPE_U3D = 3; // Unity error
    public static final int CRASHTYPE_ANR = 4; // ANR
    public static final int CRASHTYPE_COCOS2DX_JS = 5; // Cocos JS error
    public static final int CRASHTYPE_COCOS2DX_LUA = 6; // Cocos Lua error

    /**
     * Crash处理.
     *
     * @param crashType 错误类型：CRASHTYPE_JAVA，CRASHTYPE_NATIVE，CRASHTYPE_U3D ,CRASHTYPE_ANR
     * @param errorType 错误的类型名
     * @param errorMessage 错误的消息
     * @param errorStack 错误的堆栈
     * @return 返回额外的自定义信息上报
     */
    public abstract Map<String, String> onCrashHandleStart(int crashType, String errorType,
                                                           String errorMessage, String errorStack);

    /**
     * Crash处理.
     *
     * @param crashType 错误类型：CRASHTYPE_JAVA，CRASHTYPE_NATIVE，CRASHTYPE_U3D ,CRASHTYPE_ANR
     * @param errorType 错误的类型名
     * @param errorMessage 错误的消息
     * @param errorStack 错误的堆栈
     * @return byte[] 额外的2进制内容进行上报
     */
    public abstract byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType,
                                                            String errorMessage, String errorStack);

}
