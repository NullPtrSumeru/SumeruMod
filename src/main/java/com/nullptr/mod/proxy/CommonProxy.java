package com.nullptr.mod.proxy;

import com.nullptr.mod.entity.weirdzombie.EntityInit;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import com.nullptr.mod.core.info.CreatureInfo;
//import com.nullptr.mod.core.info.ModInfo;
//import com.nullptr.mod.core.info.Subspecies;
import com.nullptr.mod.openai.ChatGPTBot;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
         EntityInit.init();
    }
    public void registerItemRenderer(Item item, int meta, String id) {
        // Здесь ваш код
    }
    public void registerVariantRenderer(Item item, int meta, String filename, String id) {
        // Здесь ваш код
    }
    public void addOBJLoaderDomainIfOnClient() {
    }
    public void init() {
    }
    public void openMyGui() { } 
    public void registerRenders() {}
    public void registerTextures() {}
    public void registerModels() {}
    public void registerModel(Item item, int metadata) {}
    public String getResponse(String message) {
            String response = ChatGPTBot.getResponse(message);
	    return response;
    }
    //public String sendLongMessage(String message) {}

    //public EntityPlayer getClientPlayer() { return null; }

    public void loadCreatureModel(String modelClassName) throws ClassNotFoundException {}
    public void loadSubspeciesModel(String modelClassName) throws ClassNotFoundException {}
}
