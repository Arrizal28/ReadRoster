package com.dicoding.readroster.data

import com.dicoding.readroster.model.FakeBookDataSource
import com.dicoding.readroster.model.OrderBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BookRepository {
    private val OrderBooks = mutableListOf<OrderBook>()

    init {
        if (OrderBooks.isEmpty()) {
            FakeBookDataSource.dummyBooks.forEach {
                OrderBooks.add(OrderBook(it, 0))
            }
        }
    }

    fun getAllBooks(): Flow<List<OrderBook>> {
        return flowOf(OrderBooks)
    }

    fun getOrderBooksById(rewardId: Long): OrderBook {
        return OrderBooks.first {
            it.book.id == rewardId
        }
    }

    fun updateOrderBook(rewardId: Long, newCountValue: Int): Flow<Boolean> {
        val index = OrderBooks.indexOfFirst { it.book.id == rewardId }
        val result = if (index >= 0) {
            val orderReward = OrderBooks[index]
            OrderBooks[index] =
                orderReward.copy(book = orderReward.book, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderBooks(): Flow<List<OrderBook>> {
        return getAllBooks()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }

}