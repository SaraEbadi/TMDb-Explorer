package com.aparat.androidinterview.presentation.movies

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aparat.androidinterview.UiState
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.systemdesign.component.TMDbProgress

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit,
    onItemClicked: (MovieItem) -> Unit = {},
//    moviesResponse: List<MovieResponse>,
    movieUiState: UiState<List<MovieItem>>
) {
    when(movieUiState){
        is UiState.Success -> MovieContent(modifier, onLoadMore, onItemClicked, movieUiState)
        is UiState.Loading -> ShowProgress()
        is UiState.Error -> ShowToast(movieUiState.message.orEmpty())
    }
    



}

@Composable
fun ShowProgress() {
    Box {
        TMDbProgress()
    }
}

@Composable
fun MovieContent(modifier: Modifier = Modifier,
                 onLoadMore: () -> Unit,
                 onItemClicked: (MovieItem) -> Unit = {},
    moviesItems: UiState<List<MovieItem>>,
    ){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
//        Row (modifier = Modifier
//            .fillMaxWidth()
//            .height(32.dp).background(R.color.purple_500)){
//
//        }

        MovieList(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            movies = (moviesItems as UiState.Success).value,
            onShowDetail = {
                onItemClicked(it)
            },
            onLoadMore = {
                onLoadMore()
            }
        )
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<MovieItem>,
    onLoadMore: () -> Unit,
    onShowDetail: (item: MovieItem) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        itemsIndexed(movies!!) { index, item ->
            if(index == movies.lastIndex) onLoadMore()
            MovieItem(item = item) {
                onShowDetail(item)
            }
        }
    }
}

@Composable
fun MovieItem(modifier: Modifier = Modifier,
              item: MovieItem?,
              onItemClicked: () -> Unit = {}) {
    item?.let { movie ->
        Card(
            modifier = modifier.clickable { onItemClicked() },
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
                Row (){
                    Text(modifier = Modifier.padding(start = 8.dp), text = "${movie.date?.extractYear() ?: ""}", fontSize = 10.sp, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "(${movie.voteAverage})", fontSize = 10.sp, style = MaterialTheme.typography.bodyMedium)
                }

            }
        }
    }
}
@Composable
fun ShowToast(message: String) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
}

//@Preview(showBackground = true)
//@Composable
//private fun MoviesScreenPreview() {
//    MoviesScreen(moviesResponse = null)
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MovieItemPreview() {
//    MovieItem(item = null)
//}