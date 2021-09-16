module tokyo.nakanaka.shapeGenerator {
	requires transitive NakanakaCommons;
	requires transitive org.bukkit;
	exports tokyo.nakanaka.shapeGenerator;
	exports tokyo.nakanaka.shapeGenerator.bukkit;
	exports tokyo.nakanaka.shapeGenerator.command;
	exports tokyo.nakanaka.shapeGenerator.math.region2D;
	exports tokyo.nakanaka.shapeGenerator.math.region3D;
}