package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.math.BlockVector3D;

public interface LeftClickHandler {
    /**
     * Update the selection data on left block click
     * @param selData the selection data
     * @param blockPos the block position
     * @throws IllegalStateException if this click operation cannot be handled
     */
    void onLeftClick(SelectionData selData, BlockVector3D blockPos);

}
