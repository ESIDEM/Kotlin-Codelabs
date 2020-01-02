package ng.com.techdepo.kotlincodelabs.di



import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ng.com.techdepo.kotlincodelabs.MarsApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,MarsModule::class,StorageModule::class,
OverviewFragmentModule::class])
interface AppComponent: AndroidInjector<MarsApp> {

//    override fun inject(app: MarsApp)

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MarsApp>
//    interface Builder {
//        fun build(): AppComponent
//        @BindsInstance
//        fun app(app: Context): Builder
//    }
}


