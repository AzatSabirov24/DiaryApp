package com.asabirov.diaryapp.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.asabirov.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.asabirov.diaryapp.presentation.screens.auth.AuthenticationViewModel
import com.asabirov.diaryapp.presentation.screens.home.HomeScreen
import com.asabirov.diaryapp.util.Constants.APP_ID
import com.asabirov.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
//    onDataLoaded: () -> Unit
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
//            onDataLoaded = onDataLoaded
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            },
//            navigateToWriteWithArgs = {
//                navController.navigate(Screen.Write.passDiaryId(diaryId = it))
//            },
//            navigateToAuth = {
//                navController.popBackStack()
//                navController.navigate(Screen.Authentication.route)
//            },
//            onDataLoaded = onDataLoaded
        )
        writeRoute(
//            navigateBack = {
//                navController.popBackStack()
//            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
//    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        AuthenticationScreen(
            authenticated = authenticated,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onSuccessfulFirebaseSignIn = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully authenticated")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    },
                )
            },
            onDialogDismissed = {
                messageBarState.addError(Exception(it))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
//        val messageBarState = rememberMessageBarState()
//
//        LaunchedEffect(key1 = Unit) {
//            onDataLoaded()
//        }
//
//        AuthenticationScreen(
//            authenticated = authenticated,
//            loadingState = loadingState,
//            oneTapState = oneTapState,
//            messageBarState = messageBarState,
//            onButtonClicked = {
//                oneTapState.open()
//                viewModel.setLoading(true)
//            },
//            onSuccessfulFirebaseSignIn = { tokenId ->
//                viewModel.signInWithMongoAtlas(
//                    tokenId = tokenId,
//                    onSuccess = {
//                        messageBarState.addSuccess("Successfully Authenticated!")
//                        viewModel.setLoading(false)
//                    },
//                    onError = {
//                        messageBarState.addError(it)
//                        viewModel.setLoading(false)
//                    }
//                )
//            },
//            onFailedFirebaseSignIn = {
//                messageBarState.addError(it)
//                viewModel.setLoading(false)
//            },
//            onDialogDismissed = { message ->
//                messageBarState.addError(Exception(message))
//                viewModel.setLoading(false)
//            },
//            navigateToHome = navigateToHome
//        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
//    navigateToWriteWithArgs: (String) -> Unit,
//    navigateToAuth: () -> Unit,
//    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Home.route) {

        val scope = rememberCoroutineScope()
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(onClick = {
//                scope.launch(Dispatchers.IO) {
//                    App.create(APP_ID).currentUser?.logOut()
//                }
//            }) {
//                Text(text = "Log out")
//            }
//        }
//        val viewModel: HomeViewModel = hiltViewModel()
//        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//        val context = LocalContext.current
//        var signOutDialogOpened by remember { mutableStateOf(false) }
//        var deleteAllDialogOpened by remember { mutableStateOf(false) }
//
//        LaunchedEffect(key1 = diaries) {
//            if (diaries !is RequestState.Loading) {
//                onDataLoaded()
//            }
//        }
//
        HomeScreen(
//            diaries = diaries,
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
//            dateIsSelected = viewModel.dateIsSelected,
//            onDateSelected = { viewModel.getDiaries(zonedDateTime = it) },
//            onDateReset = { viewModel.getDiaries() },
            onSignOutClicked = {
//                signOutDialogOpened = true
            },
//            onDeleteAllClicked = { deleteAllDialogOpened = true },
            navigateToWrite = navigateToWrite,
//            navigateToWriteWithArgs = navigateToWriteWithArgs
        )
//
//        DisplayAlertDialog(
//            title = "Sign Out",
//            message = "Are you sure you want to Sign Out from your Google Account?",
//            dialogOpened = signOutDialogOpened,
//            onDialogClosed = { signOutDialogOpened = false },
//            onYesClicked = {
//                scope.launch(Dispatchers.IO) {
//                    val user = App.create(APP_ID).currentUser
//                    if (user != null) {
//                        user.logOut()
//                        withContext(Dispatchers.Main) {
//                            navigateToAuth()
//                        }
//                    }
//                }
//            }
//        )
//
//        DisplayAlertDialog(
//            title = "Delete All Diaries",
//            message = "Are you sure you want to permanently delete all your diaries?",
//            dialogOpened = deleteAllDialogOpened,
//            onDialogClosed = { deleteAllDialogOpened = false },
//            onYesClicked = {
//                viewModel.deleteAllDiaries(
//                    onSuccess = {
//                        Toast.makeText(
//                            context,
//                            "All Diaries Deleted.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        scope.launch {
//                            drawerState.close()
//                        }
//                    },
//                    onError = {
//                        Toast.makeText(
//                            context,
//                            if (it.message == "No Internet Connection.")
//                                "We need an Internet Connection for this operation."
//                            else it.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        scope.launch {
//                            drawerState.close()
//                        }
//                    }
//                )
//            }
//        )
//    }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.writeRoute(
//    navigateBack: () -> Unit
) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
        )
    ) {}
//    ) {
//        val viewModel: WriteViewModel = hiltViewModel()
//        val context = LocalContext.current
//        val uiState = viewModel.uiState
//        val galleryState = viewModel.galleryState
//        val pagerState = rememberPagerState(pageCount = { Mood.values().size })
//        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }
//
//        WriteScreen(
//            uiState = uiState,
//            pagerState = pagerState,
//            galleryState = galleryState,
//            moodName = { Mood.values()[pageNumber].name },
//            onTitleChanged = { viewModel.setTitle(title = it) },
//            onDescriptionChanged = { viewModel.setDescription(description = it) },
//            onDateTimeUpdated = { viewModel.updateDateTime(zonedDateTime = it) },
//            onBackPressed = navigateBack,
//            onDeleteConfirmed = {
//                viewModel.deleteDiary(
//                    onSuccess = {
//                        Toast.makeText(
//                            context,
//                            "Deleted",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        navigateBack()
//                    },
//                    onError = { message ->
//                        Toast.makeText(
//                            context,
//                            message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                )
//            },
//            onSaveClicked = {
//                viewModel.upsertDiary(
//                    diary = it.apply { mood = Mood.values()[pageNumber].name },
//                    onSuccess = navigateBack,
//                    onError = { message ->
//                        Toast.makeText(
//                            context,
//                            message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                )
//            },
//            onImageSelect = {
//                val type = context.contentResolver.getType(it)?.split("/")?.last() ?: "jpg"
//                viewModel.addImage(image = it, imageType = type)
//            },
//            onImageDeleteClicked = { galleryState.removeImage(it) }
//        )
//    }
}