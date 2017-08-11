import java.net.ServerSocket
import java.net.Socket

// java all types derive from Object, kotlin has the Any class, and the Nothing class
// which extends from every type automatically

class User(val name:String?)


// important for expression type resolution
fun printName(user:User?){ // both user and null may be null, thus will throw if any is
    val name = user?.name?: throw IllegalArgumentException("user was null")
    // but the local variable can be of two types, user.name is a nullable string, throw
    // extends from string, so the type is string(still not quite sure how this works)
    println("name is $name")

}

    // throw never allows execution after because it never returns
    // but you can specify to return nothing
    fun runServer(serverSocket: ServerSocket):Nothing{
        fun handleSocket(sock:Socket){}

        while (true) handleSocket(serverSocket.accept())
    }

fun main(){
    runServer(ServerSocket(80))
    println("Running") // now you get compile time warnings that this is impossible to run
}
