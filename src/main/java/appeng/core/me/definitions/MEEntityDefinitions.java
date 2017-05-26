package appeng.core.me.definitions;

import appeng.api.definitions.IEntityDefinition;
import appeng.core.lib.bootstrap.FeatureFactory;
import appeng.core.lib.definitions.Definitions;
import appeng.core.me.api.definitions.IMEEntityDefinitions;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class MEEntityDefinitions extends Definitions<EntityEntry, IEntityDefinition<EntityEntry>>
		implements IMEEntityDefinitions {

	public MEEntityDefinitions(FeatureFactory factory){
		init();
	}

}