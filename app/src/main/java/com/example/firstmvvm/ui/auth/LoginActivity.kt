package com.example.firstmvvm.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.firstmvvm.R
import com.example.firstmvvm.data.db.entities.User
import com.example.firstmvvm.databinding.ActivityLoginBinding
import com.example.firstmvvm.util.hide
import com.example.firstmvvm.util.show
import com.example.firstmvvm.util.toast

class LoginActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this)[AuthViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(user: User) {
        binding.progressBar.hide()
        toast("${user.firstName} is Logged in")
    }

    override fun onFailed(message: String) {
        binding.progressBar.hide()
        toast(message)
    }
}
