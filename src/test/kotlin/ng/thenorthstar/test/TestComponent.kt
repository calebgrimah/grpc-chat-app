package ng.thenorthstar.test

import ng.thenorthstar.data.di.module.MyModule
import ng.thenorthstar.data.repo.MyRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MyModule::class
        // Add your modules here
    ]
)
interface TestComponent {
    fun myRepo(): MyRepo
}