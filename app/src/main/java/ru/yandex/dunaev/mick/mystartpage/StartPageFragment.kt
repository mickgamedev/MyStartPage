package ru.yandex.dunaev.mick.mystartpage

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_start_page.*
import ru.yandex.dunaev.mick.mystartpage.databinding.FragmentStartPageBinding

class StartPageFragment : Fragment() {
    private lateinit var binding: FragmentStartPageBinding
    var screenNumber: Int = -1
    var onNext: () -> Unit = { Log.v("Fragment", "next not binding") }
    var onSkip: () -> Unit = { Log.v("Fragment", "skip not binding") }

    var isEndScreen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_page, container, false)
        binding.viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        binding.apply {
            buttonSkip.setOnClickListener { _ -> onSkip() }
            buttonNext.setOnClickListener { _ -> onNext() }
            buttonEnd.setOnClickListener { _ -> onSkip() }

            if (isEndScreen) {
                buttonSkip.visibility = View.GONE
                buttonSkip.visibility = View.GONE
                buttonEnd.visibility = View.VISIBLE
            }
        }
        bindingDesc()
        return binding.root
    }

    private fun bindingDesc() {
        val desc = getScreenDesc(screenNumber)
        val headerFont = Typeface.createFromAsset(context!!.assets, "fonts/BebasNeue Bold.ttf")

        binding.apply {
            imageView.setImageResource(desc.imageId)
            textHeader.text = desc.headerText
            textHeader.typeface = headerFont
            textInfo.text = desc.infoText
            buttonNext.typeface = headerFont
            buttonSkip.typeface = headerFont
            buttonEnd.typeface = headerFont
        }

    }

    private fun getScreenDesc(i: Int) = when (i) {
        0 -> ScreenDesc(
            R.drawable.ic_onboarding_1,
            resources.getString(R.string.onboarding1_header_text),
            resources.getString(R.string.onboarding1_info_text)
        )
        1 -> ScreenDesc(
            R.drawable.ic_onboarding_2,
            resources.getString(R.string.onboarding2_header_text),
            resources.getString(R.string.onboarding2_info_text)
        )
        else -> ScreenDesc(
            R.drawable.ic_onboarding_3,
            resources.getString(R.string.onboarding3_header_text),
            resources.getString(R.string.onboarding3_info_text)
        )
    }
}

data class ScreenDesc(val imageId: Int, val headerText: String, val infoText: String)
