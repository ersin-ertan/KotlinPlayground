import com.google.gson.Gson
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.http.ContentType // used in second half of tutorial?
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get


/*
* Error when running ./gradlew run
* Exception in thread "main" com.typesafe.config.ConfigException$UnresolvedSubstitution: application.conf @ file:/home/mehmet/IntellijProjects/KotlinPlayground/Ktor/build/resources/main/application.conf: 3: Could not resolve substitution to a value: ${PORT}

* */

// https://www.bignerdranch.com/blog/want-kotlin-on-the-server-do-ktor/
// connecting to emulator is different


fun Application.main(){
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing){
        get("/"){
            val person = Who("Bill", "Earth")
            val gson = Gson()
            val json = gson.toJson(person)
            call.respondText(json, ContentType.Application.Json)
        }
    }
}

data class Who(val name:String, val planet:String)