package com.example.haengsha.unitTest.board

import com.example.haengsha.fakeData.board.FakeBoardDataSource
import com.example.haengsha.fakeData.board.FakeNetworkBoardDataRepository
import com.example.haengsha.model.uiState.board.BoardDetailUiState
import com.example.haengsha.model.uiState.board.BoardFavoriteUiState
import com.example.haengsha.model.uiState.board.BoardListUiState
import com.example.haengsha.model.uiState.board.BoardPostApiUiState
import com.example.haengsha.model.uiState.board.PostLikeFavoriteUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.testRules.TestDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class BoardApiViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun boardViewModel_getBoardList_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.getBoardList(FakeBoardDataSource.date)
        assertEquals(
            BoardListUiState.BoardListResult(
                FakeBoardDataSource.listOfBoardListResponse
            ), boardApiViewModel.boardListUiState
        )
    }

    @Test
    fun boardViewModel_getBoardDetail_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.getBoardDetail(FakeBoardDataSource.token, FakeBoardDataSource.id)
        assertEquals(
            BoardDetailUiState.BoardDetailResult(
                FakeBoardDataSource.boardDetailResponse
            ), boardApiViewModel.boardDetailUiState
        )
    }

    @Test
    fun boardViewModel_getFavoriteList_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.getFavoriteBoardList(FakeBoardDataSource.token)
        assertEquals(
            BoardFavoriteUiState.BoardListResult(
                FakeBoardDataSource.listOfBoardListResponse
            ), boardApiViewModel.boardFavoriteUiState
        )
    }

    @Test
    fun boardViewModel_postEvent_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.postEvent(FakeBoardDataSource.boardPostRequest)
        assertEquals(
            BoardPostApiUiState.Success,
            boardApiViewModel.boardPostApiUiState
        )
    }

    @Test
    fun boardViewModel_postLike_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.postLike(FakeBoardDataSource.token, FakeBoardDataSource.id)
        assertEquals(
            PostLikeFavoriteUiState.Success(
                FakeBoardDataSource.postLikeFavoriteResponse.likeCount,
                FakeBoardDataSource.postLikeFavoriteResponse.favoriteCount,
                FakeBoardDataSource.postLikeFavoriteResponse.isLiked,
                FakeBoardDataSource.postLikeFavoriteResponse.isFavorite
            ),
            boardApiViewModel.postLikeFavoriteUiState
        )
    }

    @Test
    fun boardViewModel_postFavorite_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.postFavorite(FakeBoardDataSource.token, FakeBoardDataSource.id)
        assertEquals(
            PostLikeFavoriteUiState.Success(
                FakeBoardDataSource.postLikeFavoriteResponse.likeCount,
                FakeBoardDataSource.postLikeFavoriteResponse.favoriteCount,
                FakeBoardDataSource.postLikeFavoriteResponse.isLiked,
                FakeBoardDataSource.postLikeFavoriteResponse.isFavorite
            ),
            boardApiViewModel.postLikeFavoriteUiState
        )
    }

    @Test
    fun boardViewModel_searchEvent_verifyBoardUiStateSuccess() = runTest {
        val boardApiViewModel = BoardApiViewModel(
            boardDataRepository = FakeNetworkBoardDataRepository()
        )
        boardApiViewModel.searchEvent(FakeBoardDataSource.searchRequest)
        assertEquals(
            BoardListUiState.BoardListResult(
                FakeBoardDataSource.listOfBoardListResponse
            ),
            boardApiViewModel.boardListUiState
        )
    }
}