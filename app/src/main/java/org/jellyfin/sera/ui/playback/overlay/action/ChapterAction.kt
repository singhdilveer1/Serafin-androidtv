package org.jellyfin.sera.ui.playback.overlay.action

import android.content.Context
import android.view.View
import org.jellyfin.sera.R
import org.jellyfin.sera.ui.playback.PlaybackController
import org.jellyfin.sera.ui.playback.overlay.CustomPlaybackTransportControlGlue
import org.jellyfin.sera.ui.playback.overlay.VideoPlayerAdapter

class ChapterAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_select_chapter)
	}

	@Override
	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.leanbackOverlayFragment.hideOverlay()
		videoPlayerAdapter.masterOverlayFragment.showChapterSelector();
	}
}
