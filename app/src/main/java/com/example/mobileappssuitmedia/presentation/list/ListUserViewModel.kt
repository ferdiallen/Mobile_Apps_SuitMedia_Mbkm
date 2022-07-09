package com.example.mobileappssuitmedia.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobileappssuitmedia.data.dto.PageData
import com.example.mobileappssuitmedia.local.LocalUserDatabase
import com.example.mobileappssuitmedia.local.LocalUserEntity
import com.example.mobileappssuitmedia.mediator.MediatorRepository
import com.example.mobileappssuitmedia.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ListUserViewModel @Inject constructor(
    private val api: ApiService,
    private val db: LocalUserDatabase
) : ViewModel() {
   
    val pagerData: Flow<PagingData<LocalUserEntity>> =
        MediatorRepository(db, api).retrieveData().cachedIn(viewModelScope)

    private var _dataUser = MutableLiveData<PageData>()
    val dataUser: LiveData<PageData> = _dataUser
}
