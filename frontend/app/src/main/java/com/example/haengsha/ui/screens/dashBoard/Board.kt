package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.BoardRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.NavigationViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel

@Composable
fun Board(
    innerPadding: PaddingValues,
    userUiState: UserUiState,
    boardViewModel: BoardViewModel,
    boardApiViewModel: BoardApiViewModel,
    navigationViewModel: NavigationViewModel,
) {
    val boardNavController = rememberNavController()
    var eventId by rememberSaveable { mutableIntStateOf(0) }

    NavHost(
        navController = boardNavController,
        startDestination = BoardRoute.Dashboard.route
    ) {
        composable(BoardRoute.Dashboard.route) {
            navigationViewModel.updateRouteUiState("Board", BoardRoute.Dashboard.route)
            eventId = boardScreen(
                innerPadding = innerPadding,
                boardViewModel = boardViewModel,
                boardApiViewModel = boardApiViewModel,
                boardNavController = boardNavController,
                userUiState = userUiState
            )
        }
        composable(
            BoardRoute.BoardDetail.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "Board" -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "Board" -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            }
        ) {
            navigationViewModel.updateRouteUiState("Board", BoardRoute.BoardDetail.route)
            BoardDetailScreen(
                innerPadding = innerPadding,
                boardApiViewModel = boardApiViewModel,
                userUiState = userUiState,
                eventId = eventId
            )
        }
        composable(
            BoardRoute.BoardPost.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "Board" -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "Board" -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            }
        ) {
            navigationViewModel.updateRouteUiState("Board", BoardRoute.BoardPost.route)
            BoardPostScreen(
                innerPadding = innerPadding,
                boardApiViewModel = boardApiViewModel,
                boardNavController = boardNavController,
                userUiState = userUiState
            )
        }
    }
}