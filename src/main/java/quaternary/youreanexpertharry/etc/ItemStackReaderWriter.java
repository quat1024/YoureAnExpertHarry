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
import quaternary.youreanexpertharry.heck.HeckTier;

import java.lang.reflect.Type;

public class ItemStackReaderWriter implements JsonSerializer<HeckTier.TierItemStack>, JsonDeserializer<HeckTier.TierItemStack> {
	@Override
	public HeckTier.TierItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject o = JsonUtils.getJsonObject(json, "item stack");
		return new HeckTier.TierItemStack(ShapedRecipes.deserializeItem(o, true), JsonUtils.getInt(o, "tier", 0));
	}
	
	@Override
	public JsonElement serialize(HeckTier.TierItemStack stack, Type typeOfSrc, JsonSerializationContext context) {
		Item i = stack.stack.getItem();
		int count = stack.stack.getCount();
		int tier = stack.tier;
		
		JsonObject o = new JsonObject();
		o.addProperty("item", i.getRegistryName().toString());
		if(count != 1) o.addProperty("count", count);
		if(i.getHasSubtypes()) o.addProperty("data", stack.stack.getMetadata());
		if(tier != 0) o.addProperty("tier", tier);
		
		return o;
	}
}
