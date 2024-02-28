package com.example.firstmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.firstmvvm.R
import com.example.firstmvvm.databinding.ActivitySingupBinding
import com.example.firstmvvm.ui.home.HomeActivity
import com.example.firstmvvm.util.APIException
import com.example.firstmvvm.util.NoInternetException
import com.example.firstmvvm.util.snakeBar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), KodeinAware {
    private lateinit var binding: ActivitySingupBinding
    private lateinit var viewModel: AuthViewModel

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_singup)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.getLoggedInUser().observe(this) { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        binding.signUp.setOnClickListener {
            // signUpUser() function will not work correctly because the API response is not dynamic and it's only an Dummy Api
        }

        binding.haveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signUpUser() {
        binding.apply {
            val firstName = userFirstName.text.toString()
            val lastName = userLastName.text.toString()
            val age = userage.text.toString()
            val userName = username.text.toString()
            val email = emailedittext.text.toString()
            val password = passwordedittext.text.toString()
            val confirmedPassword = passwordconfirmedittext.text.toString()

            lifecycleScope.launch {
                try {
                    val authResponse =
                        viewModel.userSingUp(firstName, lastName, age, email, userName, password)
                    if (authResponse != null) {
                        viewModel.saveLoggedUser(authResponse)
                    } else {
                        binding.rootView.snakeBar("Invalid credentials")
                    }
                } catch (e: APIException) {
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    e.printStackTrace()
                }
            }
        }
    }
}