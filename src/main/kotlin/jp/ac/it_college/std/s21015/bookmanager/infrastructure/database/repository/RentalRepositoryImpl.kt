package jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.repository

import jp.ac.it_college.std.s21015.bookmanager.domain.model.Rental
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.RentalRepository
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.RentalMapper
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.deleteByPrimaryKey
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.insert
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.record.Rental as RecordRental

@Suppress("SpringJavaInjectionPointAutowiringInspection")
@Repository
class RentalRepositoryImpl(
    private val rentalMapper: RentalMapper
) : RentalRepository {
    override fun startRental(rental: Rental) {
        rentalMapper.insert(toRecord(rental))
    }

    override fun endRental(bookId: Long) {
        rentalMapper.deleteByPrimaryKey(bookId)
    }

    private fun toRecord(model: Rental): RecordRental {
        return  RecordRental(model.bookId, model.userId, model.rentalDatetime, model.returnDeadline)
    }
}