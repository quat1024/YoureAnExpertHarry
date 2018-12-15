package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.*;

public class ShapelessTwoByTwoMethod extends AbstractCraftingMethod {

	public static Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

	public ShapelessTwoByTwoMethod() {
		super(4);
	}

	public List<ItemStack> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(this.inputCount);
		HashSet<ShapelessStack> shapelessSet = new HashSet<>();

		boolean sanity = false;

		while (!(sanity)) {
			recipeStacks.clear();
			shapelessSet.clear();
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck.bannedItems, allHeck.tiers.get(allHeck.currentLevel).bannedItems, outputGood));
			}
			recipeStacks.forEach(is -> shapelessAdd(recipeStacks, shapelessSet, is));

			sanity = this.sanityCheck(shapelessSet);
		}

		sanitySet.add(shapelessSet);

		return recipeStacks;

	}

	private boolean sanityCheck(HashSet<ShapelessStack> shapelessSet) {
		if (sanitySet.contains(shapelessSet)) return false;
		return true;
	}
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		return String.format(
						"recipes.addShapeless(%s, %s, %s);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(inputs)
		);
	}

	public void shapelessAdd(List<ItemStack> recipeStacks, HashSet<ShapelessStack> shapelessSet, ItemStack is) {
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

	public class ShapelessStack {
		Heck.GoodItemStack actualStack;
		int count;
		ShapelessStack(Heck.GoodItemStack gis, int count) {
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
	}
}
