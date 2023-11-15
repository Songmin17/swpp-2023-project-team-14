package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.BoardApiService
import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.network.dataModel.BoardPostResponse

interface BoardDataRepository {
    suspend fun getBoardList(startDate: String): List<BoardListResponse>
    suspend fun getBoardDetail(token: String, postId: Int): BoardDetailResponse
    suspend fun getFavoriteList(token: String): List<BoardListResponse>
    suspend fun postEvent(boardPostRequest: BoardPostRequest): BoardPostResponse
}

class NetworkBoardDataRepository(
    private val boardApiService: BoardApiService
) : BoardDataRepository {
    override suspend fun getBoardList(startDate: String): List<BoardListResponse> {
        return boardApiService.getBoardList(startDate)
    }

    override suspend fun getBoardDetail(token: String, postId: Int): BoardDetailResponse {
        return boardApiService.getBoardDetail(token, postId)
    }

    override suspend fun getFavoriteList(token: String): List<BoardListResponse> {
        return boardApiService.getFavoriteList(token)
    }

    override suspend fun postEvent(boardPostRequest: BoardPostRequest): BoardPostResponse {
        return boardApiService.postEvent(
            boardPostRequest.token,
            boardPostRequest.image,
            boardPostRequest.title,
            boardPostRequest.isFestival,
            boardPostRequest.eventDurations,
            boardPostRequest.place,
            boardPostRequest.time,
            boardPostRequest.content
        )
    }
}