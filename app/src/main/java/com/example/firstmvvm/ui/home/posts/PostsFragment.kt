package com.example.firstmvvm.ui.home.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firstmvvm.R
import com.example.firstmvvm.util.Coroutines
import com.example.firstmvvm.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PostsFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: PostsViewModel
    override val kodein by kodein()
    private val factory: PostsViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[PostsViewModel::class.java]
        Coroutines.main {
            val posts = viewModel.posts.await()
            posts.observe(viewLifecycleOwner, Observer {
                context?.toast(it.size.toString())
            })
        }
    }

}