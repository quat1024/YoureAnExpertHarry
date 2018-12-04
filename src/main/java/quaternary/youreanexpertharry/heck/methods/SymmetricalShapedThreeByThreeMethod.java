package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SymmetricalShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public SymmetricalShapedThreeByThreeMethod() {
		super(6);
	}
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		ItemStack topCorners = inputs.get(0);
		ItemStack top = inputs.get(1);
		ItemStack sides = inputs.get(2);
		ItemStack middle = inputs.get(3);
		ItemStack bottomCorners = inputs.get(4);
		ItemStack bottom = inputs.get(5);
		
		return String.format(
						"recipes.addShaped(%s, %s, \n [%s,\n  %s,\n  %s]);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(ImmutableList.of(topCorners, top, topCorners)),
						stacksToBracketedList(ImmutableList.of(sides, middle, sides)),
						stacksToBracketedList(ImmutableList.of(bottomCorners, bottom, bottomCorners))
		);
	}
}
