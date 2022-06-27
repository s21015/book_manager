package jp.ac.it_college.std.s21015.bookmanager.application.service.security

import jp.ac.it_college.std.s21015.bookmanager.domain.model.Rental
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.BookRepository
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.RentalRepository
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

private const val RENTAL_TERM_DAYS = 14L

@Service
class RentalService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentalRepository: RentalRepository
) {
    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        userRepository.find(userId) ?: throw java.lang.IllegalArgumentException("該当するユーザーが存在しません userId:${userId}")
        val book = bookRepository.findWithRental(bookId) ?: throw java.lang.IllegalArgumentException("該当する書籍が存在しません bookId:${bookId}")

        if (book.isRental) throw IllegalStateException("貸出中の商品です bookId:${bookId}")

        val rentalDateTime = LocalDateTime.now()
        val returnDeadline = rentalDateTime.plusDays(RENTAL_TERM_DAYS)
        val rental = Rental(bookId, userId, rentalDateTime, returnDeadline)

        rentalRepository.startRental(rental)
    }

    @Transactional
    fun endRental(bookId: Long, userId: Long) {
        userRepository.find(userId) ?: throw java.lang.IllegalArgumentException("該当するユーザーが存在しません useId:${userId}")
        val book = bookRepository.findWithRental(bookId) ?: throw java.lang.IllegalArgumentException("該当する書籍が存在しません bookId:${bookId}")

        if (!book.isRental) throw IllegalStateException("未貸出の商品です bookId:${bookId}")
        if (book.rental!!.userId != userId) throw IllegalStateException("他のユーザーが貸出中の商品です userId:${userId} bookId:${bookId}")

        rentalRepository.endRental(bookId)
    }
}