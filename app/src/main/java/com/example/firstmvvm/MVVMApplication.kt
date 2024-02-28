package com.example.firstmvvm

import android.app.Application
import com.example.firstmvvm.data.db.AppDatabase
import com.example.firstmvvm.data.network.MyApi
import com.example.firstmvvm.data.network.NetworkConnectionInterceptor
import com.example.firstmvvm.data.repo.PostRepository
import com.example.firstmvvm.data.repo.UserRepository
import com.example.firstmvvm.ui.auth.AuthViewModelFactory
import com.example.firstmvvm.ui.home.posts.PostsViewModelFactory
import com.example.firstmvvm.ui.home.profile.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { PostRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { PostsViewModelFactory(instance()) }
    }
}