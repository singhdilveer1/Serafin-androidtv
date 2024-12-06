package org.jellyfin.sera.ui.playback.overlay.action

import android.content.Context
import android.view.Gravity
import android.view.View
import org.jellyfin.sera.R
import org.jellyfin.sera.preference.constant.ZoomMode
import org.jellyfin.sera.ui.playback.PlaybackController
import org.jellyfin.sera.ui.playback.overlay.CustomPlaybackTransportControlGlue
import org.jellyfin.sera.ui.playback.overlay.VideoPlayerAdapter
import org.jellyfin.sera.util.popupMenu

class ZoomAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	init {
		initializeWithIcon(R.drawable.ic_aspect_ratio)
	}

	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.leanbackOverlayFragment.setFading(false)
		val popup = popupMenu(context, view, Gravity.END) {
			item(context.getString(R.string.lbl_fit)) {
				playbackController.setZoom(ZoomMode.FIT)
			}.apply {
				isChecked = playbackController.zoomMode == ZoomMode.FIT
			}

			item(context.getString(R.string.lbl_auto_crop)) {
				playbackController.setZoom(ZoomMode.AUTO_CROP)
			}.apply {
				isChecked = playbackController.zoomMode == ZoomMode.AUTO_CROP
			}

			item(context.getString(R.string.lbl_stretch)) {
				playbackController.setZoom(ZoomMode.STRETCH)
			}.apply {
				isChecked = playbackController.zoomMode == ZoomMode.STRETCH
			}
		}
		popup.menu.setGroupCheckable(0, true, true)
		popup.setOnDismissListener { videoPlayerAdapter.leanbackOverlayFragment.setFading(true) }
		popup.show()
	}
}
