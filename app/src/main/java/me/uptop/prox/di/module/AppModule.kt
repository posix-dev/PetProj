package me.uptop.prox.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import me.uptop.prox.di.module.data.RepositoryModule
import me.uptop.prox.di.module.data.network.NetworkModule
import me.uptop.prox.di.module.manager.ManagerModule
import me.uptop.prox.di.module.service.ServiceModule

@Module(
    includes = [NetworkModule::class,
        RepositoryModule::class,
        ServiceModule::class,
        ManagerModule::class]
)
internal open class AppModule(private val application: Application) {

    companion object {
        private const val COMMON_PREFERENCES = "common"
    }

    private val cicerone: Cicerone<Router> = Cicerone.create()
    private val gson: Gson = GsonBuilder().create()

    @Provides
    internal fun provideRouter() = cicerone.router

    @Provides
    internal fun provideNavigatorHolder() = cicerone.navigatorHolder

    @Provides
    internal fun provideContext(): Context = application

    @Provides
    internal fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(COMMON_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    internal fun provideGson() = gson
}