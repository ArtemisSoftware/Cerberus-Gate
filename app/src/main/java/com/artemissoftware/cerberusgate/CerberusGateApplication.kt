package com.artemissoftware.cerberusgate

import android.app.Application
import android.provider.FontRequest
import android.util.Log
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig

class CerberusGateApplication : Application() {


    companion object {

        private val TAG = "EmojiCompatApplication"

        /** Change this to `false` when you want to use the downloadable Emoji font.  */
        private val USE_BUNDLED_EMOJI = true

    }

    override fun onCreate() {
        super.onCreate()
        val config: EmojiCompat.Config

            // Use a downloadable font for EmojiCompat
            val fontRequest = androidx.core.provider.FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs)
            config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(object : EmojiCompat.InitCallback() {
                    override fun onInitialized() {
                        Log.i(TAG, "EmojiCompat initialized")
                    }

                    override fun onFailed(throwable: Throwable?) {
                        Log.e(TAG, "EmojiCompat initialization failed", throwable)
                    }
                })

        EmojiCompat.init(config)
    }
}