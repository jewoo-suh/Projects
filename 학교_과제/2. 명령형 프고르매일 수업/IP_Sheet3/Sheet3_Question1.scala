/*
Part a: 

	If the array a is not increasing, the binary search will return an index(denoted as 'i') within 
	the array 'a' such that for a given input of 'X', "a(i-1) < X <= a(i)". However, this does not 
	have any significant meaning as the array is not sorted -> There could be multiple instances of
	'i' such that "a(i-1) < X <= a(i)", many undetectable by the binary search. For example, 

	search(Array(5,7,4,5,3,9), 6) returns 'i=5 -> a(4)=3 < X=6 < a(5)=9' but there are other cases
	such as 'i=1 -> a(0)=5 < X=6 < a(1)=7' were the conditions are met. 

	Thus binary search with an unsorted array will return an index such that "a(i-1) < X <= a(i)" holds
	true, but this may not be unique.

Part b: 
	
	The while loop does not run any instances, as the condition i<j = 0<0 = False. Thus, the function
	directly returns i=0, and the final return value is 0. 

Part c: 




*/

