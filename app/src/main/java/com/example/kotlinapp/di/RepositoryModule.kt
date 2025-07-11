package com.example.kotlinapp.di

import com.example.kotlinapp.data.repository.UserRepositoryImpl
import com.example.kotlinapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * 儲存庫模組
 * 提供儲存庫的依賴注入配置
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
} 