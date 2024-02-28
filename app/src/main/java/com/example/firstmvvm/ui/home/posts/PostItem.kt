package com.example.firstmvvm.ui.home.posts

import com.example.firstmvvm.R
import com.example.firstmvvm.data.db.entities.Post
import com.example.firstmvvm.databinding.ItemPostBinding
import com.xwray.groupie.databinding.BindableItem

class PostItem(
    private val post: Post
): BindableItem<ItemPostBinding>() {
    override fun bind(viewBinding: ItemPostBinding, position: Int) {
        viewBinding.post = post
    }

    override fun getLayout(): Int = R.layout.item_post
}