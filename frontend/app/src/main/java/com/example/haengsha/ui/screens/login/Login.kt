package com.example.haengsha.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.viewModel.login.LoginViewModel
import com.example.haengsha.ui.screens.login.findPassword.FindPasswordCompleteScreen
import com.example.haengsha.ui.screens.login.findPassword.FindPasswordOrganizerScreen
import com.example.haengsha.ui.screens.login.findPassword.FindPasswordScreen
import com.example.haengsha.ui.screens.login.findPassword.PasswordResetScreen
import com.example.haengsha.ui.screens.login.signup.SignUpOrganizerScreen
import com.example.haengsha.ui.screens.login.signup.SignupCompleteScreen
import com.example.haengsha.ui.screens.login.signup.SignupEmailVerificationScreen
import com.example.haengsha.ui.screens.login.signup.SignupPasswordSetScreen
import com.example.haengsha.ui.screens.login.signup.SignupTermsScreen
import com.example.haengsha.ui.screens.login.signup.SignupTypeScreen
import com.example.haengsha.ui.screens.login.signup.SignupUserInfoScreen

@Composable
fun Login(mainNavController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
    val loginUiState = loginViewModel.loginUiState
    val loginNavController = rememberNavController()
    val loginContext = LocalContext.current

    NavHost(
        navController = loginNavController,
        startDestination = LoginRoute.Login.route
    ) {
        composable(LoginRoute.Login.route) {
            LoginScreen(
                mainNavController = mainNavController,
                loginNavController = loginNavController,
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                loginContext = loginContext
            )
        }

        composable(LoginRoute.FindPassword.route) {
            FindPasswordScreen(
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.FindPasswordOrganizer.route) {
            FindPasswordOrganizerScreen(
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.FindPasswordReset.route) {
            PasswordResetScreen(
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.FindPasswordComplete.route) {
            FindPasswordCompleteScreen(
                loginNavController = loginNavController
            )
        }

        composable(LoginRoute.SignupType.route) {
            SignupTypeScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.SignupOrganizer.route) {
            SignUpOrganizerScreen(
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.SignupEmail.route) {
            SignupEmailVerificationScreen(
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupPassword.route) {
            SignupPasswordSetScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupUserInfo.route) {
            SignupUserInfoScreen(
                checkNickname = { loginViewModel.checkNickname(it) },
                loginUiState = loginUiState,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupTerms.route) {
            SignupTermsScreen(
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupComplete.route) {
            SignupCompleteScreen(
                loginNavController = loginNavController
            )
        }
    }
}