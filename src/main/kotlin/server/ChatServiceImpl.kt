package server

import chatapp.ChatMessage
import chatapp.ChatMessageFromServer
import chatapp.ChatServiceGrpc
import io.grpc.stub.StreamObserver

class ChatServiceImpl : ChatServiceGrpc.ChatServiceImplBase() {
    val observers: LinkedHashSet<StreamObserver<ChatMessageFromServer>?> = LinkedHashSet()
    override fun chat(responseObserver: StreamObserver<ChatMessageFromServer>?): StreamObserver<ChatMessage> {
        //add response observer to the list
        observers.add(responseObserver)
        return object : StreamObserver<ChatMessage> {
            override fun onNext(value: ChatMessage?) {
                val messageFromServer = ChatMessageFromServer
                    .newBuilder()
                    .setMessage(value)
                    .build()
                observers.forEach {
                    it?.onNext(messageFromServer)
                }
            }

            override fun onError(t: Throwable?) {
                println(t?.message)
                observers.remove(responseObserver)
            }

            override fun onCompleted() {
                observers.remove(responseObserver)
            }

        }
    }
}