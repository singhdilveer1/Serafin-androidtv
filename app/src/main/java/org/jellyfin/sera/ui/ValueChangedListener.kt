package org.jellyfin.sera.ui

fun interface ValueChangedListener<T> {
	fun onValueChanged(value: T)
}
