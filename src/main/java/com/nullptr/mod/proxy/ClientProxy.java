package com.nullptr.mod.proxy;

import com.nullptr.mod.Main;
import com.nullptr.mod.model.Netero;
import com.nullptr.mod.AssetManager;
import com.nullptr.mod.model.ModelObjOld;
import com.nullptr.mod.model.ModelCreatureObj;
import net.minecraft.util.ResourceLocation;
import com.nullptr.mod.model.projectile.LightBallModel;
//import com.nullptr.mod.BakedModelLoader;
import com.nullptr.mod.entity.weirdzombie.EntityInit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.RegistryObject;
import com.nullptr.mod.model.Netero;
//import com.nullptr.mod.core.tileentity.TileEntityEquipmentPart;
//import com.nullptr.mod.core.tileentity.TileEntityEquipment;
//import net.minecraftforge.fml.client.registry.ClientRegistry;
//import net.minecraftforge.client.model.ModelLoaderRegistry;
import com.nullptr.mod.model.projectile.LightBallModel;
//import com.nullptr.mod.renderer.EquipmentPartRenderer;
//import com.nullptr.mod.renderer.EquipmentRenderer;
import com.nullptr.mod.renderer.RenderRegister;
import net.minecraft.entity.Entity;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        // Typically initialization of models and such goes here:
        EntityInit.initModels();
        //Netero.init();
	registerModels();
	registerRenders();
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
    @Override
    public void addOBJLoaderDomainIfOnClient() {
        OBJLoader.INSTANCE.addDomain(Main.MODID);
    }
    @Override
    public void init()
    {
        Netero.init();
    }
    @Override
    public void registerModels() {
		AssetManager.registerModels();
    }

	// ========== Creatures ==========
    @Override
    public void loadCreatureModel(String modelClassName) throws ClassNotFoundException {
		//creature.modelClass = (Class<? extends ModelCustom>) Class.forName(modelClassName);
    }


    @Override
    public void loadSubspeciesModel(/*Subspecies subspecies, */String modelClassName) throws ClassNotFoundException {
		//subspecies.modelClass = (Class<? extends ModelCustom>) Class.forName(modelClassName);
    }
    @Override
    public void registerRenders() {
           LightBallModel Netero = new LightBallModel();
	  // import net.minecraft.entity.Entity;

           // Предположим, у нас есть RegistryObject для сущности под названием "example_entity"
           RegistryObject<Entity> entityRegistryObject = RegistryManager.ACTIVE.getRegistry(Netero.class).getValue(new ResourceLocation(Main.MODID, "Netero"));

           // Проверяем, что сущность зарегистрирована в реестре
           if (entityRegistryObject != null) {
               // Получаем объект сущности из RegistryObject
	       Entity NeteroEntity = entityRegistryObject.get();
	       ModelCreatureObj.render(NeteroEntity, 1200, 0F, 60F, 60F, 60F, 10F, false);
               // Теперь вы можете выполнять операции с "exampleEntity"
           } else {
              // Сущность не зарегистрирована или еще не загружена
           }
    }
}
