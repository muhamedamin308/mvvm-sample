package com.example.firstmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.firstmvvm.R
import com.example.firstmvvm.databinding.ActivityLoginBinding
import com.example.firstmvvm.ui.home.HomeActivity
import com.example.firstmvvm.util.APIException
import com.example.firstmvvm.util.NoInternetException
import com.example.firstmvvm.util.hide
import com.example.firstmvvm.util.snakeBar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
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
        binding.logIn.setOnClickListener {
            loginUser()
        }

        binding.textView2.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser() {
        val username = binding.emailedittext.text.toString().trim()
        val password = binding.passwordedittext.text.toString().trim()
        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userLogin(username, password)
                if (authResponse != null) {
                    binding.progressBar.hide()
                    viewModel.saveLoggedUser(authResponse)
                } else {
                    binding.rootLayout.snakeBar("Invalid credentials")
                }
            } catch (e: APIException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }
}
