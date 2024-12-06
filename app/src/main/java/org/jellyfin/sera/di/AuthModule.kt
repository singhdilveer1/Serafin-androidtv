package org.jellyfin.sera.di

import org.jellyfin.sera.auth.AccountManagerMigration
import org.jellyfin.sera.auth.apiclient.ApiBinder
import org.jellyfin.sera.auth.repository.AuthenticationRepository
import org.jellyfin.sera.auth.repository.AuthenticationRepositoryImpl
import org.jellyfin.sera.auth.repository.ServerRepository
import org.jellyfin.sera.auth.repository.ServerRepositoryImpl
import org.jellyfin.sera.auth.repository.ServerUserRepository
import org.jellyfin.sera.auth.repository.ServerUserRepositoryImpl
import org.jellyfin.sera.auth.repository.SessionRepository
import org.jellyfin.sera.auth.repository.SessionRepositoryImpl
import org.jellyfin.sera.auth.store.AuthenticationPreferences
import org.jellyfin.sera.auth.store.AuthenticationStore
import org.koin.dsl.module

val authModule = module {
	single { AccountManagerMigration(get()) }
	single { AuthenticationStore(get(), get()) }
	single { AuthenticationPreferences(get()) }

	single<AuthenticationRepository> {
		AuthenticationRepositoryImpl(get(), get(), get(), get(), get(), get(defaultDeviceInfo))
	}
	single<ServerRepository> { ServerRepositoryImpl(get(), get()) }
	single<ServerUserRepository> { ServerUserRepositoryImpl(get(), get()) }
	single<SessionRepository> {
		SessionRepositoryImpl(get(), get(), get(), get(), get(), get(defaultDeviceInfo), get(), get(), get())
	}

	single { ApiBinder(get(), get()) }
}
