package com.samuelcabezas.rmoviesapp.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.samuelcabezas.rmoviesapp.R
import net.gahfy.mvvmposts.utils.extension.getParentActivity
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.samuelcabezas.rmoviesapp.view.ui.main.MainActivity
import com.samuelcabezas.rmoviesapp.view.ui.main.MainPagerAdapter

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("mutableImage")
fun setMutableImage(imageView: ImageView, url: String) {
    GlideApp.with(imageView.context)
            .load(url)
            //.diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.no_image)
            .into(imageView)

}

@BindingAdapter("mutableViewPager")
fun setMutableViewPager(view: ViewPager, activity: MainActivity) {
    val adapter = MainPagerAdapter(view.context, activity.supportFragmentManager)
    view.offscreenPageLimit = 3
    view.adapter = adapter
}

@BindingAdapter("mutablePager")
fun setMutablePager(view: TabLayout, pagerView: ViewPager) {
    view.setupWithViewPager(pagerView)
}