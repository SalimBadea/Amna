package com.salem.amna.presentation.ui.on_boarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.salem.amna.R

class IntroViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val lottieAnimation : List<Int> = listOf(R.drawable.intro1, R.drawable.intro2, R.drawable.intro3),
    private val titlesList:List<String>,
    private val descriptionList: List<String>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int =  lottieAnimation.size

    override fun createFragment(position: Int): Fragment = IntroFragment(
        lottieAnimation[position],
        titlesList[position],
        descriptionList[position]
    )
}