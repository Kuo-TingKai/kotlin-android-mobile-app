package com.example.kotlinapp.util

import com.example.kotlinapp.data.model.User
import com.example.kotlinapp.data.remote.ApiResult
import timber.log.Timber
import java.util.concurrent.Callable

/**
 * Kotlin 與 Java 互操作性示例
 * 展示如何在 Kotlin 中使用 Java 代碼
 */
class KotlinJavaInterop {
    
    private val javaInteropExample = JavaInteropExample()
    
    /**
     * 調用 Java 靜態方法
     */
    fun getJavaVersion(): String {
        return JavaInteropExample.getJavaVersion()
    }
    
    /**
     * 調用 Java 實例方法
     */
    fun processDataWithJava(input: String): String {
        return javaInteropExample.processData(input)
    }
    
    /**
     * 使用 Java 函數式接口
     */
    fun useJavaFunction(input: String): String {
        val function: (String) -> String = { str -> str.uppercase() }
        return javaInteropExample.applyFunction(input, function)
    }
    
    /**
     * 使用 Java Callable
     */
    fun useJavaCallable(): String {
        val callable: Callable<String> = javaInteropExample.createCallable()
        return try {
            callable.call()
        } catch (e: Exception) {
            Timber.e(e, "Callable execution failed")
            "Error"
        }
    }
    
    /**
     * 創建 Kotlin 數據類並傳遞給 Java
     */
    fun createAndPassUserToJava(): User {
        val user = User(
            id = "1",
            email = "test@example.com",
            name = "Test User"
        )
        
        // 傳遞給 Java 方法
        javaInteropExample.handleKotlinDataClass(user)
        
        return user
    }
    
    /**
     * 從 Java 接收數據類
     */
    fun receiveUserFromJava(): User {
        return javaInteropExample.createUser("2", "Java User", "java@example.com")
    }
    
    /**
     * 處理密封類與 Java 的互操作
     */
    fun handleSealedClassWithJava() {
        val successResult = ApiResult.Success("Success data")
        val errorResult = ApiResult.Error("Error message")
        val loadingResult = ApiResult.Loading
        
        // 傳遞給 Java 方法
        javaInteropExample.handleKotlinSealedClass(successResult)
        javaInteropExample.handleKotlinSealedClass(errorResult)
        javaInteropExample.handleKotlinSealedClass(loadingResult)
    }
    
    /**
     * 展示 Kotlin 擴展函數與 Java 的整合
     */
    fun String.processWithJava(): String {
        return javaInteropExample.processData(this)
    }
    
    /**
     * 使用 Kotlin 協程與 Java 代碼
     */
    suspend fun useCoroutinesWithJava(): String {
        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            javaInteropExample.createCallable().call()
        }
    }
} 