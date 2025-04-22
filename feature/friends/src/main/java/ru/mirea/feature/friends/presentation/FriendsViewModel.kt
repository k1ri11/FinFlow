package ru.mirea.feature.friends.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.BaseViewModel
import ru.mirea.core.util.Const.PAGE_SIZE
import ru.mirea.feature.friends.data.repository.FriendsPagingSource
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsPagingSource: FriendsPagingSource,
    private val dispatchers: AppDispatchers,
) : BaseViewModel<FriendsState, FriendsEvent, FriendsEffect>(FriendsState()) {

    val friendsPagingData = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { friendsPagingSource }
    ).flow.cachedIn(viewModelScope)

    override fun event(event: FriendsEvent) {
        when (event) {
            else -> {}
        }
    }
} 