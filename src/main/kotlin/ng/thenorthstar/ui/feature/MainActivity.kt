package ng.thenorthstar.ui.feature

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.extensions.compose.jetbrains.rememberRootComponent
import ng.thenorthstar.App
import ng.thenorthstar.ui.navigation.NavHostComponent
import ng.thenorthstar.ui.value.MyAppTheme
import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import androidx.compose.ui.window.application as setContent

/**
 * The activity who will be hosting all screens in this app
 */
class MainActivity : Activity() {
    companion object {
        fun getStartIntent(): Intent {
            return Intent(MainActivity::class).apply {
                // data goes here
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        setContent {
            Window(
                onCloseRequest = ::exitApplication,
                title = "${App.appArgs.appName} (${App.appArgs.version})",
                icon = painterResource("drawables/launcher_icons/system.png"),
                state = rememberWindowState(width = 1024.dp, height = 600.dp),
            ) {
                MyAppTheme {
                    // Igniting navigation
                    rememberRootComponent(factory = ::NavHostComponent)
                        .render()
                }
            }

        }

    }
}