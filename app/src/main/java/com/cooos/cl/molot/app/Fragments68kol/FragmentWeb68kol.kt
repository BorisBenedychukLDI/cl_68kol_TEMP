package com.cooos.cl.molot.app.Fragments68kol

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cooos.cl.molot.app.*
import com.cooos.cl.molot.app.databinding.FragmentWeb68kolBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FragmentWeb68kol : Fragment(R.layout.fragment_web_68kol){

    private lateinit var bindingWeb68kol: FragmentWeb68kolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingWeb68kol = FragmentWeb68kolBinding.inflate(inflater, container, false)
        return bindingWeb68kol.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupWebView68kol()
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bindingWeb68kol.wv68kol.canGoBack()) {
                    bindingWeb68kol.wv68kol.goBack()
                } else {
                    requireActivity().finish()
                }
            }
        }.also {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        lifecycleScope.launch {
            while (isActive) {
                if (!requireContext().checkInternet68kol()) {
                    findNavController().navigate(
                        R.id.action_fragmentWeb68kol_to_fragmentInternet68kol,
                        Bundle().apply {
                            putString(
                                INTERNET_KEY_68kol, "Web"
                            )
                        })
                    cancel()
                }
                delay(500)
            }
        }
        super.onResume()
    }


    private fun setupWebView68kol() {
        bindingWeb68kol.wv68kol.run {
            bindingWeb68kol.srl68kol.setOnRefreshListener {
                loadUrl(url ?: return@setOnRefreshListener)
                bindingWeb68kol.srl68kol.isRefreshing = false
            }
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.run {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                displayZoomControls = false
                domStorageEnabled = true
                loadWithOverviewMode = true
                displayZoomControls = false
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                builtInZoomControls = true
                useWideViewPort = true
                mediaPlaybackRequiresUserGesture = false

            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view68kol: WebView?,
                    request68kol: WebResourceRequest?
                ): Boolean {
                    val prohibitedLinks68kol =
                        listOf("facebook", "instagram", "youtube", "booking", "tripadvisor", "maps")
                    val modifiedLinks68kol = listOf("mailto:", "tel:")
                    return when {
                        modifiedLinks68kol.find {
                            request68kol?.url.toString().startsWith(it)
                        } != null -> {
                            view68kol?.context?.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    request68kol?.url
                                )
                            )
                            true
                        }
                        prohibitedLinks68kol.find {
                            request68kol?.url.toString().contains(it)
                        } != null ->
                            true
                        else -> false
                    }
                }

                override fun onPageFinished(view68kol: WebView?, url68kol: String?) {
                    requireContext().getSharedPreferences(SP_KEY_68kol, MODE_PRIVATE).edit()
                        .putString("Last_Page_68kol", url ?: return).apply()
                    super.onPageFinished(view68kol, url68kol)
                }

                override fun onReceivedSslError(
                    view68kol: WebView?,
                    handler68kol: SslErrorHandler?,
                    error68kol: SslError?
                ) {
                    handler68kol?.proceed()
                    super.onReceivedSslError(view68kol, handler68kol, error68kol)
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    requireContext().checkPermissions68kol()
                    filePathCB68kol = filePathCallback
                    val captureIntent68kol = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (captureIntent68kol.resolveActivity(requireContext().packageManager) != null) {
                        val providedFile68kol = requireContext().createTempFile68kol()
                        uriView68kol = FileProvider.getUriForFile(
                            requireContext(),
                            "${requireContext().applicationContext.packageName}.provider",
                            providedFile68kol
                        )
                        captureIntent68kol.run {
                            putExtra(MediaStore.EXTRA_OUTPUT, uriView68kol)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        val actionIntent68kol = Intent(Intent.ACTION_GET_CONTENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "image/*"
                        }
                        val intentChooser68kol = Intent(Intent.ACTION_CHOOSER).apply {
                            putExtra(Intent.EXTRA_INTENT, captureIntent68kol)
                            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntent68kol))
                        }
                        startActivityForResult(intentChooser68kol, 0)
                        return true
                    }
                    return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                }
            }
            requireContext().getSharedPreferences(SP_KEY_68kol, MODE_PRIVATE).getString(
                SP_LAST_PAGE_KEY_68kol, null
            )?.let {
                loadUrl(it)
                return@run
            }
            loadUrl(arguments?.getString(SP_BINOM_PAGE_KEY_68kol) ?: return@run)
        }
    }

    override fun onActivityResult(requestCode68kol: Int, resultCode68kol: Int, data68kol: Intent?) {
        if (filePathCB68kol != null && requestCode68kol == 0) {
            val uriResult2wlkzg1e =
                if (data68kol == null || resultCode68kol != AppCompatActivity.RESULT_OK) null else data68kol.data
            if (uriResult2wlkzg1e != null) {
                filePathCB68kol?.onReceiveValue(arrayOf(uriResult2wlkzg1e))
            } else {
                filePathCB68kol?.onReceiveValue(arrayOf(uriView68kol))
            }
            filePathCB68kol = null
        }
        super.onActivityResult(requestCode68kol, resultCode68kol, data68kol)
    }
}