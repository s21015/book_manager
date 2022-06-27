package jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.repository

import jp.ac.it_college.std.s21015.bookmanager.domain.model.Book
import jp.ac.it_college.std.s21015.bookmanager.domain.model.BookWithRental
import jp.ac.it_college.std.s21015.bookmanager.domain.model.Rental
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.BookRepository
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.*
import org.springframework.stereotype.Repository
import java.time.LocalDate
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.record.Book as RecordBook
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.record.BookWithRental as RecordBookWithRental

@Repository
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
) : BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select {  }.map { toModel(it)}
    }

    override fun findWithRental(id: Long): BookWithRental? {
        return bookWithRentalMapper.selectByPrimaryKey(id) {
        }?.let { toModel(it) }
    }

    override fun register(book: Book) {
        bookMapper.insert(toRecord(book))
    }

    override fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        bookMapper.updateByPrimaryKeySelective(RecordBook(id, title, author, releaseDate))
    }

    override fun delete(id: Long) {
        bookMapper.deleteByPrimaryKey(id)
    }

    private fun toModel(record: RecordBookWithRental): BookWithRental {
        val book = Book(
            record.id!!,
            record.title!!,
            record.author!!,
            record.releaseDate!!,
        )
        val rental = record.userId?.let {
            Rental(
                record.id!!,
                record.userId!!,
                record.rentalDatetime!!,
                record.returnDeadline!!
            )
        }
        return BookWithRental(book, rental)
    }

    private fun toRecord(model: Book): RecordBook {
        return RecordBook(model.id, model.title, model.author, model.releaseDate)
    }
}