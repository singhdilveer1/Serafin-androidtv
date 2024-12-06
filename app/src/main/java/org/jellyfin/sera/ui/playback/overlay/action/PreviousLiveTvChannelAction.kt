package org.jellyfin.sera.ui.playback.overlay.action

import android.content.Context
import android.view.View
import org.jellyfin.sera.R
import org.jellyfin.sera.ui.livetv.TvManager
import org.jellyfin.sera.ui.playback.PlaybackController
import org.jellyfin.sera.ui.playback.overlay.CustomPlaybackTransportControlGlue
import org.jellyfin.sera.ui.playback.overlay.VideoPlayerAdapter as VideoPlayerAdapter

class PreviousLiveTvChannelAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_previous_episode)
	}

	@Override
	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.masterOverlayFragment.switchChannel(TvManager.getPrevLiveTvChannel())
	}
}
