package com.sampleapp.samplekotlinapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sampleapp.samplekotlinapp.presentation.news.NewsItem
import com.sampleapp.samplekotlinapp.presentation.news.NewsViewModel
import com.sampleapp.samplekotlinapp.ui.elements.PaginationBar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isDarkTheme :Boolean,
    onToggleTheme:() -> Unit,
    viewModel: NewsViewModel = viewModel()
){
    val currentPage by viewModel.currentPage
    val articles = viewModel.pagedArticles()
    val totalPages =   viewModel.totalPages()
    var query by remember { mutableStateOf(viewModel.searchQuery) }
    var searchText by remember{ mutableStateOf(query) }

    val remainingRequests by viewModel.remainingRequests

    val error by viewModel.errorMessage

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("News API Reader") },
                actions = {
                    if(remainingRequests != null){
                        Text(
                            text = "Remaining $remainingRequests",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            )
        },
        content = {
            innerPadding ->
            Column (
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
            ){
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {searchText = it},
                    label = { Text("Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.updateSearchQuery(searchText)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )
                LazyColumn (modifier = Modifier.fillMaxSize()){
                    items(articles){
                            article -> NewsItem(article)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onToggleTheme
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.WbSunny else Icons.Default.NightlightRound,
                    contentDescription = "Toggle Theme"
                )
            }
        },
        bottomBar = {
            PaginationBar(
                totalPages = totalPages,
                currentPage = currentPage,
                onPageClick = {page -> viewModel.setCurrentPage(page)}
            )
        }
    )
}