package com.cooos.cl.molot.app.Fragments68kol

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cooos.cl.molot.app.*
import com.cooos.cl.molot.app.databinding.FragmentSplash68kolBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FragmentSplash68kol : Fragment(R.layout.fragment_splash_68kol) {

    private lateinit var bindingSplash68kol: FragmentSplash68kolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireContext().getSharedPreferences(SP_KEY_68kol, MODE_PRIVATE).getString(
            SP_LAST_PAGE_KEY_68kol, null)?.let {
                findNavController().navigate(R.id.action_fragmentSplash68kol_to_fragmentWeb68kol)
        }
        bindingSplash68kol = FragmentSplash68kolBinding.inflate(inflater, container, false)
        return bindingSplash68kol.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }.also {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it)
        }
        bindingSplash68kol.bSplash68kol.setOnClickListener {
            bindingSplash68kol.bSplash68kol.isClickable = false
            lifecycleScope.launch {
                TransitionManager.go(Scene.getSceneForLayout(bindingSplash68kol.cl68kol, R.layout.transition_scene_splash_68kol, requireContext()),ChangeBounds())
                delay(5000)
                findNavController().navigate(
                    R.id.action_fragmentSplash68kol_to_fragmentWeb68kol,
                    Bundle()
                        .apply {
                            putString(
                                SP_BINOM_PAGE_KEY_68kol,
                                CODED_BINOM_KEY_68kol.decodebase6468kol()
                            )
                        })
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        lifecycleScope.launch {
            while (isActive) {
                if (!requireActivity().checkInternet68kol()) {
                    findNavController().navigate(
                        R.id.action_fragmentSplash68kol_to_fragmentInternet68kol,
                        Bundle().apply {
                            putString(
                                INTERNET_KEY_68kol, "Splash"
                            )
                        })
                    cancel()
                }
                delay(500)
            }
        }
        super.onResume()
    }
}