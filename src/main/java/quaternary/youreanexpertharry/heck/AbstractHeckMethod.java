package quaternary.youreanexpertharry.heck;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Optional;

public abstract class AbstractHeckMethod {
	public AbstractHeckMethod(int inputCount) {
		this.inputCount = inputCount;
	}
	
	public final int inputCount;
	
	public abstract Optional<String> getRequiredImports();
	public abstract String removeExistingRecipe(ItemStack output);
	public abstract String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs);
	public abstract List<ItemStack> getRequiredItems();
	
	public static String stackToBracket(ItemStack stack) {
		if(stack.getMetadata() != 0) {
			return String.format("<%s:1:%s>", stack.getItem().getRegistryName(), stack.getMetadata());
		} else {
			return String.format("<%s>", stack.getItem().getRegistryName());
		}
	}
	
	public static String stacksToBracketedList(List<ItemStack> stacks) {
		StringBuilder beb = new StringBuilder();
		beb.append('[');
		for(int i = 0; i < stacks.size(); i++) {
			beb.append(stackToBracket(stacks.get(i)));
			if(i != stacks.size() - 1) beb.append(", ");
		}
		beb.append(']');
		return beb.toString();
	}
	
	public static String quote(String s) {
		return '"' + s + '"';
	}
}
