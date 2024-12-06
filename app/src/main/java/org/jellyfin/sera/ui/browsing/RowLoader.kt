package org.jellyfin.sera.ui.browsing

interface RowLoader {
	fun loadRows(rows: MutableList<BrowseRowDef>)
}
