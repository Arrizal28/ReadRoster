package com.dicoding.readroster.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.readroster.data.BookRepository
import com.dicoding.readroster.model.OrderBook
import com.dicoding.readroster.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderBook>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderBook>>>
        get() = _uiState

    fun getAllRewards() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderBooks ->
                    _uiState.value = UiState.Success(orderBooks)
                }
        }
    }
}