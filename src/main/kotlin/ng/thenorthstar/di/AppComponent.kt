package ng.thenorthstar.di

import ng.thenorthstar.ui.feature.main.MainScreenComponent
import ng.thenorthstar.ui.feature.splash.SplashScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        // Add your modules here
    ]
)
interface AppComponent {
    fun inject(splashScreenComponent: SplashScreenComponent)
    fun inject(mainScreenComponent: MainScreenComponent)
}