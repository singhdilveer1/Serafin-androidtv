package org.jellyfin.sera.ui.preference.category

import android.os.Build
import org.jellyfin.sera.BuildConfig
import org.jellyfin.sera.R
import org.jellyfin.sera.ui.preference.dsl.OptionsScreen
import org.jellyfin.sera.ui.preference.dsl.link
import org.jellyfin.sera.ui.preference.screen.LicensesScreen

fun OptionsScreen.aboutCategory() = category {
	setTitle(R.string.pref_about_title)

	link {
		// Hardcoded strings for troubleshooting purposes
		title = "Jellyfin app version"
		content = "jellyfin-androidtv ${BuildConfig.VERSION_NAME} ${BuildConfig.BUILD_TYPE}"
		icon = R.drawable.ic_jellyfin
	}

	link {
		setTitle(R.string.pref_device_model)
		content = "${Build.MANUFACTURER} ${Build.MODEL}"
		icon = R.drawable.ic_tv
	}

	link {
		setTitle(R.string.licenses_link)
		setContent(R.string.licenses_link_description)
		icon = R.drawable.ic_guide
		withFragment<LicensesScreen>()
	}
}
