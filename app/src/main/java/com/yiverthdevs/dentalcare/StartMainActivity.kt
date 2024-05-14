package com.yiverthdevs.dentalcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.analytics.FirebaseAnalytics


class StartMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)

        supportActionBar?.hide()

        Handler().postDelayed({

            val intent=Intent(this@StartMainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)

        // Obtain the FirebaseAnalytics instance.
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle )

    }
}