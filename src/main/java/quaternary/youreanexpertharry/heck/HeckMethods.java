package quaternary.youreanexpertharry.heck;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.heck.methods.FourWaySymmetricalShapedThreeByThreeMethod;
import quaternary.youreanexpertharry.heck.methods.ShapedThreeByThreeMethod;
import quaternary.youreanexpertharry.heck.methods.ShapelessTwoByTwoMethod;
import quaternary.youreanexpertharry.heck.methods.SmeltingMethod;
import quaternary.youreanexpertharry.heck.methods.SymmetricalShapedThreeByThreeMethod;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.lang.reflect.Type;

public class HeckMethods {
	public static ShapelessTwoByTwoMethod SHAPELESS_TWO_BY_TWO;
	public static ShapedThreeByThreeMethod SHAPED_THREE_BY_THREE;
	public static SymmetricalShapedThreeByThreeMethod SYMMETRICAL_SHAPED_THREE_BY_THREE;
	public static FourWaySymmetricalShapedThreeByThreeMethod FOUR_WAY_SYMMETRICAL_THREE_BY_THREE;
	public static SmeltingMethod SMELTING;
	
	public static final BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();
	
	public static <T extends AbstractHeckMethod> T registerMethod(String id, T method) {
		methods.put(id, method);
		return method;
	}
	
	public static AbstractHeckMethod byKey(String id) {
		return methods.get(id);
	}
	
	public static String getName(AbstractHeckMethod method) {
		return methods.inverse().get(method);
	}
	
	public static void init() {
		SHAPELESS_TWO_BY_TWO = registerMethod("shapeless_2x2", new ShapelessTwoByTwoMethod());
		SHAPED_THREE_BY_THREE = registerMethod("random_shaped_3x3", new ShapedThreeByThreeMethod());
		SYMMETRICAL_SHAPED_THREE_BY_THREE = registerMethod("butterfly_shaped_3x3", new SymmetricalShapedThreeByThreeMethod());
		FOUR_WAY_SYMMETRICAL_THREE_BY_THREE = registerMethod("square_shaped_3x3", new FourWaySymmetricalShapedThreeByThreeMethod());
		SMELTING = registerMethod("furnace", new SmeltingMethod());

		for (AbstractModule mod : YoureAnExpertHarry.modules) {
			mod.init(methods);
		}
	}
	
	public static class JsonBlah implements JsonDeserializer<AbstractHeckMethod>, JsonSerializer<AbstractHeckMethod> {
		@Override
		public AbstractHeckMethod deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			if(json.isJsonPrimitive()) {
				String s = json.getAsString();
				AbstractHeckMethod hecc = byKey(s);
				if(hecc == null) throw new JsonParseException("No such heck method '" + s + "'");
				return hecc;
			} else throw new JsonParseException("Problem parsing heck method, what is this thing: " + json.toString());
		}
		
		@Override
		public JsonElement serialize(AbstractHeckMethod hecc, Type typeOfSrc, JsonSerializationContext context) {
			YoureAnExpertHarry.LOGGER.info(getName(hecc));
			return new JsonPrimitive(getName(hecc));
		}
	}
}
