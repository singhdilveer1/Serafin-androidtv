package org.jellyfin.sera.ui.home

import android.content.Context
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Row
import org.jellyfin.sera.R
import org.jellyfin.sera.data.querying.GetUserViewsRequest
import org.jellyfin.sera.ui.itemhandling.ItemRowAdapter
import org.jellyfin.sera.ui.presentation.CardPresenter
import org.jellyfin.sera.ui.presentation.MutableObjectAdapter
import org.jellyfin.sera.ui.presentation.UserViewCardPresenter

class HomeFragmentViewsRow(
	val small: Boolean,
) : HomeFragmentRow {
	private companion object {
		val smallCardPresenter = UserViewCardPresenter(true)
		val largeCardPresenter = UserViewCardPresenter(false)
	}

	override fun addToRowsAdapter(context: Context, cardPresenter: CardPresenter, rowsAdapter: MutableObjectAdapter<Row>) {
		val presenter = if (small) smallCardPresenter else largeCardPresenter
		val rowAdapter = ItemRowAdapter(context, GetUserViewsRequest, presenter, rowsAdapter)

		val header = HeaderItem(context.getString(R.string.lbl_my_media))
		val row = ListRow(header, rowAdapter)
		rowAdapter.setRow(row)
		rowAdapter.Retrieve()
		rowsAdapter.add(row)
	}
}
