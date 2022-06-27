package jp.ac.it_college.std.s21015.bookmanager.domain.repository

import jp.ac.it_college.std.s21015.bookmanager.domain.model.Rental

interface RentalRepository {
    fun startRental(rental: Rental)

    fun endRental(bookId: Long)
}