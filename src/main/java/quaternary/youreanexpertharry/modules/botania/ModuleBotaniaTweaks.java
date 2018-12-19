package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleBotaniaTweaks extends AbstractModule {

    public static PetalApothecaryMethod PETAL_APOTHECARY;

    public static BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();

    public static List<String> methodIds = new ArrayList<>();

    public void init(BiMap<String, AbstractHeckMethod> heckMethods) {
        PETAL_APOTHECARY = registerMethod("petal_apothecary", new PetalApothecaryMethod(), heckMethods);
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
