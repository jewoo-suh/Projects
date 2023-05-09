------------------------------------------------------------------------------------
6.7

> data Tree a = Fork (Tree a) a (Tree a) | Empty

> insert :: (Ord a) => a -> Tree [a] -> Tree [a]
> insert x Empty = Fork Empty [x] Empty
> insert x (Fork t1 ys t2)
>       | x < minimum ys   = Fork (insert x t1) ys t2
>       | x > maximum ys   = Fork t1 ys (insert x t2)
>       | otherwise        = Fork t1 ys t2

> flatten :: Tree [a] -> [a]
> flatten (Fork Empty x Empty) = x
> flatten (Fork Empty x t2) = x ++ flatten t2
> flatten (Fork t1 x Empty) = flatten t1  ++ x
> flatten (Fork t1 x t2) = flatten t1 ++ x ++ flatten t2

> bsort :: Ord a => [a] -> [a]
> bsort = flatten . foldr insert Empty

------------------------------------------------------------------------------------
7.1

> cp :: [[a]] -> [[a]]
> cp = foldr f [[]] where f xs xss = [y:ys | y <- xs, ys <- xss ]

------------------------------------------------------------------------------------
7.2

> cols :: [[a]] -> [[a]]
> cols [xs] = [ [x] | x <- xs ]
> cols (xs:xss) = zipWith (:) xs (cols xss)

> cols' :: [[a]] -> [[a]]
> cols' (xs:xss) = foldr f (replicate (length xs) []) (xs:xss) 
>           where f xs xss = zipWith (:) xs [ys | ys <- xss]

------------------------------------------------------------------------------------
8.1

> rjustify :: Int -> String -> String
> rjustify n str = if length str <= n then (replicate (n - length str) ' ') ++ str 
>           else drop (length str - n) str

> ljustify :: Int -> String -> String
> ljustify n str = if length str <= n then str ++ (replicate (n - length str) ' ') 
>           else take n str

If the string is wider than the target length, 
-> For rjustify, the code cuts off the excess string on the left
-> For ljustify, the code cuts off the excess string on the right

I believe this is what I would want, as rjustify and ljustify
both locate the string at the right/left end and add spaces or cut off the String
to form a string of certain length indicated by the integer input

------------------------------------------------------------------------------------
8.2

> type Matrix a = [[a]]

> scale :: Num a => a -> Matrix a -> Matrix a
> scale n xss = [[x * n | x <- xs] | xs <- xss]

> dot :: Num a => [a] -> [a] -> a
> dot xs ys = sum (zipWith (*) xs ys)

> add :: Num a => Matrix a -> Matrix a -> Matrix a 
> add xss yss = [zipWith (+) (xss!!n) (yss!!n)| n <- [0..(length xss - 1)]]

> mul :: Num a => Matrix a -> Matrix a -> Matrix a 
> mul xss yss = [map (dot xs) [(cols' yss)!!n | n <- [0..(length xs - 1)]] | xs <- xss]

> table :: Show a => Matrix a -> String 
> table xss = unlines ([unwords[rjustify ((length ((cols' xss)!!n))+2) (show x) | x <- xss!!n] | n <- [0..(length xss -1)]])