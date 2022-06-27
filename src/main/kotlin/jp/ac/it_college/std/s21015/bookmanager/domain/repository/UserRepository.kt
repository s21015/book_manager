package jp.ac.it_college.std.s21015.bookmanager.domain.repository

import jp.ac.it_college.std.s21015.bookmanager.domain.model.User

interface UserRepository {
    fun find(email: String): User?

    fun find(id: Long): User?
}