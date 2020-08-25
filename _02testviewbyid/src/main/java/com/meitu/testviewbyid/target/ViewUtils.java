package com.meitu.testviewbyid.target;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description TODO
 * @Author shaowenwen
 * @Date 2020-08-23 22:35
 */
public class ViewUtils {

    // 目前
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    // 后期
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    // 兼容上面三个方法
    private static void inject(ViewFinder viewFinder, Object object) {
        injectField(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    private static void injectField(ViewFinder viewFinder, Object object) {
//        1.获取类里面【所有的属性】
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
//        2.获取ViewById里面的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                // 拿到R.id.test_TextView
                int viewId = viewById.value();
//        3.findViewById找到view
                View view = viewFinder.findViewById(viewId);
//        4.动态注入找到的view
                try {
                    field.setAccessible(true); // 能够修改所有的修饰符 private public
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void injectEvent(ViewFinder viewFinder, Object object) {
        // 1.获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
//        2.获取方法的注解
        for (Method method : methods) {
            OnViewClick onViewClick = method.getAnnotation(OnViewClick.class);
            if (onViewClick != null) {
//                拿到viewId
                int viewId = onViewClick.value();
                View view = viewFinder.findViewById(viewId);
                method.setAccessible(true);
                view.setOnClickListener(new DeclareOnClickListener(object, method));

            }
        }


/*        object = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                CheckNet checkNet = method.getAnnotation(CheckNet.class);
                if (checkNet != null) {
                    method.invoke(o, objects);
                }
                return null;
            }
        });*/

//        3.取出注解的值
//            4.为方法赋值注解的值
    }

    public static class DeclareOnClickListener implements View.OnClickListener {

        private final Object object;
        private Method mResolvedMethod;

        public DeclareOnClickListener(@NonNull Object hostView, @NonNull Method method) {
            object = hostView;
            mResolvedMethod = method;
        }

        @Override
        public void onClick(@NonNull View v) {

            try {
                mResolvedMethod.invoke(object, v);
            } catch (Throwable throwable) {
                Log.e("haha", "onClick: "+throwable.getMessage() );
                throwable.printStackTrace();
            }
        }

    }

}
