package ng.com.techdepo.kotlincodelabs.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import ng.com.techdepo.kotlincodelabs.BASE_URL
import ng.com.techdepo.kotlincodelabs.network.MarsApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {



    @Provides
    @Singleton
    fun moshi():Moshi{
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi):Retrofit = Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
    )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL).build()

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit):MarsApiService{
        return retrofit.create(MarsApiService::class.java)
    }
}