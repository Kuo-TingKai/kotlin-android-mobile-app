package com.example.kotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.kotlinapp.ui.navigation.AppNavigation
import com.example.kotlinapp.ui.theme.KotlinAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * 主活動類別
 * 展示 Kotlin 與 Jetpack Compose 的整合
 * 使用 Hilt 進行依賴注入
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Timber.d("MainActivity 創建")
        
        setContent {
            KotlinAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity 恢復")
    }
    
    override fun onPause() {
        super.onPause()
        Timber.d("MainActivity 暫停")
    }
} 