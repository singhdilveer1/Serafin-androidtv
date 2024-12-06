package org.jellyfin.sera.ui.home

import android.content.Context
import androidx.leanback.widget.Row
import org.jellyfin.sera.ui.presentation.CardPresenter
import org.jellyfin.sera.ui.presentation.MutableObjectAdapter

interface HomeFragmentRow {
	fun addToRowsAdapter(context: Context, cardPresenter: CardPresenter, rowsAdapter: MutableObjectAdapter<Row>)
}
