package com.karegraf.androidarchitecturestarterkit.data.remote.Interceptor

import android.util.Log
import com.karegraf.androidarchitecturestarterkit.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptor @Inject constructor(private val sessionManager: SessionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val req = chain?.request()
        val res = chain?.proceed(req)
        if (res?.code() == 401) {
            Log.d("AuthInterceptor", "🔴 401 Unauthorized Yetkisiz erisim algilandi: ${req?.url()}")
            sessionManager.onSessionExpired()
        }
        return res
    }
}