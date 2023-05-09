> import Data.Char

------------------------------------------------------------------------------------
5.1

> take1 :: Int -> [a] -> [a]
> take1 0 _ = []
> take1 _ [] = []
> take1 n (x:xs) = x:(take1 (n-1) xs)

> drop1 :: Int -> [a] -> [a]
> drop1 0 xs = xs
> drop1 _ [] = []
> drop1 n (x:xs) = drop1 (n-1) xs

The function take n xs is strict in n -> 
If a value of n is not given to the function, the function does not know where to 'cut' xs, 
thus if n is undefined, the result of the function woll also be undefined.

However, the function take n xs is not strict in xs -> 
For example, if n=0, regardless of the value of xs the fucntion take n xs will evaluate to [].
Thus take 0 1 [1..] = []

------------------------------------------------------------------------------------
5.2

The map function itself is strict. If the function is given an undefined list as input, 
regardless of the calculation(a -> b) applied to each element of the list, 
the output list will also not be defined.

However, the function map f is not strict. 
For example, even if f is undefined, map f [] will evaluate to []. 

------------------------------------------------------------------------------------
5.3

> evens :: [a] -> [a]
> evens [] = []
> evens [x] = []
> evens (x:y:xs) = y:(evens xs)

> odds :: [a] -> [a]
> odds [] = []
> odds (x:xs) = [x] ++ (evens xs)

> alts :: [a] -> ([a],[a])
> alts [] = ([], [])
> alts [x] = ([x],[])
> alts (x:y:xs) = (x:(fst (alts xs)) , y:(snd (alts xs)))

------------------------------------------------------------------------------------
6.1, 6.2

> zip1 :: [a] -> [b] -> [(a, b)]
> zip1   []      _    = []
> zip1 (x:xs) (y:ys) = (x,y) : zip1 xs ys
> zip1    _     []    = []

> zipWith1 :: (a -> b -> c) -> [a] -> [b] -> [c]
> zipWith1 f xs ys = map (uncurry f) (zip1 xs ys)

> zipWith2 :: (a -> b -> c) -> [a] -> [b] -> [c]
> zipWith2 f (x:xs) (y:ys) = (f x y): (zipWith2 f xs ys)
> zipWith2 f _ _ = []

> zip2 :: [a] -> [b] -> [(a, b)]
> zip2 xs ys = zipWith2 (\x y -> (x,y)) xs ys

------------------------------------------------------------------------------------
6.3

> unfold :: (a->Bool) -> (a->b) -> (a->a) -> a -> [b]
> unfold null head tail = map head . takeWhile (not.null) . iterate tail

> splits :: [a] -> [(a,[a])]
> splits xs = unfold null (\x -> (head x, (take (length xs - length x) xs) ++ tail x)) tail xs 

------------------------------------------------------------------------------------
6.4

> permutations1 :: [a] -> [[a]]
> permutations1 [] = [[]]
> permutations1 (x:xs) = [ zs | ys <- permutations1 xs, zs <- include x ys ]

> include :: a -> [a] -> [[a]]
> include x [] = [[x]]
> include x (y:ys) = (x:y:ys) : map (y:) (include x ys)

include1 :: a -> [a] -> [[a]]
include1 x (y:ys) = foldr (++) () [xs]

Although I tried a few different methods, 
I was unable to solve for 6.4

------------------------------------------------------------------------------------
6.5

> unfold1 :: (a->Bool) -> (a->b) -> (a->a) -> a -> [b]
> unfold1 null head tail xs = if null xs then [] 
> 		else ((map head . takeWhile (not.null)) [xs]) ++ unfold1 null head tail (tail xs)

