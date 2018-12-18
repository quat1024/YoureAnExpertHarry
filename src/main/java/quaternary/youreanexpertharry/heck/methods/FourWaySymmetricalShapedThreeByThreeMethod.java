package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FourWaySymmetricalShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public FourWaySymmetricalShapedThreeByThreeMethod() {
		super(3);
	}

	public static Set<List<Heck.GoodItemStack>> sanitySet = new HashSet<>();
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		ItemStack corners = inputs.get(0);
		ItemStack sides = inputs.get(1);
		ItemStack middle = inputs.get(2);
		
		return String.format(
						"recipes.addShaped(%s, %s, \n [%s,\n  %s,\n  %s]);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(ImmutableList.of(corners, sides, corners)),
						stacksToBracketedList(ImmutableList.of(sides, middle, sides)),
						stacksToBracketedList(ImmutableList.of(corners, sides, corners))
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

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking 4s3b3");
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
