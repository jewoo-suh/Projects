===== Question 1 =====

===== 1-a =====

Replicating the (.) function, 

> dot :: (b -> c) -> (a -> b) -> (a -> c) 
> dot f g = \x -> f (g x)

Replicating the zipWith function, 

> zipWithReplica :: (a -> b -> c) -> [a] -> [b] -> [c] 
> zipWithReplica f xs ys = [f x y | x <- xs, y <- ys]

Replicating the uncurry function, 

> uncurryReplica :: (a -> b -> c) -> (a, b) -> c 
> uncurryReplica f pair = f (fst(pair)) (snd(pair))

===== 1-b =====

(i) filter p . map f = map f . filter (p . f)

(ii) filter p . concat = concat . filter p

(iii) zipWith f = ? . zip

===== 1-c =====

> pascal :: Num a => [[a]]
> pascal = iterate (\x -> zipWith (+) (x ++ [0]) (0:x)) [1]

===== 1-d ====== 

    > f g h x y = g . h x . g y

We set the following

    > x :: a1
    > y :: a2

Then, we can define the types of h and g as

    > h :: a1 -> b1
    > g :: a2 -> b2

Then, 

    > h x :: b1 
    > g y :: b2 

We notice that for 'g . h x . g y' to compile, 'h x' and 'g y' must compose to type format a -> b.
Then, 

    > b1 :: b11 -> b12 
    > b2 :: b21 -> b22 

And

    > h :: a1 -> (b11 -> b12)
    > g :: a2 -> (b21 -> b22)
    > h x :: b11 -> b12 
    > g y :: b21 -> b22 

Then, 

    > g . h x . g y :: (a2 -> (b21 -> b22)) . (b11 -> b12) . (b21 -> b22) 

And since

    > (.) :: (a -> b) -> (c -> a) -> c -> b 

    > b22 = b11

    > g . h x . g y :: (a2 -> (b21 -> b11)) . (b11 -> b12) . (b21 -> b11) 
    >               :: (a2 -> (b21 -> b11)) . (b21 -> b12) 

    > a2 = b12 

    > g . h x . g y :: (a2 -> (b21 -> b11)) . (b21 -> a2) 
    >               :: b21 -> (b21 -> b11)

Now solving for f, we first reorganize all functions

    > b1 :: b11 -> a2 
    > b2 :: b21 -> b11 
    > h :: a1 -> (b11 -> a2)
    > g :: a2 -> (b21 -> b11)
    > h x :: b11 -> a2 
    > g y :: b21 -> b11 

Then, 

    > f g h x y = g . h x . g y
    > g . h x . g y :: b21 -> (b21 -> b11)

And for the answer, 

    > f :: (a2 -> (b21 -> b11)) -> (a1 -> (b11 -> a2)) -> a1 -> a2 -> (b21 -> (b21 -> b11))

===== 2-a =====

> merge :: Ord a => [a] -> [a] -> [a] 
> merge (x:xs) (y:ys) 	 | x == y 		= x:(merge xs ys)
>  				         | x > y  		= y:(merge (x:xs) ys) 
>  				         | otherwise  	= x:(merge xs (y:ys))

===== 2-b =====

> hamming :: [Int]
> hamming = 1:(merge (map (2*) hamming) (merge (map (3*) hamming) (map (5*) hamming)))

===== 2-c ===== 

hamming = 1 : (merge (map (2*) hamming) (merge (map (3*) hamming) (map (5*) hamming)))
        = 1 : (merge (2 : ... ) (merge (3 : ... ) (5 : ... )))
        = 1 : (merge (2 : ... ) (3 : merge ( ... ) (5 : ... )))
        = 1 : 2 : (merge (4 : ... ) (3 : merge ( ... ) (5 : ... )))
        = 1 : 2 : 3 : (merge (4 : ... ) (merge (6 : ... ) (5 : ... )))
        = 1 : 2 : 3 : 4 : (merge ( ... ) (merge (6 : ... ) (5 : ... )))
        = 1 : 2 : 3 : 4 : 5 : (merge (6 : ... ) (merge (6 : ... ) ( ... )))
        = 1 : 2 : 3 : 4 : 5 : 6 : (merge ( ... ) (merge ( ... ) (10 : ... )))

===== 2-d ===== 

For any number n where n > 1, a total of two calculations are required, 
(1) The appropriate multiplication from the existing list of numbers to create the number
(Ex. 4 = (2*) 2, 10 = (5*) 2)
(2) The merge function to actually add the number to the list in front 
(Ex. 1 : (merge (2:...)(...)) -> 1 : 2 : (...))

Thus to compute the first n elements of hamming, approx 2n calculations will be required. 

===== 2-e =====

> hamming' :: [Int] -> [Int]
> hamming' xs = ham where ham = 1:(foldr1 (merge) [map ((x)*) ham | x <- xs])

===== 3-a =====

> type Grid = Matrix Int
> type Matrix a = [[a]]

> choices :: Grid -> [Grid] 
> choices xss = [[if x==0 then [1..9] else[x] | x <- xs] | xs <- xss]

> choices' :: Grid -> [Grid] 
> choices' xss = map (map (\x -> if x==0 then [1..9] else[x])) xss

===== 3-b =====

> cp :: [[a]] -> [[a]] 
> cp [] = [[]]
> cp (xs:xss) = [x:ys |x <- xs, ys <- yss] where yss = cp xss

> cp' :: [[a]] -> [[a]] 
> cp' = foldr f [[]] where f xs xss = [y:ys | y <- xs, ys <- xss]

===== 3-c =====

> expand :: Matrix [a] -> [Matrix a] 
> expand xsss = cp [cp xss | xss <- xsss]

===== 3-d =====

> complete :: Grid -> Bool 
> complete grid = True 

complete was defined as above for compiling purposes

> solve :: Grid -> [Grid] 
> solve grid = filter (/=[[]]) [if complete(x) then x else [[]] | x <- expand(choices grid)]

For an initial grid with x empty squares, 
Each of the empty squares will be replaced from 0 to [1,2,3,4,5,6,7,8,9] (from the choices function),
resulting in a list of Matrices with either single element lists ([a]) or 9-element lists ([1,2,3,4,5,6,7,8,9]).

Then, from the expand function, the cartesian product of the list of Matrices will be calculated.
The compiling of 'choices grid' will result in a list of matrices a x number of 9-element losts, and remaining 1-element lists.

We denote A_i as the number of 9-element lists as part of the ith Matrix value, where 1 <= i <= (total number of Matrices = M)

Then as there is a total x number of 9-element lists, Sum_(i=0)^M A_i = x

Then for the ith Matrix, the result of 'cp A_i' will result in 9^(A_i) total possible cases

And for the cartesian product of all the matrices, there will be a total of Sum_(i=0)^M 9^(A_i) = 9^(A_1 + A_2 + ... + A_M) = 9^x

Thus a total of 9^x numver of matrices will be considered in one 'solve' function evaluation

===== 3-e ===== 

> prune :: Matrix [Int] -> Matrix [Int]
> prune grid = grid 

prune was defined as above for compiling purposes

> solve' :: Grid -> [Grid] 
> solve' grid = filter (/=[[]]) [if complete(x) then x else [[]] | x <- prune(expand(prune(choices grid)))]

===== 4-a =====

> inits :: [x] -> [[x]] 
> inits xs  	| null(xs) 		= [[]]
>  				| otherwise  	= (inits (init xs)) ++ [xs] 

===== 4-b =====

    > scanl f e = map (foldl f e) . inits

    > f :: a -> b -> a 
    As f can be passed on as the first argument of foldl

    > e :: a 
    As e can be passed on as the second argument of foldl, and the first argument of f is of type a 

    Then
    > foldl f e :: b -> a

    We notice that since
    > inits :: [x] -> [[x]] ,

    For '(foldl f e) . inits' to compile without error, 
    > b :: [[x]]
    > f :: a -> [[x]] -> a 
    > foldl f e :: [[x]] -> a 
    > map (foldl f e) :: [[x]] -> [a] 
    > map (foldl f e) . inits :: [x] -> [a]
    > scanl f e :: [x] -> [a] 

    Thus if
    > b :: [x]

    Then
    > scanl :: (a -> b -> a) -> a -> [b] -> [a]

===== 4-c =====

> scanl' :: (a -> b -> a) -> a -> [b] -> [a] 
> scanl' f e xs = e:[f ((scanl' f e xs)!!n) (xs!!n) | n <- [0..((length xs) - 1)]]

We notice that when passing the 'scanl' function varaibles f(f :: a -> b -> a), e(e :: a), and xs(xs :: [b]),

The first value of the output of scanl' will always be 'e' itself. 
Thus, to the newly designed 'scanl' function, we can first add 'e:' to the front

After adding 'e' as the first element of the product of 'scanl' f e xs', we now move on to calculating the second element. 
In terms of calculations, we notice that the second element of 'scanl' f e xs' should be 'f e xs!!0' => Here, we notice that
e is the first element of 'scanl' f e xs', thus 'f e xs!!0 = f ((scanl' f e xs)!!0) (xs!!0)' 

Similarly, the third element of 'scanl' f e xs' should be 'f (f e xs) xs!!1 = f ((scanl' f e xs)!!1) (xs!!1)'

Here, one concern may be that we are referencing 'scanl' f e xs' to calculate 'scanl' f e xs'. 
However, Haskell is a lazy language, and for the calculation of each nth term of 'scanl' f e xs', 
since the evaluation 'f ((scanl' f e xs)!!(n-1)) (xs!!(n-1))' itself referes to the (n-1)th term, which has already been 
added to the final answer array, Haskell will simply refer to this answer. 

The last concern is until what value does 'n' evvaluate to, which in this case is simply the length of xs.

Thus to organize all parts calculated above, we get the definition above. 

===== 4-d =====

> data Natural = Zero | Succ Natural

> foldNat :: (a -> a) -> a -> Natural -> a
> foldNat f e Zero = e
> foldNat f e (Succ m) = f (foldNat f e m)

Proving that 
    f . foldNat g a = foldNat h b

When 
    f is strict
And
    f a = b
    f . g = h . f

We recognize that 'foldNat f e (Succ m)' simply applies the function 'f' to 'e' for a 'm' number of times. 
Thus it can be stated that 
    > f . foldNat g a (Succ X) = (f . (g . g . g . ... . g)) a 
    >                   = f(g(g(g( ... g(a))))) -> X number of 'g's 
                            => f . g = h . f
    >                   = h(f(g(g( ... g(a))))) -> X-1 number of 'g's, 1 number of 'h's 
    >                   = h(h(h(h( ... f(a))))) -> X number of 'h's 
                            => f a = b
    >                   = h(h(h(h( ... h(b))))) -> X number of 'h's
    >                   = (h . h . h . ... . h) b -> X number of 'h's  
    >                   = foldNat h b (Succ X)

Thus proving that f . foldNat g a = foldNat h b

=> I was able to use the conditions 'f a = b' and 'f . g = h . f', but I was unable to figure out why 'f' needs to be strict