package org.jellyfin.sera.ui.preference.dsl

import androidx.preference.PreferenceCategory

interface OptionsItem {
	fun build(category: PreferenceCategory, container: OptionsUpdateFunContainer)
}
