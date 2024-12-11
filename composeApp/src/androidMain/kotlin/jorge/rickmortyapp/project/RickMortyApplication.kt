package jorge.rickmortyapp.project

import android.app.Application
import jorge.rickmortyapp.project.di.initKoin
import org.koin.android.ext.koin.androidContext

class RickMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@RickMortyApplication)
        }
    }
}