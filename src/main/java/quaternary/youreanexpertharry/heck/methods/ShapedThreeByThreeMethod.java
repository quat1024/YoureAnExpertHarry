package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.etc.ShapelessStack;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public ShapedThreeByThreeMethod() {
		super(9);
	}

	public static Set<List<Heck.GoodItemStack>> sanitySet = new HashSet<>();
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		return String.format(
						"recipes.addShaped(%s, %s, \n [%s,\n  %s,\n  %s]);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(inputs.subList(0, 3)),
						stacksToBracketedList(inputs.subList(3, 6)),
						stacksToBracketedList(inputs.subList(6, 9))
		);
	}

	public Pair<List<ItemStack>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(this.inputCount);
		List<Heck.GoodItemStack> sanityList = new ArrayList<>(this.inputCount);

		boolean sanity = false;

		while (!(sanity)) {
			recipeStacks.clear();
			sanityList.clear();
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck.bannedItems, allHeck.tiers.get(allHeck.currentLevel).bannedItems, allHeck.baseItems, outputGood, base));
			}
			recipeStacks.forEach(is -> sanityList.add(new Heck.GoodItemStack(is)));

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking s3b3");
			//YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
			sanity = this.sanityCheck(sanityList);
		}
		//YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
		sanitySet.add(sanityList);

		return new MutablePair<>(recipeStacks, new Boolean(true));

	}

	private boolean sanityCheck(List<Heck.GoodItemStack> stackList) {
		if (sanitySet.contains(stackList)) {
			//YoureAnExpertHarry.LOGGER.info("check failed");
			return false;
		}
		return true;
	}
}
