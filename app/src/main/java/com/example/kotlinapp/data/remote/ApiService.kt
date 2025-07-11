package com.example.kotlinapp.data.remote

import com.example.kotlinapp.data.model.AuthResponse
import com.example.kotlinapp.data.model.LoginRequest
import com.example.kotlinapp.data.model.RegisterRequest
import com.example.kotlinapp.data.model.User
import retrofit2.http.*

/**
 * API 服務介面
 * 展示 Retrofit 與 Kotlin 協程的整合
 * 使用 suspend 函數進行非同步網路請求
 */
interface ApiService {
    
    /**
     * 用戶註冊
     */
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
    
    /**
     * 用戶登入
     */
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
    
    /**
     * 獲取用戶資料
     */
    @GET("user/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): User
    
    /**
     * 更新用戶資料
     */
    @PUT("user/profile")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body user: User
    ): User
    
    /**
     * 上傳頭像
     */
    @Multipart
    @POST("user/avatar")
    suspend fun uploadAvatar(
        @Header("Authorization") token: String,
        @Part("avatar") avatar: okhttp3.MultipartBody.Part
    ): User
}

/**
 * API 響應包裝類
 * 用於統一處理 API 響應格式
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

/**
 * 網路錯誤類型
 */
sealed class NetworkError : Exception() {
    object NoInternet : NetworkError()
    object ServerError : NetworkError()
    object Unauthorized : NetworkError()
    data class ApiError(val message: String, val code: Int) : NetworkError()
} 