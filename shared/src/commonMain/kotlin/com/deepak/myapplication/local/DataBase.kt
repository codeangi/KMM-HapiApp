package com.deepak.myapplication.local

import comdeepakmyapplicationlocal.User

internal class DataBase(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDataBase() {
        dbQuery.transaction {
            dbQuery.deleteAllUser()
        }
    }

    internal fun getUser(email: String): User? {
        return dbQuery.selectUser(email = email, mapper = ::mapToUserSelection).executeAsOneOrNull()
    }

    internal fun validateUser(email: String, password: String):User? {
        return dbQuery.validateUser(email = email, password = password, mapper = ::mapToUserSelection)
            .executeAsOneOrNull()
    }

    internal fun insertUser(user: User) :Boolean {
        if (getUser(user.email) == null) {
             dbQuery.insertUser(
                name = user.name,
                email = user.email,
                password = user.password,
                type = user.type
            )
            return true
        }
        return false
    }

    private fun mapToUserSelection(
        name: String,
        email: String,
        password: String,
        type: Long
    ): User {
        return User(name = name, email = email, password = password, type)
    }
}