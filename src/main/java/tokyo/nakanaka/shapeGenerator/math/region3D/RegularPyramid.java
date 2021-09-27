package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.shapeGenerator.math.region2D.RegularPolygon;

/**
 * Represents a regular pyramid. The base is on x-y plane with its center the origin of the plane.
 * It extends to positive z. The x-axis always has one vertex of the base polygon, which is the necessary and sufficient information
 * to decide other vertexes.
 */
@PublicAPI
public class RegularPyramid implements Region3D {
    private RegularPolygon rp;
    private double height;

    /**
     * @param radius the radius of the base polygon
     * @param vertexNum the vertex number of the base regular polygon
     * @param height the height of the cylinder
     * @throws IllegalArgumentException if radius is smaller than 0, vertex number is smaller than 3, or height is smaller than zero
     */
    public RegularPyramid(double radius, int vertexNum, double height) {
        this.rp = new RegularPolygon(radius, vertexNum);
        if(height < 0) {
            throw new IllegalArgumentException();
        }
        this.height = height;
    }

    @Override
    public boolean contains(double x, double y, double z) {
        if(z < 0 || this.height < z) {
            return false;
        }
        if(z == height && x == 0 && y == 0){
            return true;
        }
        double s = 1 - z / height;
        return this.rp.contains(x / s, y / s);
    }
}
