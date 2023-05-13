package ir.nikafarinegan.salefactor.di


import android.app.Application
import androidx.room.Room
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nikafarinegan.salefactor.data.database.LocalRepository
import ir.nikafarinegan.salefactor.data.database.WarehouseDatabase
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.WebServiceGateway
import ir.nikafarinegan.salefactor.data.network.api.ApiClient
import ir.nikafarinegan.salefactor.view.base.PrivateViewModel
import ir.nikafarinegan.salefactor.view.baseInformation.BaseInformationViewModel
import ir.nikafarinegan.salefactor.view.dashboard.DashboardViewModel
import ir.nikafarinegan.salefactor.view.operation.document.DocumentViewModel
import ir.nikafarinegan.salefactor.view.login.LoginViewModel
import ir.nikafarinegan.salefactor.view.password.ChangePasswordViewModel
import ir.nikafarinegan.salefactor.view.register.RegisterViewModel
import ir.nikafarinegan.salefactor.view.operation.subGoods.SubGoodsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { PersianCalendar() }
    single {
        PreferenceConfiguration(
            androidContext()
        )
    }
}

val viewModelModules = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { DashboardViewModel(get(), get(), get(), get()) }
    viewModel { DocumentViewModel(get(), get(), get()) }
    viewModel { SubGoodsViewModel(get(), get(), get()) }
    viewModel { ChangePasswordViewModel(get(), get()) }
    viewModel { BaseInformationViewModel(get(), get(), get()) }
    viewModel { PrivateViewModel(get()) }

}

val networkModules = module {
    factory { ApiClient(get()).getInterface() }
    factory { WebServiceGateway(get(), get(), get()) }
    factory { RemoteRepository(get(), get(), get()) }
}



val databaseModule = module {

    fun provideDatabase(application: Application): WarehouseDatabase {
        return Room.databaseBuilder(application, WarehouseDatabase::class.java, "Warehouse.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { provideDatabase(androidApplication()) }
    single { LocalRepository(get()) }
}

val listModule = arrayListOf(viewModelModules, networkModules, appModule, databaseModule)