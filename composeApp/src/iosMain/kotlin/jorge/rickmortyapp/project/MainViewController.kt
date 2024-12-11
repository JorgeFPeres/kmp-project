package jorge.rickmortyapp.project

import androidx.compose.ui.window.ComposeUIViewController
import jorge.rickmortyapp.project.app.App
import jorge.rickmortyapp.project.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }