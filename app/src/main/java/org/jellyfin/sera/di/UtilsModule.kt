package org.jellyfin.sera.di

import org.jellyfin.sera.util.ImageHelper
import org.koin.dsl.module

val utilsModule = module {
	single { ImageHelper(get()) }
}
