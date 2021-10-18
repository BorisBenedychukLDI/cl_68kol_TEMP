package com.cooos.cl.molot.app.Fragments68kol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cooos.cl.molot.app.INTERNET_KEY_68kol
import com.cooos.cl.molot.app.R
import com.cooos.cl.molot.app.checkInternet68kol
import com.cooos.cl.molot.app.databinding.FragmentInternet68kolBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FragmentInternet68kol : Fragment(R.layout.fragment_internet_68kol) {

    private lateinit var bindingInternet68kol: FragmentInternet68kolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingInternet68kol = FragmentInternet68kolBinding.inflate(inflater, container , false)
        return bindingInternet68kol.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }.also {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        lifecycleScope.launch {
            while (isActive) {
                if (requireContext().checkInternet68kol()) {
                    if (arguments?.getString(INTERNET_KEY_68kol) == "Splash") {
                        findNavController().navigate(R.id.action_fragmentInternet68kol_to_fragmentSplash68kol)
                        cancel()
                    } else if (arguments?.getString(INTERNET_KEY_68kol) == "Web") {
                        findNavController().navigate(R.id.action_fragmentInternet68kol_to_fragmentWeb68kol)
                    }
                    cancel()
                }
                delay(500)
            }
        }
        super.onResume()
    }
}