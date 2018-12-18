package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import quaternary.youreanexpertharry.etc.HeckMethodProps;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleBotania extends AbstractModule {

    public static ManaInfusionMethod MANA_INFUSION;

    public static BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();

    public static List<String> methodIds = new ArrayList<>();

    public ModuleBotania() {

    }

    public void init(BiMap<String, AbstractHeckMethod> heckMethods) {
        MANA_INFUSION = registerMethod("mana_infusion", new ManaInfusionMethod(), heckMethods);
    }

    public static HeckMethodProps getMethod() {
        return null;
    }

    public static <T extends AbstractHeckMethod> T registerMethod(String id, T method, BiMap<String, AbstractHeckMethod> heckMethods) {
        methods.put(id, method);
        methodIds.add(id);
        heckMethods.put(id, method);
        return method;
    }

    public BiMap<String, AbstractHeckMethod> getMethods() {
        return methods;
    }

    public List<String> getMethodIds() {
        return methodIds;
    }
}
