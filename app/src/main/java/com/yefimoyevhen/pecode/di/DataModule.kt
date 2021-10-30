package com.yefimoyevhen.pecode.di

import android.content.Context
import com.yefimoyevhen.pecode.util.SharedPreferencesManager
import com.yefimoyevhen.pecode.util.SharedPreferencesManager.Companion.PREF_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesShearedPreferencesManager(@ApplicationContext context: Context) =
        SharedPreferencesManager(
            context.getSharedPreferences(
                PREF_KEY,
                Context.MODE_PRIVATE
            )
        )

}