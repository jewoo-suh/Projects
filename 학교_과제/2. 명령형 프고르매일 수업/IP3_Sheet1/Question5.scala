/*

When the code runs, 

theX = new X(1); show(theX) 
-> println(s"Showing␣anX:␣$anX") ++ { override def toString: String = s"X($x)"} -> Showing␣anX:␣X(1)
theX = new XY(2,3); show(theX) 
-> println(s"Showing␣anX:␣$anX") ++ { override def toString: String = s"XY($x,␣$y)"} -> Showing␣anX:␣XY(2,␣3)
theX = new XYZ(4,5,6); show(theX) 
-> println(s"Showing␣anX:␣$anX") ++ { override def toString: String = s"XYZ($x,␣$y,␣$z)"} -> Showing␣anX:␣XYZ(4,␣5,␣6)

theXX = new XX(7,8); show(theXX) 
-> This line will return an error as XX receives 1 input, not 2
theXX = new XXY(7,8); show(theXX) 
-> println(s"Showing␣anXX:␣$anXX") ++ { override def toString: String = s"${super.toString}+XXY($x,$y)"} -> Showing␣anXX:␣X(7)+XX(7)+XXY(7,8)
theXX = new XXYZ(9,10,11); show(theXX) 
-> println(s"Showing␣anXX:␣$anXX") ++ { override def toString: String = s"${super.toString}+XXYZ($x,␣$y,␣$z)"} = Showing␣anXX:␣X(9)+XX(9)+XXY(9,10)+XXYZ(9,␣10,␣11)
theX = theXX; show(theX) 
-> println(s"Showing␣anX:␣$anX") ++ { override def toString: String = s"${super.toString}+XXYZ($x,␣$y,␣$z)"} = Showing␣anX:␣X(9)+XX(9)+XXY(9,10)+XXYZ(9,␣10,␣11)
show(new XXYZ(12, 13, 14))
-> println(s"Showing␣anXX:␣$anXX") ++ { override def toString: String = s"${super.toString}+XXYZ($x,␣$y,␣$z)"} = Showing␣anXX:␣X(12)+XX(12)+XXY(12,13)+XXYZ(12,␣13,␣14)

theXXX = new XXXY(15,16); show(theXXX)
-> println(s"Showing␣anXXX:␣$anXXX") ++ { override def toString: String = s"${super.toString}+XXX($x)"} = Showing␣anXXX:␣X(15)+XX(15)+XXX(15)
theXXX = new XXXYZ(17,18,19); show(theXXX)
-> println(s"Showing␣anXXX:␣$anXXX") ++ { override def toString: String = s"${super.toString}+XXX($x)"} = Showing␣anXXX:␣X(17)+XX(17)+XXX(17)
theX = theXXX show(theX)
-> println(s"Showing␣anX:␣$anX") ++ { override def toString: String = s"${super.toString}+XXX($x)"} = Showing␣anX:␣X(17)+XX(17)+XXX(17)
show(new XXXYZ(20,21,22))
-> println(s"Showing␣anXXX:␣$anXXX") ++ { override def toString: String = s"${super.toString}+XXX($x)"} = Showing␣anXXX:␣X(20)+XX(20)+XXX(20)

*/