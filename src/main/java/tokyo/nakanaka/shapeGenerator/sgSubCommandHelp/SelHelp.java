package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelHelp implements CommandHelp {
	private String usage = "sel <subcommand>";
	private String description = "Specify the selection";
	private LogColor color = LogColor.GOLD;
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

	/**
	 * Return multiple lines which contains the information for the command
	 * This is used by HelpCommandHandler class
	 * @return multiple lines which contains the information for the command
	 */
	public List<String> toMultipleLines(SelectionShape shape) {
		List<String> lines = new ArrayList<>();
		lines.add(MessageUtils.title(color + "Help for " + LogColor.RESET + "/sg sel"));
		lines.add(color + "Description: " + LogColor.RESET + this.description);
		lines.add(color + "Usage: " + LogColor.RESET + "/sg sel " + color + "<subcommand>");
		lines.add(color + "Subcommand: ");
		lines.add(color + "  offset [x] [y] [z]: " + LogColor.RESET + "set the offset of the selection");
		lines.add(color + "  reset: " + LogColor.RESET + "reset the current selection");
		switch (shape) {
			case CUBOID -> {
				lines.add(color + "  pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
				lines.add(color + "  pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
			}
			case DIAMOND -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center");
				lines.add(color + "  width <length>: " + LogColor.RESET + "set width(distance along x)");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height(distance along y)");
				lines.add(color + "  length <length>: " + LogColor.RESET + "set length(distance along z)");
			}
			case SPHERE -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center");
				lines.add(color + "  radius <length>: " + LogColor.RESET + "set radius");
			}
			case CYLINDER -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
				lines.add(color + "  radius <length>: " + LogColor.RESET + "set radius of base disc");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case CONE -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
				lines.add(color + "  radius <length>: " + LogColor.RESET + "set radius of base disc");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case TORUS -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center");
				lines.add(color + "  major_radius <length>: " + LogColor.RESET + "set major radius");
				lines.add(color + "  minor_radius <length>: " + LogColor.RESET + "set minor radius");
				lines.add(color + "  axis x|y|z: " + LogColor.RESET + "set axis direction");
			}
			case LINE -> {
				lines.add(color + "  pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
				lines.add(color + "  pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
				lines.add(color + "  thickness <length>: " + LogColor.RESET + "set thickness");
			}
			case TRIANGLE -> {
				lines.add(color + "  pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
				lines.add(color + "  pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
				lines.add(color + "  pos3 [x] [y] [z]: " + LogColor.RESET + "set pos3");
				lines.add(color + "  thickness <length>: " + LogColor.RESET + "set thickness");
			}
			case TETRAHEDRON -> {
				lines.add(color + "  pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
				lines.add(color + "  pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
				lines.add(color + "  pos3 [x] [y] [z]: " + LogColor.RESET + "set pos3");
				lines.add(color + "  pos4 [x] [y] [z]: " + LogColor.RESET + "set pos4");
			}
			case REGULAR_PRISM -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
				lines.add(color + "  radius <length>: " + LogColor.RESET + "set radius of base regular polygon");
				lines.add(color + "  side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case REGULAR_PYRAMID -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
				lines.add(color + "  radius <length>: " + LogColor.RESET + "set radius of base regular polygon");
				lines.add(color + "  side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case HOLLOW_SPHERE -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center");
				lines.add(color + "  outer_radius <length>: " + LogColor.RESET + "set outer radius");
				lines.add(color + "  inner_radius <length>: " + LogColor.RESET + "set inner radius");
			}
			case HOLLOW_CYLINDER -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
				lines.add(color + "  outer_radius <length>: " + LogColor.RESET + "set outer radius of base disc");
				lines.add(color + "  inner_radius <length>: " + LogColor.RESET + "set inner radius of base disc");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case HOLLOW_CONE -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
				lines.add(color + "  outer_radius <length>: " + LogColor.RESET + "set outer radius of base disc");
				lines.add(color + "  inner_radius <length>: " + LogColor.RESET + "set inner radius of base disc");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case HOLLOW_TORUS -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center");
				lines.add(color + "  major_radius <length>: " + LogColor.RESET + "set major radius");
				lines.add(color + "  outer_minor_radius <length>: " + LogColor.RESET + "set outer minor radius");
				lines.add(color + "  inner_minor_radius <length>: " + LogColor.RESET + "set inner minor radius");
				lines.add(color + "  axis x|y|z: " + LogColor.RESET + "set axis direction");
			}
			case HOLLOW_REGULAR_PRISM -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
				lines.add(color + "  outer_radius <length>: " + LogColor.RESET + "set outer radius of base regular polygon");
				lines.add(color + "  inner_radius <length>: " + LogColor.RESET + "set inner radius of base regular polygon");
				lines.add(color + "  side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
			case HOLLOW_REGULAR_PYRAMID -> {
				lines.add(color + "  center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
				lines.add(color + "  outer_radius <length>: " + LogColor.RESET + "set outer radius of base regular polygon");
				lines.add(color + "  inner_radius <length>: " + LogColor.RESET + "set inner radius of base regular polygon");
				lines.add(color + "  side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
				lines.add(color + "  height <length>: " + LogColor.RESET + "set height");
				lines.add(color + "  direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
			}
		}
		return lines;
	}

}
