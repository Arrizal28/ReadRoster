package com.dicoding.readroster.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.readroster.data.BookRepository
import com.dicoding.readroster.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderBook() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderBooks()
                .collect { orderBook ->
                    val totalPrice =
                        orderBook.sumOf { it.book.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderBook, totalPrice))
                }
        }
    }

    fun updateOrderBook(bookId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderBook(bookId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderBook()
                    }
                }
        }
    }
}