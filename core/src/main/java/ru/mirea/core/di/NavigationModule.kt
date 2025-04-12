package ru.mirea.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.navigation.navigator.NavigatorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @Singleton
    abstract fun bindNavigator(navigator: NavigatorImpl): Navigator
} 