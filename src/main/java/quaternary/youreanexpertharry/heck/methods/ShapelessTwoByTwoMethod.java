package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.etc.ShapelessStack;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.*;

public class ShapelessTwoByTwoMethod extends AbstractCraftingMethod {

	public static Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

	public ShapelessTwoByTwoMethod() {
		super(4);
	}

	public Pair<List<ItemStack>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(this.inputCount);
		HashSet<ShapelessStack> shapelessSet = new HashSet<>();

		boolean sanity = false;

		while (!(sanity)) {
			recipeStacks.clear();
			shapelessSet.clear();
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck.bannedItems, allHeck.tiers.get(allHeck.currentLevel).bannedItems, allHeck.baseItems, outputGood, base));
			}
			recipeStacks.forEach(is -> shapelessAdd(recipeStacks, shapelessSet, is));

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking s2b2");
            //YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
			sanity = this.sanityCheck(shapelessSet);
		}
		//YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
		sanitySet.add(shapelessSet);

		return new MutablePair<>(recipeStacks, new Boolean(true));

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
}
