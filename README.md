GenericObject
=============

Provides *multiple* different implementations of the generic-object pattern.

The focus is on NOT using primitive wrappers.

You have data, a mixture of primitive values and objects (List, arrays,
Strings, ... all fall in "Objects"), and you want to "store" them in something
accessible using an integer index. You want the primitive values to be stored
without creating primitive wrappers, to reduce garbage creation and memory
usage (an Integer uses MUCH more memory then an int). You want a minimal memory
footprint, and fast access speed. You manage the type of your data, so you do
not need the type information to be redundantly stored in the data-structure.

All you want is "create a container of size X", and then get/set values in it...

But without being inefficient.

That is basically what this API gives you. There are several different
implementations, which offer different tradeoffs.

For example, we have an "int" implementation that stores everything as ints. So
boolean take 4 bytes, not one bit. But you can have has many booleans as you
want, and do not need to keep track of the boolean "ID space" independently
from the rest of the primitives. Long, float and double are a bit slower to
access, due to the greater processing overhead when using ints.

We also have a "double" implementation. It offers the best speed for any primitive
type, but you are limited to using a maximum of 52 booleans. The booleans have
then their own "ID space", independent of the other primitives. On the plus
side, each of those boolean use the first, "reserved" double value. Therefore,
their memory cost is basically free (since the space is reserved anyway). Only
the long values are somewhat more expensive to access, in terms of speed.

And we also currently have a "long" implementation. It is similar to the
"double" in that the boolean space is "reserved". It supports 64 booleans, and
offers good speed for long values, be is somewhat slower for float and double.

Each API defines *exactly the same public static methods*. So you can use them
in two ways; either directly, by doing a static import of the methods, or
indirectly, by using a state-less "service", which can then be "injected" in
the code if so desired. The service adds a small performance overhead, due
to one additional level of indirection, but you can then completely abstract
your choice of implementation, and even delay the choice until runtime. OTOH,
I advise only using one single implementation at a time. In this case, the JIT
can do a better job of optimizing the code, to remove the overhead added by
the service interface.

The "services" interface is called IGenericObjectAccessor, within
com.blockwithme.generic, and *contains most of the documentation*.

There is also a GenericObject class (with IGenericObject interface), that ties
one particular instance of a generic object with it's "implementation". It
offers a nicer API, but with some additional overhead in speed and memory.
Since this class uses the API interface (service), it can be used with any
implementation, and would make it easy even for multiple instances to use
different implementations.

More implementations might come, as needs arise, or benchmarking shows a faster
way to do things, but for now, the main concern was reducing memory usage and
GC. It is to note that currently no implementation is thread-safe, but the
service interface would allow such an implementation to be defined without any
change required.

Currently, primitives, and sometimes booleans, have a separate "ID space" from
Objects. This might, or not, suite your use-case. One way to unify both ID
spaces would be to use "negative" integer indexes for one type of data. There
is currently no plan for a version where both primitives and Objects would
truly be in a continuous ID space, because I do not know how to achieve this
efficiently.

Future work:

 * Add example / tutorial.
 * Add benchmarks
 * Add type-safe GenericObject variant, including toString, hashCode and equals implementations.
 * Estimate shallow memory footprint of generic objects.
 * Add some support for a "unified ID space".