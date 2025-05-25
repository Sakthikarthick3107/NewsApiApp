package com.sampleapp.samplekotlinapp.presentation.news

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sampleapp.samplekotlinapp.data.models.Article
import com.sampleapp.samplekotlinapp.data.models.formatPublishedDate
import com.sampleapp.samplekotlinapp.ui.theme.Black
import com.sampleapp.samplekotlinapp.ui.theme.White


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsItem(article: Article){
    val context = LocalContext.current
    val url = article.url
    Card (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ){
        Column (modifier = Modifier.padding(16.dp)){
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "Article Image",
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            article.source
                .name
                ?.let { sourceName ->
                    AssistChip(
                        label = { Text(sourceName) },
                        onClick = {},
                        shape = RoundedCornerShape(16.dp),
                        elevation = AssistChipDefaults.assistChipElevation(
                            elevation = 2.dp,
                            pressedElevation = 4.dp,
                            focusedElevation = 2.dp,
                            hoveredElevation = 3.dp,
                            draggedElevation = 6.dp,
                            disabledElevation = 0.dp
                        )

                    )
                }
            Text(text = article.title, style = TextStyle(
                fontSize = 18.sp
            ))
            article.description?.let{
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, style = TextStyle(
                    fontSize = 14.sp
                ))
            }

            Button(
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black,
                    contentColor = White
                ),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW , Uri.parse(url))
                    context.startActivity(intent)
                }
            ) {
                Text("Read more" , style = TextStyle(color = White))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = formatPublishedDate(article.publishedAt) , style = TextStyle(
                fontSize = 12.sp
            ))
        }
    }
}