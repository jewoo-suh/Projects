--- Factoring Numbers ---

> factor :: Integer -> (Integer, Integer)
> factor n = factorFrom 2 n

> factorFrom :: Integer -> Integer -> (Integer, Integer)
> factorFrom m n    | r == 0 = (m,q)
>                   | otherwise = factorFrom (m+1) n
>   where (q,r) = n `divMod` m

-------------------------------------------------------

Exercise 1)

We notice that for factor 0 and factor 1, 

factor 0 will pass factorFrom 2 0, which will evaluate 0 `divMod` 2,
and since 0 = 0*2 + 0, (q,r) = 0 `divMod` 2 = (0,0), and factorFrom 2 0
will return (2,0)

factor 1 will pass factorFrom 2 1, which will evaluate 1 `divMod` 2,
but since 1 = 0*2 + 1, (q,r) = 1 `divMod` 2 = (0,1) and since r != 0, 
factorFrom will evaluate factorFrom (m+1) n = factorFrom 3 1, 
but since 1 = 0*3 + 1, (q,r) = 1 `divMod` 3 = (0,1) and since r != 0, 
factorFrom will evaluate factorFrom (m+1) n = factorFrom 4 1, and this loop
will never terminate, causing an error.

-------------------------------------------------------

Exercise 2)

ghci> factor 0
(2,0)
ghci> factor 1
^C
-------------------------------------------------------

Exercise 3)

First we state that all factors of a number are equal to or less than the number itself.

Now, using proof by contradiction, we assume that a and b are 2 factors of n -> a * b = n
Then, if a > √n and b > √n, a * b > √n * √n = n, which is a contradiction as a * b = n.
Thus, a <= √n or b <= √n, we notice that n must have a divisor smaller than √n. Thus
the smallest factor of n cannot be both bigger than √n and less than n

> factor1 :: Integer -> (Integer, Integer)
> factor1 n = factorFrom1 2 n 

> factorFrom1 :: Integer -> Integer -> (Integer, Integer)
> factorFrom1 m n 	| r == 0 		= (m, q) 
> 					| n <= m*m 		= (n, 1)
>				  	| otherwise 	= factorFrom1 (m+1) n
> 	where (q, r) = n `divMod` m 

Here, the order of the guarded equations will matter as if 

| n <= m*m      = (n, 1) 
came before 
| r == 0        = (m, q) , 

for certain numbers of n, for example 4, 
factorFrom1 2 4 => 4 <= 2*2 thus the function will return (4,1)
when it should return (2,2), and for any square of a prime number, 
the function will not work and will return (p*p,1) when it should return (p,p)

In the worst case, approximately sqrt(n) = √n number of cases are needed to evaluate factor1,
as for any number in the worst case its smallest factor will be at maximum √n,
and √n iterations are needed to reach the point where n `divMod` √n is compiled

-------------------------------------------------------

Exercise 4)

Replacing n <= m*m with q < m is more efficient the code needs to do 1 less calculation, 
mainly it does not compile m*m in the latter case. 

> factor2 :: Integer -> (Integer, Integer)
> factor2 n = factorFrom2 2 n 

> factorFrom2 :: Integer -> Integer -> (Integer, Integer)
> factorFrom2 m n 	| r == 0 		= (m, q)
> 					| q <= m 		= (n, 1)		
>				  	| otherwise 	= factorFrom2 (m+1) n
> 	where (q, r) = n `divMod` m 

-------------------------------------------------------

Exercise 5)

> factor3 :: Integer -> (Integer, Integer)
> factor3 n = factorFrom3 2 n 

> factorFrom3 :: Integer -> Integer -> (Integer, Integer)
> factorFrom3 m n 	| r == 0 		= (m, q)
> 					| q <= m 		= (n, 1)
>				  	| m == 2 	 	= factorFrom3 (m+1) n
>					| otherwise 	= factorFrom3 (m+2) n
> 	where (q, r) = n `divMod` m 


factor3 would be significantly more efficient, and will take
approximately half the time of factor2

-------------------------------------------------------

Exercise 6)

ghci> factor3 2
(2,1)
(0.00 secs, 64,912 bytes)

ghci> factor3 62615533
(7907,7919)
(0.01 secs, 1,911,248 bytes)

ghci> factor3 349898389
(18121,19309)
(0.01 secs, 4,284,944 bytes)

-------------------------------------------------------

Exercise 7)

> factor4 :: Integer -> (Integer, Integer)
> factor4 n = factorFrom4 2 n 2

> factorFrom4 :: Integer -> Integer -> Integer -> (Integer, Integer)
> factorFrom4 m n s	| r == 0 			= (m, q)
> 					| q <= m 			= (n, 1)
>				  	| m == 2 	 		= factorFrom4 (m+1) n 2
>					| (m `mod` 3) == 1 	= factorFrom4 (m+6-s) n 2
>					| otherwise 		= factorFrom4 (m+s) n 2
> 	where (q, r) = n `divMod` m 

Providing some test cases,

ghci> factor4 2
(2,1)
(0.00 secs, 64,928 bytes)

ghci> factor4 5
(5,1)
(0.00 secs, 64,984 bytes)

ghci> factor4 62615533
(7907,7919)
(0.01 secs, 1,774,456 bytes)

ghci> factor4 396324239
(19843,19973)
(0.02 secs, 4,340,952 bytes)

-------------------------------------------------------

Exercise 8)

The main problem with using prime numbers as trial divisors is that there does not exist a method
to identify only prime numbers which can be used for factors. Also, on a slightly more philosophical 
perspective, in retrospect we are essentially using the factors function to identify prime numbers 
i.e. numbers that return (n,1) when factor4 n is evaluated. Thus it is impossible to use prime numbers
for trial divisors in factors without using factors to identify prime numbers and is logically impossible. 

-------------------------------------------------------

--- Prime Factorisation ---

for example

*Main> factor 7654321
(19,402859)

because 

*Main> 19 * 402859
7654321

Repeatedly extracting the smallest factor will return a list
of prime factors:

> factors :: Integer -> [Integer]
> factors n = factorsFrom 2 n

> factorsFrom :: Integer -> Integer -> [Integer]
> factorsFrom m n | n == 1    = []
>                 | otherwise = p:factorsFrom p q
>    where (p,q) = factorFrom m n

for example

*Main> factor 123456789
(3,41152263)
*Main> factors 123456789
[3,3,3607,3803]

Exercise 9)

Providing some examples, 

ghci> factors 10
[2,5]
(0.00 secs, 67,168 bytes)

ghci> factors 121
[11,11]
(0.00 secs, 73,376 bytes)

ghci> factors 396324239
[19843,19973]
(0.02 secs, 7,271,840 bytes)

ghci> factors 2550
[2,3,5,5,17]
(0.00 secs, 87,680 bytes)


> factors2 :: Integer -> [Integer]
> factors2 n = factorsFrom2 2 n

> factorsFrom2 :: Integer -> Integer -> [Integer]
> factorsFrom2 m n | n == 1    = []
>                  | otherwise = p:factorsFrom2 p q
>    where (p,q) = factorFrom4 m n 2

Checking if factors2 works as intended, 

ghci> map factors [2..1000] == map factors2 [2..1000]
True
(0.13 secs, 55,222,344 bytes)

-------------------------------------------------------

Exercise 10) 

Comparing the speed of the functions, 

ghci> factors 2550
[2,3,5,5,17]
(0.00 secs, 87,672 bytes)
ghci> factors2 2550
[2,3,5,5,17]
(0.00 secs, 83,496 bytes)

ghci> factors 23942723
[7,7,488627]
(0.40 secs, 175,985,928 bytes)
ghci> factors2 23942723
[7,7,488627]
(0.00 secs, 231,520 bytes)

ghci> factors 287391222
[2,3,3,17,41,22907]
(0.02 secs, 8,342,912 bytes)
ghci> factors2 287391222
[2,3,3,17,41,22907]
(0.00 secs, 129,872 bytes)

And for Jevon's number

ghci> factors 8616460799
[89681,96079]
(0.07 secs, 34,670,000 bytes)
ghci> factors2 8616460799
[89681,96079]
(0.06 secs, 19,333,904 bytes)

We notice that for all cases, factors2 is much more efficient