package com.nullptr.mod.events;
//import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraft.client.entity.EntityPlayerSP;

@Mod.EventBusSubscriber(Side.CLIENT)
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) { // Use the correct event parameter
        EntityPlayer player = event.player;
        if (player.inventory.armorItemInSlot(0) != null) { // Use `player.inventory.armorItemInSlot()` instead of `getCurrentArmor()`
            ItemStack boots = player.inventory.armorItemInSlot(0);

            if (boots.getItem() == Items.DIAMOND_BOOTS) { // Use `Items.DIAMOND_BOOTS` instead of `Items.diamond_boots`
                World world = player.world;
                int i = MathHelper.floor(player.posX);
               // int j = MathHelper.floor(player.boundingBox.minY - 1);
		int j = MathHelper.floor(player.getEntityBoundingBox().minY - 1); // Use `getEntityBoundingBox()` to get the bounding box 
                int k = MathHelper.floor(player.posZ);
                Material m = world.getBlockState(new BlockPos(i, j, k)).getMaterial(); // Use `getBlockState()` instead of `getBlock()`
                boolean flag = (m == Material.WATER); // Use `Material.WATER` instead of `Material.water`

                if (flag && player.motionY < 0.0D) {
                    player.posY += -player.motionY;
                    player.motionY = 0.0D;
                    player.fallDistance = 0.0F;
                }
            }
        }
    }
    @SubscribeEvent
    public void doTheOtherThing(RenderGameOverlayEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledRes = new ScaledResolution(mc);
        mc.getTextureManager().bindTexture(new ResourceLocation("mod", "textures/items/obsidian_ingot.png"));
        mc.ingameGUI.drawTexturedModalRect(scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() / 2, 0, 0, 59, 8);
	mc.getTextureManager().bindTexture(Gui.ICONS);
	Gui.drawModalRectWithCustomSizedTexture(100, 100, 100, 100, 16, 16, 16, 16);
    }
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = event.getMessage().getUnformattedText();
        if (message.toLowerCase().contains("tree")) {
	    EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
            BlockPos playerPos = event.playerSP.getPosition();
            World world = event.playerSP.getEntityWorld();
            generateTree(world, playerPos);
        }
    }
    private void generateTree(World world, BlockPos pos) {
        Random rand = new Random();
        int height = 4 + rand.nextInt(3); // Высота дерева от 4 до 6 блоков
        int trunkHeight = height - 1; // Высота ствола = высота дерева - 1

        // Генерация ствола дерева
        for (int y = 0; y < trunkHeight; y++) {
            world.setBlockState(pos.up(y), Blocks.LOG.getDefaultState());
        }

        // Генерация верхушки дерева
        for (int y = trunkHeight; y <= height + 1; y++) {
            for (int xOffset = -2; xOffset <= 2; xOffset++) {
                for (int zOffset = -2; zOffset <= 2; zOffset++) {
                    if (Math.abs(xOffset) != 2 || Math.abs(zOffset) != 2) {
                        world.setBlockState(pos.add(xOffset, y, zOffset), Blocks.LEAVES.getDefaultState());
                    }
                }
            }
        }

        // Генерация дополнительных листьев на верхушке дерева
        for (int yOffset = height - 3; yOffset <= height + 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                     if (Math.abs(xOffset) != 1 || Math.abs(zOffset) != 1) {
                        world.setBlockState(pos.add(xOffset, yOffset, zOffset), Blocks.LEAVES.getDefaultState());
                     }
                }
            }
        }
    }
}