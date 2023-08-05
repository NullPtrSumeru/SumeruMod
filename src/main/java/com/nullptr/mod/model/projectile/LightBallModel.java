package com.nullptr.mod.model.projectile;

import com.nullptr.mod.Main;
import com.nullptr.mod.model.ModelCreatureObj;
//import com.nullptr.mod.renderer.layer.LayerCreatureBase;
//import com.nullptr.mod.renderer.layer.LayerCreatureEffect;
//import com.nullptr.mod.renderer.RenderCreature;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector4f;

@SideOnly(Side.CLIENT)
public class LightBallModel extends ModelCreatureObj {
	//LayerCreatureEffect ballGlowLayer;

	// ==================================================
  	//                    Constructors
  	// ==================================================
    public LightBallModel() {
        this(1.0F);
    }

    public LightBallModel(float shadowSize) {

		// Load Model:
		this.initModel("lightball", "projectile/lightball");
    }


	// ==================================================
	//             Add Custom Render Layers
	// ==================================================
	@Override
	public void addCustomLayers() {
		super.addCustomLayers();
		//this.ballGlowLayer = new LayerCreatureEffect(renderer, "", true, LayerCreatureEffect.BLEND.ADD.id, true);
		//renderer.addLayer(this.ballGlowLayer);
	}


	// ==================================================
	//                 Animate Part
	// ==================================================
	@Override
	public void animatePart(String partName, EntityLiving entity, float time, float distance, float loop, float lookY, float lookX, float scale) {
		super.animatePart(partName, entity, time, distance, loop, lookY, lookX, scale);
		this.rotate(loop * 8, 0, 0);
	}


	// ==================================================
	//                Can Render Part
	// ==================================================
	@Override
	public boolean canRenderPart(String partName, Entity entity, boolean trophy) {
		return null;
	}


	// ==================================================
	//                Get Part Color
	// ==================================================
	/** Returns the coloring to be used for this part and layer. **/
	public Vector4f getPartColor(String partName, Entity entity, boolean trophy, float loop) {
		float glowSpeed = 40;
		float glow = loop * glowSpeed % 360;
		float color = ((float)Math.cos(Math.toRadians(glow)) * 0.1f) + 0.9f;
		return new Vector4f(color, color, color, 1);
	}


	// ==================================================
	//                      Visuals
	// ==================================================
	@Override
	public void onRenderStart(Entity entity, boolean renderAsTrophy) {
		super.onRenderStart(layer, entity, renderAsTrophy);
		int i = 15728880;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
		GlStateManager.disableLighting();
	}

	@Override
	public void onRenderFinish(Entity entity, boolean renderAsTrophy) {
		super.onRenderFinish(layer, entity, renderAsTrophy);
		int i = entity.getBrightnessForRender();
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
		GlStateManager.enableLighting();
	}
}
