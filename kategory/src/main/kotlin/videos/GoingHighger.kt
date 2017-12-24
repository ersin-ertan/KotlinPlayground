package videos

/*
* Going Higher
* Alejandro Serrano
* https://www.youtube.com/watch?v=AwBWqm4bGSk
* */

/*
Higher ordered functions
Higher kinded types
Higher rank polymorphism(impredicative ploymorphism is even higher)

Why for higher abstractions, create more powerful abstractions

The notion of function or procedure was a big development
Reusable block of code with name, behaviour and result depends on input, can produce an output

Statically typed languages have functions with a signature describing input and output

merge takes map kv map kv and outputs map kv

Rights precedence, left? Numbers by max, min? Who knows. We don't want the data to dictate behaviour.

Parameterize the behaviour, which is what higher order functions allow

merge takes two values map kv map kv and a function(to resolve competing values)

mergeLeft, mergeRight, mergeMax

Conceptual frameworks: compose elements of kind/type A to give you element of kind/type B
compose int to give list, compose screws, plastic to get car

Higher order erases that distinction: compose elements of type/kind to get new one of same type/kind
results in continuous composition layer.

Now functions as arguments or return types

map, foldr, filter

fold can be done with any data type, not just list

To extreme, no need for functions, the list is the function called Church encoding of a data type

Data types can be simulated by higher order functions

Higher kinded types: type of a type
Integer is the ground type: represents *values() in memory during run time, may appear in arg
or return pos of a function

Maybe is not a ground type, but a constructor, needs a type argument to become ground

Most languages can only abstract over ground types

What are the other commonalities? Higher kind allow abstraction over non ground types. Abstract over the
container of the thing, not the thing in the container.

Without such a facility, the relation between the different maps are informal, we cannot write functions
which abstract over it, or if we can, we need some sort of dynamic typing

The other half of modern FP patterns are higher kinded types: function, applicative, monad, alternative,
traversable

Being a monad is a property of constructors, monad transformers take a monad to generate a new monad.
This opens a new world of possibilities to compose monads, akin to pandoras box.

Separate the shape of the data from the recursive structure, allows us to write generic fold function

Kategory has done it with kotlin

Higher rank and impredicative polymorphism: Hindley-Damas-Milner inference, expressions have types,
no quantifiers, type variables allowed, declarations have tpe schemes, quantified over a set of type
variables

When using a variable the compiler instantiates its scheme, quantified variables are replaced by variables

Hendley milner tehre is a seperation, functions are composed from other types, type schemes quantify over
a type

Higher rank polymorphism to the rescue, types and type schemes are merged, types may contain quantification

Impredicative types: higher rank functions allow quantifiers in arguments, impredicative polymorphism allow
it everywhere, formally f p means that a type variable can be substituted by a type scheme

Hiher rank types can generic programming, record of polymorphic function asd natural transformations,
van Laarhoven lenses


*/