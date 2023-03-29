package com.deepak.myapplication.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.deepak.myapplication.infra.getNetworkClient
import com.deepak.myapplication.local.DataBase
import com.deepak.myapplication.local.DataStoreProvider
import com.deepak.myapplication.local.DatabaseDriverFactory
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.platformModule
import com.deepak.myapplication.repository.PatientRepository
import com.deepak.myapplication.repository.PatientRepositoryImpl
import com.deepak.myapplication.repository.UserRepository
import com.deepak.myapplication.repository.UserRepositoryImpl
import com.deepak.myapplication.usecase.HomeUseCase
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
    single { provideDataStore(get()) }
    single { UserSettingsRepository(get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory { LoginUseCase(get(), get()) }
    factory { UserRegistrationUseCase(get(), get()) }
    factory { HomeUseCase(get(), get()) }
    factory <PatientRepository>{ PatientRepositoryImpl(get()) }

}

internal fun provideDataBase(databaseDriverFactory: DatabaseDriverFactory): DataBase {
    return DataBase(databaseDriverFactory)
}

internal fun provideDataStore(dataStoreProvider: DataStoreProvider): DataStore<Preferences> {
    return dataStoreProvider.createDataStore()
}


fun <T> Koin.provideDependency(clazz: KClass<*>): T {
    return get(clazz, qualifier = null)
}