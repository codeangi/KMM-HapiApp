package com.deepak.myapplication.di

import com.deepak.myapplication.getNetworkClient
import com.deepak.myapplication.local.DataBase
import com.deepak.myapplication.local.DatabaseDriverFactory
import com.deepak.myapplication.platformModule
import com.deepak.myapplication.repository.UserRepository
import com.deepak.myapplication.repository.UserRepositoryImpl
import com.deepak.myapplication.usecase.LoginUseCase
import com.deepak.myapplication.usecase.UserRegistrationUseCase
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import kotlin.reflect.KClass

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, platformModule())
}

fun initKoin() = initKoin {

}
val commonModule = module {
    single { getNetworkClient() }
    single { provideDataBase(get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory { LoginUseCase(get()) }
    factory { UserRegistrationUseCase(get()) }

}

internal fun provideDataBase(databaseDriverFactory: DatabaseDriverFactory): DataBase {
    return DataBase(databaseDriverFactory)
}

fun <T> Koin.provideDependency(clazz: KClass<*>): T{
    return get(clazz, qualifier = null)
}