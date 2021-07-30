package tokyo.nakanaka;

import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class MainFunctions {
	public static void setDefaultSelection(SelectionStrategySource selStrtgSource, Player player) {
		SelectionShape shape = selStrtgSource.getDefaultSelectionShape();
		player.setSelectionShape(shape);
		RegionBuildingData regionData = selStrtgSource.get(shape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(player.getWorld(), regionData);
		player.setSelectionBuildingData(selData);
	}
}
