object Sheet2_Question4 {

	def main(args: Array[String]) : Unit = {

		val timeEnd:Double = 1.0
		val numSteps:Int = ...
		val timeStep:Double = timeEnd/numSteps
		// timeEnd=numSteps*timeStep and numSteps∈ N
		var time = 0.0
		while (time < timeEnd) {
			// Inv: 0 <= time <= timeEnd and time=k*timeStep for some k∈ N
			time += timeStep
		}
	}
}

/*
Part a: 

	Using numSteps = 6 as an example, we notice that on the scala compiler, 
	"
		scala> 1.0/6
		val res0: Double = 0.16666666666666666

		scala> (1.0 / 6) +(1.0 / 6) +(1.0 / 6) +(1.0 / 6)
		val res0: Double = 0.6666666666666666

		scala> (1.0 / 6) +(1.0 / 6) +(1.0 / 6) +(1.0 / 6) + (1.0/6)
		val res1: Double = 0.8333333333333333

		scala> (1.0 / 6) +(1.0 / 6) +(1.0 / 6) +(1.0 / 6) + (1.0/6) + (1.0/6)
		val res2: Double = 0.9999999999999999
	"
	Even after 6 iterations of adding timeStep, the final result is not 1, but 0.9999999999999999, 
	and since 0.9999999999999999 < 1, the while loop will iterate one more time for a total of 7 
	iterations. This occurs because if timeStep is not a finite decimal (ex. 1/2 = 0.5, 1/5 = 0.2), 
	but inifnite(ex. 1/6 = 0.1666...), the Double variable type has a limit to how many digits it 
	can compute, and will cut off the decimal after some point. Thus, 0.16666666666666666 added
	6 times will become 0.16666666666666666 * 6 = 0.9999999999999999, and not round to 1, causing 
	the loop to iterate one more time than expected. 

Part b:
	
	The margin of error of the 'time' variable would be 
	0 < error < timeStep, 
	as due to floating point arithmetic, the while loop would execute one more time as time
	would be slightly less than timeEnd thus indicating that the error would be greater than 0,
	but less than one full value of timeStep.

Part c:
	
	One viable option would be to change the while loop from

		while (time < timeEnd)

	to 

		while (time < timeStep * numSteps)

	As then the 'time' variable could accunt for floating point arithmetic and the rounding of
	variables. This would execute the while loop the exact desired number of times; however, the 
	time vaiable would still be slightly inaccurate - time < timeEnd - as timeEnd would be less 
	than 1 due to timeStep having its decimals cut off. 

*/