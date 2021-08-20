package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.shapeGenerator.user.User;

public class PhyCommandHandler implements UserCommandHandler{

	@Override
	public String getLabel() {
		return "phy";
	}
	
	@Override
	public String getDescription() {
		return "Toggle physics option for generating blocks";
	}

	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, new String[] {"true", "false"}, ""));
		return list;
	}
	
	@Override
	public boolean onCommand(User user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length != 1) {
			return false;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			return false;
		}
		user.setBlockPhysics(physics);
		if(physics) {
			logger.print(LogDesignColor.NORMAL + "Set physics -> " + bool);
		}else {
			logger.print(LogDesignColor.NORMAL+ "Set physics -> " + bool);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(User user, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("true", "false");
		}else {
			return new ArrayList<>();
		}
	}

}