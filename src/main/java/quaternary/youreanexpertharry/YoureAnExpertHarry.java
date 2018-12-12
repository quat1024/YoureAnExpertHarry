package quaternary.youreanexpertharry;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.youreanexpertharry.command.CommandDump;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.etc.ItemStackReaderWriter;
import quaternary.youreanexpertharry.heck.HeckTier;
import quaternary.youreanexpertharry.settings.YAEHSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Mod(
				modid = YoureAnExpertHarry.MODID,
				name = YoureAnExpertHarry.NAME,
				version = YoureAnExpertHarry.VERSION,
				clientSideOnly = true
)
public class YoureAnExpertHarry {
	public static final String MODID = "youre_an_expert_harry";
	public static final String NAME = "You're an Expert, Harry!";
	public static final String VERSION = "GRADLE:VERSION";
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static YAEHSettings settings = null;
	public static Gson gson = new GsonBuilder()
					.setPrettyPrinting()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.registerTypeHierarchyAdapter(HeckTier.TierItemStack.class, new ItemStackReaderWriter())
					.registerTypeHierarchyAdapter(AbstractHeckMethod.class, new HeckMethods.JsonBlah())
					.create();
	
	public static File settingsFile;
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		HeckMethods.init();
		
		settingsFile = new File(e.getSuggestedConfigurationFile().getParent() + File.separator + MODID + ".json");
		
		
	}
	
	@Mod.EventHandler
	public static void postinit(FMLPostInitializationEvent e) {
		
		try {
			boolean newlyCreated = settingsFile.createNewFile();
			
			if(newlyCreated) {
				try(FileWriter writer = new FileWriter(settingsFile)) {
					writer.write(gson.toJson(new YAEHSettings()));
				} catch(IOException eeee) {
					LOGGER.error("Problem writing to the new configuration file", eeee);
				}
			}
			
		} catch (IOException eeee) {
			LOGGER.error("Problem creating the configuration file", eeee);
		}
		
		try {
			settings = gson.fromJson(new FileReader(settingsFile), YAEHSettings.class);
		} catch (FileNotFoundException eeee) {
			LOGGER.error("Cannot find the configuration file but I tried to create it?", eeee);
		} catch (JsonSyntaxException eeee) {
			LOGGER.error("Cannot parse the configuration file. Is there a syntax error?", eeee);
		}
		
		if(settings == null) {
			LOGGER.error("Falling back to using default settings!");
			settings = new YAEHSettings();
		}
		
		ClientCommandHandler.instance.registerCommand(new CommandDump());
		
	}
}
