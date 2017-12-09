package com.suhel.photosphere.Helper

import android.os.Bundle

enum class Actions {

    ACTION_ENTER_FULLSCREEN,
    ACTION_EXIT_FULLSCREEN,

    ACTION_LOGIN_FACEBOOK,
    ACTION_LOGIN_GOOGLE,
    ACTION_LOGOUT,

    ACTION_NAV_TIMELINE,
    ACTION_NAV_TRENDING,
    ACTION_NAV_PROFILE,
    ACTION_NAV_COMPETITIONS,
    ACTION_NAV_SUPPORT,
    ACTION_NAV_LOGOUT,

    ACTION_CHOOSE_GALLERY,
    ACTION_CHOOSE_EXTERNAL_CAMERA,
    ACTION_CHOOSE_INTERNAL_CAMERA,

    ACTION_IMAGE_CAPTURE,

    INTERNAL_CAMERA_LOAD,
    EXTERNAL_CAMERA_LOAD,
    GALLERY_LOAD,

    CALLBACK_SPLASH_COMPLETE,
    CALLBACK_LOGGED_IN,
    CALLBACK_LOGGED_OUT

}

interface CommonCallback {

    fun onAction(action: Actions)

    fun onError(errorMessage: String)

    fun setTitle(title: String)

    fun showAppBar(shown: Boolean)

    fun setTitleVisibility(shown: Boolean)

    fun setAppFullscreen(fullscreen: Boolean)

    fun setModalTaskOngoing(ongoing: Boolean)

    fun isModalTaskOngoing(): Boolean

}
