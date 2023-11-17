package com.example.haengsha.model.viewModel.board

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.haengsha.HaengshaApplication
import com.example.haengsha.model.dataSource.BoardDataRepository
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.network.dataModel.SearchRequest
import com.example.haengsha.model.uiState.board.BoardDetailUiState
import com.example.haengsha.model.uiState.board.BoardListUiState
import com.example.haengsha.model.uiState.board.BoardPostUiState
import com.example.haengsha.model.uiState.board.PostLikeFavoriteUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BoardApiViewModel(private val boardDataRepository: BoardDataRepository) : ViewModel() {
    var boardListUiState: BoardListUiState by mutableStateOf(BoardListUiState.Loading)
        private set

    var boardDetailUiState: BoardDetailUiState by mutableStateOf(BoardDetailUiState.Loading)
        private set

    var boardPostUiState: BoardPostUiState by mutableStateOf(BoardPostUiState.Loading)
        private set

    var postLikeFavoriteUiState: PostLikeFavoriteUiState by mutableStateOf(PostLikeFavoriteUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as HaengshaApplication
                val boardDataRepository = application.container.boardDataRepository
                BoardApiViewModel(boardDataRepository)
            }
        }
    }

    fun getBoardList(startDate: String) {
        viewModelScope.launch {
            boardListUiState = BoardListUiState.Loading
            boardListUiState = try {
                val boardListResult = boardDataRepository.getBoardList(startDate)
                BoardListUiState.BoardListResult(boardListResult)
            } catch (e: HttpException) {
                BoardListUiState.HttpError
            } catch (e: IOException) {
                BoardListUiState.NetworkError
            } catch (e: Exception) {
                BoardListUiState.Error
            }
        }
    }

    fun getBoardDetail(token: String, postId: Int) {
        viewModelScope.launch {
            boardDetailUiState = try {
                val authToken = "Token: $token"
                val boardDetailResult = boardDataRepository.getBoardDetail(authToken, postId)
                BoardDetailUiState.BoardDetailResult(boardDetailResult)
            } catch (e: HttpException) {
                BoardDetailUiState.HttpError
            } catch (e: IOException) {
                BoardDetailUiState.NetworkError
            } catch (e: Exception) {
                BoardDetailUiState.Error
            }
        }
    }

    fun getFavoriteBoardList(token: String) {
        viewModelScope.launch {
            boardListUiState = BoardListUiState.Loading
            boardListUiState = try {
                val authToken = "Token: $token"
                val boardListResult = boardDataRepository.getFavoriteList(authToken)
                BoardListUiState.BoardListResult(boardListResult)
            } catch (e: HttpException) {
                BoardListUiState.HttpError
            } catch (e: IOException) {
                BoardListUiState.NetworkError
            } catch (e: Exception) {
                BoardListUiState.Error
            }
        }
    }

    fun postEvent(boardPostRequest: BoardPostRequest) {
        viewModelScope.launch {
            boardPostUiState = BoardPostUiState.Loading
            boardPostUiState = try {
                boardDataRepository.postEvent(boardPostRequest)
                BoardPostUiState.Success
            } catch (e: HttpException) {
                BoardPostUiState.HttpError
            } catch (e: IOException) {
                BoardPostUiState.NetworkError
            } catch (e: Exception) {
                BoardPostUiState.Error
            }
        }
    }

    fun postLike(token: String, postId: Int) {
        viewModelScope.launch {
            postLikeFavoriteUiState = PostLikeFavoriteUiState.Loading
            postLikeFavoriteUiState = try {
                val authToken = "Token $token"
                val boardDetailResult = boardDataRepository.postLike(authToken, postId)
                PostLikeFavoriteUiState.Success(
                    boardDetailResult.likeCount,
                    boardDetailResult.favoriteCount,
                    boardDetailResult.isLiked,
                    boardDetailResult.isFavorite
                )
            } catch (e: HttpException) {
                PostLikeFavoriteUiState.HttpError
            } catch (e: IOException) {
                PostLikeFavoriteUiState.NetworkError
            } catch (e: Exception) {
                PostLikeFavoriteUiState.Error
            }
        }
    }

    fun postFavorite(token: String, postId: Int) {
        viewModelScope.launch {
            postLikeFavoriteUiState = PostLikeFavoriteUiState.Loading
            postLikeFavoriteUiState = try {
                val authToken = "Token $token"
                val boardDetailResult = boardDataRepository.postFavorite(authToken, postId)
                PostLikeFavoriteUiState.Success(
                    boardDetailResult.likeCount,
                    boardDetailResult.favoriteCount,
                    boardDetailResult.isLiked,
                    boardDetailResult.isFavorite
                )
            } catch (e: HttpException) {
                PostLikeFavoriteUiState.HttpError
            } catch (e: IOException) {
                PostLikeFavoriteUiState.NetworkError
            } catch (e: Exception) {
                PostLikeFavoriteUiState.Error
            }
        }
    }

    fun searchEvent(searchRequest: SearchRequest) {
        viewModelScope.launch {
            boardListUiState = BoardListUiState.Loading
            boardListUiState = try {
                val boardSearchResult = boardDataRepository.searchEvent(searchRequest)
                BoardListUiState.BoardListResult(boardSearchResult)
            } catch (e: HttpException) {
                BoardListUiState.HttpError
            } catch (e: IOException) {
                BoardListUiState.NetworkError
            } catch (e: Exception) {
                BoardListUiState.Error
            }
        }
    }
}