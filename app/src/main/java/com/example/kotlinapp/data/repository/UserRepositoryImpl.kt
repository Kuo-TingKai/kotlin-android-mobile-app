package com.example.kotlinapp.data.repository

import com.example.kotlinapp.data.local.UserDao
import com.example.kotlinapp.data.model.AuthResponse
import com.example.kotlinapp.data.model.LoginRequest
import com.example.kotlinapp.data.model.RegisterRequest
import com.example.kotlinapp.data.model.User
import com.example.kotlinapp.data.remote.ApiResult
import com.example.kotlinapp.data.remote.ApiService
import com.example.kotlinapp.data.remote.NetworkError
import com.example.kotlinapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用戶儲存庫實現類別
 * 整合遠端 API 和本地數據庫
 * 展示 Repository 模式的應用
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {
    
    override suspend fun register(request: RegisterRequest): ApiResult<AuthResponse> {
        return try {
            val response = apiService.register(request)
            // 保存用戶到本地
            saveUserToLocal(response.user)
            ApiResult.Success(response)
        } catch (e: Exception) {
            Timber.e(e, "註冊失敗")
            ApiResult.Error(e.message ?: "註冊失敗")
        }
    }
    
    override suspend fun login(request: LoginRequest): ApiResult<AuthResponse> {
        return try {
            val response = apiService.login(request)
            // 保存用戶到本地
            saveUserToLocal(response.user)
            ApiResult.Success(response)
        } catch (e: Exception) {
            Timber.e(e, "登入失敗")
            ApiResult.Error(e.message ?: "登入失敗")
        }
    }
    
    override suspend fun logout() {
        try {
            clearLocalUserData()
            Timber.d("用戶登出成功")
        } catch (e: Exception) {
            Timber.e(e, "登出失敗")
        }
    }
    
    override fun getCurrentUser(): Flow<User?> = flow {
        // 這裡可以從 SharedPreferences 或其他地方獲取當前用戶 ID
        val currentUserId = "current_user_id" // 實際應用中需要從安全存儲獲取
        val user = userDao.getUserById(currentUserId)
        emit(user)
    }.catch { e ->
        Timber.e(e, "獲取當前用戶失敗")
        emit(null)
    }
    
    override suspend fun getUserProfile(): ApiResult<User> {
        return try {
            val token = "Bearer token" // 實際應用中需要從安全存儲獲取
            val user = apiService.getUserProfile(token)
            saveUserToLocal(user)
            ApiResult.Success(user)
        } catch (e: Exception) {
            Timber.e(e, "獲取用戶資料失敗")
            ApiResult.Error(e.message ?: "獲取用戶資料失敗")
        }
    }
    
    override suspend fun updateUserProfile(user: User): ApiResult<User> {
        return try {
            val token = "Bearer token" // 實際應用中需要從安全存儲獲取
            val updatedUser = apiService.updateUserProfile(token, user)
            saveUserToLocal(updatedUser)
            ApiResult.Success(updatedUser)
        } catch (e: Exception) {
            Timber.e(e, "更新用戶資料失敗")
            ApiResult.Error(e.message ?: "更新用戶資料失敗")
        }
    }
    
    override suspend fun isUserLoggedIn(): Boolean {
        return try {
            val currentUserId = "current_user_id" // 實際應用中需要從安全存儲獲取
            userDao.userExists(currentUserId)
        } catch (e: Exception) {
            Timber.e(e, "檢查登入狀態失敗")
            false
        }
    }
    
    override suspend fun saveUserToLocal(user: User) {
        try {
            userDao.insertUser(user)
            Timber.d("用戶保存到本地成功: ${user.id}")
        } catch (e: Exception) {
            Timber.e(e, "保存用戶到本地失敗")
        }
    }
    
    override suspend fun getUserFromLocal(userId: String): User? {
        return try {
            userDao.getUserById(userId)
        } catch (e: Exception) {
            Timber.e(e, "從本地獲取用戶失敗")
            null
        }
    }
    
    override suspend fun clearLocalUserData() {
        try {
            userDao.deleteAllUsers()
            Timber.d("清除本地用戶資料成功")
        } catch (e: Exception) {
            Timber.e(e, "清除本地用戶資料失敗")
        }
    }
} 