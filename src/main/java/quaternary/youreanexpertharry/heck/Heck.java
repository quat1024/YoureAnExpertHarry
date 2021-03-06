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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Heck {
	static Random random = new Random();
	static List<Item> allItems;
	
	public static void doHeck() throws Heckception {
		YAEHSettings settings = YoureAnExpertHarry.settings;
		
		Collection<Item> allItemsCollection = ForgeRegistries.ITEMS.getValuesCollection();
		allItems = new ArrayList<>(allItemsCollection);
		
		int currentLevel = settings.topDifficulty;
		Set<GoodItemStack> toAddRecipesFor = new HashSet<>();
		Set<GoodItemStack> toAddRecipesForNext = new HashSet<>();
		Set<GoodItemStack> bannedItems = new HashSet<>(); 
		Set<AbstractHeckMethod> usedMethods = new HashSet<>();
		
		settings.bannedItems.forEach(s -> bannedItems.add(new GoodItemStack(s)));
		settings.goalItems.forEach(s -> toAddRecipesFor.add(new GoodItemStack(s)));
		
		//don't use a top tier item in another top tier item recipe
		//for that VARIED GAMEPLAY
		bannedItems.addAll(toAddRecipesFor);
		
		List<String> zenBody = new ArrayList<>();
		int recipeCount = 0;
		
		while(currentLevel >= 1) {
			zenBody.add("// RECIPE LEVEL: " + currentLevel + "\n\n");
			
			for(GoodItemStack outputGood : toAddRecipesFor) {
				ItemStack output = outputGood.actualStack;
				
				AbstractHeckMethod method = chooseMethod(settings, currentLevel);
				usedMethods.add(method);
				
				List<ItemStack> recipeStacks = new ArrayList<>(method.inputCount);
				for(int a = 0; a < method.inputCount; a++) {
					recipeStacks.add(chooseItem(bannedItems, outputGood));
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
				recipeStacks.forEach(r -> toAddRecipesForNext.add(new GoodItemStack(r)));
				//don't use this item again in recipes
				bannedItems.add(outputGood);
			}
			
			currentLevel--;
			toAddRecipesFor.clear();
			toAddRecipesFor.addAll(toAddRecipesForNext);
			toAddRecipesForNext.clear();
		}
		
		StringBuilder header = new StringBuilder();
		usedMethods.forEach(a -> a.getRequiredImports().ifPresent(i -> {
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
	
	private static ItemStack chooseItem(Set<GoodItemStack> forbiddenItems, GoodItemStack alsoBannedItem) throws Heckception {
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
			if(!hahayes.isEmpty() && !forbiddenItems.contains(bep) && !alsoBannedItem.equals(bep)) return hahayes;
		}
		
		throw new Heckception("Ran out of input items for recipes (couldn't find a fresh item to add to a recipe after 1000 tries). Either your difficulty is set too high, or you are just unlucky");
	}
	
	public static class GoodItemStack {
		public GoodItemStack(ItemStack actualStack) {
			this.actualStack = actualStack;
		}
		
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
