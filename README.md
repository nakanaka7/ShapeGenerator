# ShapeGenerator
Version: 0.1.0(Semantic Versioning 2.0.0)</br>
Public API definition:
  1. The package must be the exported one in module system.
  2. The class must be public.
  3. The class must has "@PublicAPI" annotation
  4. The methods which has "@PrivateAPI" annotation is not public API methods.
  5. (Note) Some methods have "@PublicAPI" annotations in order to distinguish them from private API 	methods, but this is not all the case. No "@PublicAPI" annotation methods in "@PublicAPI" class are 	always public API methods.
  
Note: The package, class(including interface etc...), or methods of private API may be change without notice.