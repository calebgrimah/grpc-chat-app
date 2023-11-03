package server

import io.grpc.ServerBuilder

const val SERVER_PORT = 50053

fun main() {
    //build server
    val server = ServerBuilder.forPort(SERVER_PORT)
        .addService(ChatServiceImpl())
        .build()
    //start server
    println("Starting server at port => $SERVER_PORT")
    server.start()
    println("Server Started")
    Runtime.getRuntime().addShutdownHook(Thread {
        println("Received Shutdown request!")
        server.shutdown()
        println("Successfully stopped the server!")
    })
    server.awaitTermination()
}