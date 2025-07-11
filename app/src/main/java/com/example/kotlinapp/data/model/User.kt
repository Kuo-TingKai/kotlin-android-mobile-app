package com.example.kotlinapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 用戶數據模型
 * 展示 Kotlin 數據類、Room 實體和 Parcelable 的使用
 */
@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String? = null,
    val isEmailVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis()
) : Parcelable {
    
    companion object {
        fun createEmpty() = User(
            id = "",
            email = "",
            name = ""
        )
    }
}

/**
 * 用戶註冊請求模型
 */
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

/**
 * 用戶登入請求模型
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * 認證響應模型
 */
data class AuthResponse(
    val user: User,
    val token: String
) 