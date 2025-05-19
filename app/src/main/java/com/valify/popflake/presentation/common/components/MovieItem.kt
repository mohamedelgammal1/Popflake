package com.valify.popflake.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.valify.popflake.BuildConfig
import com.valify.popflake.domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .clickable { onMovieClick(movie) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (movie.posterPath != null) {
                    AsyncImage(
                        model = "${BuildConfig.IMAGE_BASE_URL}w342${movie.posterPath}",
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f/3f)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f/3f)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = movie.title.first().toString(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }

            Text(
                text = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Text(
                text = "⭐ ${String.format("%.1f", movie.voteAverage)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}