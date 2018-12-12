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
	
	public List<HeckMethodProps> heckMethods = new ArrayList<>();
	
	{
		goalItems.add(new HeckTier.TierItemStack(Items.CLAY_BALL));
		goalItems.add(new HeckTier.TierItemStack(Blocks.CLAY));
		goalItems.add(new HeckTier.TierItemStack(Blocks.BEACON));
		//You can't even use these in gamemode 0 but that won't stop me
		goalItems.add(new HeckTier.TierItemStack(Blocks.COMMAND_BLOCK));
		
		bannedItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.BEDROCK), 1));
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
		
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPELESS_TWO_BY_TWO, 1, 3));
		heckMethods.add(new HeckMethodProps(HeckMethods.FOUR_WAY_SYMMETRICAL_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SYMMETRICAL_SHAPED_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPED_THREE_BY_THREE, 5, 5));
		heckMethods.add(new HeckMethodProps(HeckMethods.SMELTING, 1, 4));
	}
	
	private static void addAllSubtypesTo(List<HeckTier.TierItemStack> list, Block b, int tier) {
		NonNullList<ItemStack> bepis = NonNullList.create();
		b.getSubBlocks(b.getCreativeTab(), bepis);
		bepis.forEach(is -> list.add(new HeckTier.TierItemStack(is, tier)));
	}
}
