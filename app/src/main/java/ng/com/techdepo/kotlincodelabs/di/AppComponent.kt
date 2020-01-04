package ng.com.techdepo.kotlincodelabs.di



import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ng.com.techdepo.kotlincodelabs.MarsApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,MarsModule::class,StorageModule::class,
OverviewFragmentModule::class,NetworkModule::class])
interface AppComponent: AndroidInjector<MarsApp> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MarsApp>

}


