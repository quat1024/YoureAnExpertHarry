package quaternary.youreanexpertharry.settings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.etc.HeckMethodProps;
import quaternary.youreanexpertharry.heck.HeckTier;

import java.util.ArrayList;
import java.util.List;

public class YAEHSettings {
	public List<HeckTier.TierItemStack> goalItems = new ArrayList<>();
	public int topDifficulty = 5;
	
	public List<HeckTier.TierItemStack> bannedItems = new ArrayList<>();
	public List<HeckTier.TierItemStack> baseItems = new ArrayList<>();

	public List<HeckMethodProps> heckMethods = new ArrayList<>();
	
	{
		goalItems.add(new HeckTier.TierItemStack(Items.CLAY_BALL));
		goalItems.add(new HeckTier.TierItemStack(Blocks.CLAY));
		goalItems.add(new HeckTier.TierItemStack(Blocks.BEACON));
		//You can't even use these in gamemode 0 but that won't stop me
		goalItems.add(new HeckTier.TierItemStack(Blocks.COMMAND_BLOCK));
		goalItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.OBSIDIAN), 2));
		
		bannedItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.BEDROCK), 3));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.COMMAND_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.CHAIN_COMMAND_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.REPEATING_COMMAND_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.BARRIER));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.STRUCTURE_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.STRUCTURE_VOID));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.END_PORTAL_FRAME));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.MOB_SPAWNER));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.FARMLAND));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.GRASS_PATH));
		addAllSubtypesTo(bannedItems, Blocks.MONSTER_EGG, 0);
		bannedItems.add(new HeckTier.TierItemStack(Items.COMMAND_BLOCK_MINECART));
		bannedItems.add(new HeckTier.TierItemStack(Items.SPAWN_EGG));
		bannedItems.add(new HeckTier.TierItemStack(Items.KNOWLEDGE_BOOK));

		//Gatherable
		baseItems.add(new HeckTier.TierItemStack(Items.COAL));
		baseItems.add(new HeckTier.TierItemStack(new ItemStack(Items.DYE, 1, 4), 0));
		baseItems.add(new HeckTier.TierItemStack(Items.REDSTONE));
		baseItems.add(new HeckTier.TierItemStack(Items.IRON_INGOT));
		baseItems.add(new HeckTier.TierItemStack(Items.GOLD_INGOT));
		baseItems.add(new HeckTier.TierItemStack(Items.DIAMOND));
		addAllSubtypesTo(baseItems, Blocks.LOG, 0);
		addAllSubtypesTo(baseItems, Blocks.LOG2, 0);
		baseItems.add(new HeckTier.TierItemStack(Blocks.COBBLESTONE));
		baseItems.add(new HeckTier.TierItemStack(Blocks.DIRT));
		addAllSubtypesTo(baseItems, Blocks.SAPLING, 0);
		baseItems.add(new HeckTier.TierItemStack(Blocks.SAND));
		baseItems.add(new HeckTier.TierItemStack(Blocks.GRAVEL));

		baseItems.add(new HeckTier.TierItemStack(Items.BEETROOT_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.MELON_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.PUMPKIN_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.WHEAT_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.WHEAT));
		baseItems.add(new HeckTier.TierItemStack(Items.MELON));
		baseItems.add(new HeckTier.TierItemStack(Items.BEETROOT));
		baseItems.add(new HeckTier.TierItemStack(Blocks.PUMPKIN));

		//Simple crafting
		baseItems.add(new HeckTier.TierItemStack(Items.BUCKET));
		addAllSubtypesTo(baseItems, Blocks.PLANKS, 0);
		baseItems.add(new HeckTier.TierItemStack(Items.STICK));
		baseItems.add(new HeckTier.TierItemStack(Blocks.TORCH));
		baseItems.add(new HeckTier.TierItemStack(Blocks.LAPIS_BLOCK));
		baseItems.add(new HeckTier.TierItemStack(Blocks.REDSTONE_BLOCK));
		baseItems.add(new HeckTier.TierItemStack(Items.WOODEN_PICKAXE));
		baseItems.add(new HeckTier.TierItemStack(Items.STONE_PICKAXE));
		baseItems.add(new HeckTier.TierItemStack(Items.IRON_PICKAXE));
		baseItems.add(new HeckTier.TierItemStack(Items.GOLD_NUGGET));
		baseItems.add(new HeckTier.TierItemStack(Blocks.FURNACE));
		baseItems.add(new HeckTier.TierItemStack(Blocks.CRAFTING_TABLE));
		
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPELESS_TWO_BY_TWO, 1, 3));
		heckMethods.add(new HeckMethodProps(HeckMethods.FOUR_WAY_SYMMETRICAL_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SYMMETRICAL_SHAPED_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPED_THREE_BY_THREE, 5, 5));
		//Sanity check--one furnace recipe per tier!
		heckMethods.add(new HeckMethodProps(HeckMethods.SMELTING, 1, 4));
	}
	
	private static void addAllSubtypesTo(List<HeckTier.TierItemStack> list, Block b, int tier) {
		NonNullList<ItemStack> bepis = NonNullList.create();
		b.getSubBlocks(b.getCreativeTab(), bepis);
		bepis.forEach(is -> list.add(new HeckTier.TierItemStack(is, tier)));
	}
}
