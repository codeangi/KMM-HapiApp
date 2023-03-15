package com.deepak.myapplication.repository

import com.deepak.myapplication.local.DataBase
import com.deepak.myapplication.local.DatabaseDriverFactory
import comdeepakmyapplicationlocal.User

interface UserRepository {
    suspend fun addUser(user: User): Boolean
    suspend fun verifyUser(email: String, password: String): User?
    suspend fun getTheUser(email: String): User?
}

class UserRepositoryImpl constructor(private val databaseDriverFactory: DatabaseDriverFactory) :
    UserRepository {
    private val database = DataBase(databaseDriverFactory)
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