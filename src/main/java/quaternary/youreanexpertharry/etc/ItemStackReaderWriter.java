package quaternary.youreanexpertharry.etc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

public class ItemStackReaderWriter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject o = JsonUtils.getJsonObject(json, "item stack");
		return ShapedRecipes.deserializeItem(o, true);
	}
	
	@Override
	public JsonElement serialize(ItemStack stack, Type typeOfSrc, JsonSerializationContext context) {
		Item i = stack.getItem();
		int count = stack.getCount();
		
		JsonObject o = new JsonObject();
		o.addProperty("item", i.getRegistryName().toString());
		if(count != 1) o.addProperty("count", count);
		if(i.getHasSubtypes()) o.addProperty("data", stack.getMetadata());
		
		return o;
	}
}
