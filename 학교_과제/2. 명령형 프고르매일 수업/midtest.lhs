LONG VACATION 2017 FP/DAA PAPER

Question 2 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

(a) 

> map1 :: (a -> b) -> [a] -> [b] 
> map1 f [] = [] 
> map1 f (x:xs) = f x : map1 f xs 

(b) 

> applyAll :: [(a -> b)] -> a -> [b] 
> applyAll [] y = [] 
> applyAll (f:fs) y = f y : applyAll fs y 

(c) 

> applyAll' :: [(a -> b)] -> a -> [b] 
> applyAll' fList x = map ((\x f -> f x) x) fList 

(d)

Using the take Lemma we first notice that for the base case, 

take 0 (applyAll fList x) = [] 
take 0 (applyAll' fList x) = []

Then, assuming that

take n (applyAll fs x) = take n (applyAll' fs x)

We notice that

take (n+1) (applyAll fs x) 						{fs = f:fx} 
= take (n+1) (applyAll (f:fx) x)  					{Definition of applyAll}
= take (n+1) (f x : applyAll fx x)					{Definition of take}
= (f x) : take n (applyAll fx x) 					{Induction Hypothesis}
= (f x) : take n (applyAll' fx x) 					{Definition of take} 
= take (n+1) (f x : applyAll' fx x) 					{Definition of applyAll'} 
= take (n+1) (f x : map ((\x f -> f x) x) fx)				{f x = (\x f -> f x) x f}
= take (n+1) (map ((\x f -> f x) x) (f:fx))				{Definition of applyAll'}
= take (n+1) (applyAll' (f:fx) x)					{f:fx = fs}
= take (n+1) (applyAll' fs x)	

And thus if take n (applyAll fs x) = take n (applyAll' fs x), 
then take (n+1) (applyAll fs x) = take (n+1) (applyAll' fs x)
And the take lemme hold true, proving that 
applyAll fs x = applyAll' fs x

(e)

We notice that

map g . applyAll fs = applyAll ((map ((.) g)) fs)

We first notice tha by definition of (.) operator in Haskell, 
(.) g f = (g . f), and (g . f) x = g ( f(x) )

Then, defining fs = [f1, f2, ..., fn], 

(map g . applyAll fs) x 			{Definition of (.)}
= map g (applyAll fs x) 			{Definition of applyAll}
= map g [f1 x, f2 x, ... , fn x]		{Definition of map}
= [g(f1 x), g(f2 x), ... , g(fn x)]	

And

applyAll ((map ((.) g)) fs) x 				{Definition of fs}
= applyAll ((map ((.) g)) [f1, f2, ..., fn]) x 		{Definition of map}
= applyAll [(.) g f1, (.) g f2, ..., (.) g fn] x 	{Definition of (.)}
= applyAll [(g . f1), (g . f2), ..., (g . fn)] x 	{Definition of applyAll}
= [(g . f1) x, (g . f2) x, ..., (g . fn) x] 		{Definition of (.)}
= [g(f1 x), g(f2 x), ... , g(fn x)]	

And we notice that 
(map g . applyAll fs) x = applyAll ((map ((.) g)) fs) x 
Thus
map g . applyAll fs = applyAll ((map ((.) g)) fs)


Question 3 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

(a) 

Using recursion, 

> subseqs :: [a] -> [[a]] 
> subseqs xs = filter (not . null) (subseqs' xs)


> subseqs' :: [a] -> [[a]]
> subseqs' [] = [[]]
> subseqs' (x:xs) = (map ((:) x) (subseqs' xs)) ++ (subseqs' xs)

Using folds, 

> subseqsF :: [a] -> [[a]] 
> subseqsF xs = filter (not . null) (subseqsF' xs)

> subseqsF' :: [a] -> [[a]] 
> subseqsF' xs = foldr (\x xss -> xss ++ [x:xs | xs <- xss]) [[]] xs

(b) 

> nearest :: Int -> [(a, Int)] -> (a, Int) 
> nearest t xs = nearest' t (tail xs) (head xs)

> nearest' :: Int -> [(a, Int)] -> (a, Int) -> (a, Int) 
> nearest' t xs x  	| null xs  							= x
> 					| snd x == t  					= x 
>  					| abs(snd x - t) < abs (snd (head xs) - t) 	= nearest' t (tail xs) x 
>  					| otherwise 					= nearest' t (tail xs) (head xs)

(c)

 mkExprs :: [Int] -> [(Expr, Int)] 

 countdown :: Int -> [Int] -> (Expr, Int) 
 countdown t ts = nearest t ((mkExprs . concat . subseqs) ts)

(d)  

We could store intermediate values of  possible valid arithmetic expressions of
some sequences that are subsequences of other sequences. For example, in the function
above, mkExprs [1,2,3] and mkExprs [1,2,3,4] are both seperately evaluated, 
where we could simply store the value of 

Question 4 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

(a)

> unfold :: (a -> Bool) -> (a -> b) -> (a -> a) -> a -> [b]
> unfold p f g x = if (p x) then [] else (f x) : unfold p f g (g x)

(b) 

> iterateC :: (a -> a) -> a -> [a] 
> iterateC f x = x : iterateC f (f x)

(c) 

unfold p f g = map f (takeWhile (not . p) (iterate g x))

(d) 

map f = unfold null (f . head) tail
iterate f = unfold ((\x -> False)) id f

(e)

> fib :: [Int] 
> fib = unfold (\x -> False) f g 0

(f)

filter p = unfold (p . head) head tail

filter p is more efficient, as the unfold version has to calculate the 
head and tail of a lits every iteration, unlike the standard version

Question 7 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

(a)

DFS-Search (Input: G(V, E)) =
for u in V 
	color[u] = White 
	p[u] = NIL 

time = 0

for u in V
	if color[u] == White
		DFS-Visit(u)

DFS-Visit(u) =
time ++
d[u] = time
color[u] = Grey 
for v in Adj[u]
	if color[v] == White
		pi[v] = u
		DFS-Visit(v) 
time ++
f[u] = time
color[u] = Black

(b)

A tree edge is an edge that directly connects a parent and child node

A forward edge is an edge that connects a node to a non-child descendant node

A back edge is an edge that connects a node to an ancestor node

A corss edge is an edge that connects a node to a node that are not in a 
ancestor - descendant relationship

(c)

(i) 

False
if u and v are located in different DFS trees, 
d[u] < d[v] can be true but v and u are not ancestor-descendants

(ii)

True
If there exists a path from u to v, this implies that v is a few adjgencies away from u - that is, the DFS-Visit of u will be called before the DFS-Visit of v, and the DFS-Visit of v will be called within the call of of the DFS-Visit of u. This implies that the DFS-Visit(v) will be called after DFS-Visit(u), and end before DFS-Visit(u) ends. (It is nested inside). Thus d[v] <= f[u] 

(iii)

True
If a Cycle exists within G, for example (u1 -> u2 -> .. -> un -> u1), where un is a descendant of u1, we notice that it is apparent that un -> u1 is a back node. 

On the other hand, if a back edge exists within G, for example un -> u1, this implies that u1 is an ancestor of u1, and thus there exists a series of edges that connectes from u1 to un, thus a cycle exists.

(iv)

False
If a DFS-tree contains only one node (u), this indicates that in the original graph the only type of edge that is connected to u can be a self-loop (u -> u), which is considered a back edge and thus it does not have both incoming and outgoing edges.

Thus since 
DFS-tree contains only u => Graph does not have both incoming and outgoing edges
then
Graph has both incoming and outgoing edges => DFS-Tree does not only contain u

Question 8 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

(a) 

First create a max heap
Then take the first element, and switch it with the last element within the heap
Decrease the size of the heap by 1 (excluding the last element)
Repeat the process until the Heapsize becomes 1 to create a sorted list

On A = [3,4,5,1,2]

Step 1:
We first create a max heap -> A = [5,4,3,1,2] HeapSize = 5

Step 2:
Swap the first element with the last element within the hap, and decrease the heap
size by 1 -> A = [2,4,3,1,5] HeapSize = 4 

Step 3:
Max-Heapify the heap -> A = [4,2,3,1,5] HeapSize = 4

Repeat Step 2 -> A = [1,2,3,4,5] HeapSize = 3 

Repeat Step 3 -> A = [3,2,1,4,5] HeapSize = 3

Repeat Step 2 & 3 -> A = [1,2,3,4,5] HeapSize = 2 

Repeat Step 2 & 3 -> A = [1,2,3,4,5] HeapSize = 1

And A is sorted

(b)

(c) 

The Master Theorem states that if the time complexity of a function is defined as
T(n) = a * T(⌊ n/b ⌋) + O(n^d)

Then the time complexity of T is
 -> log b a < d => T(n) = O(n^d)
 -> log b a = d => T(n) = O(n^d * log b n)
 -> log b a > d => T(n) = O(n^(log b a))

Using master theorem, we notice that in Stooge-Sort, worst case the time 
complexity is

T(n) = 3 * T(2n/3) + O(1)

d = 0, a = 3, b = 3/2 -> log b a = log 1.5 3 > 0

Thus the time complexity is O(n^(log 1.5 3))

(d)

For arrays of significant size, stooge sort could possibly be favored over the 
other options since although stooge sort does make more recursive calls (log 1.5 > log 2), 
unlike merge sort or heap sort for each recursive step it takes linear time.

However, in general Stooge sort has a complexity of O(n^(log 1.5 3)) > O(n^2) > O(n log n) 
and will rarely be favored over the other 3 options given

(e) 

No, for example the sequence 

2(1) 2(2) 2(3) 1 

will be sorted as

1 2(2) 2(3) 2(1)

And thus Stooge-Sort is not a stable sorting algorithm
