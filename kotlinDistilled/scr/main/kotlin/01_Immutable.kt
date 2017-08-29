/*
https://yogthos.github.io/ClojureDistilled.html

Functional is good for large apps, scale requires modular reasonability, and benefits from testibility and reusibility.
Avoid global state, favour immutability as default, this allows for immutable shared state for isolation.
Mutable data can be passed by value or reference, value being the safest since changes remain in local scope, but is
inefficient.
Pass by reference is fast, but difficult to reason with due to reference counts and complexity. Thus immutable data structs,
for each change, a new data struct revision is created. Price is paid proportional to the size of change.
Immutable is to reference counting, as garbage collection is to alloc/dealloc.
A pure function is without side effects, not relying on outside state, aside from their input.
*/