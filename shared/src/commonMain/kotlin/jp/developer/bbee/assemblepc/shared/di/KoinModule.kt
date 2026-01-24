package jp.developer.bbee.assemblepc.shared.di

import jp.developer.bbee.assemblepc.shared.data.remote.AssemblePcApi
import jp.developer.bbee.assemblepc.shared.data.remote.AssemblePcApiImpl
import jp.developer.bbee.assemblepc.shared.data.repository.CurrentCompositionRepositoryImpl
import jp.developer.bbee.assemblepc.shared.data.repository.CurrentDeviceTypeRepositoryImpl
import jp.developer.bbee.assemblepc.shared.data.repository.DeviceRepositoryImpl
import jp.developer.bbee.assemblepc.shared.data.repository.ReviewRepositoryImpl
import jp.developer.bbee.assemblepc.shared.data.room.AppDatabase
import jp.developer.bbee.assemblepc.shared.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentDeviceTypeRepository
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import jp.developer.bbee.assemblepc.shared.domain.repository.ReviewRepository
import jp.developer.bbee.assemblepc.shared.domain.use_case.AddAssemblyUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.ClearCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.ClearCurrentDeviceTypeUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.DeleteAssemblyUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.DeleteCompositionUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetAssemblyUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetCompositionsUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetCurrentDeviceTypeUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetDeviceUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetMaxAssemblyIdUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.RenameAssemblyUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.ReviewUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.SaveCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.SaveCurrentDeviceTypeUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.UpdateAssemblyReviewUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.UpdateCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.presentation.AppViewModel
import jp.developer.bbee.assemblepc.shared.presentation.screen.assembly.AssemblyViewModel
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.DeviceViewModel
import jp.developer.bbee.assemblepc.shared.presentation.screen.selection.SelectionViewModel
import jp.developer.bbee.assemblepc.shared.presentation.screen.top.TopViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val targetModule: Module

fun initializeNapier() {
    Napier.base(DebugAntilog())
}

private val sharedModule = module {
    single<AssemblyDeviceDao> { get<AppDatabase>().getAssemblyDeviceDao() }
    singleOf(::AssemblePcApiImpl).bind(AssemblePcApi::class)
    singleOf(::CurrentCompositionRepositoryImpl).bind(CurrentCompositionRepository::class)
    singleOf(::CurrentDeviceTypeRepositoryImpl).bind(CurrentDeviceTypeRepository::class)
    singleOf(::DeviceRepositoryImpl).bind(DeviceRepository::class)
    singleOf(::ReviewRepositoryImpl).bind(ReviewRepository::class)
    singleOf(::AddAssemblyUseCase)
    singleOf(::ClearCurrentCompositionUseCase)
    singleOf(::ClearCurrentDeviceTypeUseCase)
    singleOf(::DeleteAssemblyUseCase)
    singleOf(::DeleteCompositionUseCase)
    singleOf(::GetAssemblyUseCase)
    singleOf(::GetCompositionsUseCase)
    singleOf(::GetCurrentCompositionUseCase)
    singleOf(::GetCurrentDeviceTypeUseCase)
    singleOf(::GetDeviceUseCase)
    singleOf(::GetMaxAssemblyIdUseCase)
    singleOf(::RenameAssemblyUseCase)
    singleOf(::ReviewUseCase)
    singleOf(::SaveCurrentCompositionUseCase)
    singleOf(::SaveCurrentDeviceTypeUseCase)
    singleOf(::UpdateAssemblyReviewUseCase)
    singleOf(::UpdateCurrentCompositionUseCase)
}

private val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::TopViewModel)
    viewModelOf(::SelectionViewModel)
    viewModelOf(::DeviceViewModel)
    viewModelOf(::AssemblyViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    initializeNapier()
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule, viewModelModule)
    }
}
