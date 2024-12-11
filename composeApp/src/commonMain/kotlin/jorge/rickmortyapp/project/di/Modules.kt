package jorge.rickmortyapp.project.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import jorge.rickmortyapp.project.character.data.database.DatabaseFactory
import jorge.rickmortyapp.project.character.data.database.FavoriteCharacterDatabase
import jorge.rickmortyapp.project.character.data.network.KtorRemoteCharacterDataSource
import jorge.rickmortyapp.project.character.data.network.RemoteCharacterDataSource
import jorge.rickmortyapp.project.character.data.repository.DefaultRepository
import jorge.rickmortyapp.project.character.domain.CharacterRepository

import jorge.rickmortyapp.project.character.presentation.SelectedCharacterViewModel
import jorge.rickmortyapp.project.character.presentation.character_detail.CharacterDetailViewModel
import jorge.rickmortyapp.project.character.presentation.character_list.CharacterListViewModel
import jorge.rickmortyapp.project.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get())}
    singleOf(::KtorRemoteCharacterDataSource).bind<RemoteCharacterDataSource>()
    singleOf(::DefaultRepository).bind<CharacterRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteCharacterDatabase>().favoriteBookDao }

    viewModelOf(::CharacterListViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::SelectedCharacterViewModel)
}