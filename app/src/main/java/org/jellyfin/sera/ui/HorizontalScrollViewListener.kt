package org.jellyfin.sera.ui

fun interface HorizontalScrollViewListener {
	fun onScrollChanged(view: ObservableHorizontalScrollView?, x: Int, y: Int, oldx: Int, oldy: Int)
}
