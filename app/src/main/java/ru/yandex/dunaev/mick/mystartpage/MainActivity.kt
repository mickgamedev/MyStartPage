package ru.yandex.dunaev.mick.mystartpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProviders
import ru.yandex.dunaev.mick.mystartpage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.viewPager.adapter = StartPagerAdapter(supportFragmentManager, ::onNext, ::onSkip)

    }

    fun onNext() {
        binding.viewPager.apply {
            val i = currentItem + 1
            val max = adapter!!.count
            if(i > max - 1) return
            currentItem = i
        }
    }

    fun onSkip() {
        finish()
    }
}

class StartPagerAdapter(fm: FragmentManager, val nextPage: () -> Unit = {}, val skipPage: () -> Unit = {}) :
    FragmentPagerAdapter(fm) {

    private fun setFragment(ir: Int) = StartPageFragment().apply {
        screenNumber = ir
        onSkip = skipPage
        onNext = nextPage
        if(ir == getCount() -1 ) isEndScreen = true
    }

    override fun getItem(position: Int): Fragment = setFragment(position)

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? = ""
}
