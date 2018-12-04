package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCraftingMethod extends AbstractHeckMethod {
	public AbstractCraftingMethod(int count) {
		super(count);
	}
	
	@Override
	public Optional<String> getRequiredImports() {
		return Optional.empty();
	}
	
	@Override
	public String removeExistingRecipe(ItemStack output) {
		return String.format(
						"recipes.remove(%s);",
						stackToBracket(output)
		);
	}
	
	@Override
	public List<ItemStack> getRequiredItems() {
		return ImmutableList.of(new ItemStack(Blocks.CRAFTING_TABLE));
	}
}
