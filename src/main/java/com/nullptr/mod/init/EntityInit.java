package com.nullptr.mod.init;
import com.nullptr.mod.Main;
import net.minecraft.entity.Entity;
import com.nullptr.mod.entity.test.EntityTest;
//import com.nullptr.mod.model.Netero;
import com.nullptr.mod.entity.python.EntityPython;
import com.nullptr.mod.entity.centaur.EntityCentaur;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import com.nullptr.mod.entity.jenny.EntityJenny;
public class EntityInit
{
   public static void registerEntities()
   {
      registerEntity("test", EntityTest.class, Main.ENTITY_TEST, 50, 13310623, 65354);
      registerEntity("centaur", EntityCentaur.class, Main.ENTITY_CENTAUR, 50, 11437146, 000000);
      registerEntity("python", EntityPython.class, Main.ENTITY_PYTHON, 50, 16776960, 000000);
      registerEntity("jenny", EntityJenny.class, Main.ENTITY_JENNY, 50, 16776960, 000000 );
   }
   private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
   {
       EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID + ":" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
   }
   private static void registerNonMobEntity()
   {
     
   }
}
