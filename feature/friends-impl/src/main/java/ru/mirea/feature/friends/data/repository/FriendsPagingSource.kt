package ru.mirea.feature.friends.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.mirea.core.service.UserService
import ru.mirea.core.util.Const.PAGE_SIZE
import ru.mirea.feature.friends.data.api.FriendsApi
import ru.mirea.feature.friends.domain.mapper.toDomain
import ru.mirea.friends_api.Friend
import javax.inject.Inject

class FriendsPagingSource @Inject constructor(
    private val api: FriendsApi,
    private val userService: UserService,
) : PagingSource<Int, Friend>() {

    override fun getRefreshKey(state: PagingState<Int, Friend>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Friend> {
        return try {
            val userNickname = userService.getCurrentUserNick()
                ?: throw IllegalStateException("Текущий пользователь не установлен")

            val page = params.key ?: 1
            val response = api.getFriends(
                nickName = userNickname,
                pageSize = PAGE_SIZE,
                page = page
            )

            LoadResult.Page(
                data = response.toDomain().friends,
                prevKey = null,
                nextKey = if (response.friends.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
} 