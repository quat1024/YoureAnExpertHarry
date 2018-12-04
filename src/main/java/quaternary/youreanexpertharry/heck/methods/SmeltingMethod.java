package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;

import java.util.List;
import java.util.Optional;

public class SmeltingMethod extends AbstractHeckMethod {
	public SmeltingMethod() {
		super(1);
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
