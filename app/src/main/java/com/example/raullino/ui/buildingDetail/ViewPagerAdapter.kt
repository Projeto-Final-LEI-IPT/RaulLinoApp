package com.example.raullino.ui.buildingDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.raullino.databinding.ImageItemBinding

class ImageViewPagerAdapter(private val drawableList: List<Int>) :
    RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(drawableResId: Int) {
            Glide.with(binding.root.context)
                .load(drawableResId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivImage)
        }
    }

    override fun getItemCount(): Int = drawableList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(drawableList[position])
    }
}