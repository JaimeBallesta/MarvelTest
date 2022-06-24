package com.jaimeballesta.marveltest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.jaimeballesta.marveltest.presentation.common.launchAndCollect
import com.jaimeballesta.marveltest.presentation.home.state.HomeState
import com.jaimeballesta.marveltest.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        viewModel.getCharacters()
        collectViewModelFlows()
        return super.onCreateView(name, context, attrs)
    }

    private fun collectViewModelFlows() = launchAndCollect(viewModel.state){
        when(it){
            is HomeState.Error -> ""
            is HomeState.LoadCharacters -> Log.i("result: ", it.characters.toString())
            HomeState.Loading -> ""
        }
    }

}