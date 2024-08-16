package com.aparat.androidinterview.presentation.movies

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aparat.androidinterview.R
import com.aparat.androidinterview.util.UiState
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.systemdesign.component.TMDbProgress
import com.aparat.androidinterview.util.extractYear

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    searchQueryTextState: State<String?>,
    movieUiState: UiState<List<MovieItem>>,
    onItemClicked: (MovieItem) -> Unit = {}
) {
    when(movieUiState){
        is UiState.Success -> MovieContent(modifier, onLoadMore, onItemClicked, movieUiState, searchQueryTextState, onSearchQueryChange)
        is UiState.Loading -> ShowProgress()
        is UiState.Error -> ShowToast(movieUiState.message.orEmpty())
    }
}

@Composable
fun ShowProgress() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TMDbProgress(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun MovieContent(modifier: Modifier = Modifier,
                 onLoadMore: () -> Unit,
                 onItemClicked: (MovieItem) -> Unit = {},
                 moviesItems: UiState<List<MovieItem>>,
                 searchQueryTextState: State<String?>,
                 onSearchQueryChange: (String) -> Unit,
    ){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextField(searchQueryTextState, onSearchQueryChange)
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
internal fun TextField(
    searchQueryTextState: State<String?>,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQueryTextState.value.orEmpty(),
        onValueChange = {
            onSearchQueryChange(it)
        },
        placeholder = { Text(text = stringResource(id = R.string.hint_search)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .testTag("search_users")
    )
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<MovieItem>,
    onLoadMore: () -> Unit,
    onShowDetail: (item: MovieItem) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        itemsIndexed(movies) { index, item ->
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
              onItemClicked: () -> Unit) {
    item?.let { movie ->
        Card(
            modifier = modifier
                .height(180.dp)
                .clickable { onItemClicked() },
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column() {
                AsyncImage(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    model = "https://media.themoviedb.org/t/p/w440_and_h660_face/${movie.thumbnail}", contentDescription = movie?.title
                )
                Text(modifier = Modifier.padding(start = 8.dp),
                    text = movie.title.orEmpty(),fontSize = 12.sp,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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