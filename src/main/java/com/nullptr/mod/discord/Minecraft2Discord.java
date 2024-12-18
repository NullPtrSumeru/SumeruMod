package com.nullptr.mod.discord;

import com.nullptr.mod.discord.events.DiscordEvents;
import com.nullptr.mod.discord.Utils;
import com.nullptr.mod.discord.events.ServerEvents;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.FMLCommonHandler;

//import net.minecraftforge.fml.network.FMLNetworkConstants;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.relauncher.Side;
//import javax.security.auth.login.LoginException;
import java.util.Date;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
// The value here should match an entry in the META-INF/mods.toml file
//@Mod(value = "minecraft2discord")
//@Mod.EventBusSubscriber
public class Minecraft2Discord {
    // Directly reference a log4j logger.
 //   private static final Logger LOGGER = LogManager.getLogger();
    private static JDA DISCORD_BOT = null;

    public static JDA getDiscordBot() {
        return DISCORD_BOT;
    }
  //  @Mod.EventHandler
    public static void onServerReady()
    {
        Utils.started_time = new Date().getTime();
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        server.getCommandManager().executeCommand(server, "say Server Ready!");
        DISCORD_BOT = JDABuilder.createDefault("MTE2ODIxMjg0NDI1MzI4MjQxNg.GHLt-S.prUaAEf0TkBSBdkdSdb65u6zisXFrIVc80CPNM")
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new DiscordEvents())
                .build();
        server.getCommandManager().executeCommand(server, "say Jda connected!");
    }
    //@Mod.EventHandler
    public static void onServerStarting()
    {
        
    }
   // @Mod.EventHandler
    public static void onServerStop()
    {
            if (getDiscordBot() == null) {
                return;
            }

            Utils.sendInfoMessage("Server down!");
    }
    //@Mod.EventHandler
    public static void onServerStopped()
    {
       // Utils.updateOfflineVoiceChannel();
       // Utils.updateOfflineChannelTopic();
        Utils.discordWebhookClient.close();
        DISCORD_BOT.shutdown();
        OkHttpClient client = DISCORD_BOT.getHttpClient();
        client.connectionPool().evictAll();
        client.dispatcher().executorService().shutdown();
    }
}
