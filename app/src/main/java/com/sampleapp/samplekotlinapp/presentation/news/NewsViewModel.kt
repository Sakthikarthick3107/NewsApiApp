package com.sampleapp.samplekotlinapp.presentation.news

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.samplekotlinapp.data.models.Article
import com.sampleapp.samplekotlinapp.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel(){
    private val _allArticles = mutableStateListOf<Article>()
    val allArticles: List<Article> get() = _allArticles

    private val _remainingRequests = mutableStateOf<Int?>(null)
    val remainingRequests: State<Int?> = _remainingRequests

    var searchQuery by mutableStateOf("india")
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set


    private val _currentPage = mutableStateOf(1)
    val currentPage : State<Int> = _currentPage

    val pageSize = 20

    init {
        getNews()
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        _currentPage.value = 1
        getNews()
    }

    private fun getNews(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getNews(query = searchQuery , apiKey = "54885bf294fc4c9d828f9f8d5d1aeb9e")
                println(response)
                if(response.isSuccessful){
                    _allArticles.clear()
                    _allArticles.addAll(response.body()?.articles ?: emptyList())
                    println("Remaining ${response.headers()["x-cache-remaining"]}")
                    val remaining = response.headers()["x-cache-remaining"]
                    _remainingRequests.value = remaining?.toIntOrNull()

                }
                else {
                    errorMessage.value = "Failed to load news: ${response.message()}"
                }
            }
            catch(e:Exception){
                errorMessage.value = "Network error: ${e.localizedMessage ?: "Unknown error"}"
            }
        }
    }

    fun pagedArticles() : List<Article>{
        val from = (_currentPage.value - 1) * pageSize
        val to = minOf(from + pageSize, allArticles.size)
        return allArticles.subList(from,to)
    }

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }
    fun totalPages() : Int = (allArticles.size + pageSize - 1) / pageSize
}