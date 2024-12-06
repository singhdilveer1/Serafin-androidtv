package org.jellyfin.sera.ui.startup.preference

import androidx.core.os.bundleOf
import org.jellyfin.sera.R
import org.jellyfin.sera.auth.repository.ServerUserRepository
import org.jellyfin.sera.ui.preference.dsl.OptionsFragment
import org.jellyfin.sera.ui.preference.dsl.action
import org.jellyfin.sera.ui.preference.dsl.link
import org.jellyfin.sera.ui.preference.dsl.optionsScreen
import org.jellyfin.sera.ui.startup.StartupViewModel
import org.jellyfin.sera.util.getValue
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

class EditServerScreen : OptionsFragment() {
	private val startupViewModel: StartupViewModel by activityViewModel()
	private val serverUserRepository: ServerUserRepository by inject()

	override val rebuildOnResume = true

	override val screen by optionsScreen {
		val serverUUID = requireNotNull(
			requireArguments().getValue<UUID>(ARG_SERVER_UUID)
		) { "Server null or malformed uuid" }

		val server = requireNotNull(startupViewModel.getServer(serverUUID)) { "Server not found" }
		val users = serverUserRepository.getStoredServerUsers(server)

		title = server.name

		if (users.isNotEmpty()) {
			category {
				setTitle(R.string.pref_accounts)

				users.forEach { user ->
					link {
						title = user.name
						icon = R.drawable.ic_user

						val lastUsedDate = LocalDateTime.ofInstant(
							Instant.ofEpochMilli(user.lastUsed),
							ZoneId.systemDefault()
						)
						content = context.getString(
							R.string.lbl_user_last_used,
							lastUsedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
							lastUsedDate.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
						)

						withFragment<EditUserScreen>(bundleOf(
							EditUserScreen.ARG_SERVER_UUID to server.id,
							EditUserScreen.ARG_USER_UUID to user.id,
						))
					}
				}
			}
		}

		category {
			setTitle(R.string.lbl_server)

			action {
				setTitle(R.string.lbl_remove_server)
				setContent(R.string.lbl_remove_users)
				icon = R.drawable.ic_delete
				onActivate = {
					startupViewModel.deleteServer(serverUUID)

					parentFragmentManager.popBackStack()
				}
			}
		}
	}

	companion object {
		const val ARG_SERVER_UUID = "server_uuid"
	}
}
