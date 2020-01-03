package ng.com.techdepo.kotlincodelabs.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ng.com.techdepo.kotlincodelabs.ViewModelKey
import ng.com.techdepo.kotlincodelabs.viewmodels.OverviewViewModel


@Module
abstract class OverviewFragmentModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel::class)
    internal abstract fun overViewViewModel(viewModel: OverviewViewModel): ViewModel

}