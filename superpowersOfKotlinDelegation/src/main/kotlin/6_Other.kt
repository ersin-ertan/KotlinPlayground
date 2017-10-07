// view binding
interface LoginView{
    fun showProgress(show:Boolean)
    fun getEmail():String
    // etc
}

// view binding pollutes code

//override  fun getEmail(text:String){
//    passwordView.text = text
//}

// thus make interface containing the var email:String
// and include the delegates override var email by bindToText(R.id.emailView)

// or preference binding for shared prefs
// object Pref which has the vars that are var myVar by bindToPrefernectField(true, "myVar")
// to which you call Pref.myVar and can set using =

// What and why delegates - manage state for well known patterns without the creation of classes, functions, contexts