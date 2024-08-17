package com.aparat.androidinterview.presentation.tvshows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aparat.androidinterview.presentation.ui_model.TvShowItem

@Composable
fun TvShowDetailsScreen(tvShow: TvShowItem) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = tvShow.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row {
            Text(
                text = "Rating: ${tvShow.voteAverage}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = tvShow.date,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            text = tvShow.voteAverage.toString(),
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}