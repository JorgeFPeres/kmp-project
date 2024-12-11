package jorge.rickmortyapp.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jorge.rickmortyapp.project.app.App
import jorge.rickmortyapp.project.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "RickMortyApp",
        ) {
            App()
        }
    }
}