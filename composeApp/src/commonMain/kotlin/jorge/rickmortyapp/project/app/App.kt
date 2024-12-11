package jorge.rickmortyapp.project.app


import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import jorge.rickmortyapp.project.character.presentation.SelectedCharacterViewModel
import jorge.rickmortyapp.project.character.presentation.character_detail.CharacterDetailAction
import jorge.rickmortyapp.project.character.presentation.character_detail.CharacterDetailScreenRoot
import jorge.rickmortyapp.project.character.presentation.character_detail.CharacterDetailViewModel

import jorge.rickmortyapp.project.character.presentation.character_list.CharacterListScreenRoot
import jorge.rickmortyapp.project.character.presentation.character_list.CharacterListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.CharacterGraph
        ) {
            navigation<Route.CharacterGraph>(
                startDestination = Route.CharacterList
            ) {
                composable<Route.CharacterList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    val viewModel = koinViewModel<CharacterListViewModel>()
                    val selectedCharacterViewModel =
                        it.sharedKoinViewModel<SelectedCharacterViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedCharacterViewModel.onSelectCharacter(null)
                    }

                    CharacterListScreenRoot(
                        viewModel = viewModel,
                        onCharacterClick =  { character ->
                            selectedCharacterViewModel.onSelectCharacter(character)
                            navController.navigate(
                                Route.CharacterDetail(character.id)
                            )

                        }
                    )
                }
                composable<Route.CharacterDetail>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
//                  Usado para pegar o id por exemplo pela navegação
//                  val args = it.toRoute<Route.CharacterDetail>()
                    val selectedCharacterViewModel =
                        it.sharedKoinViewModel<SelectedCharacterViewModel>(navController)
                    val viewModel = koinViewModel<CharacterDetailViewModel>()
                    val selectedChar by selectedCharacterViewModel.selectedCharacter.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedChar) {
                        selectedChar?.let { selectedChar ->
                            viewModel.onAction(CharacterDetailAction.OnSelectedCharacterChange(selectedChar))
                        }
                    }


                    CharacterDetailScreenRoot(
                        viewModel= viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )

                }
            }
        }

    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel (
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}