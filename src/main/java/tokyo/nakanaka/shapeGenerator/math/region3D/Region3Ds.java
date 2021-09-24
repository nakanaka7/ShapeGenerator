package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

@PrivateAPI
public class Region3Ds {
	private Region3Ds() {
	}
	
	public static Region3D and(Region3D region1, Region3D region2) {
		return new LogicalConjunctRegion3D(region1, region2);
	}
	
	public static Region3D or(Region3D region1, Region3D region2) {
		return new LogicalDisjunctRegion3D(region1, region2);
	}
	
	public static Region3D shift(Region3D original, Vector3D displacement) {
		if(original instanceof AffineTransformedRegion3D) {
			AffineTransformedRegion3D affReg = (AffineTransformedRegion3D) original;
			Region3D newOriginal = affReg.originalRegion3D();
			LinearTransformation newTrans = affReg.linearTransformation();
			Vector3D newOffset = affReg.displacement().add(displacement);
			return new AffineTransformedRegion3D(newOriginal, newTrans, newOffset);
		}else {
			return new AffineTransformedRegion3D(original, LinearTransformation.IDENTITY, displacement);
		}
	}
	
	public static Region3D linearTransform(Region3D original, LinearTransformation trans) {
		if(original instanceof AffineTransformedRegion3D) {
			AffineTransformedRegion3D affReg = (AffineTransformedRegion3D) original;
			Vector3D offset = affReg.displacement();
			if(offset.equals(Vector3D.ORIGIN)) {
				Region3D newOriginal = affReg.originalRegion3D();
				LinearTransformation newTrans = trans.multipy(affReg.linearTransformation());
				return new AffineTransformedRegion3D(newOriginal, newTrans, Vector3D.ORIGIN);
			}
		}
		return new AffineTransformedRegion3D(original, trans, Vector3D.ORIGIN);
	}
	
	public static Region3D linearTransform(Region3D original, LinearTransformation trans, Vector3D offset) {
		Region3D region = Region3Ds.shift(original, offset.multiply(-1));
		region = Region3Ds.linearTransform(region, trans);
		return Region3Ds.shift(region, offset);
	}
	
}
