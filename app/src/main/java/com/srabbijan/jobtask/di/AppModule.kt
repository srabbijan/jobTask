package com.srabbijan.jobtask.di

import android.app.Application
import androidx.room.Room
import com.srabbijan.jobtask.data.local.LocalDB
import com.srabbijan.jobtask.data.local.dao.DemoDao
import com.srabbijan.jobtask.data.remote.ApiServices
import com.srabbijan.jobtask.data.repositoryImpl.DashboardRepositoryImpl
import com.srabbijan.jobtask.domain.repository.DashboardRepository
import com.srabbijan.jobtask.domain.useCases.dashboard.DashboardUseCases
import com.srabbijan.jobtask.domain.useCases.dashboard.FetchDemoData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ): LocalDB {
        return Room.databaseBuilder(
            context = application,
            klass = LocalDB::class.java,
            name = "srabbijan_job_task_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDemoDao(
        db: LocalDB
    ): DemoDao = db.demoDao

    @Provides
    @Singleton
    fun provideDashboardRepository(
        apiServices: ApiServices
    ): DashboardRepository = DashboardRepositoryImpl(apiServices)

    @Provides
    @Singleton
    fun provideDashboardUseCases(
        repository: DashboardRepository
    ) = DashboardUseCases(
        demoRemoteData = FetchDemoData(repository)
    )
}