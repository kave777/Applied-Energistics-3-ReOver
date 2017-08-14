package appeng.core.skyfall.definitions;

import appeng.api.bootstrap.DefinitionFactory;
import appeng.core.AppEng;
import appeng.core.core.api.bootstrap.BlockItemCustomizer;
import appeng.core.core.api.bootstrap.IBlockBuilder;
import appeng.core.core.api.bootstrap.IItemBuilder;
import appeng.core.core.api.definition.IBlockDefinition;
import appeng.core.core.client.bootstrap.ItemMeshDefinitionComponent;
import appeng.core.lib.definitions.Definitions;
import appeng.core.skyfall.api.definitions.ISkyfallBlockDefinitions;
import appeng.core.skyfall.block.CertusInfusedBlock;
import appeng.core.skyfall.client.CertusInfusedBlockModelComponent;
import appeng.core.skyfall.item.CertusInfusedBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

public class SkyfallBlockDefinitions extends Definitions<Block, IBlockDefinition<Block>> implements ISkyfallBlockDefinitions {

	private final IBlockDefinition<Block> certusInfused;

	public SkyfallBlockDefinitions(DefinitionFactory registry){
		certusInfused = registry.<Block, IBlockDefinition<Block>, IBlockBuilder<Block, ?>, Block>definitionBuilder(new ResourceLocation(AppEng.MODID, "certus_infused"), ih(new CertusInfusedBlock())).createItem(new BlockItemCustomizer<Block, ItemBlock>() {

			@Nonnull
			@Override
			public ItemBlock createItem(IBlockDefinition<Block> block){
				return new CertusInfusedBlockItem((CertusInfusedBlock) block.maybe().get());
			}

			@Nonnull
			@Override
			public IItemBuilder<ItemBlock, ?> customize(@Nonnull IItemBuilder<ItemBlock, ?> builder, @Nonnull IBlockDefinition<Block> block){
				return builder.<ItemMeshDefinitionComponent.BlockStateMapper2ItemMeshDefinition<ItemBlock>>initializationComponent(Side.CLIENT, ItemMeshDefinitionComponent.BlockStateMapper2ItemMeshDefinition.createByMetadata(block.maybe().get()));
			}

		}).initializationComponent(Side.CLIENT, new CertusInfusedBlockModelComponent<>()).build();
	}

	private DefinitionFactory.InputHandler<Block, Block> ih(Block block){
		return new DefinitionFactory.InputHandler<Block, Block>(block) {};
	}
}