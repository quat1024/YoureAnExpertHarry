package quaternary.youreanexpertharry.etc;

import quaternary.youreanexpertharry.heck.AbstractHeckMethod;

public class HeckMethodProps {
	public HeckMethodProps(AbstractHeckMethod method, int minLevel, int maxLevel) {
		this.method = method;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
	}
	
	public HeckMethodProps(AbstractHeckMethod method) {
		this(method, 0, Integer.MAX_VALUE);
	}
	
	public AbstractHeckMethod method;
	public int minLevel;
	public int maxLevel;
}
