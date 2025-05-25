package com.sampleapp.samplekotlinapp.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun PaginationBar(totalPages : Int , currentPage  : Int , onPageClick : (Int)-> Unit){
    LazyRow (
        modifier = Modifier.fillMaxWidth().padding(8.dp).navigationBarsPadding()
    ){
        items(totalPages){
            index: Int ->
            val page = index + 1
            Button(
                onClick = {onPageClick(page)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (page == currentPage) Color.DarkGray else Color.LightGray,
                    contentColor = if (page == currentPage) Color.White else Color.Black
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "$page",

                )
            }
        }
    }
}