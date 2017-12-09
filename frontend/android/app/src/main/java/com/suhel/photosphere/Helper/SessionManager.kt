package com.suhel.photosphere.Helper

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.suhel.photosphere.DataModels.Types.Derived.User
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.REQ_GOOGLE_SIGN_IN
import com.suhel.photosphere.Singleton.callbackManager

fun googleLogin(activity: Activity) {

    val opts = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.google_web_client_id))
            .requestEmail()
            .build()

    val googleApiClient = GoogleApiClient.Builder(activity)
            .enableAutoManage(activity as FragmentActivity, {

            })
            .addApi(Auth.GOOGLE_SIGN_IN_API, opts)
            .build()

    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
    activity.startActivityForResult(signInIntent, REQ_GOOGLE_SIGN_IN)
}

fun facebookLogin(activity: Activity) {

    LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

        override fun onSuccess(result: LoginResult?) {

            handleFacebookLogin(result?.accessToken)
        }

        override fun onError(error: FacebookException?) {

        }

        override fun onCancel() {
        }

    })
    LoginManager.getInstance().logInWithReadPermissions(activity, listOf("public_profile", "email"))

}

fun handleGoogleLogin(result: GoogleSignInResult) {
    val authCredential = GoogleAuthProvider.getCredential(result.signInAccount?.idToken, null)
    FirebaseAuth.getInstance().signInWithCredential(authCredential)
            .addOnCompleteListener {
                task ->

                Log.e("Facebook LoginFragment", task.toString())

                if (task.isSuccessful) {
                    FirebaseAuth.getInstance().currentUser?.let {
                        val id = it.uid

                        val user = User()
                        user.id = id
                        user.name = result.signInAccount?.displayName ?: ""
                        user.email = result.signInAccount?.email ?: ""
                        user.profilePicUrl = result.signInAccount?.photoUrl.toString()
                        user.providerId = result.signInAccount?.id ?: ""
                        user.providerType = User.ProviderType.GOOGLE

                        user.insert()
                    }

                }
            }
}

fun handleFacebookLogin(token: AccessToken?) {
    val authCredential = FacebookAuthProvider.getCredential(token!!.token)
    FirebaseAuth.getInstance().signInWithCredential(authCredential)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    FirebaseAuth.getInstance().currentUser?.let {
                        val id = it.uid

                        val facebookGraphRequest = GraphRequest.newMeRequest(token, {
                            jsonObject, graphResponse ->

                            if (graphResponse.error == null) {

                                val user = User()
                                user.id = id
                                user.name = jsonObject["name"].toString()
                                user.email = jsonObject["email"].toString()
                                user.providerId = jsonObject["id"].toString()
                                user.profilePicUrl = "https://graph.facebook.com/${user.providerId}/picture?width=256&height=256"
                                user.providerType = User.ProviderType.FACEBOOK

                                user.insert()
                            }
                        })

                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,email")
                        facebookGraphRequest.parameters = parameters
                        facebookGraphRequest.executeAsync()

                    }

                }
            }
}

fun logout() {

    FirebaseAuth.getInstance().signOut()

}