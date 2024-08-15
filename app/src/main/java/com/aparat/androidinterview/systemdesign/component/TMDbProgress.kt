package com.aparat.androidinterview.systemdesign.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TMDbProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = Color.Blue
    )
}