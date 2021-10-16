package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

/**
 * Holds SelectionShape delegating methods
 */
public interface SelectionShapeStrategy {

	default String clickDescription() {
		return  "";
	}

	/**
	 * Returns a short description for left clicking block event
	 * @return a short description for left clicking block event
	 */
	default String leftClickDescription(){
		return "";
	}
	
	/**
	 * Returns a short description for right clicking block event
	 * @return a short description for right clicking block event
	 */
	default String rightClickDescription(){
		return "";
	}

}
