package org.jellyfin.sera.di

import android.content.Context
import android.os.Build
import coil3.ImageLoader
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.serviceLoaderEnabled
import coil3.svg.SvgDecoder
import org.jellyfin.sera.BuildConfig
import org.jellyfin.sera.auth.repository.ServerRepository
import org.jellyfin.sera.auth.repository.UserRepository
import org.jellyfin.sera.auth.repository.UserRepositoryImpl
import org.jellyfin.sera.data.eventhandling.SocketHandler
import org.jellyfin.sera.data.model.DataRefreshService
import org.jellyfin.sera.data.repository.CustomMessageRepository
import org.jellyfin.sera.data.repository.CustomMessageRepositoryImpl
import org.jellyfin.sera.data.repository.ItemMutationRepository
import org.jellyfin.sera.data.repository.ItemMutationRepositoryImpl
import org.jellyfin.sera.data.repository.NotificationsRepository
import org.jellyfin.sera.data.repository.NotificationsRepositoryImpl
import org.jellyfin.sera.data.repository.UserViewsRepository
import org.jellyfin.sera.data.repository.UserViewsRepositoryImpl
import org.jellyfin.sera.data.service.BackgroundService
import org.jellyfin.sera.integration.dream.DreamViewModel
import org.jellyfin.sera.ui.ScreensaverViewModel
import org.jellyfin.sera.ui.itemhandling.ItemLauncher
import org.jellyfin.sera.ui.navigation.Destinations
import org.jellyfin.sera.ui.navigation.NavigationRepository
import org.jellyfin.sera.ui.navigation.NavigationRepositoryImpl
import org.jellyfin.sera.ui.picture.PictureViewerViewModel
import org.jellyfin.sera.ui.playback.PlaybackControllerContainer
import org.jellyfin.sera.ui.playback.nextup.NextUpViewModel
import org.jellyfin.sera.ui.playback.segment.MediaSegmentRepository
import org.jellyfin.sera.ui.playback.segment.MediaSegmentRepositoryImpl
import org.jellyfin.sera.ui.search.SearchFragmentDelegate
import org.jellyfin.sera.ui.search.SearchRepository
import org.jellyfin.sera.ui.search.SearchRepositoryImpl
import org.jellyfin.sera.ui.search.SearchViewModel
import org.jellyfin.sera.ui.startup.ServerAddViewModel
import org.jellyfin.sera.ui.startup.StartupViewModel
import org.jellyfin.sera.ui.startup.UserLoginViewModel
import org.jellyfin.sera.util.KeyProcessor
import org.jellyfin.sera.util.MarkdownRenderer
import org.jellyfin.sera.util.PlaybackHelper
import org.jellyfin.sera.util.apiclient.ReportingHelper
import org.jellyfin.sera.util.sdk.SdkPlaybackHelper
import org.jellyfin.sera.util.sdk.legacy
import org.jellyfin.apiclient.AppInfo
import org.jellyfin.apiclient.android
import org.jellyfin.apiclient.logging.AndroidLogger
import org.jellyfin.sdk.android.androidDevice
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.jellyfin.sdk.model.DeviceInfo
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.jellyfin.apiclient.Jellyfin as JellyfinApiClient
import org.jellyfin.sdk.Jellyfin as JellyfinSdk

val defaultDeviceInfo = named("defaultDeviceInfo")

val appModule = module {
	// New SDK
	single(defaultDeviceInfo) { androidDevice(get()) }
	single {
		createJellyfin {
			context = androidContext()

			// Add client info
			clientInfo = ClientInfo("Android TV", BuildConfig.VERSION_NAME)
			deviceInfo = get(defaultDeviceInfo)

			// Change server version
			minimumServerVersion = ServerRepository.minimumServerVersion
		}
	}

	single {
		// Create an empty API instance, the actual values are set by the SessionRepository
		get<JellyfinSdk>().createApi()
	}

	single { SocketHandler(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

	// Old apiclient
	single {
		JellyfinApiClient {
			appInfo = AppInfo("Android TV", BuildConfig.VERSION_NAME)
			logger = AndroidLogger()
			android(androidApplication())
		}
	}

	single {
		get<JellyfinApiClient>().createApi(
			device = get<DeviceInfo>(defaultDeviceInfo).legacy()
		)
	}

	// Coil (images)
	single {
		ImageLoader.Builder(androidContext()).apply {
			serviceLoaderEnabled(false)
			components {
				add(OkHttpNetworkFetcherFactory())
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(AnimatedImageDecoder.Factory())
				else add(GifDecoder.Factory())
				add(SvgDecoder.Factory())
			}
		}.build()
	}

	// Non API related
	single { DataRefreshService() }
	single { PlaybackControllerContainer() }

	single<UserRepository> { UserRepositoryImpl() }
	single<UserViewsRepository> { UserViewsRepositoryImpl(get()) }
	single<NotificationsRepository> { NotificationsRepositoryImpl(get(), get()) }
	single<ItemMutationRepository> { ItemMutationRepositoryImpl(get(), get()) }
	single<CustomMessageRepository> { CustomMessageRepositoryImpl() }
	single<NavigationRepository> { NavigationRepositoryImpl(Destinations.home) }
	single<SearchRepository> { SearchRepositoryImpl(get()) }
	single<MediaSegmentRepository> { MediaSegmentRepositoryImpl(get(), get()) }

	viewModel { StartupViewModel(get(), get(), get(), get()) }
	viewModel { UserLoginViewModel(get(), get(), get(), get(defaultDeviceInfo)) }
	viewModel { ServerAddViewModel(get()) }
	viewModel { NextUpViewModel(get(), get(), get(), get()) }
	viewModel { PictureViewerViewModel(get()) }
	viewModel { ScreensaverViewModel(get()) }
	viewModel { SearchViewModel(get()) }
	viewModel { DreamViewModel(get(), get(), get(), get(), get()) }

	single { BackgroundService(get(), get(), get(), get(), get()) }

	single { MarkdownRenderer(get()) }
	single { ItemLauncher() }
	single { KeyProcessor() }
	single { ReportingHelper(get(), get()) }
	single<PlaybackHelper> { SdkPlaybackHelper(get(), get(), get(), get(), get(), get(), get()) }

	factory { (context: Context) -> SearchFragmentDelegate(context, get(), get()) }
}
