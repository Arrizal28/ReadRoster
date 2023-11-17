package com.dicoding.readroster.data

import com.dicoding.readroster.model.Book
import com.dicoding.readroster.model.FakeBookDataSource
import com.dicoding.readroster.model.OrderBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Locale.filter

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

    fun searchBooks(query: String) = flow {
        val data = OrderBooks.filter {
            it.book.title.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getOrderBooksById(bookdId: Long): OrderBook {
        return OrderBooks.first {
            it.book.id == bookdId
        }
    }

    fun updateOrderBook(bookId: Long, newCountValue: Int): Flow<Boolean> {
        val index = OrderBooks.indexOfFirst { it.book.id == bookId }
        val result = if (index >= 0) {
            val orderBook = OrderBooks[index]
            OrderBooks[index] =
                orderBook.copy(book = orderBook.book, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderBooks(): Flow<List<OrderBook>> {
        return getAllBooks()
            .map { orderBooks ->
                orderBooks.filter { orderBook ->
                    orderBook.count != 0
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