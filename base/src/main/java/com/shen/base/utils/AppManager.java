package com.shen.base.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author:Shen
 * @time: 2022/3/4 15:45
 * @desc:App activity栈的管理类
 **/
public class AppManager {

    public static Stack<WeakReference<Activity>> getActivityStack() {
        return activityStack;
    }

    private static Stack<WeakReference<Activity>> activityStack;

    private static AppManager instance;

    private AppManager() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        activityStack.add(activityWeakReference);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            return activityStack.lastElement().get();
        }
        return null;
    }


    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            Activity activity = activityStack.lastElement().get();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        Iterator iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> activity = (WeakReference<Activity>) iterator.next();
            if (activity != null && activity.get() != null && activity.get().getClass().equals(cls)) {
                iterator.remove();
                activity.get().finish();
            }
        }
    }

    public void finishOtherActivity(Class<?> cls) {
        Iterator iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> activity = (WeakReference<Activity>) iterator.next();
            if (activity != null && activity.get() != null && !activity.get().getClass().equals(cls)) {
                iterator.remove();
                activity.get().finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    @SuppressWarnings("WeakerAccess")
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (activityStack.get(i).get() != null) {
                    activityStack.get(i).get().finish();
                }
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            for (WeakReference<Activity> activityWeakReference : activityStack) {
                if (activity.equals(activityWeakReference.get())) {
                    activityStack.remove(activityWeakReference);
                    break;
                }
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (WeakReference<Activity> activity : activityStack) {
            if (activity != null && activity.get() != null && activity.get().getClass().equals(cls)) {
                return activity.get();
            }
        }
        return null;
    }

    /**
     * 获取指定类在栈中的索引
     *
     * @param cls
     * @return 当前Activity在栈中的索引,-1代表获取索引失败
     */
    public int getActivityIndex(Class<?> cls) {
        int index = -1;
        for (int i = 0; i < activityStack.size(); i++) {
            WeakReference<Activity> activity = activityStack.get(i);
            if (activity != null && activity.get() != null && activity.get().getClass().equals(cls)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 返回当前栈中activity的size
     *
     * @return
     */
    public int getActivityStackSize() {
        if (activityStack != null) {
            return activityStack.size();
        }
        return 0;
    }


    /**
     * 删除页面的索引到栈顶的所有Activity
     */
    public void finishIndexToLastElement(int index) {
        if (index >= 0) {
            for (int i = activityStack.size() - 1; i >= index; i--) {
                WeakReference<Activity> activityRef = activityStack.get(i);
                if (activityRef != null) {
                    Activity activity = activityRef.get();
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
        }
    }
}
