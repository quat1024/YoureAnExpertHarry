package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapelessTwoByTwoMethod extends AbstractCraftingMethod {
	public ShapelessTwoByTwoMethod() {
		super(4);
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
}
