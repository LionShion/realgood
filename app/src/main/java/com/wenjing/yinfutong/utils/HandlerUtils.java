package com.wenjing.yinfutong.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Printer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HandlerUtils {

    private static final String TAG = "HandlerUtils";

//	private static MZooerHandler zooerHandler;

//	private static Object mZooerHandlerLock = new Object();

    private static Handler mainHandler;

    private static Object mMainHandlerLock = new Object();

    /**
     * 该Handler经过了优化，优化了主线程的繁忙程度
     *
     * @return
     */
    public static Handler getMainZooerHandler() {
//		if(zooerHandler == null){
//			synchronized (mZooerHandlerLock) {
//				if(zooerHandler == null){
//					zooerHandler = new MZooerWeakReferenceHandler(Looper.getMainLooper(),null);
//				}
//			}
//		}
//		return zooerHandler;
        return getMainHandler();
    }

    /**
     * 未经优化的handler使用系统handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        if (mainHandler == null) {
            synchronized (mMainHandlerLock) {
                mainHandler = new Handler(Looper.getMainLooper());
            }
        }
        return mainHandler;
    }

    private static Map<String, Handler> handlerMap = Collections.synchronizedMap(new HashMap<String, Handler>());


    /**
     * 取得一个非主线Handler，每次获得这个handler都会产生一个线程，所以获得到的这个handler必须做复用！！！
     *
     * @param threadName
     * @return
     */
    public static Handler getHandler(String threadName) {
        if (TextUtils.isEmpty(threadName)) {
            threadName = "default-thread";
        }
        TLog.e(TAG, "getHandler(" + threadName + ") exists at cache:" + handlerMap.containsKey(threadName));
        Handler handler = null;
        if (handlerMap.containsKey(threadName)) {
            handler = handlerMap.get(threadName);
            if (TLog.DEBUG) {
                // 打印Handler的Message队列信息，这个可能会影响Handler的处理性能，因为内部有个同步锁
                Printer printer = new LogPrinter(Log.DEBUG, TAG);
                // print handler and looper info , contain messages count.
                // 打印出Handler和Looper对象的信息，包含Looper中的Message个数等。
                handler.dump(printer, "getHandler(" + threadName + ")");
            }
        } else {
            try {
                HandlerThread handlerThread = new HandlerThread(threadName);
                handlerThread.start();
                Looper loop = handlerThread.getLooper();
                if (loop != null) {
                    handler = new Handler(loop);
                    handlerMap.put(threadName, handler);
                } else {
                    handlerThread.quit();
                    handlerThread = null;
                }
            } catch (StackOverflowError ignore) {
                // TODO: why java.lang.StackOverflowError: thread creation failed?
            }
        }
        return handler;
    }
}
