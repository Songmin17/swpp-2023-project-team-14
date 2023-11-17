package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.haengsha.R
import com.example.haengsha.model.route.BoardRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.board.BoardListUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.FilterDialog
import com.example.haengsha.ui.uiComponents.SearchBar
import com.example.haengsha.ui.uiComponents.boardList
import es.dmoral.toasty.Toasty

@Composable
fun boardScreen(
    innerPadding: PaddingValues,
    boardApiViewModel: BoardApiViewModel,
    boardNavController: NavController,
    userUiState: UserUiState
): Int {
    val boardViewModel: BoardViewModel = viewModel()
    val boardUiState = boardViewModel.uiState.collectAsState()
    val boardContext = LocalContext.current
    val boardListUiState = boardApiViewModel.boardListUiState
    var eventId by remember { mutableIntStateOf(0) }
    var isFestival by remember { mutableIntStateOf(boardUiState.value.isFestival) }
    var startDate by remember { mutableStateOf(boardUiState.value.startDate) }
    var endDate by remember { mutableStateOf(boardUiState.value.endDate) }
    var filterModal by remember { mutableStateOf(false) }

    boardViewModel.saveToken(userUiState.token)

    LaunchedEffect(Unit) {
        boardApiViewModel.resetApiUiState()
        boardViewModel.setInitial()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                boardViewModel
            ) { boardApiViewModel.searchEvent(it) }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(25.dp)
                        .border(
                            width = 1.dp,
                            color = HaengshaBlue,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            filterModal = true
                        }
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        // TODO 필터 텍스트
                        text = "필터 : 선택 안 함",
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = HaengshaBlue
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(PlaceholderGrey)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                when (boardListUiState) {
                    is BoardListUiState.HttpError -> {
                        boardViewModel.resetUiState()
                    }

                    is BoardListUiState.NetworkError -> {
                        items(1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                Toasty.error(boardContext, "네트워크 연결을 확인해주세요", Toasty.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    is BoardListUiState.Error -> {
                        items(1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                Toasty.error(
                                    boardContext,
                                    "알 수 없는 에러가 발생했어요 :( 메일로 제보해주세요!",
                                    Toasty.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    is BoardListUiState.Loading -> {
                        // handled in when branch
                    }

                    is BoardListUiState.BoardListResult -> {
                        boardViewModel.updateBoardList(boardListUiState.boardList)
                    }
                }
                if (boardUiState.value.boardList.isNotEmpty()) {
                    items(boardUiState.value.boardList) { event ->
                        Box(modifier = Modifier.clickable {
                            eventId = event.id
                            boardNavController.navigate(BoardRoute.BoardDetail.route)
                        }) {
                            boardList(
                                isFavorite = false,
                                event = event
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = PlaceholderGrey
                        )
                    }
                } else {
                    if (boardListUiState is BoardListUiState.Loading) {
                        if (boardUiState.value.initialState) {
                            items(1) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "찾고 싶은 행사를 검색해보세요!",
                                        fontFamily = poppins,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "(단체 계정은 행사를 등록할 수도 있습니다)",
                                        fontFamily = poppins,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else {
                            items(1) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    } else {
                        items(1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "찾는 행사가 없어요 :(",
                                    fontFamily = poppins,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        if (userUiState.role == "Group") {
            Box(modifier = Modifier.offset(330.dp, 600.dp)) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(ButtonBlue, RoundedCornerShape(30.dp))
                        .clickable(onClick = { boardNavController.navigate(BoardRoute.BoardPost.route) }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.write_festival_icon),
                        contentDescription = "event post Button",
                        tint = Color.White
                    )
                }
            }
        }

        if (filterModal) {
            FilterDialog(
                boardViewModel = boardViewModel,
                onSubmit = { boardApiViewModel.searchEvent(it) },
                onDismissRequest = { filterModal = false }
            )
        }
    }

    return if (eventId != 0) eventId
    else 0
}