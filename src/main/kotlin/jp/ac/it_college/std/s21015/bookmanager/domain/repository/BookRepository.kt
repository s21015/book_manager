package jp.ac.it_college.std.s21015.bookmanager.domain.repository

import jp.ac.it_college.std.s21015.bookmanager.domain.model.Book
import jp.ac.it_college.std.s21015.bookmanager.domain.model.BookWithRental
import java.time.LocalDate

interface BookRepository {
    fun findAllWithRental(): List<BookWithRental>

    fun findWithRental(id: Long): BookWithRental?

    fun register(book: Book)

    fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?)

    fun delete(id: Long)
}