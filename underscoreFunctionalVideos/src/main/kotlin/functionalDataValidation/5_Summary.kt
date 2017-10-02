package functionalDataValidation

// Functional design:
// model simplest parts(rules and results)
// create combinators(building blocks)

// in validation, combinators are:
// sequencing(map, flatMap)
// parallel composition(and, J, applicative functors/builders)

// we can sometimes lift operations, which leads to higher levels of abstractions
// any combinator to find the result, we can produce a variant of it that directly combines rules
// if i can get two rules, get out the results and combine them with J, instead we can J the two rules directly
// write a method that plugs the gap
// if you do that, you don't have to define anony fuc that defines the rules, you can directly say
// read number is getField("number") and parse int, etc