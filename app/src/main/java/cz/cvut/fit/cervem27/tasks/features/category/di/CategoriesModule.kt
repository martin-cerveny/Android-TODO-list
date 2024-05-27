package cz.cvut.fit.cervem27.tasks.features.category.di

import cz.cvut.fit.cervem27.tasks.features.category.data.api.IconsApiDescription
import cz.cvut.fit.cervem27.tasks.features.category.data.api.IconRemoteDataSource
import cz.cvut.fit.cervem27.tasks.features.category.data.CategoryRepository
import cz.cvut.fit.cervem27.tasks.features.category.data.api.RetrofitProvider
import cz.cvut.fit.cervem27.tasks.features.category.presentation.CreateCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val categoriesModule = module {
    single { RetrofitProvider.provide() }
    single { get<Retrofit>().create(IconsApiDescription::class.java) }

    factoryOf(::IconRemoteDataSource)

    singleOf(::CategoryRepository)

    viewModelOf(::CreateCategoryViewModel)
}