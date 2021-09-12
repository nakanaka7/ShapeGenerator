package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

public class MaxMinCalculator {
	private MaxMinCalculator() {
	}
	
	public static double max(double... nums) {
		if(nums.length == 0) {
			throw new IllegalArgumentException();
		}
		double max = nums[0];
		for(double num : nums) {
			if(num > max) {
				max = num;
			}
		}
		return max;
	}
	
	public static double min(double... nums) {
		if(nums.length == 0) {
			throw new IllegalArgumentException();
		}
		double min = nums[0];
		for(double num : nums) {
			if(num < min) {
				min = num;
			}
		}
		return min;
	}
}
