package com.example.kotlinapp

import com.example.kotlinapp.data.model.AuthResponse
import com.example.kotlinapp.data.model.LoginRequest
import com.example.kotlinapp.data.model.User
import com.example.kotlinapp.data.remote.ApiResult
import com.example.kotlinapp.data.repository.UserRepositoryImpl
import com.example.kotlinapp.data.remote.ApiService
import com.example.kotlinapp.data.local.UserDao
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * 用戶儲存庫測試
 * 展示單元測試的實現
 */
class UserRepositoryTest {
    
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var userDao: UserDao
    
    @Before
    fun setup() {
        apiService = mockk()
        userDao = mockk()
        userRepository = UserRepositoryImpl(apiService, userDao)
    }
    
    @Test
    fun `login should return success when API call succeeds`() = runTest {
        // Given
        val loginRequest = LoginRequest("test@example.com", "password")
        val user = User("1", "test@example.com", "Test User")
        val authResponse = AuthResponse(user, "token")
        
        coEvery { apiService.login(loginRequest) } returns authResponse
        coEvery { userDao.insertUser(user) } returns Unit
        
        // When
        val result = userRepository.login(loginRequest)
        
        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(authResponse, (result as ApiResult.Success).data)
    }
    
    @Test
    fun `login should return error when API call fails`() = runTest {
        // Given
        val loginRequest = LoginRequest("test@example.com", "password")
        val errorMessage = "Invalid credentials"
        
        coEvery { apiService.login(loginRequest) } throws Exception(errorMessage)
        
        // When
        val result = userRepository.login(loginRequest)
        
        // Then
        assertTrue(result is ApiResult.Error)
        assertEquals(errorMessage, (result as ApiResult.Error).message)
    }
    
    @Test
    fun `getUserProfile should return success when API call succeeds`() = runTest {
        // Given
        val user = User("1", "test@example.com", "Test User")
        
        coEvery { apiService.getUserProfile(any()) } returns user
        coEvery { userDao.insertUser(user) } returns Unit
        
        // When
        val result = userRepository.getUserProfile()
        
        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(user, (result as ApiResult.Success).data)
    }
} 