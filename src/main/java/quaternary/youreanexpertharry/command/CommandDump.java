package quaternary.youreanexpertharry.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.Heckception;

public class CommandDump extends CommandBase {
	@Override
	public String getName() {
		return "youreanexpertharry";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "youreanexpertharry.usage";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			Heck.doHeck();
			sender.sendMessage(new TextComponentTranslation("youreanexpertharry.itworked"));
		} catch (Heckception e) {
			throw new CommandException("youreanexpertharry.itbroked", e.getMessage());
		}
	}
}
