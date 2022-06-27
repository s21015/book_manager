package jp.ac.it_college.std.s21015.bookmanager.domain.model

import java.time.LocalDate

data class Book (
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)