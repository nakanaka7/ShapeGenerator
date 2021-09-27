package tokyo.nakanaka.shapeGenerator.math.region3D;

/**
 * Represents a hollow regular pyramid. The base is on x-y plane with its center the origin of the plane.
 * It extends to positive z. The x-axis always has outer and inner vertexes of the base polygon, which is the necessary and sufficient information
 *  * to decide other vertexes.
 */
public class HollowRegularPyramid implements Region3D {
    private Region3D outerPyramid;
    private Region3D innerPyramid;

    /**
     * @param outerRadius an outer radius
     * @param innerRadius an inner radius
     * @param vertexNum the vertex number of the base regular polygon
     * @param height a height
     * @throws IllegalArgumentException if the inner radius is smaller than 0, the outer radius is
     * smaller than 0, the inner radius is larger than the outer radius,
     * the vertex number is smaller than 3, or height is smaller than 0
     */
    public HollowRegularPyramid(double outerRadius, double innerRadius, int vertexNum, double height) {
        if(innerRadius < 0 || outerRadius < 0 || innerRadius > outerRadius){
            throw new IllegalArgumentException();
        }
        this.outerPyramid = new RegularPyramid(outerRadius, vertexNum, height);
        double s = height * (1 - innerRadius / outerRadius);
        this.innerPyramid = this.outerPyramid.createShifted(0,0, - s);
    }

    @Override
    public boolean contains(double x, double y, double z) {
        return this.outerPyramid.contains(x, y, z) && ! this.innerPyramid.contains(x, y, z);
    }

}
