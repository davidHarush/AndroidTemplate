package com.david.haru.myandroidtemplate.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import com.david.haru.myandroidtemplate.R
import com.david.haru.myandroidtemplate.autoCleared
import com.david.haru.myandroidtemplate.databinding.DetailsFragmentBinding
import com.david.haru.myandroidtemplate.network.getImageUrl
import com.david.haru.myandroidtemplate.network.getTransitionName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private var binding by autoCleared<DetailsFragmentBinding>()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)
        val data = args.argMovie

        with(binding) {
            image.transitionName = data.getTransitionName() + "image"
            title.transitionName = data.getTransitionName() + "title"

            image.load(data = data.getImageUrl().toString())
            title.text = data.title
            subTitle.text = data.overview

        }
    }


}

