package com.dicoding.readroster.ui.screen.cart

import com.dicoding.readroster.model.OrderBook

data class CartState(
    val orderBook: List<OrderBook>,
    val totalPrice: Int
)