package jp.ac.it_college.std.s21015.bookmanager.application.service

import com.nhaarman.mockitokotlin2.*
import jp.ac.it_college.std.s21015.bookmanager.application.service.security.RentalService
import jp.ac.it_college.std.s21015.bookmanager.domain.enum.RoleType
import jp.ac.it_college.std.s21015.bookmanager.domain.model.Book
import jp.ac.it_college.std.s21015.bookmanager.domain.model.BookWithRental
import jp.ac.it_college.std.s21015.bookmanager.domain.model.Rental
import jp.ac.it_college.std.s21015.bookmanager.domain.model.User
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.BookRepository
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.RentalRepository
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class RentalServiceTest {
    private val userRepository = mock<UserRepository>()
    private val bookRepository = mock<BookRepository>()
    private val rentalRepository = mock<RentalRepository>()

    private val rentalService = RentalService(userRepository, bookRepository, rentalRepository)

    @Test
    fun `endRental when book is rental then delete to rental`() {
        val userId = 100L
        val bookId = 100L
        val user = User(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
        val book = Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val rental = Rental(bookId, userId, LocalDateTime.now(), LocalDateTime.MAX)
        val bookWithRental = BookWithRental(book, rental)

        whenever(userRepository.find(any() as Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        rentalService.endRental(bookId, userId)

        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository).endRental(bookId)
    }

    @Test
    fun `endRental when book is not rental then throw exception`() {
        val userId = 100L
        val bookId = 100L
        val user = User(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
        val book = Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)

        whenever(userRepository.find(any() as Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        val exception = Assertions.assertThrows(IllegalStateException::class.java) {
            rentalService.endRental(bookId, userId)
        }

        assertThat(exception.message).isEqualTo("未貸出の商品です bookId:${bookId}")

        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository, times(0)).endRental(bookId)
    }
}