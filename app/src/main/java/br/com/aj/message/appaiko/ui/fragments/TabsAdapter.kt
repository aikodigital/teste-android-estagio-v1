package br.com.aj.message.appaiko.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter(fm:FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
         return 2
    }

    override fun createFragment(position: Int): Fragment {
        return  when (position){
            0 -> MapFragment()
            else -> { Fragment() }
        }


    }
}