package tokyo.nakanaka.selection;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tokyo.nakanaka.world.World;

public class SelectionManager {
	private Map<SelectionShape, Class<? extends SelectionBuilder>> shapeBuilderMap = new HashMap<>(); 
	private Map<Class<? extends SelectionBuilder>, SelectionShape> builderShapeMap = new HashMap<>();
	
	public void register(SelectionShape shape, Class<? extends SelectionBuilder> builderClass) {
		this.shapeBuilderMap.put(shape, builderClass);
		this.builderShapeMap.put(builderClass, shape);
	}
	
	public Set<SelectionShape> getAllShape(){
		return this.shapeBuilderMap.keySet();
	}
	
	public SelectionShape getShape(SelectionBuilder builder) {
		return this.builderShapeMap.get(builder.getClass());
	}
	
	public SelectionBuilder newInstance(SelectionShape shape, World world) {
		Class<? extends SelectionBuilder> builderClass = this.shapeBuilderMap.get(shape);
		if(builderClass == null) {
			throw new IllegalArgumentException();
		}
		try {
			return builderClass.getDeclaredConstructor(World.class).newInstance(world);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
}
