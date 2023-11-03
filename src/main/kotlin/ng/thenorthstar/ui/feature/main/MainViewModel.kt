package ng.thenorthstar.ui.feature.main

import androidx.compose.runtime.snapshots.SnapshotStateList
import chatapp.ChatMessage
import chatapp.ChatMessageFromServer
import chatapp.ChatServiceGrpc
import chatapp.ChatServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.MutableSharedFlow
import ng.thenorthstar.data.repo.MyRepo
import ng.thenorthstar.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import server.SERVER_PORT
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {
    companion object {
        const val INIT_WELCOME_MSG = "Hello World!"
    }

    private val _welcomeText = MutableStateFlow(INIT_WELCOME_MSG)
    val welcomeText: StateFlow<String> = _welcomeText

    private val _messagesFrom = SnapshotStateList<String>()
    val messageFrom: SnapshotStateList<String> = _messagesFrom

    val managedChannel =
        ManagedChannelBuilder.forAddress("localhost", SERVER_PORT)
            .usePlaintext()
            .build()
    val chatService = ChatServiceGrpc.newStub(managedChannel)
    val observer = chatService.chat(object : StreamObserver<ChatMessageFromServer> {
        override fun onNext(value: ChatMessageFromServer?) {
            _messagesFrom.add((value?.message?.from ?: "") + " : " + value?.message?.message)
        }

        override fun onError(t: Throwable?) {
            t?.printStackTrace()
        }

        override fun onCompleted() {
            println("Completed!")
        }
    })

    fun sendNewMessage (fromText: String, newText:String){
        observer.onNext(
            ChatMessage.newBuilder()
                .setFrom(fromText)
                .setMessage(newText).build()
        )
    }
}