package com.example.kotlinapp.util;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Java 互操作性示例
 * 展示 Kotlin 與 Java 的整合
 */
public class JavaInteropExample {
    
    private static final String TAG = "JavaInteropExample";
    
    /**
     * Java 靜態方法，可以被 Kotlin 調用
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }
    
    /**
     * Java 實例方法
     */
    public String processData(String input) {
        Log.d(TAG, "Processing data: " + input);
        return "Processed: " + input;
    }
    
    /**
     * 使用 Java 8 函數式接口
     */
    public String applyFunction(String input, Function<String, String> function) {
        return function.apply(input);
    }
    
    /**
     * 使用 Callable 接口
     */
    public Callable<String> createCallable() {
        return () -> "Hello from Java Callable";
    }
    
    /**
     * 處理 Kotlin 數據類
     */
    public void handleKotlinDataClass(User user) {
        Log.d(TAG, "User ID: " + user.getId());
        Log.d(TAG, "User Name: " + user.getName());
        Log.d(TAG, "User Email: " + user.getEmail());
    }
    
    /**
     * 返回 Kotlin 數據類
     */
    public User createUser(String id, String name, String email) {
        return new User(id, email, name, null, false, 
                       System.currentTimeMillis(), System.currentTimeMillis());
    }
    
    /**
     * 處理 Kotlin 密封類
     */
    public void handleKotlinSealedClass(ApiResult result) {
        if (result instanceof ApiResult.Success) {
            ApiResult.Success success = (ApiResult.Success) result;
            Log.d(TAG, "Success: " + success.getData());
        } else if (result instanceof ApiResult.Error) {
            ApiResult.Error error = (ApiResult.Error) result;
            Log.d(TAG, "Error: " + error.getMessage());
        } else if (result instanceof ApiResult.Loading) {
            Log.d(TAG, "Loading...");
        }
    }
} 