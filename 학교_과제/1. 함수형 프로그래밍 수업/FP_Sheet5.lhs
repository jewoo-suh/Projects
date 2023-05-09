> unfold :: (a->Bool) -> (a->b) -> (a->a) -> a -> [b]
> unfold null head tail = map head . takeWhile (not.null) . iterate tail

----- 9.1 -----

> data Nat = Zero | Succ Nat

> int :: Nat -> Int 
> int Zero = 0 
> int (Succ n) = 1 + int n

> nat :: Int -> Nat 
> nat n = if n == 0 then Zero else Succ (nat (n-1))

> add :: Nat -> Nat -> Nat 
> add n1 Zero = n1
> add n1 (Succ n2) = add (Succ n1) n2

> mul :: Nat -> Nat -> Nat 
> mul n1 Zero = Zero
> mul n1 (Succ Zero) = n1
> mul n1 (Succ n2) = add (mul n1 n2) n1

> pow :: Nat -> Nat -> Nat 
> pow n1 Zero = (Succ Zero)
> pow n1 (Succ Zero) = n1 
> pow n1 (Succ n2) = mul (pow n1 n2) n1

> tet :: Nat -> Nat -> Nat 
> tet n1 Zero = Zero 
> tet n1 (Succ Zero) = n1 
> tet n1 (Succ n2) = pow n1 (tet n1 n2) 

----- 9.2 ----- 

> foldNat :: a -> (a -> a) -> Nat -> a 
> foldNat x _ Zero = x 
> foldNat x f (Succ n1) = foldNat (f x) f n1

> isNothing :: Maybe a -> Bool
> isNothing Nothing = True
> isNothing _       = False

> unfoldNat :: Maybe a -> (Maybe a -> Maybe a) -> Nat
> unfoldNat x f = if isNothing(x) then Zero else Succ(unfoldNat (f x) f)

> unfoldNatInt:: Int -> (Int -> Int) -> Nat
> unfoldNatInt x f = if x == 0 then Zero else Succ(unfoldNatInt (f x) f)

> unfoldNat2 :: (a->Bool) -> (a->a) -> a -> Nat
> unfoldNat2 f h x 	| f x 			= Zero
>  					| otherwise 	= Succ (unfoldNat2 f h (h x))

I tried to numerous unfoldNat designs - my initial goal was to create a function
which constructs a Nat value based on its input, but then I also made a function
which was only receievs ints as input, and one based off the original unfold function

> int' :: Nat -> Int 
> int' n1 = foldNat 0 (+1) n1

> nat' :: Int -> Nat 
> nat' n = unfoldNatInt n (\x -> x - 1)

> nat'' :: Int -> Nat 
> nat'' n = unfoldNat2 (==0) (\x -> x - 1) n

> add' :: Nat -> Nat -> Nat 
> add' n1 n2 = foldNat n1 (\x -> Succ x) n2

> mul' :: Nat -> Nat -> Nat 
> mul' n1 n2 = foldNat n1 (add' n2) n2

> pow' :: Nat -> Nat -> Nat 
> pow' n1 n2 = foldNat n1 (mul' n1) n2

> tet' :: Nat -> Nat -> Nat 
> tet' n1 n2 = foldNat n1 (pow' n1) n2

----- 10.1 ----- 

Hypothesis: fold c n (xs ++ ys) == fold c (fold c n ys) xs
---------------------------------------------------------------------------------
1. We first observe when xs and ys are finite lists

(1) We first observe when xs = []. 

fold c n (xs ++ ys)
= fold c n ([] ++ ys)
= fold c n ys 				-> unit of list concatenation

And 

fold c (fold c n ys) xs
= fold c (fold c n ys) [] 	 
= fold c n ys 				-> definition of fold: fold f x [] = x

Thus fold c n (xs ++ ys) == fold c (fold c n ys) xs when xs = []

(2) We then assume that the hypothesis holds for xs and observe for x:xs

Assumption: fold c n (xs ++ ys) = fold c (fold c n ys) xs

fold c n (x:xs ++ ys)
= f x (fold c n (xs ++ ys)) 		-> Definition of fold
= f x (fold c (fold c n ys) xs) 	-> Assumption
= fold c (fold c n ys) x:xs 		-> Definition of fold

Thus the hypothesis holds for all finite lists 													ㅁ
---------------------------------------------------------------------------------
2. We now observe when xs and ys are partial lists

(1) We first observe when xs = ㅗ

fold c n (xs ++ ys)
= fold c n (ㅗ ++ ys)
= fold c n ㅗ				-> From strictness of ++: ++ is strict in its left argument
= ㅗ 						-> From strictness of fold: fold is strict in its list argument

(2) We then assume that the hypothesis holds for xs and observe for x:xs

Assumption: fold c n (xs ++ ys) = fold c (fold c n ys) xs
fold c n (xs) = fold c (fold c n ys) xs -> Definition of partial list

fold c (fold c n ys) x:xs
= fold c n (x:xs ++ ys) 			-> From part 1.
= fold c n (x:(xs ++ ys)) 
= fold c (x:xs) 					-> Definition of partial list

Thus the hypothesis holds for all partial lists 												ㅁ
---------------------------------------------------------------------------------
3. We now observe when xs and ys are infinite lists

From above, as we have proven that the equation holds true for both finite and partial lists, 
it can be stated that the equation also holds true for infinite lists, from Sheet 9 Section 6.	ㅁ

Thus fold c n (xs ++ ys) = fold c (fold c n ys) xs 												ㅁ

----- 10.2 ----- 

For the equality [f . fold g a xs = fold h b xs] to hold for xs,
we need to satisfy the following conditions:

(1) b = f a
(2) h x (f y) = f (g x y)

Thus we assign the following equations to each variable

f = fold f1 n
g = (:)
h = f1
a = ys
b = fold f1 n ys

Now we check if the variables satisfy the condtions
(1) b = fold f1 n ys = f a
(2) h x (f zs)
= f1 x (fold f1 n zs)
= fold f1 n (x:zs)
= f ((:) x zs)
= f (g x zs)

Thus as the conditions have been satisfied, we apply the varaibles to the original function
f . fold g a x:xs = fold h b x:xs

fold f1 n (fold (:) ys x:xs) = fold f1 (fold f1 n ys) x:xs

And converting f1 -> c and x:xs -> xs

fold c n (fold (:) ys xs) = fold c (fold c n ys) xs 
fold c n (xs ++ ys) = fold c (fold c n ys) xs 													ㅁ

----- 10.3 ----- 

Using the same equation as 10.2, 
for the equality [f . fold g a xs = fold h b xs] to hold for xs,
we need to satisfy the following conditions:

(1) b = f a
(2) h x (f y) = f (g x y)

Thus we assign the following equations to each variable

f = filter p
g = (:)
h = (\x -> if (>2) x then ([x]++) else ([]++))
a = ys
b = filter p ys

Now we check if the variables satisfy the condtions
(1) b = filter p ys = f a
(2) h x (f zs)
= (\x -> if p x then (:) x else []++) x (filter p zs)
= filter p (x:zs)
= filter p ((:) x zs)
= f (g x zs)

Thus as the conditions have been satisfied, we apply the varaibles to the original function
f . fold g a xs = fold h b xs

filter p (fold (:) ys xs) = fold (\x -> if (>2) x then ([x]++) else ([]++)) (filter p ys) xs
filter p (xs ++ ys) = fold (\x -> if (>2) x then ([x]++) else ([]++)) (filter p ys) xs

-> fold (\x -> if (>2) x then ([x]++) else ([]++)) (filter p ys) xs
effectively iterates through xs, and filters the elements of xs which satisfy p;
then, they add that list to (filter p ys); thus, 

filter p (fold (:) ys xs) = filter p xs ++ filter p ys

Below is a test case which shows the functionality of (\x -> if (>2) x then ([x]++) else ([]++))

> test :: [Int] -> [Int]
> test xs = foldr f [1,2,3] xs where f = (\x -> if (>2) x then ([x]++) else ([]++))

ghci> test [10,11,12
[10,11,12,1,2,3]

Thus the equation is proved.																	ㅁ

----- 10.4 ----- 

> data Liste a = Snoc (Liste a) a | Lin

> cat :: Liste a -> Liste a -> Liste a 
> cat Lin Lin = Lin
> cat l1 Lin = l1
> cat Lin l2 = l2
> cat l1 (Snoc (l2) a2) = cat (Snoc l1 a2) l2

> folde :: a -> (a -> a) -> Liste b -> a 
> folde x _ Lin = x 
> folde x f (Snoc (l2) a2) = folde (f x) f l2

> cat' :: Liste a -> Liste a -> Liste a 
> cat' l1 (Snoc (l2) a2) = folde l1 (\x -> Snoc x a2) (Snoc (l2) a2)

> list :: Liste a -> [a] 
> list (Snoc (l2) a2) = folde [] ((:) a2) (Snoc (l2) a2)

> liste :: [a] -> Liste a
> liste [] = Lin
> liste xs = foldr (\x y -> Snoc y x) Lin xs

When applied to an infinite list, liste will return an infinite loop of
nested Snocs. For example, liste [1..] will correspond to 
... Snoc(Snoc Lin 1)2 ... 

Using the loop function provided in the lecture notes, 

> loop s n = foldr (flip s) n . reverse 

> list' :: Liste a -> [a] 
> list' Lin = []
> list' (Snoc (l2) a2) = loop (\x y -> y:x) [a2]  (list' l2)

> liste' :: [a] -> Liste a
> liste' xs = loop (Snoc) (Lin) xs 

----- 10.5 ----- 

> unfolde :: Maybe a -> (Maybe a -> Maybe a) -> (Maybe a -> Maybe a) -> Liste (Maybe a)
> unfolde x f g = if isNothing(x) then Lin else Snoc (unfolde (f x) f g) (g x)

> unfolde' :: (Liste a->Bool) -> (Liste a->a) -> (Liste a -> Liste a) -> Liste a -> Liste a
> unfolde' f g h list 	| f list 		= Lin
>  						| otherwise 	= Snoc (unfolde' f g h (h list)) (g list)

> unfolde2 :: (Liste a->Bool) -> (Liste a->b) -> (Liste a->Liste a) -> Liste a -> [b]
> unfolde2 f g h list 	| f list 		= []
>  						| otherwise 	= g list : (unfolde2 f g h (h list))

> unfolde3 :: (a->Bool) -> (a->b) -> (a->a) -> a -> Liste b
> unfolde3 f g h list 	| f list 		= Lin
>  						| otherwise 	= Snoc (unfolde3 f g h (h list)) (g list)

Out of the three, I believe that unfolde' was what the question was asking for, since
unfolde' (\Lin -> True) (\(Snoc x y) -> y) (\(Snoc x y) -> x) list = list
-however, I am adding a few more of the possible solutions I derived 
for clarification of my thought process.

> list'' :: Liste a -> [a] 
> list'' list = unfold (\(Lin) -> True) (\(Snoc x y) -> y) (\(Snoc x y) -> x) list

> liste'' :: [a] -> Liste a 
> liste'' xs = unfolde3 null last init xs

Overall in this worksheet I think I struggled a bit with the "create a unfold function 
for this data type" parts - would we be able to go over these parts in more detail
during the tutorial? Thank you!

