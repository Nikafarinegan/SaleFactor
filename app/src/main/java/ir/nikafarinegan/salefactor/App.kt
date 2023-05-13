package ir.nikafarinegan.salefactor

import android.app.Application
import ir.awlrhm.modules.log.AWLRHMLogger
import ir.nikafarinegan.salefactor.di.listModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listModule)
        }

        AWLRHMLogger.init(this)
    }
}