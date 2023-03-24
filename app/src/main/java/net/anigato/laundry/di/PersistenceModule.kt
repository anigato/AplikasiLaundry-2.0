package net.anigato.laundry.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.anigato.laundry.presistences.AppDatabase
import net.anigato.laundry.presistences.SetoranLaundryDao
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "pengelolaan-laundry"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideSetoranLaundryDao(appDatabase: AppDatabase): SetoranLaundryDao {
        return appDatabase.setoranLaundryDao()
    }
}