package ru.mirea.feature.friends.di

import androidx.paging.PagingSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mirea.feature.friends.data.repository.FriendsPagingSource
import ru.mirea.friends_api.Friend
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FriendsModule {
    @Binds
    @Singleton
    abstract fun provideFriendsDatasource(source: FriendsPagingSource): PagingSource<Int, Friend>
}