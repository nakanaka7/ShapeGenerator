package tokyo.nakanaka.shapeGenerator.math.region2D;

import tokyo.nakanaka.annotation.PublicAPI;

@PublicAPI
public interface Region2D {
	boolean contains(double x, double y);
}
