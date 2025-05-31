package ru.mirea.expense.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mirea.expense.data.ExpenseRepositoryImpl
import ru.mirea.expense.domain.repository.ExpenseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ExpenseModule {
    @Binds
    @Singleton
    abstract fun bindExpenseRepository(impl: ExpenseRepositoryImpl): ExpenseRepository
} 