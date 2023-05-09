/*

#1

Chan[T] < Writer[T], Chan[T] < Reader[T]

#2

All 4 definitions are well-typed

#3

provderS(cu) 	is well typed, as class Writer[-T] makes provderS contravariant, 
				and since Stoppable extends UnStoppable, Stopppable < Unstoppable, 
				and thus Writer[Unstoppable] is a subtype of Writer[Stoppable],
				and it is legal to pass Chan[UnStoppable] to provderS, 
				as Chan[UnStoppable] < Writer[Unstoppable] < Writer[Stoppable]

userU(cu) 		is well typed, as it passes Chan[UnStoppable] < Reader[Unstoppable]
				and this line will print { print("Stoppable ⋅ "); super.go() } 
				= Stoppable . go()

userS(cu) 		is not well typed, as class Reader[+T] makes userS covariant, 
				and since Stoppable extends UnStoppable, Stopppable < Unstoppable, 
				and thus Reader[Stoppable] is a subtype of Reader[Unstoppable],
				Thus Chan[UnStoppable] < Reader[Unstoppable] !< Reader[Stoppable]

providerU(cu) 	is well typed, as it passes Chan[UnStoppable] < Writer[UnStoppable]

userU(cu) 		is well typed, as it passes Chan[UnStoppable] < Reader[UnStoppable],
				and this line will print { r.read().go() }
				= go()

providerS(cs) 	is well typed, as it passes Chan[Stoppable] < Writer[Stoppable]

userS(cs) 		is well typed, as it passes Chan[Stoppable] < Reader[Stoppable], 
				and this line will print { {print("Stoppable ⋅ "); super.go()} println("Stoppable ⋅ stop()") }
				= Stoppable . go() \n Stoppable . stop()

userU(cs) 		is well typed, as Stoppable extends UnStoppable and Stopppable < Unstoppable,
				thus Chan[Stoppable] < Chan[UnStoppable] < Reader[UnStoppable], 
				and this line will print { print("Stoppable ⋅ "); super.go() }
				= Stoppable . go()

providerU(cs) 	is not well typed, as class Writer[-T] makes providerU contravariant, 
				and thus Writer[Unstoppable] is a subtype of Writer[Stoppable],
				and Chan[Stoppable] < Writer[Stoppable] !< Writer[Unstoppable]

*/