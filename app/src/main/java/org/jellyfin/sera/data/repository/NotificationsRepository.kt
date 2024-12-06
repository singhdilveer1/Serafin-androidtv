package org.jellyfin.sera.data.repository

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jellyfin.sera.BuildConfig
import org.jellyfin.sera.R
import org.jellyfin.sera.auth.model.Server
import org.jellyfin.sera.auth.repository.ServerRepository
import org.jellyfin.sera.data.model.AppNotification
import org.jellyfin.sera.preference.SystemPreferences
import org.jellyfin.sera.util.isTvDevice
import org.jellyfin.sdk.model.ServerVersion

interface NotificationsRepository {
	val notifications: StateFlow<List<AppNotification>>

	fun dismissNotification(item: AppNotification)
	fun addDefaultNotifications()
	fun updateServerNotifications(server: Server?)
}

class NotificationsRepositoryImpl(
	private val context: Context,
	private val systemPreferences: SystemPreferences,
) : NotificationsRepository {
	override val notifications = MutableStateFlow(emptyList<AppNotification>())

	override fun dismissNotification(item: AppNotification) {
		notifications.value = notifications.value.filter { it != item }
		item.dismiss()
	}

	override fun addDefaultNotifications() {
		addUiModeNotification()
		addBetaNotification()
	}

	private fun addNotification(
		message: String,
		public: Boolean = false,
		dismiss: () -> Unit = {}
	): AppNotification {
		val notification = AppNotification(message, dismiss, public)
		notifications.value += notification
		return notification
	}

	private fun removeNotification(notification: AppNotification) {
		notifications.value -= notification
	}

	private fun addUiModeNotification() {
		val disableUiModeWarning = systemPreferences[SystemPreferences.disableUiModeWarning]

		if (!context.isTvDevice() && !disableUiModeWarning) {
			addNotification(
				context.getString(R.string.app_notification_uimode_invalid),
				public = true
			)
		}
	}

	private fun addBetaNotification() {
		val dismissedVersion = systemPreferences[SystemPreferences.dismissedBetaNotificationVersion]
		val currentVersion = BuildConfig.VERSION_NAME
		val isBeta = currentVersion.lowercase().contains("beta")

		if (isBeta && currentVersion != dismissedVersion) {
			addNotification(context.getString(R.string.app_notification_beta, currentVersion)) {
				systemPreferences[SystemPreferences.dismissedBetaNotificationVersion] =
					currentVersion
			}
		}
	}

	// Update server notification
	private var _updateServerNotification: AppNotification? = null
	override fun updateServerNotifications(server: Server?) {
		// Remove current update notification
		_updateServerNotification?.let(::removeNotification)

		val currentServerVersion = server?.version?.let(ServerVersion::fromString) ?: return
		if (currentServerVersion < ServerRepository.upcomingMinimumServerVersion) {
			_updateServerNotification =
				addNotification(
					message = context.getString(
						R.string.app_notification_update_soon,
						currentServerVersion,
						ServerRepository.upcomingMinimumServerVersion
					),
				)
		}
	}
}
