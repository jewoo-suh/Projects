/*

The types of g1 ~ g4 are

:t g1 = Greeter
:t g2 = AbstractGreeter
:t g3 = Greeter Interface
:t g4 = object

When defining 
>	val gt = (g1, g2, g3, g4)
>	val gl = List(g1, g2, g3, g4)
gt will be defined with type (Greeter, AbstractGreeter, GreeterInterface, g4.type), 
while gl will be defined with type List[Any]

The indecipherable values printed for the elements of the tuple/list are 
the standard toString() output for variables in Java/Scala, in the format of (classtype)@(hashcode)

*/