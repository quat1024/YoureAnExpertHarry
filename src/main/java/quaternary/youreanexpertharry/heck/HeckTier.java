package quaternary.youreanexpertharry.heck;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeckTier {
    public int id;

    public static class TierItemStack {
        public ItemStack stack;
        public int tier;
        public TierItemStack(ItemStack stack, int tier) {
            this.stack = stack;
            this.tier = tier;
        }

        public TierItemStack(Block block) {
            this.stack = new ItemStack(block);
            this.tier = 0;
        }

        public TierItemStack(Item item) {
            this.stack = new ItemStack(item);
            this.tier = 0;
        }

    }
}
