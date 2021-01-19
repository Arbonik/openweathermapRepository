package com.castprogramms.openweathermap.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.castprogramms.openweathermap.MainActivity
import com.castprogramms.openweathermap.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


val USER_UUID_TAG = "userUUID"

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()


        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)

        loginViewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            login.setOnClickListener {
                val userLogin = username.text.toString()
                val userPass = password.text.toString()

                auth.createUserWithEmailAndPassword(userLogin, userPass)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext, context.getString(R.string.success_register),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                baseContext, resources.getString(R.string.welcome),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        auth.signInWithEmailAndPassword(userLogin, userPass)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    successLogin(auth.currentUser)
                                }
                            }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null)
            successLogin(auth.currentUser)
    }

    private fun successLogin(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle().apply {
            putString(USER_UUID_TAG, user?.uid)
        }
        startActivity(intent, bundle)
        finish()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}