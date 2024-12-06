package org.jellyfin.sera.constant

sealed interface CustomMessage {
	data object RefreshCurrentItem : CustomMessage
	data object ActionComplete : CustomMessage
}
