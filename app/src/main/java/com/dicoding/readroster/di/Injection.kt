package com.dicoding.readroster.di

import com.dicoding.readroster.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}