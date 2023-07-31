package com.nullptr.mod.objects.blocks;
import com.nullptr.mod.util.interfaces.IHasModel;
import com.nullptr.mod.Main;
import com.nullptr.mod.init.BlockInit;
import com.nullptr.mod.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.material.Material;
public class BlockBase extends Block implements IHasModel
{
   public BlockBase(String name, Material material)
   {
       super(material);
       setUnlocalizedName(name);
       setRegistryName(name);
       setCreativeTab(CreativeTabs.MATERIALS);
       BlockInit.BLOCKS.add(this);
       ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
   }
   @override
   public void registerModels()
   {
      Mod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
   }
}