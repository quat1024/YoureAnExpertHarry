package quaternary.youreanexpertharry.modules;

import com.google.common.collect.BiMap;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;

import java.util.List;

public abstract class AbstractModule {

    public abstract void init(BiMap<String, AbstractHeckMethod> heckMethods);

    public abstract BiMap<String, AbstractHeckMethod> getMethods();

    public abstract List<String> getMethodIds();
}
