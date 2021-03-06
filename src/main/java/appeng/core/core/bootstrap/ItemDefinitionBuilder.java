package appeng.core.core.bootstrap;

import appeng.api.bootstrap.DefinitionFactory;
import appeng.api.bootstrap.IDefinitionBuilder;
import appeng.core.core.api.definition.IItemDefinition;
import appeng.api.item.IStateItem;
import appeng.core.core.api.bootstrap.IItemBuilder;
import appeng.core.core.AppEngCore;
import appeng.core.core.definition.ItemSubDefinition;
import appeng.core.lib.bootstrap.DefinitionBuilder;
import appeng.core.core.definition.ItemDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;

public class ItemDefinitionBuilder<I extends Item> extends DefinitionBuilder<I, I, IItemDefinition<I>, ItemDefinitionBuilder<I>> implements IItemBuilder<I, ItemDefinitionBuilder<I>> {

	//TODO 1.11.2-ReOver - Be back?
	/*@SideOnly(Side.CLIENT)
	private ItemRendering itemRendering;*/

	public ItemDefinitionBuilder(DefinitionFactory factory, ResourceLocation registryName, I item){
		super(factory, registryName, item, "item");
		/*if(Platform.isClient()){
			itemRendering = new ItemRendering();
		}*/
	}

	@Override
	public ItemDefinitionBuilder<I> defaultModel(){
		return this.<IDefinitionBuilder.DefinitionInitializationComponent.PreInit<I, IItemDefinition<I>>>initializationComponent(Side.CLIENT, def -> AppEngCore.proxy.acceptModelRegisterer(() -> ModelLoader.setCustomModelResourceLocation(def.maybe().get(), 0, new ModelResourceLocation(def.identifier(), "inventory"))));
	}

	/*@SideOnly(Side.CLIENT)
	private void customizeForClient(ItemRenderingCustomizer callback){
		callback.customize(itemRendering);
	}*/

	@Override
	public IItemDefinition<I> def(I item){
		if(item == null) return new ItemDefinition<>(registryName, null);
		/*if(Platform.isClient()){
			itemRendering.apply(factory, item);
		}*/

		if(item.getUnlocalizedName().equals("item.null")) item.setUnlocalizedName(registryName.getResourceDomain() + "." + registryName.getResourcePath());

		ItemDefinition<I> definition = new ItemDefinition<>(registryName, item);

		if(item instanceof IStateItem) definition.setSubDefinition(() -> new ItemSubDefinition(((IStateItem) item).getDefaultState(), definition));

		return definition;
	}
}
