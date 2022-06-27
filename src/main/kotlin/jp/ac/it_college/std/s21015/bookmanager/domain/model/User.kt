package jp.ac.it_college.std.s21015.bookmanager.domain.model

import jp.ac.it_college.std.s21015.bookmanager.domain.enum.RoleType

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val roleType: RoleType
)
