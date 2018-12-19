package quaternary.youreanexpertharry.etc;

import net.minecraft.item.ItemStack;
import quaternary.youreanexpertharry.heck.Heck;

import java.util.HashSet;
import java.util.Iterator;

public class ShapelessStack {

    public Heck.GoodItemStack actualStack;
    public int count;
    public ShapelessStack(Heck.GoodItemStack gis, int count) {
        this.actualStack = gis;
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShapelessStack) {
            ItemStack other = ((ShapelessStack)obj).actualStack.actualStack;
            return other.getItem() == actualStack.actualStack.getItem() && other.getMetadata() == actualStack.actualStack.getMetadata()
                    && ((ShapelessStack) obj).count == count;
        } else return false;
    }

    @Override
    public int hashCode() {
        return actualStack.actualStack.getItem().getRegistryName().hashCode() + actualStack.actualStack.getMetadata() * 1232323 + count * 4565656;
    }

    public static void shapelessAdd(HashSet<ShapelessStack> shapelessSet, ItemStack is) {
        Heck.GoodItemStack gis = new Heck.GoodItemStack(is);
        Iterator<ShapelessStack> setIter = shapelessSet.iterator();
        boolean found = false;
        while (setIter.hasNext()) {
            ShapelessStack ss = setIter.next();
            if (ss.actualStack.equals(gis)) {
                ss.count++;
                found = true;
                break;
            }
        }
        if (!(found)) shapelessSet.add(new ShapelessStack(gis, 1));
    }
}
