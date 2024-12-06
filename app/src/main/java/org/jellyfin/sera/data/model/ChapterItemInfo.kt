package org.jellyfin.sera.data.model

import java.util.UUID

data class ChapterItemInfo(
	val itemId: UUID,
	val name: String?,
	val startPositionTicks: Long,
	val imagePath: String?,
)
