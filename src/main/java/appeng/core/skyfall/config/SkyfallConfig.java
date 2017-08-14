package appeng.core.skyfall.config;

import appeng.core.AppEng;
import appeng.core.lib.util.BlockState2String;
import appeng.core.skyfall.api.generator.SkyobjectGenerator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;
import java.util.stream.Collectors;

public class SkyfallConfig {

	private Map<ResourceLocation, Float> weights = new HashMap<>();

	public Meteorite meteorite = new Meteorite();

	public SkyfallConfig(){

	}

	public float getWeight(ResourceLocation gen){
		return weights.get(gen);
	}

	public void initPostLoad(IForgeRegistry<SkyobjectGenerator> registry){
		for(SkyobjectGenerator generator : registry) if(!weights.containsKey(generator.getRegistryName())) weights.put(generator.getRegistryName(), generator.getDefaultWeight());
		meteorite.initPostLoad();
	}

	public static class Meteorite {

		public float minRadius = 5;
		public float maxRadius = 110;
		private List<String> allowedBlocks = Lists.newArrayList(AppEng.MODID + ":skystone{variant=stone}", "minecraft:stone{variant=stone}", "minecraft:cobblestone", "minecraft:ice", "minecraft:obsidian");
		private transient ImmutableList<IBlockState> allowedBlockStates;

		public Meteorite(){

		}

		public void initPostLoad(){
			minRadius = Math.min(minRadius, maxRadius);
			maxRadius = Math.max(minRadius, maxRadius);
			minRadius = Math.max(minRadius, 1);
			maxRadius = Math.min(maxRadius, 110);
			allowedBlocks = allowedBlocks.stream().sorted().limit(16).collect(Collectors.toList());
			allowedBlockStates = ImmutableList.copyOf(allowedBlocks.stream().map(s -> BlockState2String.fromStringSafe(s)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()));
		}

		public List<IBlockState> getAllowedBlockStates(){
			return allowedBlockStates;
		}

		public boolean isAllowedState(IBlockState state){
			states: for(IBlockState next : allowedBlockStates){
				if(next.getBlock() == state.getBlock()){
					for(IProperty property : next.getBlock().getBlockState().getProperties()) if(!Objects.equals(next.getValue(property), state.getValue(property))) continue states;
					return true;
				}
			}
			return false;
		}

	}

}
