package com.deepak.myapplication.repository

import com.deepak.myapplication.local.DataBase
import comdeepakmyapplicationlocal.User
import io.ktor.client.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserRepository {
    suspend fun addUser(user: User): Boolean
    suspend fun verifyUser(email: String, password: String): User?
    suspend fun getTheUser(email: String): User?
}

class UserRepositoryImpl constructor (private val httpClient: HttpClient) :
    UserRepository, KoinComponent {

    private val database: DataBase by inject()

    override suspend fun addUser(user: User): Boolean {
        return database.insertUser(user)
    }

    override suspend fun verifyUser(email: String, password: String): User? {
        return database.validateUser(email = email, password = password)
    }

    override suspend fun getTheUser(email: String): User? {
        return database.getUser(email = email)
    }
}