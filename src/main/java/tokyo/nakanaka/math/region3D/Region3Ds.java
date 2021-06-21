package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

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
			Region3D newOriginal = affReg.getOriginalRegion3D();
			LinearTransformation newTrans = affReg.getLinearTransformation();
			Vector3D newOffset = affReg.getOffset().add(displacement);
			return new AffineTransformedRegion3D(newOriginal, newTrans, newOffset);
		}else {
			return new AffineTransformedRegion3D(original, LinearTransformation.IDENTITY, displacement);
		}
	}
	
	public static Region3D linearTransform(Region3D original, LinearTransformation trans) {
		return new AffineTransformedRegion3D(original, trans, Vector3D.ORIGIN);
	}
	
}
