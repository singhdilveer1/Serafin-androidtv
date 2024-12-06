package org.jellyfin.sera.ui.playback.overlay.action

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import org.jellyfin.sera.R
import org.jellyfin.sera.ui.playback.PlaybackController
import org.jellyfin.sera.ui.playback.PlaybackManager
import org.jellyfin.sera.ui.playback.overlay.CustomPlaybackTransportControlGlue
import org.jellyfin.sera.ui.playback.overlay.VideoPlayerAdapter

class SelectAudioAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
	private val playbackManager: PlaybackManager,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_select_audio)
	}

	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.leanbackOverlayFragment.setFading(false)
		val audioTracks = playbackManager.getInPlaybackSelectableAudioStreams(playbackController.currentStreamInfo)
			?: return
		val currentAudioIndex = playbackController.audioStreamIndex

		PopupMenu(context, view, Gravity.END).apply {
			with(menu) {
				for (track in audioTracks) {
					add(0, track.index, track.index, track.displayTitle).apply {
						isChecked = currentAudioIndex == track.index
					}
				}
				setGroupCheckable(0, true, false)
			}

			setOnDismissListener { videoPlayerAdapter.leanbackOverlayFragment.setFading(true) }
			setOnMenuItemClickListener { item ->
				playbackController.switchAudioStream(item.itemId)
				true
			}
		}.show()
	}
}
