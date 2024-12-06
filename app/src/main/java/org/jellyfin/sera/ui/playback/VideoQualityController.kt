package org.jellyfin.sera.ui.playback

import org.jellyfin.sera.preference.UserPreferences;

class VideoQualityController(
	previousQualitySelection: String,
	private val userPreferences: UserPreferences,
) {
	var currentQuality = previousQualitySelection
		set(value) {
			userPreferences[UserPreferences.maxBitrate] = value
			field = value
		}
}
