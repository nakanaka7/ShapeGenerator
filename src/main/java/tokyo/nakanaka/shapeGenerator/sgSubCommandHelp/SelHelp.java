package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;

public class SelHelp implements CommandHelp {
	private String usage = "/sg sel <subcommand>";
	private String description = "Specify the selection";
	private LogColor color = LogColor.GOLD;

	@Override
	public String syntax() {
		return usage;
	}

	@Override
	public String description() {
		return description;
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
		lines.add(color + "Usage: " + LogColor.RESET + this.usage);
		lines.add(color + "Parameter: ");
		lines.add(color + "  <subcommand>: ");
		lines.add(LogColor.WHITE + "    (common)");
		lines.add(color + "    offset [x] [y] [z]: " + LogColor.RESET + "set the offset of the selection");
		lines.add(color + "    reset: " + LogColor.RESET + "reset the current selection");
		lines.add(LogColor.WHITE + "    (cuboid)");
		lines.add(color + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(color + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(LogColor.WHITE + "    (diamond)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(color + "    width <length>: " + LogColor.RESET + "set width(distance along x)");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height(distance along y)");
		lines.add(color + "    length <length>: " + LogColor.RESET + "set length(distance along z)");
		lines.add(LogColor.WHITE + "    (sphere)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(color + "    radius <length>: " + LogColor.RESET + "set radius");
		lines.add(LogColor.WHITE + "    (cylinder)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(color + "    radius <length>: " + LogColor.RESET + "set radius of base disc");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(color + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (cone)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(color + "    radius <length>: " + LogColor.RESET + "set radius of base disc");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(color + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (torus)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(color + "    major_radius <length>: " + LogColor.RESET + "set major radius");
		lines.add(color + "    minor_radius <length>: " + LogColor.RESET + "set minor radius");
		lines.add(color + "    axis x|y|z: " + LogColor.RESET + "set axis direction");
		lines.add(LogColor.WHITE + "    (line)");
		lines.add(color + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(color + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(color + "    thickness <length>: " + LogColor.RESET + "set thickness");
		lines.add(LogColor.WHITE + "    (triangle)");
		lines.add(color + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(color + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(color + "    pos3 [x] [y] [z]: " + LogColor.RESET + "set pos3");
		lines.add(color + "    thickness <length>: " + LogColor.RESET + "set thickness");
		lines.add(LogColor.WHITE + "    (tetrahedron)");
		lines.add(color + "    pos1 [x] [y] [z]: " + LogColor.RESET + "set pos1");
		lines.add(color + "    pos2 [x] [y] [z]: " + LogColor.RESET + "set pos2");
		lines.add(color + "    pos3 [x] [y] [z]: " + LogColor.RESET + "set pos3");
		lines.add(color + "    pos4 [x] [y] [z]: " + LogColor.RESET + "set pos4");
		lines.add(LogColor.WHITE + "    (regular_prism)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
		lines.add(color + "    radius <length>: " + LogColor.RESET + "set radius of base regular polygon");
		lines.add(color + "    side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(color + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (hollow_sphere)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(color + "    outer_radius <length>: " + LogColor.RESET + "set outer radius");
		lines.add(color + "    inner_radius <length>: " + LogColor.RESET + "set inner radius");
		lines.add(LogColor.WHITE + "    (hollow_cylinder)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(color + "    outer_radius <length>: " + LogColor.RESET + "set outer radius of base disc");
		lines.add(color + "    inner_radius <length>: " + LogColor.RESET + "set inner radius of base disc");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(color + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (hollow_cone)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base disc");
		lines.add(color + "    outer_radius <length>: " + LogColor.RESET + "set outer radius of base disc");
		lines.add(color + "    inner_radius <length>: " + LogColor.RESET + "set inner radius of base disc");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(color + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		lines.add(LogColor.WHITE + "    (hollow_torus)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center");
		lines.add(color + "    major_radius <length>: " + LogColor.RESET + "set major radius");
		lines.add(color + "    outer_minor_radius <length>: " + LogColor.RESET + "set outer minor radius");
		lines.add(color + "    inner_minor_radius <length>: " + LogColor.RESET + "set inner minor radius");
		lines.add(color + "    axis x|y|z: " + LogColor.RESET + "set axis direction");
		lines.add(LogColor.WHITE + "    (hollow_regular_prism)");
		lines.add(color + "    center [x] [y] [z]: " + LogColor.RESET + "set center of base regular polygon");
		lines.add(color + "    outer_radius <length>: " + LogColor.RESET + "set outer radius of base regular polygon");
		lines.add(color + "    inner_radius <length>: " + LogColor.RESET + "set inner radius of base regular polygon");
		lines.add(color + "    side <number>: " + LogColor.RESET + "set side numbers of regular polygon");
		lines.add(color + "    height <length>: " + LogColor.RESET + "set height");
		lines.add(color + "    direction north|south|east|west|up|down: " + LogColor.RESET + "set direction");
		return lines;
	}

}
