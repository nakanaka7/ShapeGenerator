package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.World;

public interface InitialSelectionDataCreator {
    /**
     * Returns new selection data
     * @param world a world
     * @return new selection data
     */
    SelectionData newSelectionData(World world);
}
