package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.math.BlockVector3D;

public interface RightClickHandler {

    /**
     * Update the selection data on right block click
     * @param selData the selection data
     * @param blockPos the block position
     * @throws IllegalStateException if this click operation cannot be handled
     */
    void onRightClick(SelectionData selData, BlockVector3D blockPos);


}
