package com.example.kotlinapp.domain.repository

import com.example.kotlinapp.data.model.AuthResponse
import com.example.kotlinapp.data.model.LoginRequest
import com.example.kotlinapp.data.model.RegisterRequest
import com.example.kotlinapp.data.model.User
import com.example.kotlinapp.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * 用戶儲存庫介面
 * 定義用戶相關的業務邏輯操作
 * 展示領域驅動設計 (DDD) 的應用
 */
interface UserRepository {
    
    /**
     * 用戶註冊
     */
    suspend fun register(request: RegisterRequest): ApiResult<AuthResponse>
    
    /**
     * 用戶登入
     */
    suspend fun login(request: LoginRequest): ApiResult<AuthResponse>
    
    /**
     * 用戶登出
     */
    suspend fun logout()
    
    /**
     * 獲取當前用戶
     */
    fun getCurrentUser(): Flow<User?>
    
    /**
     * 獲取用戶資料
     */
    suspend fun getUserProfile(): ApiResult<User>
    
    /**
     * 更新用戶資料
     */
    suspend fun updateUserProfile(user: User): ApiResult<User>
    
    /**
     * 檢查用戶是否已登入
     */
    suspend fun isUserLoggedIn(): Boolean
    
    /**
     * 保存用戶到本地
     */
    suspend fun saveUserToLocal(user: User)
    
    /**
     * 從本地獲取用戶
     */
    suspend fun getUserFromLocal(userId: String): User?
    
    /**
     * 清除本地用戶資料
     */
    suspend fun clearLocalUserData()
} 