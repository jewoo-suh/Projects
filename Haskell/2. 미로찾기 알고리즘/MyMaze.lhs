Module to define the type of a maze

> module MyMaze (
>   module MyMaze
> )
> where

> import Geography
> import Maze

> data MyMaze = AMaze Size [Place] [Place] [Place] [Place]

Order of N S E W

> mazeToMyMaze :: Maze -> MyMaze
> mazeToMyMaze maze = AMaze (sizeOf maze) (getListOfPlaces maze N) (getListOfPlaces maze S) (getListOfPlaces maze E) (getListOfPlaces maze W) 

> getListOfPlaces :: Maze -> Direction -> [Place] 
> getListOfPlaces maze d = filter (/= (-1,-1)) [if (hasWall maze (x,y) d) then (x,y) else (-1,-1) | x <- [0..(fst(sizeOf maze)-1)], y <- [0..(snd(sizeOf maze)-1)]]