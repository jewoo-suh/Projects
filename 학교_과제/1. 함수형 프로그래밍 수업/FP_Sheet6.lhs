
11.1
====================================================================================================

> foldBool :: a -> a -> Bool -> a
> foldBool true false True = true 
> foldBool true false False = false

> data Day = Sunday | Monday | Tuesday | Wednesday |
>  				Thursday | Friday | Saturday

> foldDay :: a -> a -> a -> a -> a -> a -> a -> Day -> a
> foldDay sunday monday tuesday wednesday thursday friday saturday Sunday = sunday
> foldDay sunday monday tuesday wednesday thursday friday saturday Monday = monday
> foldDay sunday monday tuesday wednesday thursday friday saturday Tuesday = tuesday
> foldDay sunday monday tuesday wednesday thursday friday saturday Wednesday = wednesday
> foldDay sunday monday tuesday wednesday thursday friday saturday Thursday = thursday
> foldDay sunday monday tuesday wednesday thursday friday saturday Friday = friday
> foldDay sunday monday tuesday wednesday thursday friday saturday Saturday = saturday

11.2
====================================================================================================

a <= b corresponds to not(a) || b

> (<=) :: Bool -> Bool -> Bool 
> (<=) a b = not(a) || b

11.3
====================================================================================================

> data Set a = Empty | Singleton a | Union (Set a) (Set a)

> foldSet :: b -> (a -> b) -> (b -> b -> b) -> Set a -> b 
> foldSet empty singleton union Empty = empty 
> foldSet empty singleton union (Singleton x) = singleton x 
> foldSet empty singleton union (Union l r) = union (foldSet empty singleton union l) (foldSet empty singleton union r)

> isIn :: Eq a => a -> Set a -> Bool
> isIn x setx = foldSet False (==x) (||) setx

> subset :: Eq a => Set a -> Set a -> Bool
> subset setx sety = foldSet False (\x -> isIn x sety) (||) setx

The code below is for (==)

> areEqualSets :: Eq a => Set a -> Set a -> Bool 
> areEqualSets setx sety = (subset setx sety) && (subset sety setx)

11.4
====================================================================================================

> data BTree a = Leaf a | Fork (BTree a) (BTree a)

> data Direction = L | R
> type Path = [ Direction ]

> foldBTree :: (a -> b) -> (b -> b -> b) -> BTree a -> b
> foldBTree leaf fork (Leaf x) = leaf x
> foldBTree leaf fork (Fork l r) = fork (foldBTree leaf fork l)
>  										(foldBTree leaf fork r)

> find :: Eq a => a -> BTree a -> Maybe Path
> find x treex = foldBTree f paths treex 
>  					where   
>						f (y) = if y == x then Just [] else Nothing 
>						paths :: Maybe Path -> Maybe Path -> Maybe Path 
>						paths Nothing  Nothing    = Nothing 
>						paths Nothing  (Just xs)  = Just (R:xs) 
>						paths (Just xs) _         = Just (L:xs)

12.1
====================================================================================================

> data Queue a = Queue [a]

> empty :: Queue a 
> empty = Queue []

> isEmpty :: Queue a -> Bool 
> isEmpty (Queue xs) = null(xs)

Both the empty and isEmpty functions would not be too expensive in terms of performance, 
as empty is a constant-step process of creating a Queue with an empty list, 
and isEmpty is also a constant-step process of checking whether the queue is empty or not. 
Both functions are not affected by the length of the queue. 
Thus the cost of both functions will be O(1)

> add :: a -> Queue a -> Queue a 
> add x (Queue xs) = (Queue (xs ++ [x])) 

The add function is more expensive then the empty or isEmpty functions, 
as the ++ operator requires the function to iterate unitl the end of a list 
to add an element to a list, thus being an n-step process (with n being the length of xs).
The add function is linearly dependent on the length of the queue.
Thus the cost of the function will be O(n)

> get :: Queue a -> (a, Queue a)
> get (Queue (x:xs)) = (x, (Queue xs))

The get function is like the empty and isEmpty functions; the function takes a list as an input, 
and splits the list into its first element and the rest. Here, the function is a constant(2)-step process,
as it only needs to (1) extract the first element and (2) pass the rest of the list to the queue.
The get function is also not affected by the length of the queue.
Thus the cost of the function will be O(1)

> data QueueNew a = QueueNew ([a] , [a])

> emptyNew :: QueueNew a 
> emptyNew = QueueNew ([],[])

> isEmptyNew :: QueueNew a -> Bool 
> isEmptyNew (QueueNew (xs, ys)) = null(xs) && null(ys)

> addNew :: a -> QueueNew a -> QueueNew a 
> addNew x (QueueNew (xs, ys)) = QueueNew (xs, x:ys)

> getNew :: QueueNew a -> (a, QueueNew a) 
> getNew (QueueNew ((x:xs), ys)) = (x, (QueueNew (xs, ys)))

Similar to above, the emptyNew, isEmptyNew, getNew functions are still constant-step processes, 
and are not affected by the length of the two lists that form QueneNew; thus the cost of 
these functions will be O(1).

As for addNew, the major difference compared to the original add function is that in add, 
the ++ operator was used, which is linearly dependent on the length of the list it is working on, 
while in addNew, the : operator is used which is not dependent on the length of the list it is working on.
Thus the cost of the function has reduced from O(n) to O(1), and as a result is more efficient.

12.2
====================================================================================================

> fib 0 = 0
> fib 1 = 1
> fib n = fib (n-1) + fib (n-2)

When using the implementation above, the time to calculate 'fib n' is equal to the sum of the time to 
calculate 'fib (n-1)' and 'fib (n-2)'. Here, we notice that to calculate a fobonacci number, it is split into
2 parts; thus we assume that the time to calculate fib n is O(2^n). Then using proof by induction, 

Base: n = 1
fib 1 = 1 = O(2^1)

Assumption: time to calculate fib (n-1) = T(n-1) = O(2^(n-1)). 
Then, T(n) = T(n-1) + T(n-2) = O(2^(n-1)) + O(2^(n-2)) = O(2^n)

Thus the time to compute fib n is O(2^n), and this also explains why as n increases, it takes longer to evaluate 
fib n -> The time to compute fib n exponentially increases.

Now using two, 

> two :: (Int, Integer, Integer) -> Int -> (Int, Integer, Integer)
> two (currentN, x, y) n = if currentN < n then two (currentN + 1, y, x + y) n else (currentN, x, y)

> third :: (x,y,z) -> z
> third (x,y,z) = z

> fib' :: Int -> Integer
> fib' n = third (two (0,0,1) n)

Compared to fib, fib' is more efficient because for fib' n, the function calculates from two 0 and works its way up,
unlike fib n where the calculations are oriented from the highest order to lowest. Here, only n recursions are
required to calculate fib' n, thus the time to compute fib' n is O(n) - significantly faster than fib n.

*I was unable to solve for a recursion for two in the format of two :: Int -> (Integer, Integer) -
Would we be able to go voer this during the tutorial?*

> roughly :: Integer -> String
> roughly n = x : 'e' : show (length xs) where x:xs = show n

Calculating the size of the 10000th fibonacci number, 

ghci> roughly (fib' 10000)
"5e2089"

Now setting F to be the matrix [0, 1 \\ 1, 1], we notice that F^n = [fib(n-1), fib n \\ fib n, fib (n+1)]
To show that this is indeed true, we use proof by induction:

Base: n = 1 
F^1 = [0, 1 \\ 1, 1] = [fib 0, fib 1 \\ fib 1, fib 2]

Assumption: F^(n-1) = [fib(n-2), fib (n-1) \\ fib (n-1), fib n]
Then, F^n = F^1 * F^(n-1) 	= [0, 1 \\ 1, 1] * [fib(n-2), fib (n-1) \\ fib (n-1), fib n] 
							= [fib(n-1), fib n \\ fib(n-2) + fib(n-1), fib(n-1) + fib n]
							= [fib(n-1), fib n \\ fib n, fib (n+1)] = F^n

Thus it is true for all n >= 1 that F^n = [fib(n-1), fib n \\ fib n, fib (n+1)]. 

Using the power function given in th notes, we can calculate fibonacci numbers as below.

> power (*) y x n  -- x^n*y
> 		| n == 0 	= y
> 		| even n 	= power (*) y (x*x) (n `div` 2)
> 		| odd n 	= power (*) (x*y) x (n-1)

we can calculate fibonacci numbers by making a variant of this function.

> matrixM :: Num a => [[a]] -> [[a]] -> [[a]] 
> matrixM xss yss = [[a1*b1 + a2*b3, a1*b2 + a2*b4],[a3*b1+a3*b3, a3*b2+a4*b4]]
>   				where { a1 = (xss!!0)!!0 ; a2 = (xss!!0)!!1 ; a3 = (xss!!1)!!0 ; a4 = (xss!!1)!!1 ; 
>							b1 = (yss!!0)!!0 ; b2 = (yss!!0)!!1 ; b3 = (yss!!1)!!0 ; b4 = (yss!!1)!!1 }

> x = [[0, 1], [1, 1]] 
> y = [[1, 0], [0, 1]] 

> powerMatrix matrixM y x n 
> 		| n == 0 	= y
> 		| even n 	= power matrixM y (matrixM x x) (n `div` 2)
> 		| odd n 	= power matrixM (matrixM x y) x (n-1)	

> fib'' :: Int -> Integer 
> fib'' n = ((powerMatrix matrixM y x (n-1))!!1)!!1

Calculating the size of the 1000000th fibonacci number, 

ghci> roughly (fib'' 1000000)
"5e192320"

12.3
====================================================================================================

> loop s n = foldr (flip s) n . reverse

> loop' s n [] = n
> loop' s (!n) (x:xs) = loop' s (s n x) xs

> test f = f (const error) () ["strict","lazy"]

Observing test loop,

loop (const error) () ["strict","lazy"] 
= loop (const error) (const error () "strict") ["lazy"]
= loop (const error) (const error (const error () "strict") "lazy") [] 
= const error (const error () "strict") "lazy"

Here, haskell will initially run the code segment
-> const error (const error () "strict") <-
To which it will return 'error' 

Then haskell will run
-> error "lazy" <- 
To which the ghci will print an error statement. Thus, 

= const error (const error () "strict") "lazy"
= *** Exception: lazy

Observing test loop,

loop' (const error) () ["strict","lazy"] 
= loop' (const error) (const error () "strict") ["lazy"]

Here, the ! declaration in the loop' definition ensures the argument is evaluated.
Thus, haskell will evaluate
-> const error () "strict" <-
And will first run the code segment
-> const error () <- and return 'error'
Then haskell will run 
-> error "strict" <- 
To which the ghci will interrupt the code and print an error statement. Thus, 

= loop' (const error) (const error () "strict") ["lazy"]
= *** Exception: strict

Checking with ghci, 

ghci> test loop
*** Exception: lazy
CallStack (from HasCallStack):
  error, called at FP6.lhs:227:21 in main:Main

ghci> test loop'
*** Exception: strict
CallStack (from HasCallStack):
  error, called at FP6.lhs:227:21 in main:Main 

Thus confirming the observations. 

Now observing test foldl

foldl (const error) () ["strict","lazy"] 
= foldl (const error) (const error () "strict") ["lazy"]
= foldl (const error) (const error (const error () "strict") "lazy") [] 
= const error (const error () "strict") "lazy"
= error "lazy"
= *** Exception: lazy

Checking with ghci, 

ghci> test foldl
*** Exception: lazy
CallStack (from HasCallStack):
  error, called at FP6.lhs:227:21 in main:Main

Thus also confirming the observations. 

To double check, it is stated in lecture notes 10 that loop s n and foldl f e are the same functions, 
thus test loop and test foldl will theoretically return the same error statements - and as shwon above they
both return *** Exception: lazy