package quaternary.youreanexpertharry.heck;

import quaternary.youreanexpertharry.settings.YAEHSettings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HeckData {

    public Set<Heck.GoodItemStack> toAddRecipesFor = new HashSet<>();
    public Set<Heck.GoodItemStack> toAddRecipesForNext = new HashSet<>();
    public Set<Heck.GoodItemStack> bannedItems = new HashSet<>();
    public Set<Heck.GoodItemStack> allGoalItems = new HashSet<>();
    public Set<Heck.GoodItemStack> baseItems = new HashSet<>();
    public Set<AbstractHeckMethod> usedMethods = new HashSet<>();

    public List<HeckTier> tiers = new ArrayList<>();
    public int currentLevel;

    public HeckData(YAEHSettings settings) {
        this.currentLevel = settings.topDifficulty;
        for (int i = 0; i <= settings.topDifficulty; i++) {
            tiers.add(new HeckTier(i));
        }

        //If 0, put in bannedItems; if greater, put in specific categories.
        //If it's banned at tier 1 then it'll never be chosen. If it's banned at tier 2 then it'll be
        for (HeckTier.TierItemStack tis : settings.bannedItems) {
            if (tis.tier == 0) {
                bannedItems.add(new Heck.GoodItemStack(tis));
            }
            else for (int i = tis.tier; i <= settings.topDifficulty; i++) {
                tiers.get(i).bannedItems.add(new Heck.GoodItemStack(tis));
            }
        }

        //Test in mind to check if a goalItem for a tier should be banned at that tier.
        //Wait! Shouldn't it be chooseable at any tier as long as it's been made in a previous tier?
        //So we shouldn't ban it! But we have to make sure it doesn't get added in any tier higher than it should be.
        for (HeckTier.TierItemStack tis : settings.goalItems) {
            Heck.GoodItemStack gis = new Heck.GoodItemStack(tis);
            allGoalItems.add(gis);
            if (tis.tier == 0 || tis.tier == settings.topDifficulty) {
                toAddRecipesFor.add(gis);
            }
            else {
                tiers.get(tis.tier).goalItems.add(gis);
                //for (int i = tis.tier + 1; i <= settings.topDifficulty; i++) {
                //	tiers.get(i).bannedItems.add(new GoodItemStack(tis));
                //}
            }
        }

        for (HeckTier.TierItemStack tis : settings.baseItems) {
            Heck.GoodItemStack gis = new Heck.GoodItemStack(tis);
            baseItems.add(gis);
        }

        //don't use a top tier item in another top tier item recipe
        //for that VARIED GAMEPLAY
        bannedItems.addAll(toAddRecipesFor);

    }
}
