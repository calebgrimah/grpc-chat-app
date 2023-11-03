package ng.thenorthstar.data.repo

import javax.inject.Inject

class MyRepo @Inject constructor() {
    fun getClickedWelcomeText() = "Hello chat-app"
}