package com.nullptr.mod.entity.test;

import com.nullptr.mod.Main;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTest extends RenderLiving<EntityTest>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID + ":textures/entity/test/test.png");
	
	public RenderTest(RenderManager manager) 
	{
		super(manager, new ModelTest(), 0.2f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityTest entity) 
	{
		return TEXTURE;
	}
}
