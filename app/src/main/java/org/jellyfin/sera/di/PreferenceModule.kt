package org.jellyfin.sera.di

import org.jellyfin.sera.preference.LiveTvPreferences
import org.jellyfin.sera.preference.PreferencesRepository
import org.jellyfin.sera.preference.SystemPreferences
import org.jellyfin.sera.preference.TelemetryPreferences
import org.jellyfin.sera.preference.UserPreferences
import org.jellyfin.sera.preference.UserSettingPreferences
import org.koin.dsl.module

val preferenceModule = module {
	single { PreferencesRepository(get(), get(), get()) }

	single { LiveTvPreferences(get()) }
	single { UserSettingPreferences(get()) }
	single { UserPreferences(get()) }
	single { SystemPreferences(get()) }
	single { TelemetryPreferences(get()) }
}
