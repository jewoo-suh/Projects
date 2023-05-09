/*
	The reason linked lists are used above other data structures (such as arrays) is because, unlike arrays and 
	other structures, linked lists do not have a predetermined size. This allows for more flexibility when more
	'buckets' or 'elements' within buckets need to be added. 

	There are potential cases where arrays or other structures could be preferred over linked lists; for example, 
	if we were to define a hash function that maps a value 'x' at the hashcode 'x%10', there would be 10 and only 10
	possible hashcodes; in this case, using an array for the buckets could be more efficient for speed and memory.

	However, in the general case, it is hard to assume that either the number of buckets or elements will be fixed, 
	thus linked lists, which are flexible in size, are used. 
*/