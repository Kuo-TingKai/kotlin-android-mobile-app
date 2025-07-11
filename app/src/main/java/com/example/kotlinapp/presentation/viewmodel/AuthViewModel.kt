package com.example.kotlinapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinapp.data.model.LoginRequest
import com.example.kotlinapp.data.model.RegisterRequest
import com.example.kotlinapp.data.model.User
import com.example.kotlinapp.data.remote.ApiResult
import com.example.kotlinapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 認證 ViewModel
 * 展示 MVVM 架構和狀態管理
 * 使用 StateFlow 進行響應式狀態管理
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    // UI 狀態
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    // 當前用戶
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    init {
        observeCurrentUser()
        checkLoginStatus()
    }
    
    /**
     * 用戶註冊
     */
    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            
            val request = RegisterRequest(email, password, name)
            val result = userRepository.register(request)
            
            _uiState.value = when (result) {
                is ApiResult.Success -> {
                    _currentUser.value = result.data.user
                    AuthUiState.Success(result.data.user)
                }
                is ApiResult.Error -> {
                    AuthUiState.Error(result.message)
                }
                is ApiResult.Loading -> {
                    AuthUiState.Loading
                }
            }
        }
    }
    
    /**
     * 用戶登入
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            
            val request = LoginRequest(email, password)
            val result = userRepository.login(request)
            
            _uiState.value = when (result) {
                is ApiResult.Success -> {
                    _currentUser.value = result.data.user
                    AuthUiState.Success(result.data.user)
                }
                is ApiResult.Error -> {
                    AuthUiState.Error(result.message)
                }
                is ApiResult.Loading -> {
                    AuthUiState.Loading
                }
            }
        }
    }
    
    /**
     * 用戶登出
     */
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _currentUser.value = null
            _uiState.value = AuthUiState.Initial
        }
    }
    
    /**
     * 檢查登入狀態
     */
    private fun checkLoginStatus() {
        viewModelScope.launch {
            val isLoggedIn = userRepository.isUserLoggedIn()
            if (!isLoggedIn) {
                _uiState.value = AuthUiState.Initial
            }
        }
    }
    
    /**
     * 觀察當前用戶
     */
    private fun observeCurrentUser() {
        viewModelScope.launch {
            userRepository.getCurrentUser().collect { user ->
                _currentUser.value = user
                if (user != null) {
                    _uiState.value = AuthUiState.Success(user)
                }
            }
        }
    }
    
    /**
     * 清除錯誤狀態
     */
    fun clearError() {
        _uiState.value = AuthUiState.Initial
    }
}

/**
 * 認證 UI 狀態
 */
sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
} 