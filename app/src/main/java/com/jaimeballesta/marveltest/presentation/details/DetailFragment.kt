package com.jaimeballesta.marveltest.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jaimeballesta.domain.common.Sections.*
import com.jaimeballesta.domain.common.notDetailsErrorCode
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.marveltest.R
import com.jaimeballesta.marveltest.databinding.FragmentDetailBinding
import com.jaimeballesta.marveltest.presentation.common.*
import com.jaimeballesta.marveltest.presentation.details.adapter.ItemAdapter
import com.jaimeballesta.marveltest.presentation.details.state.CharacterDetailsState
import com.jaimeballesta.marveltest.presentation.details.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var _binding: FragmentDetailBinding
    private lateinit var itemAdapter: ItemAdapter
    private val binding get() = _binding

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfig)
        setHasOptionsMenu(true)
        detailsViewModel.getCharacterDetail(args.characterId)
        collectViewModelFlows()
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(detailsViewModel.state) { result ->
            showLoading(false)
            when (result) {
                is CharacterDetailsState.Error -> errorHandler(result.errorMessage)
                is CharacterDetailsState.LoadDetails -> if (result.characterDetails.isEmpty()) {
                    errorHandler(notDetailsErrorCode.toString())
                } else setInfoView(result.characterDetails.first())
                CharacterDetailsState.Loading -> showLoading(true)
            }
        }

    private fun setInfoView(detailItem: CharacterDetailItem) = with(binding) {
        detailItem.apply {
            collapsingToolbar.title = name
            detailImage.loadUrl(buildImageUrl(thumbnail.path, thumbnail.extension))
            this@with.description.text = description
            comicCard.setOnClickListener { navigateToSection(COMICS.value) }
            storiesCard.setOnClickListener { navigateToSection(STORIES.value) }
            eventsCard.setOnClickListener { navigateToSection(EVENTS.value) }
            seriesCard.setOnClickListener { navigateToSection(SERIES.value) }
        }
    }

    private fun showLoading(isVisible: Boolean) {
        val progress = getResourceView(R.id.progress_view)
        progress.isVisible = isVisible
    }

    private fun navigateToSection(value: String) {
        val destination = DetailFragmentDirections.actionDetailFragmentToDetailSectionFragment(
            value, args.characterId
        )
        findNavController().navigate(destination)
    }

    private fun showErrorDialog(title: String? = null, description: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title ?: getString(R.string.titleError))
            .setMessage(description)
            .setPositiveButton(R.string.ok) { _, _ -> findNavController().navigateUp() }
            .show()
    }

    private fun errorHandler(errorCode: String) = with(requireContext()) {
        showErrorDialog(description = getErrorMessage(errorCode))
    }
}