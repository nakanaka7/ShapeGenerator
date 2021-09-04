package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.SphereRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TetrahedronRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TriangleRegionData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ShapeHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg shape" command
 */
public class ShapeCommandHandler implements SgSubCommandHandler {

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " + new ShapeHelp().getUsage());
			return;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid shape");
			return;
		}
		SelectionShape original = userData.getSelectionShape();
		if(shape != original) {
			userData.setSelectionShape(shape);
			RegionData regData = switch(shape) {
				case CUBOID -> new CuboidRegionData();
				case DIAMOND -> new DiamondRegionData();
				case SPHERE -> new SphereRegionData();
				case TORUS -> new TorusRegionData();
				case LINE -> new LineRegionData();
				case TRIANGLE -> new TriangleRegionData();
				case TETRAHEDRON -> new TetrahedronRegionData();
				case REGULAR_POLYGON -> new RegularPolygonRegionData();		
			};
			World world = player.getEntityPosition().world();
			SelectionData selData = new SelectionData(world, regData);
			userData.setSelectionData(selData);
		}
		player.print(LogColor.GOLD + "Set the shape -> " + shape);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of(SelectionShape.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
			default -> List.of();
		};
	}
	
}
