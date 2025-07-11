package com.example.kotlinapp.data.local

import androidx.room.*
import com.example.kotlinapp.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * 用戶數據訪問物件 (DAO)
 * 展示 Room 與 Kotlin 協程 Flow 的整合
 */
@Dao
interface UserDao {
    
    /**
     * 插入或更新用戶
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    /**
     * 根據 ID 獲取用戶
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
    
    /**
     * 根據 ID 獲取用戶 (Flow)
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: String): Flow<User?>
    
    /**
     * 獲取所有用戶
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
    
    /**
     * 更新用戶資料
     */
    @Update
    suspend fun updateUser(user: User)
    
    /**
     * 刪除用戶
     */
    @Delete
    suspend fun deleteUser(user: User)
    
    /**
     * 清空所有用戶
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
    
    /**
     * 檢查用戶是否存在
     */
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE id = :userId)")
    suspend fun userExists(userId: String): Boolean
} 