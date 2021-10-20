package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.commandHelp.*;

import java.util.HashMap;
import java.util.Map;

public class SelHelp implements CommandHelp {
	private Map<SelectionShape, BranchCommandHelp[]> shapeHelpMap = new HashMap<>();

	public SelHelp(){
		var cuboidHelps = new BranchCommandHelp[]{
				CuboidCommandHelps.POS1,
				CuboidCommandHelps.POS2,
		};
		shapeHelpMap.put(SelectionShape.CUBOID, cuboidHelps);
		var diamondHelps = new BranchCommandHelp[]{
				DiamondCommandHelps.CENTER,
				DiamondCommandHelps.WIDTH,
				DiamondCommandHelps.HEIGHT,
				DiamondCommandHelps.LENGTH
		};
		shapeHelpMap.put(SelectionShape.DIAMOND, diamondHelps);
		var sphereHelps = new BranchCommandHelp[]{
				SphereCommandHelps.CENTER,
				SphereCommandHelps.RADIUS
		};
		shapeHelpMap.put(SelectionShape.SPHERE, sphereHelps);
		var cylinderHelps = new BranchCommandHelp[]{
				CylinderCommandHelps.CENTER,
				CylinderCommandHelps.RADIUS,
				CylinderCommandHelps.HEIGHT,
				CylinderCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.CYLINDER, cylinderHelps);
		var coneHelps = new BranchCommandHelp[]{
				ConeCommandHelps.CENTER,
				ConeCommandHelps.RADIUS,
				ConeCommandHelps.HEIGHT,
				ConeCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.CONE, coneHelps);
		var torusHelps = new BranchCommandHelp[]{
				TorusCommandHelps.CENTER,
				TorusCommandHelps.MAJOR_RADIUS,
				TorusCommandHelps.MINOR_RADIUS,
				TorusCommandHelps.AXIS
		};
		shapeHelpMap.put(SelectionShape.TORUS, torusHelps);
		var lineHelps = new BranchCommandHelp[]{
				LineCommandHelps.POS1,
				LineCommandHelps.POS2,
				LineCommandHelps.THICKNESS
		};
		shapeHelpMap.put(SelectionShape.LINE, lineHelps);
		var triangleHelps = new BranchCommandHelp[]{
				TriangleCommandHelps.POS1,
				TriangleCommandHelps.POS2,
				TriangleCommandHelps.POS3,
				TriangleCommandHelps.THICKNESS
		};
		shapeHelpMap.put(SelectionShape.TRIANGLE, triangleHelps);
		var tetrahedronHelps = new BranchCommandHelp[]{
				TetrahedronCommandHelps.POS1,
				TetrahedronCommandHelps.POS2,
				TetrahedronCommandHelps.POS3,
				TetrahedronCommandHelps.POS4
		};
		shapeHelpMap.put(SelectionShape.TETRAHEDRON, tetrahedronHelps);
		var regularPrismHelps = new BranchCommandHelp[]{
				RegularPrismCommandHelps.CENTER,
				RegularPrismCommandHelps.RADIUS,
				RegularPrismCommandHelps.SIDE,
				RegularPrismCommandHelps.HEIGHT,
				RegularPrismCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.REGULAR_PRISM, regularPrismHelps);
		var regularPyramidHelps = new BranchCommandHelp[]{
				RegularPyramidCommandHelps.CENTER,
				RegularPyramidCommandHelps.RADIUS,
				RegularPyramidCommandHelps.SIDE,
				RegularPyramidCommandHelps.HEIGHT,
				RegularPyramidCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.REGULAR_PYRAMID, regularPyramidHelps);
		var hollowSphereHelps = new BranchCommandHelp[]{
				HollowSphereCommandHelps.CENTER,
				HollowSphereCommandHelps.OUTER_RADIUS,
				HollowSphereCommandHelps.INNER_RADIUS
		};
		shapeHelpMap.put(SelectionShape.HOLLOW_SPHERE, hollowSphereHelps);
		var hollowCylinderHelps = new BranchCommandHelp[]{
				HollowCylinderCommandHelps.CENTER,
				HollowCylinderCommandHelps.OUTER_RADIUS,
				HollowCylinderCommandHelps.INNER_RADIUS,
				HollowCylinderCommandHelps.HEIGHT,
				HollowCylinderCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.HOLLOW_CYLINDER, hollowCylinderHelps);
		var hollowConeHelps = new BranchCommandHelp[]{
				HollowConeCommandHelps.CENTER,
				HollowConeCommandHelps.OUTER_RADIUS,
				HollowConeCommandHelps.INNER_RADIUS,
				HollowConeCommandHelps.HEIGHT,
				HollowConeCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.HOLLOW_CONE, hollowConeHelps);
		var hollowTorusHelps = new BranchCommandHelp[]{
				HollowTorusCommandHelps.CENTER,
				HollowTorusCommandHelps.MAJOR_RADIUS,
				HollowTorusCommandHelps.OUTER_MINOR_RADIUS,
				HollowTorusCommandHelps.INNER_MINOR_RADIUS,
				HollowTorusCommandHelps.AXIS
		};
		shapeHelpMap.put(SelectionShape.HOLLOW_TORUS, hollowTorusHelps);
		var hollowRegularPrismHelps = new BranchCommandHelp[]{
				HollowRegularPrismCommandHelps.CENTER,
				HollowRegularPrismCommandHelps.OUTER_RADIUS,
				HollowRegularPrismCommandHelps.INNER_RADIUS,
				HollowRegularPrismCommandHelps.SIDE,
				HollowRegularPrismCommandHelps.HEIGHT,
				HollowRegularPrismCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.HOLLOW_REGULAR_PRISM, hollowRegularPrismHelps);
		var hollowRegularPyramidHelps = new BranchCommandHelp[]{
				HollowRegularPyramidCommandHelps.CENTER,
				HollowRegularPyramidCommandHelps.OUTER_RADIUS,
				HollowRegularPyramidCommandHelps.INNER_RADIUS,
				HollowRegularPyramidCommandHelps.SIDE,
				HollowRegularPyramidCommandHelps.HEIGHT,
				HollowRegularPyramidCommandHelps.DIRECTION
		};
		shapeHelpMap.put(SelectionShape.HOLLOW_REGULAR_PYRAMID, hollowRegularPyramidHelps);
	}

	@Override
	public String description() {
		return "Specify the selection";
	}

	@Override
	public String[] parameterSyntaxes() {
		return new String[]{"<subcommand>"};
	}

	/**
	 * Returns subcommand helps. The subcommands are independent of the selection shape
	 * @return subcommand helps. The subcommands are independent of the selection shape
	 *
	 */
	public BranchCommandHelp[] commonSubcommandHelps(){
		return new BranchCommandHelp[]{
				SelSubCommonHelps.OFFSET,
				SelSubCommonHelps.RESET};
	}

	/**
	 * Returns subcommand helps. The subcommands are dependent of the selection shape
	 * @param shape a selection shape
	 * @return subcommand helps. The subcommands are dependent of the selection shape
	 */
	public BranchCommandHelp[] shapeSubcommandHelps(SelectionShape shape){
		BranchCommandHelp[] helps = this.shapeHelpMap.get(shape);
		if(helps != null){
			return helps;
		}else{
			return new BranchCommandHelp[]{};
		}
	}

}
