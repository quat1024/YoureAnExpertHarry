package quaternary.youreanexpertharry.settings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.etc.HeckMethodProps;

import java.util.ArrayList;
import java.util.List;

public class YAEHSettings {
	public List<ItemStack> goalItems = new ArrayList<>();
	public int topDifficulty = 10;
	
	public List<ItemStack> bannedItems = new ArrayList<>();
	
	public List<HeckMethodProps> heckMethods = new ArrayList<>();
	
	{
		goalItems.add(new ItemStack(Items.CLAY_BALL));
		goalItems.add(new ItemStack(Blocks.CLAY));
		goalItems.add(new ItemStack(Blocks.BEACON));
		//You can't even use these in gamemode 0 but that won't stop me
		goalItems.add(new ItemStack(Blocks.COMMAND_BLOCK));
		
		bannedItems.add(new ItemStack(Blocks.BEDROCK));
		bannedItems.add(new ItemStack(Blocks.COMMAND_BLOCK));
		bannedItems.add(new ItemStack(Blocks.CHAIN_COMMAND_BLOCK));
		bannedItems.add(new ItemStack(Blocks.REPEATING_COMMAND_BLOCK));
		bannedItems.add(new ItemStack(Blocks.BARRIER));
		bannedItems.add(new ItemStack(Blocks.STRUCTURE_BLOCK));
		bannedItems.add(new ItemStack(Blocks.STRUCTURE_VOID));
		bannedItems.add(new ItemStack(Blocks.END_PORTAL_FRAME));
		bannedItems.add(new ItemStack(Blocks.MOB_SPAWNER));
		bannedItems.add(new ItemStack(Blocks.FARMLAND));
		bannedItems.add(new ItemStack(Blocks.GRASS_PATH));
		addAllSubtypesTo(bannedItems, Blocks.MONSTER_EGG);
		bannedItems.add(new ItemStack(Items.COMMAND_BLOCK_MINECART));
		bannedItems.add(new ItemStack(Items.SPAWN_EGG));
		bannedItems.add(new ItemStack(Items.KNOWLEDGE_BOOK));
		
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPELESS_TWO_BY_TWO, 1, 3));
		heckMethods.add(new HeckMethodProps(HeckMethods.FOUR_WAY_SYMMETRICAL_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SYMMETRICAL_SHAPED_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPED_THREE_BY_THREE, 5, 5));
		heckMethods.add(new HeckMethodProps(HeckMethods.SMELTING, 1, 4));
	}
	
	private static void addAllSubtypesTo(List<ItemStack> list, Block b) {
		NonNullList<ItemStack> bepis = NonNullList.create();
		b.getSubBlocks(b.getCreativeTab(), bepis);
		list.addAll(bepis);
	}
}
