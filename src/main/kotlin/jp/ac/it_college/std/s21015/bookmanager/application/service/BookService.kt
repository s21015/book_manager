package jp.ac.it_college.std.s21015.bookmanager.application.service

import jp.ac.it_college.std.s21015.bookmanager.domain.model.BookWithRental
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }

    fun getDetail(bookId: Long): BookWithRental {
        return bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID： $bookId")
    }
}