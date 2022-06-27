package jp.ac.it_college.std.s21015.bookmanager.application.service

import jp.ac.it_college.std.s21015.bookmanager.domain.model.User
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val userRepository: UserRepository) {
    fun findUser(email: String): User? {
        return userRepository.find(email)
    }
}