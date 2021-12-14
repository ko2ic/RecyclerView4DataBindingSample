package com.ko2ic.binding

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["onRefresh", "refreshColors"])
fun SwipeRefreshLayout.onRefresh(listener: SwipeRefreshLayout.OnRefreshListener, @ColorInt vararg colors: Int) {
    setColorSchemeColors(*colors)
    setOnRefreshListener(listener)
}

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    Picasso.get()
        .load(url)
        .into(this);
}