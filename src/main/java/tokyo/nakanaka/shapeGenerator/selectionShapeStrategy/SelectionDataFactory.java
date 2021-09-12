package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.SelectionData;

public interface SelectionDataFactory {
	/**
	 * Returns new selection data
	 * @param world a world of the selection data
	 */
	SelectionData newSelectionData(World world);
}
