package org.jellyfin.sera.auth.model

import org.jellyfin.sera.R
import org.jellyfin.preference.PreferenceEnum

enum class AuthenticationSortBy(
	override val nameRes: Int
) : PreferenceEnum {
	LAST_USE(R.string.last_use),
	ALPHABETICAL(R.string.alphabetical);
}
