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
import com.jaimeballesta.domain.common.notDetailsErrorCode
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import com.jaimeballesta.marveltest.R
import com.jaimeballesta.marveltest.databinding.FragmentSectionDetailBinding
import com.jaimeballesta.marveltest.presentation.common.*
import com.jaimeballesta.marveltest.presentation.details.adapter.ItemAdapter
import com.jaimeballesta.marveltest.presentation.details.state.CharacterDetailsSectionState
import com.jaimeballesta.marveltest.presentation.details.viewmodel.DetailsSectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSectionFragment : Fragment() {
    private val detailsSectionViewModel: DetailsSectionViewModel by viewModels()
    private lateinit var _binding: FragmentSectionDetailBinding
    private val binding get() = _binding
    private lateinit var itemAdapter: ItemAdapter

    private val args: DetailSectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSectionDetailBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.apply {
            setupWithNavController(navController, appBarConfig)
            title = args.section
        }
        setHasOptionsMenu(true)
        detailsSectionViewModel.getCharacterSectionDetail(args.section, args.characterId)
        collectViewModelFlows()
        itemAdapter = ItemAdapter()
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(detailsSectionViewModel.state) { result ->
            showLoading(false)
            when (result) {
                is CharacterDetailsSectionState.Error -> errorHandler(result.errorMessage)
                is CharacterDetailsSectionState.LoadSectionDetails -> if (result.details.isEmpty()) {
                    errorHandler(notDetailsErrorCode.toString())
                } else setInfoView(result.details)

                CharacterDetailsSectionState.Loading -> showLoading(true)
            }
        }

    private fun setInfoView(detailSectionItem: List<SectionDetailItem>) = with(binding) {
        detailSectionItem.apply {
            itemAdapter.submitList(detailSectionItem)
            referencesRecyclerView.adapter = itemAdapter
        }
    }

    private fun showLoading(isVisible: Boolean) {
        val progress = getResourceView(R.id.progress_view)
        progress.isVisible = isVisible
    }

    private fun showErrorDialog(title: String? = null, description: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title ?: getString(R.string.titleError))
            .setMessage(description)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ -> findNavController().navigateUp() }
            .show()
    }

    private fun errorHandler(errorCode: String) = with(requireContext()) {
        showErrorDialog(description = getErrorMessage(errorCode))
    }
}