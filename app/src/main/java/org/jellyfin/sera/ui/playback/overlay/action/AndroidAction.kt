package org.jellyfin.sera.ui.playback.overlay.action

import org.jellyfin.sera.ui.playback.overlay.VideoPlayerAdapter

interface AndroidAction {
	fun onActionClicked(
		videoPlayerAdapter: VideoPlayerAdapter
	)
}
