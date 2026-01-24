package jp.developer.bbee.assemblepc

import android.app.Application
import jp.developer.bbee.assemblepc.shared.data.room.getDatabaseBuilder
import jp.developer.bbee.assemblepc.shared.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeKoin(
            config = {
                androidContext(this@App)
                getDatabaseBuilder(this@App)
            },
        )
    }
}