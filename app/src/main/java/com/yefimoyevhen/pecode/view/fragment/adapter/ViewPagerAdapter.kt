package com.yefimoyevhen.pecode.view.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yefimoyevhen.pecode.view.fragment.ScreenFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments: List<Int> = listOf()

    fun submitList(fragments:List<Int>) {
        this.fragments = fragments
        notifyDataSetChanged()
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return ScreenFragment.newInstance(fragments[position])
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.contains(itemId.toInt())
    }

}
