package tokyo.nakanaka.shapeGenerator;

import java.util.List;

import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.annotation.PublicAPI;

/**
 * An interface which has one method to return a list of block IDs
 */
@PublicAPI
public interface BlockIDListFactory {
	/**
	 * Returns a list of block IDs
	 * @return a list of block IDs
	 */
	List<NamespacedID> getBlockIDList();
	
}
