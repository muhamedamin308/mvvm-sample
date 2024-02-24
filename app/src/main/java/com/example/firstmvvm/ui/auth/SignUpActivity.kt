package com.example.firstmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.firstmvvm.R
import com.example.firstmvvm.data.db.entities.User
import com.example.firstmvvm.databinding.ActivitySingupBinding
import com.example.firstmvvm.ui.home.HomeActivity
import com.example.firstmvvm.util.hide
import com.example.firstmvvm.util.show
import com.example.firstmvvm.util.snakeBar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), AuthListener, KodeinAware {
    private lateinit var binding: ActivitySingupBinding

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_singup)

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
        binding.progressBar2.show()
    }

    override fun onSuccess(user: User) {
        binding.progressBar2.hide()
    }

    override fun onFailed(message: String) {
        binding.progressBar2.hide()
        binding.rootView.snakeBar(message)
    }
}