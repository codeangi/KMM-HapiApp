package com.deepak.myapplication.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.deepak.myapplication.datamapper.AppointmentDataMapper
import com.deepak.myapplication.datamapper.HomeDataMapper
import com.deepak.myapplication.infra.getNetworkClient
import com.deepak.myapplication.local.DataBase
import com.deepak.myapplication.local.DataStoreProvider
import com.deepak.myapplication.local.DatabaseDriverFactory
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.platformModule
import com.deepak.myapplication.repository.*
import com.deepak.myapplication.usecase.*
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
    //Infra
    single { getNetworkClient() }
    single { provideDataBase(get()) }
    single { provideDataStore(get()) }

    //User specific
    single { UserSettingsRepository(get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get()) }

    //Auth
    factory { LoginUseCase(get(), get()) }
    factory { UserRegistrationUseCase(get(), get()) }

    //Home
    factory { HomeUseCase(get(), get(), get(), get()) }
    factory { HomeDataMapper() }

    //Patient
    factory<PatientRepository> { PatientRepositoryImpl(get(), get()) }

    //Appointment
    factory { AppointmentUseCase(get(), get(), get(), get(), get()) }
    factory { AppointmentDataMapper() }

    //Practitioner
    factory<PractitionerRepository> { PractitionerRepositoryImpl(get(), get()) }
    factory { PractitionerUseCase(get()) }

    // Profile
    factory { ProfileUseCase(get(), get()) }

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