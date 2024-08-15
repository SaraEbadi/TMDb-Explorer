package com.aparat.androidinterview.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aparat.androidinterview.model.MovieResponse

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit,
    onItemClick: (MovieResponse) -> Unit = {},
    moviesResponse: List<MovieResponse>?
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        Row (modifier = Modifier
//            .fillMaxWidth()
//            .height(32.dp).background(R.color.purple_500)){
//
//        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            items(moviesResponse!!) { item ->
                MovieItem(movie = item)
            }
        }
    }

}

@Composable
fun MovieItem(movie: MovieResponse?) {
    movie?.let { movie ->
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    model = movie.thumbnail, contentDescription = movie?.title
                )
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "(${movie.date})", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Rating: ${movie.voteAverage}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviesScreenPreview() {
    MoviesScreen(moviesResponse = null)
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieItem(null)
}