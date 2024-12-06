package org.jellyfin.sera.ui.itemhandling

import android.content.Context
import org.jellyfin.sera.constant.ImageType
import org.jellyfin.sera.ui.GridButton
import org.jellyfin.sera.util.ImageHelper

class GridButtonBaseRowItem(
	val gridButton: GridButton,
) : BaseRowItem(
	baseRowType = BaseRowType.GridButton,
	staticHeight = true,
) {
	override fun getImageUrl(
		context: Context,
		imageHelper: ImageHelper,
		imageType: ImageType,
		fillWidth: Int,
		fillHeight: Int
	) = gridButton.imageRes?.let { imageHelper.getResourceUrl(context, it) }

	override fun getFullName(context: Context) = gridButton.text
	override fun getName(context: Context) = gridButton.text
}
