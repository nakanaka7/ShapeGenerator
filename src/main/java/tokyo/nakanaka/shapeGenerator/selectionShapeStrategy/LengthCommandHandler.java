package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public abstract class LengthCommandHandler extends BaseSelSubCommandHandler<Double> {
	
	public LengthCommandHandler(String subLabel) {
		super("/sg sel " + subLabel + " <length>");
	}
		
	protected abstract RegionData newRegionData();
	protected abstract void setLength(RegionData regData, Double length);
	
	
	protected Double parse(Player player, String[] args) {
		return switch(args.length) {
		case 1 -> Double.parseDouble(args[0]);
		default -> throw new IllegalArgumentException();
		};
	}
	
	protected void setParsedValue(RegionData regData, Double parsed) {
		this.setLength(regData, (Double)parsed);
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
					"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0");
			default -> List.of();
		};
	}
	
}
