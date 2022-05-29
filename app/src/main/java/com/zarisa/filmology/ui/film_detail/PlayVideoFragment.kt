package com.zarisa.filmology.ui.film_detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.zarisa.filmology.databinding.FragmentPlayVideoBinding


class PlayVideoFragment : Fragment() {
    lateinit var binding: FragmentPlayVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentPlayVideoBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView(requireArguments().getString("url",""))
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(videoKey: String) {
        binding.webView.let {
            it.loadUrl("https://www.youtube.com/watch?v=${videoKey}")
            it.settings.javaScriptEnabled = true
            it.webViewClient = WebViewClient()
            it.canGoBack()
        }
    }
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()){
//            binding.webView.goBack()
//            return true
//        }
//        return false
//    }

}