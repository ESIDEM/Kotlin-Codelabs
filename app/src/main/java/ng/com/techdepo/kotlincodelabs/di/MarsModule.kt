package ng.com.techdepo.kotlincodelabs.di


import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ng.com.techdepo.kotlincodelabs.MarsApp
import ng.com.techdepo.kotlincodelabs.overview.OverviewFragment
import javax.inject.Singleton


@Module
abstract class MarsModule {

    @Singleton
    @Binds
    abstract fun provideContext(app: MarsApp): Context

    @FragmnetScope
    @ContributesAndroidInjector(modules = [OverviewFragmentModule::class])
    abstract fun overViewFragment():OverviewFragment


}