package jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.repository

import jp.ac.it_college.std.s21015.bookmanager.domain.model.User
import jp.ac.it_college.std.s21015.bookmanager.domain.repository.UserRepository
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.UserDynamicSqlSupport.user
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.UserMapper
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.selectByPrimaryKey
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.mapper.selectOne
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s21015.bookmanager.infrastructure.database.record.User as RecordUser

@Repository
class UserRepositoryImpl(
    private val mapper: UserMapper
) : UserRepository {
    override fun find(email: String): User? {
        val record = mapper.selectOne {
            where {
                user.email isEqualTo email
            }
        }

        return record?.let { toModel(it) }
    }

    override fun find(id: Long): User? {
        val record = mapper.selectByPrimaryKey(id)
        return record?.let { toModel(it) }
    }

    private fun toModel(record: RecordUser): User {
        return User(
            record.id!!,
            record.email!!,
            record.password!!,
            record.name!!,
            record.roleType!!
        )
    }
}