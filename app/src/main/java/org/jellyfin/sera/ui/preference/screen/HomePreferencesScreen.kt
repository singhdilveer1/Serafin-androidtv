package org.jellyfin.sera.ui.preference.screen

import org.jellyfin.sera.R
import org.jellyfin.sera.constant.HomeSectionType
import org.jellyfin.sera.preference.UserSettingPreferences
import org.jellyfin.sera.ui.preference.dsl.OptionsFragment
import org.jellyfin.sera.ui.preference.dsl.enum
import org.jellyfin.sera.ui.preference.dsl.optionsScreen
import org.jellyfin.preference.store.PreferenceStore
import org.koin.android.ext.android.inject

class HomePreferencesScreen : OptionsFragment() {
	private val userSettingPreferences: UserSettingPreferences by inject()

	override val stores: Array<PreferenceStore<*, *>>
		get() = arrayOf(userSettingPreferences)

	override val screen by optionsScreen {
		setTitle(R.string.home_prefs)

		category {
			setTitle(R.string.home_sections)

			userSettingPreferences.homesections.forEachIndexed { index, section ->
				enum<HomeSectionType> {
					title = getString(R.string.home_section_i, index + 1)
					bind(userSettingPreferences, section)
				}
			}
		}
	}
}
