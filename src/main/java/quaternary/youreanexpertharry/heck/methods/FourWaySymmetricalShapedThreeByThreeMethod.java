package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FourWaySymmetricalShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public FourWaySymmetricalShapedThreeByThreeMethod() {
		super(3);
	}
	
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
}
