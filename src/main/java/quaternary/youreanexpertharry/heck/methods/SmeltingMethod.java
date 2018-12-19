package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.*;

public class SmeltingMethod extends AbstractHeckMethod {
	public SmeltingMethod() {
		super(1);
	}

	public static Set<Heck.GoodItemStack> sanitySet = new HashSet<>();

	public Pair<List<ItemStack>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(1);
		Heck.GoodItemStack sanityItem = null;

		boolean sanity = false;
		int attemptCount = 0;
		boolean success = true;

		while (!(sanity)) {
			recipeStacks.clear();
			attemptCount++;
			if (attemptCount > 250) {
				success = false;
				break;
			}
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
			}
			sanityItem = new Heck.GoodItemStack(recipeStacks.get(0));

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking smelting");
            //YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
			sanity = this.sanityCheck(sanityItem);
		}
		//YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
		sanitySet.add(sanityItem);

		return new MutablePair<>(recipeStacks, new Boolean(success));

	}

	private boolean sanityCheck(Heck.GoodItemStack sanityItem) {
		if (sanitySet.contains(sanityItem)) return false;
		return true;
	}

	@Override
	public Optional<String> getRequiredImports() {
		return Optional.empty();
	}
	
	@Override
	public String removeExistingRecipe(ItemStack output) {
		return String.format(
						"furnace.remove(%s);",
						stackToBracket(output)
		);
	}
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		return String.format(
						"furnace.addRecipe(%s, %s);",
						stackToBracket(output),
						stackToBracket(inputs.get(0))
		);
	}
	
	@Override
	public List<ItemStack> getRequiredItems() {
		return ImmutableList.of(new ItemStack(Blocks.FURNACE));
	}
}
