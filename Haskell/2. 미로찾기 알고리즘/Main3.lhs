> import Geography
> import Maze
> import MyMaze

======================================================================

Draw a maze.

***************************************
*              Question 2             *
* Complete the definition of drawMaze *
***************************************

> drawMaze :: Maze -> IO()
> drawMaze maze = putStrLn(drawMazeFinal maze 0 (snd(sizeOf maze) * 2 -1))

> drawMazeHOneLine :: Maze -> Int -> Int -> String
> drawMazeHOneLine maze x y	| x == (fst(sizeOf maze)) 	= "+\n"
>  							| y == -2 	= "\n"
>  							| y == -1    = "+--" ++ drawMazeHOneLine maze (x+1) y
>  							| otherwise 				= 
>	if (hasWall maze (x,y) N) then "+--" ++ drawMazeHOneLine maze (x+1) (y) 
>	else "+  " ++ drawMazeHOneLine maze (x+1) (y)

> drawMazeVOneLine :: Maze -> Int -> Int -> String 
> drawMazeVOneLine maze x y | x == (fst(sizeOf maze)) 	= "\n"
>  							| x == -1 	= "|" ++ drawMazeVOneLine maze (x+1) y
>  							| y == -1 	= "\n"
>  							| otherwise	= 
>	if (hasWall maze (x,y) E) then "  |" ++ drawMazeVOneLine maze (x+1) (y) 
>	else "   " ++ drawMazeVOneLine maze (x+1) (y)

> drawMazeFinal :: Maze -> Int -> Int -> String 
> drawMazeFinal maze x y 	| y == (-2) 			="\n"
>  							| (y `mod` 2) == 1  	= 
> 		drawMazeHOneLine maze 0 (y `div` 2) ++ drawMazeFinal maze x (y-1)
>  							| (y `mod` 2) == 0 		= 
>  		drawMazeVOneLine maze (-1) (y `div` 2) ++ drawMazeFinal maze x (y-1)


======================================================================

Solve the maze, giving a result of type:

> type Path = [Direction]

***************************************
*            Questions 3--4           *
*     Complete the definition of      *
*              solveMaze              *
***************************************

> solveMaze :: Maze -> Place -> Place -> Path
> solveMaze maze start final = solveMazeIter maze final (nextPossibleMove maze start)

> solveMaze' :: Maze -> Place -> Place -> Path
> solveMaze' maze start final = fastSolveMazeIter maze final (startThing maze start) []

 solveMazeIter :: Maze -> Place -> [(Place, Path)] -> Path
 solveMazeIter maze final startList = 	if (checkTupleForDestination startList final)
   									then snd(returnTupleForDestination startList final)
  										else solveMazeIter maze final (fromTupleListToTupleList maze startList)

> solveMazeIter :: Maze -> Place -> [(Place, Path)] -> Path
> solveMazeIter maze final startList 	|(checkTupleForDestination startList final)    	= snd(returnTupleForDestination startList final)
>  										| otherwise  									= solveMazeIter maze final (fromTupleListToTupleList maze startList)

> fastSolveMazeIter :: Maze -> Place -> [(Place, Path)] -> [Place] -> Path
> fastSolveMazeIter maze final startList visited  		| null(startList) 								= error "I'm lost!  Please help me!"
>  														| (checkTupleForDestination startList final)  	= snd(returnTupleForDestination startList final)
> 	 													| otherwise = fastSolveMazeIter maze final (removeDuplicates visited (fromTupleListToTupleList maze startList)) (visited ++ (foldr (\x acc -> (fst x):acc) [] startList))

> removeDuplicates :: [Place] -> [(Place, Path)] -> [(Place, Path)] 
> removeDuplicates xs ys   				| null xs 			= ys
> 										| otherwise			= removeDuplicates (tail xs) ((filter (((xs!!0) /=) . fst)) ys)

> checkTupleForDestination :: [(Place, Path)] -> Place -> Bool 
> checkTupleForDestination xs target = any (target==) (fromTupleListToPlaceList xs)

> returnTupleForDestination :: [(Place, Path)] -> Place -> (Place, Path)
> returnTupleForDestination (x:xs) target = if fst(x) == target then x else returnTupleForDestination xs target

> fromTupleListToTupleList :: Maze -> [(Place, Path)] -> [(Place, Path)]
> fromTupleListToTupleList maze [] = []
> fromTupleListToTupleList maze (x:xs) = (nextPossibleMoveTuple maze x) ++ (fromTupleListToTupleList maze xs)

> nextPossibleMoveTuple :: Maze -> (Place, Path) -> [(Place, Path)]
> nextPossibleMoveTuple maze ((x,y),path) = [(pList!!n, path ++ [dList!!n]) | n <- [0..3], not ((checkWalls maze (x,y))!!n)]
> 			where { pList = [(x,y+1),(x-1,y),(x,y-1),(x+1,y)] ; dList = [N,W,S,E] }

> checkWalls :: Maze -> Place -> [Bool] 
> checkWalls maze place = [hasWall maze place x | x <- [N,W,S,E]]

> fromTupleListToPlaceList :: [(Place, Path)]  -> [Place]
> fromTupleListToPlaceList xs = [fst(x) | x <- xs]


> startThing :: Maze -> Place -> [(Place, Path)]
> startThing maze (x,y) = [(pList!!n,[dList!!n]) | n <- [0..3], not ((checkWalls maze (x,y))!!n)] 
> 			where { pList = [(x,y+1),(x-1,y),(x,y-1),(x+1,y)] ; dList = [N,W,S,E] }


> nextPossibleMove :: Maze -> Place -> [(Place, Path)] 
> nextPossibleMove maze (x,y) =  [(pList!!n,[dList!!n]) | n <- [0..3], not ((checkWalls maze (x,y))!!n)] 
> 			where { pList = [(x,y+1),(x-1,y),(x,y-1),(x+1,y)] ; dList = [N,W,S,E] }

======================================================================
For MyMaze

> checkWallsNew :: MyMaze -> Place -> [Bool] 
> checkWallsNew (AMaze size nl sl el wl) place = [(elem place nl), (elem place wl), (elem place sl), (elem place el)]

> nextPossibleMoveTupleNew :: MyMaze -> (Place, Path) -> [(Place, Path)]
> nextPossibleMoveTupleNew maze ((x,y),path) = [(pList!!n, path ++ [dList!!n]) | n <- [0..3], not ((checkWallsNew maze (x,y))!!n)]
> 			where { pList = [(x,y+1),(x-1,y),(x,y-1),(x+1,y)] ; dList = [N,W,S,E] }

> startThingNew :: MyMaze -> Place -> [(Place, Path)]
> startThingNew maze (x,y) = [(pList!!n,[dList!!n]) | n <- [0..3], not ((checkWallsNew maze (x,y))!!n)] 
> 			where { pList = [(x,y+1),(x-1,y),(x,y-1),(x+1,y)] ; dList = [N,W,S,E] }

> nextPossibleMoveNew :: MyMaze -> Place -> [(Place, Path)] 
> nextPossibleMoveNew maze (x,y) =  [(pList!!n,[dList!!n]) | n <- [0..3], not ((checkWallsNew maze (x,y))!!n)] 
> 			where { pList = [(x,y+1),(x-1,y),(x,y-1),(x+1,y)] ; dList = [N,W,S,E] }

> fromTupleListToTupleListNew :: MyMaze -> [(Place, Path)] -> [(Place, Path)]
> fromTupleListToTupleListNew maze [] = []
> fromTupleListToTupleListNew maze (x:xs) = (nextPossibleMoveTupleNew maze x) ++ (fromTupleListToTupleListNew maze xs)

> fastSolveMazeIterNew :: MyMaze -> Place -> [(Place, Path)] -> [Place] -> Path
> fastSolveMazeIterNew maze final startList visited  		| null(startList) 								= error "I'm lost!  Please help me!"
>  															| (checkTupleForDestination startList final)  	= snd(returnTupleForDestination startList final)
> 	 														| otherwise = fastSolveMazeIterNew maze final (removeDuplicates visited (fromTupleListToTupleListNew maze startList)) (visited ++ (foldr (\x acc -> (fst x):acc) [] startList))

> solveMazeNew :: Maze -> Place -> Place -> Path
> solveMazeNew maze start final = fastSolveMazeIterNew (mazeToMyMaze maze) final (startThingNew (mazeToMyMaze maze) start) []

> solveMazeNewFinal :: MyMaze -> Place -> Place -> Path 
> solveMazeNewFinal maze start final = fastSolveMazeIterNew maze final (startThingNew  maze start) []

> largeMazeNew = mazeToMyMaze (largeMaze)
> smallMazeNew = mazeToMyMaze (smallMaze)

======================================================================
Some test mazes.  In both cases, the task is to find a path from the bottom
left corner to the top right.

First a small one

> smallMaze :: Maze
> smallMaze = 
>   let walls = [((0,0), N), ((2,2), E), ((2,1),E), ((1,0),E), 
>                ((1,2), E), ((1,1), N)]
>   in makeMaze (4,3) walls

Now a large one.  Define a function to produce a run of walls:

> run (x,y) n E = [((x,y+i),E) | i <- [0..n-1]]
> run (x,y) n N = [((x+i,y),N) | i <- [0..n-1]]

And here is the maze.

> largeMaze :: Maze 
> largeMaze =
>   let walls = 
>         run (0,0) 3 E ++ run (1,1) 3 E ++ [((1,3),N)] ++ run (0,4) 5 E ++
>         run (2,0) 5 E ++ [((2,4),N)] ++ run (1,5) 3 E ++
>         run (1,8) 3 N ++ run (2,6) 3 E ++
>         run (3,1) 7 E ++ run (4,0) 4 N ++ run (4,1) 5 E ++ run (5,2) 3 N ++
>         run (4,6) 2 N ++ run (5,4) 3 E ++ run (6,3) 5 N ++ run (8,0) 4 E ++
>         run (6,1) 3 N ++ run (0,9) 3 N ++ run (1,10) 3 N ++ run (0,11) 3 N ++
>         run (1,12) 6 N ++ run (3,9) 4 E ++ run (4,11) 2 N ++
>         run (5,9) 3 E ++ run (4,8) 3 E ++ run (5,7) 5 N ++ run (6,4) 9 E ++
>         run (7,5) 3 N ++ run (8,4) 4 N ++ run (8,6) 3 N ++ run (10,5) 7 E ++
>         run (9,8) 3 E ++ run (8,9) 3 E ++ run (7,8) 3 E ++ run (8,11) 3 N ++
>         run (0,13) 5 N ++ run (4,14) 2 E ++ run (0,15) 2 E ++ 
>         run (1,14) 3 N ++ run (3,15) 2 E ++ run (0,17) 2 N ++ 
>         run (1,16) 2 E ++ run (2,15) 1 N ++ run (3,16) 3 N ++
>         run (2,17) 2 E ++ run (1,18) 6 N ++ run (4,17) 3 N ++ 
>         run (6,14) 7 E ++ run (5,13) 4 E ++ run (7,12) 2 E ++
>         run (8,13) 3 N ++ run (7,14) 3 N ++ run (10,14) 2 E ++
>         run (8,15) 5 N ++ run (7,16) 5 N ++ run (9,1) 2 E ++
>         run (10,0) 12 N ++ run (21,1) 1 E ++ run (10,2) 2 E ++
>         run (11,1) 7 N ++ run (17,1) 1 E ++ run (11,3) 3 E ++
>         run (12,2) 7 N ++ run (18,2) 2 E ++ run (19,1) 2 N ++
>         run (15,3) 3 N ++ run (14,4) 3 E ++ run (13,3) 3 E ++
>         run (12,4) 3 E ++ run (12,6) 3 N ++ run (11,7) 8 E ++ 
>         run (9,12) 3 N ++ run (12,14) 1 N ++ run (12,8) 10 E ++
>         run (0,19) 6 N ++ run (1,20) 6 N ++ run (7,18) 8 E ++
>         run (8,17) 1 N ++ run (8,18) 3 E ++ run (9,17) 4 E ++ 
>         run (10,18) 2 E ++ run (11,17) 2 E ++ run (10,20) 3 N ++
>         run (11,19) 3 N ++ run (12,18) 2 N ++ run (13,17) 2 N ++
>         run (13,13) 4 E ++ run (14,12) 7 N ++ run (13,11) 2 N ++
>         run (14,10) 2 E ++ run (13,9)2 E ++ run (14,8) 3 N ++ 
>         run (13,7) 3 N ++ run (15,5) 3 E ++ run (16,6) 3 E ++
>         run (18,5) 4 N ++ run (16,4) 2 N ++ run (13,20) 2 E ++
>         run (14,18) 4 E ++ run (20,2) 3 N ++ run (19,3) 2 E ++
>         run (18,4) 2 E ++ run (23,4) 1 E ++ run (22,4) 1 N ++
>         run (21,3) 1 N ++ run (20,4) 2 E ++ run (17,6) 4 N ++ 
>         run (20,7) 2 E ++ run (21,7) 2 N ++ run (21,6) 1 E ++ 
>         run (15,9) 1 E ++ run (17,8) 2 E ++ run (18,7) 2 E ++ 
>         run (19,8) 2 E ++ run (21,9) 1 E ++ run (16,9) 6 N ++
>         run (16,10) 7 N ++ run (15,11) 2 E ++ run (17,11) 5 N ++ 
>         run (14,14) 3 E ++ run (15,15) 6 E ++ run (17,14) 4 E ++
>         run (16,18) 4 E ++ run (15,17) 1 N ++ run (17,17) 3 N ++
>         run (15,13) 7 N ++ run (21,12) 2 E ++ run (16,16) 1 N ++
>         run (16,14) 1 N ++ run (17,15) 3 N ++ run (19,14) 4 N ++
>         run (20,15) 5 E ++ run (19,16) 2 N ++ run (21,16) 5 E ++
>         run (17,19) 2 E ++ run (18,20) 2 E ++ run (19,19) 2 E ++
>         run (18,18) 2 N ++ run (20,20) 3 N
>   in makeMaze (23,22) walls

And now an impossible maze

> impossibleMaze :: Maze
> impossibleMaze =
>   let walls = [((0,1), E), ((1,0),N), ((1,2), E), ((2,1), N)]
>   in makeMaze (3,3) walls