package com.example.pregnancyvitalstracker.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.pregnancyvitalstracker.data.PregnancyDao
import com.example.pregnancyvitalstracker.data.PregnancyDatabase
import com.example.pregnancyvitalstracker.data.PregnancyRepository
import com.example.pregnancyvitalstracker.data.PregnancyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase( @ApplicationContext appContext: Context) :PregnancyDatabase{
        return Room.databaseBuilder(
            appContext,
            PregnancyDatabase::class.java,
            "room_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao (pregnancyDatabase: PregnancyDatabase): PregnancyDao {
        return pregnancyDatabase.pregnancyDao()
    }

    @Provides
    @Singleton
    fun providePregnancyRepository(impl: PregnancyRepositoryImpl):PregnancyRepository =impl

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}