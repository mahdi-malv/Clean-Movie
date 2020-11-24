package ir.malv.cleanmovies.ui.utils

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

fun NavController.navigateToCompose(route: String, options: NavOptions? = null) {
    navigate(NavDeepLinkRequest.Builder.fromUri(createRoute(route).toUri()).build(), options)
}

fun createRoute(route: String) = "android-app://androidx.navigation.compose/$route"