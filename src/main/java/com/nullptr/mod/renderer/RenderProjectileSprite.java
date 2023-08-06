package com.nullptr.mod.renderer;

import com.nullptr.mod.Main;
//import com.lycanitesmobs.core.entity.*;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderProjectileSprite extends Render {
    private float scale;
    private int renderTime = 0;
    Class projectileClass;
    
    // Laser Box:
    protected ModelBase laserModel = new ModelBase() {};
    private ModelRenderer laserBox;
    
    // ==================================================
    //                     Constructor
    // ==================================================
    public RenderProjectileSprite(RenderManager renderManager, Class projectileClass) {
    	super(renderManager);
        this.projectileClass = projectileClass;
    }
    
    
    // ==================================================
    //                     Do Render
    // ==================================================
    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float par9) {
    	if(this.renderTime++ > Integer.MAX_VALUE - 1)
            this.renderTime = 0;
		this.renderProjectile(entity, x, y, z, par8, par9);
    }
    
    
    // ==================================================
    //                 Render Projectile
    // ==================================================
    public void renderProjectile(Entity entity, double x, double y, double z, float par8, float par9) {
    	double scale = 1d;

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(scale, scale, scale);

        this.bindTexture(texture);
      //  this.renderTexture(Tessellator.getInstance(), entity);

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }


    // ==================================================
    //                  Render Texture
    // ==================================================
    private void renderTexture(Tessellator tessellator, Entity entity) {
        double minU = 0;
        double maxU = 1;
        double minV = 0;
        double maxV = 1;
        double textureWidth = 0.5D;
        double textureHeight = 0.5D;
        double offsetY = 0;

        //GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-scale / 2, -scale / 2, -scale / 2);

        if(this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

        vertexbuffer.pos(-textureWidth, -textureHeight + (textureHeight / 2) + offsetY, 0.0D)
                .tex(minU, maxV)
                .normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(textureWidth, -textureHeight + (textureHeight / 2) + offsetY, 0.0D)
                .tex(maxU, maxV)
                .normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(textureWidth, textureHeight + (textureHeight / 2) + offsetY, 0.0D)
                .tex(maxU, minV)
                .normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(-textureWidth, textureHeight + (textureHeight / 2) + offsetY, 0.0D)
                .tex(minU, minV)
                .normal(0.0F, 1.0F, 0.0F).endVertex();

        tessellator.draw();
    }
    
    
    // ==================================================
    //                 Render Laser
    // ==================================================
    public void renderLaser(double x, double y, double z, float par8, float par9, float scale) {
    	// Create Laser Model If Null:
    	if(this.laserBox == null) {
    		laserBox = new ModelRenderer(laserModel, 0, 0);
    		laserBox.addBox(-(scale / 2), -(scale / 2), 0, (int)scale, (int)scale, 16);
    		laserBox.rotationPointX = 0;
    		laserBox.rotationPointY = 0;
    		laserBox.rotationPointZ = 0;
    	}

    	float factor = (float)(1.0 / 16.0);
    	float lastSegment = 0;
	//float length = 0;

    	// Render Laser Beam:
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.translate(x, y, z);
    	//this.bindTexture(this.getLaserTexture(entity));

        // Rotation:
		//float[] angles = new float[] {0, 0, 0, 0};
		//float dx = (float)(entity.getLaserEnd().x - entity.posX);
		//float dy = (float)(entity.getLaserEnd().y - entity.posY);
		//float dz = (float)(entity.getLaserEnd().z - entity.posZ);
		//angles[0] = (float)Math.toDegrees(Math.atan2(dz, dy)) - 90;
		//angles[1] = (float)Math.toDegrees(Math.atan2(dx, dz));
		//angles[2] = (float)Math.toDegrees(Math.atan2(dx, dy)) - 90;

		// Distance based x/z rotation:
		//float dr = (float)Math.sqrt(dx * dx + dz * dz);
		//angles[3] = (float)Math.toDegrees(Math.atan2(dr, dy)) - 90;
       // GlStateManager.rotate(angles[1], 0, 1, 0);
      //  GlStateManager.rotate(angles[3], 1, 0, 0);
        
    	
        //GlStateManager.scale((length - lastSegment), 1, 1);
        this.laserBox.render(factor);

        GlStateManager.popMatrix();
    }


	// ==================================================
	//                 Render Old Laser
	// ==================================================
	public void renderOldLaser(/*BaseProjectileEntity entity, LaserEndProjectileEntity laserEnd, */double x, double y, double z, float par8, float par9, float scale, float length) {
		// Create Laser Model If Null:
		if(this.laserBox == null) {
			laserBox = new ModelRenderer(laserModel, 0, 0);
			laserBox.addBox(-(scale / 2), -(scale / 2), 0, (int)scale, (int)scale, 16);
			laserBox.rotationPointX = 0;
			laserBox.rotationPointY = 0;
			laserBox.rotationPointZ = 0;
		}

		float factor = (float)(1.0 / 16.0);
		float lastSegment = 0;
		if(length <= 0)
			return;

		// Render Laser Beam:
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.translate(x, y, z);
		//this.bindTexture(this.getLaserTexture(entity));

		// Rotation:
		float[] angles = new float[] {0, 0, 0, 0};
		GlStateManager.rotate(angles[1], 0, 1, 0);
		GlStateManager.rotate(angles[3], 1, 0, 0);

		// Length:
		for(float segment = 0; segment <= length - 1; ++segment) {
			this.laserBox.render(factor);
			GlStateManager.translate(0, 0, 1);
			lastSegment = segment;
		}
		lastSegment++;
		GlStateManager.scale((length - lastSegment), 1, 1);
		this.laserBox.render(factor);

		GlStateManager.popMatrix();
	}
    
    
    // ==================================================
    //                       Visuals
    // ==================================================
    // ========== Get Texture ==========
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
    	return null;
    }

    // ========== Get Laser Texture ==========
}
