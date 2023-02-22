package com.example.composedemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.composedemo.ui.theme.ComposeDemoTheme

class NewFeatureFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ComposeDemoTheme {
                    Column(modifier = Modifier.padding(20.dp)) {
//                        MaxLengthEditTextWithCounter()
                        Spacer(modifier = Modifier.height(10.dp))
//                        PrivacyPolicyText2()
                    }
                }
            }
        }
    }
}