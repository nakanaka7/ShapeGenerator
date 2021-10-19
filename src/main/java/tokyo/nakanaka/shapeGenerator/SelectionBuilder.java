package tokyo.nakanaka.shapeGenerator;

public interface SelectionBuilder {

    /**
     * Returns a selection from the selection data
     * @param selData a selection data
     * @return a selection from the selection data
     */
    Selection buildSelection(SelectionData selData);

}
