package com.example.firstmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.firstmvvm.R
import com.example.firstmvvm.data.db.AppDatabase
import com.example.firstmvvm.data.db.entities.User
import com.example.firstmvvm.data.network.MyApi
import com.example.firstmvvm.data.network.NetworkConnectionInterceptor
import com.example.firstmvvm.data.repo.UserRepo
import com.example.firstmvvm.databinding.ActivityLoginBinding
import com.example.firstmvvm.ui.home.HomeActivity
import com.example.firstmvvm.util.hide
import com.example.firstmvvm.util.show
import com.example.firstmvvm.util.snakeBar

class LoginActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepo(api, db)
        val factory = AuthViewModelFactory(repository)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.authListener = this
        viewModel.getLoggedInUser().observe(this) { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(user: User) {
        binding.progressBar.hide()
    }

    override fun onFailed(message: String) {
        binding.progressBar.hide()
        binding.rootLayout.snakeBar(message)
    }
}
