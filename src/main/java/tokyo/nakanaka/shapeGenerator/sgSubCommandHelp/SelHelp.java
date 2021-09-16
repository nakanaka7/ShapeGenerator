package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;

public class SelHelp implements CommandHelp {
	private String usage = "/sg sel <subcommand>";
	private String description = "Specify the selection";

	/**
	 * Get the usage
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	@Override
	public String toSingleLine() {
		return LogColor.GOLD + this.usage + ": " + LogColor.RESET + this.description;
	}

	@Override
	public List<String> toMultipleLines() {
		List<String> lines = new ArrayList<>();
		lines.add("--- [" + LogColor.GOLD + "Help for " + LogColor.RESET + "/sg sel] ---------------------");
		lines.add(LogColor.GOLD + "Description: " + LogColor.RESET + this.description);
		lines.add(LogColor.GOLD + "Usage: " + LogColor.RESET + this.usage);
		lines.add(LogColor.GOLD + "Parameter: ");
		lines.add(LogColor.GOLD + "  <subcommand>: ");
		lines.add(LogColor.WHITE + "    (common)");
		lines.add(LogColor.GOLD + "    offset [x] [y] [z]: " + LogColor.RESET + "set the offset of the selection");
		lines.add(LogColor.GOLD + "    reset: " + LogColor.RESET + "reset the current selection");
		lines.add(LogColor.WHITE + "    (cuboid)");
		lines.add(LogColor.GOLD + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(LogColor.GOLD + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(LogColor.WHITE + "    (diamond)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(LogColor.GOLD + "    width <length>: " + LogColor.RESET + "set width(distance along x)");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height(distance along y)");
		lines.add(LogColor.GOLD + "    length <length>: " + LogColor.RESET + "set length(distance along z)");
		lines.add(LogColor.WHITE + "    (sphere)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(LogColor.GOLD + "    radius <length>: " + LogColor.RESET + "set radius");
		lines.add(LogColor.WHITE + "    (cylinder)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(LogColor.GOLD + "    radius <length>: " + LogColor.RESET + "set radius of base disc");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(LogColor.GOLD + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (cone)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(LogColor.GOLD + "    radius <length>: " + LogColor.RESET + "set radius of base disc");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(LogColor.GOLD + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (torus)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(LogColor.GOLD + "    major_radius <length>: " + LogColor.RESET + "set major radius");
		lines.add(LogColor.GOLD + "    minor_radius <length>: " + LogColor.RESET + "set minor radius");
		lines.add(LogColor.GOLD + "    axis x|y|z: " + LogColor.RESET + "set axis direction");
		
		lines.add(LogColor.WHITE + "    (line)");
		lines.add(LogColor.GOLD + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(LogColor.GOLD + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(LogColor.GOLD + "    thickness <length>: " + LogColor.RESET + "set thickness");
		lines.add(LogColor.WHITE + "    (triangle)");
		lines.add(LogColor.GOLD + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(LogColor.GOLD + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(LogColor.GOLD + "    pos3 [x] [y] [z]: " + LogColor.RESET + "set pos3");
		lines.add(LogColor.GOLD + "    thickness <length>: " + LogColor.RESET + "set thickness");
		lines.add(LogColor.WHITE + "    (tetrahedron)");
		lines.add(LogColor.GOLD + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(LogColor.GOLD + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(LogColor.GOLD + "    pos3 [x] [y] [z]: " + LogColor.RESET + "set pos3");
		lines.add(LogColor.GOLD + "    pos4 [x] [y] [z]: " + LogColor.RESET + "set pos4");
		lines.add(LogColor.WHITE + "    (regular_prism)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
		lines.add(LogColor.GOLD + "    radius <length>: " + LogColor.RESET + "set radius of base regular polygon");
		lines.add(LogColor.GOLD + "    side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(LogColor.GOLD + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (hollow_sphere)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(LogColor.GOLD + "    outer_radius <length>: " + LogColor.RESET + "set outer radius");
		lines.add(LogColor.GOLD + "    inner_radius <length>: " + LogColor.RESET + "set inner radius");
		lines.add(LogColor.WHITE + "    (hollow_cylinder)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(LogColor.GOLD + "    outer_radius <length>: " + LogColor.RESET + "set outer radius of base disc");
		lines.add(LogColor.GOLD + "    inner_radius <length>: " + LogColor.RESET + "set inner radius of base disc");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(LogColor.GOLD + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (hollow_cone)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(LogColor.GOLD + "    outer_radius <length>: " + LogColor.RESET + "set outer radius of base disc");
		lines.add(LogColor.GOLD + "    inner_radius <length>: " + LogColor.RESET + "set inner radius of base disc");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height");	
		lines.add(LogColor.GOLD + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (hollow_torus)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(LogColor.GOLD + "    major_radius <length>: " + LogColor.RESET + "set major radius");
		lines.add(LogColor.GOLD + "    outer_minor_radius <length>: " + LogColor.RESET + "set outer minor radius");
		lines.add(LogColor.GOLD + "    inner_minor_radius <length>: " + LogColor.RESET + "set inner minor radius");
		lines.add(LogColor.GOLD + "    axis x|y|z: " + LogColor.RESET + "set axis direction");
		lines.add(LogColor.WHITE + "    (hollow_regular_prism)");
		lines.add(LogColor.GOLD + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
		lines.add(LogColor.GOLD + "    outer_radius <length>: " + LogColor.RESET + "set outer radius of base regular polygon");
		lines.add(LogColor.GOLD + "    inner_radius <length>: " + LogColor.RESET + "set inner radius of base regular polygon");
		lines.add(LogColor.GOLD + "    side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
		lines.add(LogColor.GOLD + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(LogColor.GOLD + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");	
		return lines;
	}

}
