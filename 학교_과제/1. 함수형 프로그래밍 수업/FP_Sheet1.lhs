> double :: Integer -> Integer 
> double n = 2 * n 

-- | 2.1
-- | (i) Defining a Factorial function

> factorial :: Integer -> Integer 
> factorial 0 = 1 
> factorial n = product [n, factorial (n-1)] 

-- | (ii) Defining a Choose function

> choose :: (Integer, Integer) -> Integer 
> choose (n, r) = factorial(n) `div` (factorial(r) * factorial(n-r)) 

-- | (iii) Defining a Check function

> check :: Integer -> Bool 
> check n = if sum[choose(n, r) |r <- [0..n]] == 2^n then True else False 

-- | 2.2
-- |Defining 4 functions equivalent to the Not function

> notVariant1 :: Bool -> Bool 
> notVariant1 x = if x then False else True 

> notVariant2 :: Bool -> Bool 
> notVariant2 x  
>	| x==True   = False  
> 	| x==False  = True 

> notVariant3 :: Bool -> Bool 
> notVariant3 False = True 
> notVariant3 True = False 

> notVariant4 :: Bool -> Bool 
> notVariant4 x = case x of  
> 	True -> False  
> 	False -> True 