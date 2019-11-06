package com.example.newproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val TAG:String = "Result Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth!!.currentUser

        result_emailData.text = user!!.email
        result_uidData.text = user.uid

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null) {
                startActivity(Intent(this@ResultActivity, LoginActivity::class.java))
                finish()
            }
        }

        result_IndexBtn.setOnClickListener {
            startActivity(Intent(this@ResultActivity, IndexActivity::class.java))
        }

        result_signOutBtn.setOnClickListener {
            mAuth!!.signOut()
            toast("Signed out!")
            Log.d(TAG, "Signed out!")
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) { mAuth!!.removeAuthStateListener { mAuthListener } }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { moveTaskToBack(true) }
        return super.onKeyDown(keyCode, event)
    }
}
