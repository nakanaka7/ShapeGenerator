package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class MainFunctions {
	public static void setDefaultSelection(SelectionStrategySource selStrtgSource, UserData user) {
		SelectionShapeNew shape = selStrtgSource.getDefaultSelectionShape();
		user.setSelectionShapeNew(shape);
		RegionBuildingData regionData = selStrtgSource.get(shape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(user.getWorld(), regionData);
		user.setSelectionBuildingData(selData);
	}
}
