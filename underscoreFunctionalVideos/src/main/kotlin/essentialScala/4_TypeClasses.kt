package essentialScala

// Type Classes for ad-hoc polymorphism, for concerns that cross class hierarchy
// ex. serialize to Json, common behaviour without useful common type

// abstract behaviour to a type class
// can implement type class instances in ad-hoc manner

// Conclusions
// First three patterns are 90% of the code, with the fourth being 99%
// Program design can be systematic


// testing something

private sealed class S
private data class SS(val s:String):S() // cannot be both open and data
//private class SSS(s:String):SS(s)
private class SSS(s:String):S() // but data subclasses of sealed classes are allowed