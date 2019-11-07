package yut.fall2019.esaat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class QuestionFragmentAdapter(fm: FragmentManager, private val fragments: ArrayList<Fragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return this.fragments[position]
    }


    override fun getCount(): Int {
        return this.fragments.size
    }
}