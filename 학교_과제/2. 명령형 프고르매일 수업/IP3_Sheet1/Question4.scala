trait Vehicle { def weight: Double }
trait Truck extends Vehicle { def brake: Unit }
trait Ship extends Vehicle { def anchor: Unit }
trait Tanker extends Ship { def volume: Double}

trait Collection[+E] {
	def add(e: E): Collection[E]
	def rem(e: E): Collection[E]
	def fold[F](seed: F, op: (F, E) => F): F
}

object Question4 {

	def size[T](c: Collection[T]) : Int = c.fold(0, {(n: Int, _) => n+1})

	def count[T](c: Collection[T], _t: T): Int = c.fold(0, {(n: Int, t: T) => n+(if (t == _t) 1 else 0)})

	def weight(c: Collection[Vehicle]): Double = c.fold(0.0, { (n: Double, v: Vehicle) => n+v.weight })

	def volume(fleet: Collection[Tanker]): Double = fleet.fold(0.0, {(n: Double, t: Tanker) => n+t.volume})

	def density(fleet: Collection[Tanker]): Double = weight(fleet)/volume(fleet)
}





/*

#1

Tanker < Ship < Vehicle
Truck < Vehicle

The most general type is Vehicle

#2

The definition for weight and volume are well-typed, but the definition for density is not.
The problem arouses from the fact that Tanker < Vehicle, but the trait Collection is invariant in E,
thus the density function looks for the 'weight' variable within the trait Tanker, 
but the 'weight' variable lies within the Vehicle trait, which is the parent of the Tanker trait.
In order to access the variable, we could change the definition to trait Collection[+E], 
which makes Collection Covariant, and allows it to access weight ∈ Vehicle > Tanker

However, just fixing the code to trait Collection[+E] {//as before} raises a different problem, 
because making Collection covariant allows for the passing of subtypes of the parameter of E, 
and potentially this may cause an error as the user might try to compile 
>	add(A < E) 
to a list of 
>	Collections(B < E)
which will cause an error.Thus add and rem need to be adjusted accordingly.

#3
The Collection trait is abstract, and contains abstact functions for adding and removing variables, 
and thus can be implemented mutably.

#4

As explained in #2, the error will rise from 
>	ships = tankers
>	ships = ships ⋅ add(anotherShip)
since Ship > Tanker, and Collections is covariant, the first line is valid and the variable 'ships' 
will now be a collection of Tankers < Ships. However, in the next line, the code tries to ass a Ship type to
'ships', which is technically possible as 'ships' was declared as a collection of Ships. However, an error
occurs because now a list of Tankers ( < Ships) contains a Ship, and the arguments appear in a contravariant position, 
when the Collection trait is in fact covariant. 












*/