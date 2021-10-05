package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;

@PrivateAPI
public interface BranchCommandHandler extends SubCommandHandler {
    ParameterUsage[] parameterUsages();
}
