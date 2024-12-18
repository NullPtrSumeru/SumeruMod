package com.nullptr.mod.commands;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import java.util.Random;
import com.nullptr.mod.Main;
import net.minecraft.server.MinecraftServer;
import com.nullptr.mod.commands.util.Teleport;
import java.util.List;
import com.google.common.collect.Lists;

public class CommandDimensionTeleport extends CommandBase {
    private final List<String> aliases = Lists.newArrayList(Main.MODID, "tp", "tpdim", "tpdimension", "teleportdimension", "teleport");
    @Override
    public String getName() {
        return "tpdimension";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "tpdimension <id>";
    }
    @Override
    public List<String> getAliases()
    {
        return aliases;
    }
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) return;
        String s = args[0];
        int dimensionID;
        try {
            dimensionID = Integer.parseInt(s);
        } catch(NumberFormatException e) {
            sender.sendMessage(new TextComponentString("Dimension ID invalid"));
            return;
        }
        if (sender instanceof EntityPlayer) {
            if (dimensionID == 1) {
                Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, 0, 55, 0);
            }
            else {
                Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, sender.getPosition().getX(), sender.getPosition().getY() + 5, sender.getPosition().getZ());
            }
            sender.sendMessage(new TextComponentString("You have been teleported."));
        }
    }
}
