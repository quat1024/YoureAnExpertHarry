package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public ShapedThreeByThreeMethod() {
		super(9);
	}
	
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
}
