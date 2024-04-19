package com.example.firstmvvm.ui.home.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstmvvm.R
import com.example.firstmvvm.data.db.entities.Post
import com.example.firstmvvm.util.Coroutines
import com.example.firstmvvm.util.hide
import com.example.firstmvvm.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PostsFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: PostsViewModel
    override val kodein by kodein()
    private val factory: PostsViewModelFactory by instance()
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        recycler = view.findViewById(R.id.post_rec)
        progressBar = view.findViewById(R.id.progressBar3)
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[PostsViewModel::class.java]
        bindUi()
    }

    private fun bindUi() = Coroutines.main {
        progressBar.show()
        val posts = viewModel.posts.await().observe(viewLifecycleOwner) {
            progressBar.hide()
            initRecyclerView(it.toPostItem())
        }
    }

    private fun initRecyclerView(postItem: List<PostItem>) {
        val adapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(postItem)
        }
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun List<Post>.toPostItem(): List<PostItem> = this.map {
        PostItem(it)
    }
}