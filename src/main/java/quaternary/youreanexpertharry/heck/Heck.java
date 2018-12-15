package quaternary.youreanexpertharry.heck;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.settings.YAEHSettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Heck {
	static Random random = new Random();
	static List<Item> allItems;
	
	public static void doHeck() throws Heckception {
		YAEHSettings settings = YoureAnExpertHarry.settings;
		HeckData allHeck = new HeckData(settings);
		
		Collection<Item> allItemsCollection = ForgeRegistries.ITEMS.getValuesCollection();
		allItems = new ArrayList<>(allItemsCollection);
		List<HeckTier> tiers = new ArrayList<>();
		for (int i = 0; i <= settings.topDifficulty; i++) {
			tiers.add(new HeckTier(i));
		}
		//Set<GoodItemStack> toAddRecipesFor = new HashSet<>();
		//Set<GoodItemStack> toAddRecipesForNext = new HashSet<>();
		//Set<GoodItemStack> bannedItems = new HashSet<>();
		//Set<GoodItemStack> allGoalItems = new HashSet<>();
		//Set<GoodItemStack> baseItems = new HashSet<>();
		//Set<AbstractHeckMethod> usedMethods = new HashSet<>();

		//If 0, put in bannedItems; if greater, put in specific categories.
		//If it's banned at tier 1 then it'll never be chosen. If it's banned at tier 2 then it'll be
		//for (HeckTier.TierItemStack tis : settings.bannedItems) {
		//if (tis.tier == 0) {
		//bannedItems.add(new GoodItemStack(tis));
		//}
		//else for (int i = tis.tier; i <= settings.topDifficulty; i++) {
		//tiers.get(i).bannedItems.add(new GoodItemStack(tis));
		//}
		//}

		//Test in mind to check if a goalItem for a tier should be banned at that tier.
		//Wait! Shouldn't it be chooseable at any tier as long as it's been made in a previous tier?
		//So we shouldn't ban it! But we have to make sure it doesn't get added in any tier higher than it should be.
		//for (HeckTier.TierItemStack tis : settings.goalItems) {
		//GoodItemStack gis = new GoodItemStack(tis);
		//allGoalItems.add(gis);
		//if (tis.tier == 0 || tis.tier == settings.topDifficulty) {
		//toAddRecipesFor.add(gis);
		//}
		//else {
		//tiers.get(tis.tier).goalItems.add(gis);
				//for (int i = tis.tier + 1; i <= settings.topDifficulty; i++) {
				//	tiers.get(i).bannedItems.add(new GoodItemStack(tis));
				//}
		//}
		//}

		//for (HeckTier.TierItemStack tis : settings.baseItems) {
		//GoodItemStack gis = new GoodItemStack(tis);
		//baseItems.add(gis);
		//}
		
		//don't use a top tier item in another top tier item recipe
		//for that VARIED GAMEPLAY
		//bannedItems.addAll(toAddRecipesFor);
		
		List<String> zenBody = new ArrayList<>();
		int recipeCount = 0;

		//We need to ensure that all the base items from tier 1 have recipes. That will be... tough.
		//We need base items that the mod can use.
		while(allHeck.currentLevel >= 1) {
			zenBody.add("// RECIPE LEVEL: " + allHeck.currentLevel + "\n\n");

			//don't use these items within this tier or in future recipes
			allHeck.toAddRecipesFor.forEach(outputGood -> allHeck.bannedItems.add(outputGood));


			for(GoodItemStack outputGood : allHeck.toAddRecipesFor) {
				ItemStack output = outputGood.actualStack;
				
				AbstractHeckMethod method = chooseMethod(settings, allHeck.currentLevel);
				allHeck.usedMethods.add(method);
				
				List<ItemStack> recipeStacks = new ArrayList<>(method.inputCount);
				for(int a = 0; a < method.inputCount; a++) {
					recipeStacks.add(chooseItem(allHeck.bannedItems, allHeck.tiers.get(allHeck.currentLevel).bannedItems, outputGood));
				}
				
				StringBuilder b = new StringBuilder();
				
				b.append("//Recipe ");
				b.append(recipeCount);
				b.append('\n');
				
				b.append(method.removeExistingRecipe(output));
				b.append('\n');
				
				b.append(method.writeZenscript("youre_an_expert_harry_" + recipeCount, output, recipeStacks));
				b.append('\n');
				
				zenBody.add(b.toString());
				
				recipeCount++;
				
				//mark all of the items added in this recipe as candidates for items to add next turn
				//Say we're in tier 5 and obsidian is a tier 2 goal item and it gets added to the recipe.
				//It's contained in allGoalItems, so it doesn't get added to toAddRecipesForNext.
				//But when we get to tier 2 then it will get added.
				//If we're in tier 1 obsidian will already be banned because it was added as a recipe in tier 2.
				for (ItemStack is : recipeStacks) {
					GoodItemStack gis = new GoodItemStack(is);
					if (!(allHeck.allGoalItems.contains(gis)) && !(allHeck.baseItems.contains(gis))) {
						allHeck.toAddRecipesForNext.add(gis);
					}
				}
			}
			
			allHeck.currentLevel--;
			allHeck.toAddRecipesFor.clear();
			allHeck.toAddRecipesFor.addAll(allHeck.toAddRecipesForNext);
			//Adds all the relevant goal items to the next tier.
			allHeck.toAddRecipesFor.addAll(allHeck.tiers.get(allHeck.currentLevel).goalItems);
			allHeck.toAddRecipesForNext.clear();
		}

		zenBody.add("// RECIPE LEVEL: Base" + "\n\n");

		//don't use these items within this tier or in future recipes
		allHeck.toAddRecipesFor.forEach(outputGood -> allHeck.bannedItems.add(outputGood));

		for(GoodItemStack outputGood : allHeck.toAddRecipesFor) {
			ItemStack output = outputGood.actualStack;

			AbstractHeckMethod method = chooseMethod(settings, 1);
			allHeck.usedMethods.add(method);

			List<ItemStack> recipeStacks = new ArrayList<>(method.inputCount);
			for(int a = 0; a < method.inputCount; a++) {
				recipeStacks.add(chooseBaseItem(allHeck.baseItems));
			}

			StringBuilder b = new StringBuilder();

			b.append("//Recipe ");
			b.append(recipeCount);
			b.append('\n');

			b.append(method.removeExistingRecipe(output));
			b.append('\n');

			b.append(method.writeZenscript("youre_an_expert_harry_" + recipeCount, output, recipeStacks));
			b.append('\n');

			zenBody.add(b.toString());

			recipeCount++;
		}


		
		StringBuilder header = new StringBuilder();
		allHeck.usedMethods.forEach(a -> a.getRequiredImports().ifPresent(i -> {
			header.append(i);
			header.append('\n');
		}));
		
		//I'm really bad a java files pls halp.
		File mainFolder = YoureAnExpertHarry.settingsFile.getParentFile().getParentFile();
		File scriptsFolder = new File(mainFolder.getAbsolutePath() + File.separator + "scripts");
		scriptsFolder.mkdirs();
		splitAndWriteZenscript(header.toString(), zenBody, scriptsFolder);
		
		YoureAnExpertHarry.LOGGER.info("Done");
	}
	
	private static AbstractHeckMethod chooseMethod(YAEHSettings settings, int currentLevel) throws Heckception {
		List<AbstractHeckMethod> methods = settings.heckMethods.stream()
						.filter(p -> currentLevel <= p.maxLevel && currentLevel >= p.minLevel)
						.map(p -> p.method)
						.collect(Collectors.toList());
		
		if(methods.size() == 0) throw new Heckception("No heckmethods available for level " + currentLevel);
		else return methods.get(random.nextInt(methods.size()));
	}


	public static ItemStack chooseItem(Set<GoodItemStack> forbiddenItems, Set<GoodItemStack> tierBannedItems,  GoodItemStack alsoBannedItem) throws Heckception {
		for(int tries = 0; tries < 1000; tries++) {
			Item i = allItems.get(random.nextInt(allItems.size()));
			int data;
			if(i.getHasSubtypes()) {
				NonNullList<ItemStack> choices = NonNullList.create();
				i.getSubItems(i.getCreativeTab(), choices);
				if(choices.isEmpty()) data = 0;
				else data = choices.get(random.nextInt(choices.size())).getMetadata();
			} else {
				data = 0;
			}
			
			ItemStack hahayes = new ItemStack(i, 1, data);
			GoodItemStack bep = new GoodItemStack(hahayes);
			if(!hahayes.isEmpty() && !forbiddenItems.contains(bep) && !tierBannedItems.contains(bep) && !alsoBannedItem.equals(bep)) return hahayes;
		}
		
		throw new Heckception("Ran out of input items for recipes (couldn't find a fresh item to add to a recipe after 1000 tries). Either your difficulty is set too high, or you are just unlucky");
	}


	private static ItemStack chooseBaseItem(Set<GoodItemStack> baseItems) {
		ArrayList<GoodItemStack> base = new ArrayList<>();
		base.addAll(baseItems);
		Item i = base.get(random.nextInt(base.size())).actualStack.getItem();
		int data;
		if (i.getHasSubtypes()) {
			NonNullList<ItemStack> choices = NonNullList.create();
			i.getSubItems(i.getCreativeTab(), choices);
			if(choices.isEmpty()) data = 0;
			else data = choices.get(random.nextInt(choices.size())).getMetadata();
		} else {
			data = 0;
		}

		return new ItemStack(i, 1, data);
	}
	
	public static class GoodItemStack {
		public GoodItemStack(ItemStack actualStack) {
			this.actualStack = actualStack;
		}
		public GoodItemStack(HeckTier.TierItemStack actualStack) {this.actualStack = actualStack.stack;}
		
		public ItemStack actualStack;
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof GoodItemStack) {
				ItemStack other = ((GoodItemStack)obj).actualStack;
				return other.getItem() == actualStack.getItem() && other.getMetadata() == actualStack.getMetadata();
			} else return false;
		}
		
		@Override
		public int hashCode() {
			return actualStack.getItem().getRegistryName().hashCode() + actualStack.getMetadata() * 1232323;
		}
	}
	
	private static final int LINES_PER_FILE = 200;
	
	public static void splitAndWriteZenscript(String header, List<String> lines, File scriptsFolder) throws Heckception {
		int fileCount = MathHelper.ceil(lines.size() / (float) LINES_PER_FILE);
		
		for(int i = 0; i < fileCount; i++) {
			StringBuffer b = new StringBuffer();
			b.append("#priority ");
			b.append(fileCount + 5 - i);
			b.append('\n');
			b.append(header);
			
			int from = Math.min(i * LINES_PER_FILE, lines.size());
			int to = Math.min((i + 1) * LINES_PER_FILE, lines.size());
			lines.subList(from, to).forEach(s -> {
				b.append(s);
				b.append('\n');
			});
			
			try {
				File outputFile = new File(scriptsFolder.getAbsolutePath() + File.separator + "youre_an_expert_harry_" + i + ".zs");
				if(outputFile.exists()) {
					YoureAnExpertHarry.LOGGER.info("Deleting " + outputFile.getAbsolutePath());
					outputFile.delete();
				}
				
				YoureAnExpertHarry.LOGGER.info("Creating " + outputFile.getAbsolutePath());
				outputFile.createNewFile();
				YoureAnExpertHarry.LOGGER.info("Writing");
				
				try(FileWriter writer = new FileWriter(outputFile)) {
					writer.write(b.toString());
				}
			} catch(Exception eee) {
				YoureAnExpertHarry.LOGGER.error(eee);
				throw new Heckception("Couldn't write output file number " + i + " hmm (check log)");
			}
		}
	}
}
