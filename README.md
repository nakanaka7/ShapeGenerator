# ShapeGenerator
Version: 1.0.0(Semantic Versioning 2.0.0)</br>
Java Version: 16</br>
Original Spigot Library: 1.16.5-R0.1-SNAPSHOT</br>
Public API Definition:</br>
  1. The package must be the exported one in module system.
  2. The class must be public.
  3. The class must have "@PublicAPI" annotation
  4. The methods which have "@PrivateAPI" annotations are not public API ones.
  5. (Note) Some methods have "@PublicAPI" annotations in order to distinguish them from private API 	methods, but this is not all the case. No "@PublicAPI" annotation methods in "@PublicAPI" class are 	always public API ones.</br>

Note: The packages, classes(including interfaces etc...), or methods of private API may change without notice.</br>
