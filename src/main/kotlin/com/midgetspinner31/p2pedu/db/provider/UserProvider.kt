package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.UserRepository
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.exception.UserNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserProvider(
    repository: UserRepository
) : AbstractProvider<User, UserRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return UserNotFoundException()
    }
}
