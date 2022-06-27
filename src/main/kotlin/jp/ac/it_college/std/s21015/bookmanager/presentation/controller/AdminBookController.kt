package jp.ac.it_college.std.s21015.bookmanager.presentation.controller

import jp.ac.it_college.std.s21015.bookmanager.application.service.AdminBookService
import jp.ac.it_college.std.s21015.bookmanager.domain.model.Book
import jp.ac.it_college.std.s21015.bookmanager.presentation.form.RegisterBookRequest
import jp.ac.it_college.std.s21015.bookmanager.presentation.form.UpdateBookRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin/book")
@CrossOrigin(origins = ["http://localhost:8081/"], allowCredentials = "true")
class AdminBookController(
    private val adminBookService: AdminBookService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterBookRequest) {
        adminBookService.register(
            Book(request.id, request.title, request.author, request.releaseDate)
        )
    }

    @PutMapping("/update")
    fun update(@RequestBody request: UpdateBookRequest) {
        with(request) {
            adminBookService.update(id, title, author, releaseDate)
        }
    }

    @DeleteMapping("/delete/{book_id}")
    fun delete(@PathVariable("book_id") bookId: Long) {
        adminBookService.delete(bookId)
    }
}