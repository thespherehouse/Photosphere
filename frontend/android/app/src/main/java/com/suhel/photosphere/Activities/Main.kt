package com.suhel.photosphere.Activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.suhel.photosphere.Singleton.FIELD_POST_LIKES_COUNT
import com.suhel.photosphere.DataModels.Types.Derived.User
import com.suhel.photosphere.Dialogs.PostUpload
import com.suhel.photosphere.Fragments.*
import com.suhel.photosphere.Helper.*
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.*
import kotlinx.android.synthetic.main.activity_main.*

class Main : AppCompatActivity(), CommonCallback {

    var isModalTaskOngoingBacking = false

    val login by lazy { LoginFragment() }
    val timeline by lazy { TimelineFragment() }
    val trending by lazy { TimelineFragment("trending", FIELD_POST_LIKES_COUNT) }
    val chooseCompetition by lazy { ChooseCompetition() }
    val fragSupport by lazy { SupportFragment() }
    val myProfile by lazy { ProfileFragment(auth.currentUser!!.uid) }

    val authListener = FirebaseAuth.AuthStateListener {
        auth ->

        if (auth.currentUser == null)
            onAction(Actions.CALLBACK_LOGGED_OUT)
        else
            onAction(Actions.CALLBACK_LOGGED_IN)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        setContentView(R.layout.activity_main)
        callbackManager = CallbackManager.Factory.create()
        mainDrawer.setScrimColor(0xDD000000.toInt())
        bMainMenu.setOnClickListener {
            if (mainDrawer.isDrawerOpen(Gravity.LEFT)) {
                mainDrawer.closeDrawers()
            } else {
                mainDrawer.openDrawer(Gravity.LEFT)
            }
        }
        bNavTimeline.setOnClickListener {
            onAction(Actions.ACTION_NAV_TIMELINE)
        }
        bNavTrending.setOnClickListener {
            onAction(Actions.ACTION_NAV_TRENDING)
        }
        bNavProfile.setOnClickListener {
            onAction(Actions.ACTION_NAV_PROFILE)
        }
        bNavSupport.setOnClickListener {
            onAction(Actions.ACTION_NAV_SUPPORT)
        }
        bNavLogout.setOnClickListener {
            onAction(Actions.ACTION_NAV_LOGOUT)
        }
        FirebaseAuth.getInstance()?.addAuthStateListener(authListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE)
            invokeTaskForPermissionSuccess(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {

                REQ_FROM_GALLERY -> {
                    handleGalleryIntent(data)
                }

                REQ_FROM_EXTERNAL_CAMERA -> {
                    handleExternalCameraIntent()
                }

                REQ_GOOGLE_SIGN_IN -> {
                    val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                    if (result.isSuccess)
                        handleGoogleLogin(result)
                }

            }
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAction(action: Actions) {

        when (action) {

            Actions.ACTION_LOGIN_GOOGLE -> googleLogin(this)

            Actions.ACTION_LOGIN_FACEBOOK -> facebookLogin(this)

            Actions.ACTION_NAV_TIMELINE ->
                if (!isModalTaskOngoingBacking) {
                    mainDrawer.closeDrawers()
                    loadFragmentWithFade(R.id.mainFrame, timeline, true)
                } else {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ERROR, "Timeline")
                    Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()
                }

            Actions.ACTION_NAV_TRENDING ->
                if (!isModalTaskOngoingBacking) {
                    mainDrawer.closeDrawers()
                    loadFragmentWithFade(R.id.mainFrame, trending, true)
                } else {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ERROR, "Trending")
                    Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()
                }

            Actions.ACTION_NAV_PROFILE ->
                if (!isModalTaskOngoingBacking) {
                    mainDrawer.closeDrawers()
                    loadFragmentWithFade(R.id.mainFrame, myProfile, true)
                } else {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ERROR, "Profile")
                    Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()
                }

            Actions.ACTION_NAV_COMPETITIONS ->
                if (!isModalTaskOngoingBacking) {
                    mainDrawer.closeDrawers()
                    loadFragmentWithFade(R.id.mainFrame, chooseCompetition, true)
                } else {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ERROR, "Competitions")
                    Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()
                }

            Actions.ACTION_NAV_SUPPORT ->
                if (!isModalTaskOngoingBacking) {
                    mainDrawer.closeDrawers()
                    loadFragmentWithFade(R.id.mainFrame, fragSupport, true)
                } else {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ERROR, "Support")
                    Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()
                }

            Actions.ACTION_NAV_LOGOUT ->
                if (!isModalTaskOngoingBacking) {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.NAVIGATE, "Logout")
                    mainDrawer.closeDrawers()
                    logout()
                } else {
                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ERROR, "Logout")
                    Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()
                }

            Actions.EXTERNAL_CAMERA_LOAD ->
                currentFilePath?.let {
                    PostUpload().show(supportFragmentManager, null)
                }

            Actions.GALLERY_LOAD ->
                currentFilePath?.let {
                    PostUpload().show(supportFragmentManager, null)
                }

            Actions.ACTION_CHOOSE_EXTERNAL_CAMERA ->
                if (Build.VERSION.SDK_INT >= 23)
                    askForPermissionsWithCallback(arrayListOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), {
                        fireExternalCameraIntent()
                    })
                else {
                    fireExternalCameraIntent()
                }

            Actions.ACTION_CHOOSE_GALLERY -> {
                if (Build.VERSION.SDK_INT >= 23)
                    askForPermissionsWithCallback(arrayListOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), {
                        fireGalleryIntent()
                    })
                else
                    fireGalleryIntent()
            }

            Actions.CALLBACK_LOGGED_IN -> {
                User.changeLoginState(true)
                val packageInfo = packageManager.getPackageInfo(packageName, 0)
                val versionCode = packageInfo.versionCode
                User.changeCurrentAppVersion(versionCode)
                loadFragmentWithFade(R.id.mainFrame, timeline, true)
            }

            Actions.CALLBACK_LOGGED_OUT -> {
                User.changeLoginState(false)
                loadFragmentWithFade(R.id.mainFrame, login, true)
            }

            else -> return

        }
    }

    override fun onError(errorMessage: String) {

    }

    override fun showAppBar(shown: Boolean) {
        if (shown) {
            appBar.visibility = View.VISIBLE
            mainDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            appBar.visibility = View.GONE
            mainDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    override fun setTitle(title: String) {
        lblMainTitle.text = title
    }

    override fun setTitleVisibility(shown: Boolean) {
        if (shown) {
            lblMainTitle.animate().translationX(0.0f).alpha(1.0f)
        } else {
            lblMainTitle.animate().translationX(-50.0f).alpha(0.0f)
        }
    }

    override fun setAppFullscreen(fullscreen: Boolean) {

        if (fullscreen) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun setModalTaskOngoing(ongoing: Boolean) {
        isModalTaskOngoingBacking = ongoing
    }

    override fun isModalTaskOngoing(): Boolean {
        return isModalTaskOngoingBacking
    }

    override fun onStart() {
        super.onStart()
        User.changeCurrentUserOnlineState(User.OnlineState.ONLINE)
        supportCallback = {
            support ->

            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionCode = packageInfo.versionCode
            if (support.criticalVersion >= versionCode) {
                val alertDialog = AlertDialog.Builder(this@Main)
                        .setMessage("Please update the application to continue")
                        .setPositiveButton("Ok", {
                            _, _ ->

                            finish()
                        }).create()
                alertDialog.setCancelable(false)
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.show()
            }
        }
        addSupportListener()
    }

    override fun onStop() {
        super.onStop()
        User.changeCurrentUserOnlineState(User.OnlineState.OFFLINE)
        removeSupportListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        activity = null
        FirebaseAuth.getInstance()?.removeAuthStateListener(authListener)
    }

    override fun onBackPressed() {
        if (!isModalTaskOngoingBacking)
            super.onBackPressed()
        else
            Toast.makeText(this, "Cannot navigate now", Toast.LENGTH_SHORT).show()

    }

}
