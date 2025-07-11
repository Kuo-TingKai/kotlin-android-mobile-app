package com.example.kotlinapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * 應用程式主類別
 * 使用 Hilt 進行依賴注入管理
 * 展示 Kotlin 與 Java 互操作性的最佳實踐
 */
@HiltAndroidApp
class KotlinAppApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // 初始化日誌系統
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // 初始化其他組件
        initializeComponents()
    }
    
    private fun initializeComponents() {
        // 這裡可以初始化其他第三方庫
        Timber.d("應用程式初始化完成")
    }
} 