package com.nullptr.astgrief;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import java.util.Random;

class heal implements CommandExecutor { 
       @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
           if (sender instanceof Player) {
               Player player = (Player) sender;
               player.setHealth(player.getMaxHealth());
               player.sendMessage(ChatColor.GREEN + "Вы восстановили свое здоровье до максимума!");
              // return true; 
           }
           return true;
       } 
}

class thor implements CommandExecutor { 
       @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
           return true; 
       } 
}

class column implements CommandExecutor { 
       @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
           return true; 
       } 
}

class spawnDiamonds implements CommandExecutor { 
       private final int WATER_HEIGHT = 100; // Высота воды над миром (увеличено до 100)
       private Random random = new Random();
       private World world;
       private int getRandomCoordinate() {
           return random.nextInt(41) + 80; // Генерируем число от 80 до 120
       }
       private void droppedDiamonds(Location position) {
           world = Bukkit.getServer().getWorlds().get(0);
           world.dropItemNaturally(position, new ItemStack(Material.DIAMOND));
       }
       @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
           if (sender instanceof Player) {
               Player player = (Player) sender;
               int x = (int) player.getLocation().getX();
               int y = getRandomCoordinate();
               int z = (int) player.getLocation().getZ();
               Location position = new Location(world, x, y + WATER_HEIGHT, z);
               sender.sendMessage(position.toString());
               for (int i = 0; i < 15; i++) {
                    position.setX(position.getX() + 1);
                    position.setZ(position.getZ() + 1);
                    droppedDiamonds(position);
               }
           }
           return true; 
      } 
}

class helpCommand implements CommandExecutor { 
       @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
            sender.sendMessage(ChatColor.RED + "Плагин создан командой AstGrief.");
            sender.sendMessage(ChatColor.YELLOW + "Если у вас есть вопросы, пожалуйста обратитесь к Nullptr#4001");
            sender.sendMessage(ChatColor.BLUE + "Authors:");
            sender.sendMessage(ChatColor.BOLD + "- Vendik");
            sender.sendMessage(ChatColor.UNDERLINE + "- NullPtr");
            return true; 
       } 
}

class boomCommand implements CommandExecutor { 
       @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
           return true; 
       } 
}

public class Main extends JavaPlugin implements Listener {
    private Server server = Bukkit.getServer();
    private World world;
    private Plugin plugin;

    @Override
    public void onEnable() {
        server.getLogger().info("AstGrief plugin by NullPtr Enabled!");
        world = server.getWorlds().get(0);
        plugin = server.getPluginManager().getPlugin("astgrief");
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("kheal").setExecutor(new heal()); 
        getCommand("kdiamonds").setExecutor(new spawnDiamonds()); 
        getCommand("khelp").setExecutor(new helpCommand()); 
        getCommand("kboom").setExecutor(new boomCommand()); 
        getCommand("kthor").setExecutor(new thor()); 
        getCommand("kforest").setExecutor(new column());
        items();
    }

    @Override
    public void onDisable() {
        server.getLogger().info("AstGrief plugin disabled!");
    }

    private void items() {
        ItemStack customItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = customItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.YELLOW + "Кастомный Меч");
        customItem.setItemMeta(itemMeta);
        customItem.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);

        NamespacedKey key = new NamespacedKey(this, "custom_item");
        ShapedRecipe recipe = new ShapedRecipe(key, customItem);
        recipe.shape(" # ", " # ", " / ");
        recipe.setIngredient('#', Material.MAGMA);
        recipe.setIngredient('/', Material.STICK);
        Bukkit.addRecipe(recipe);
    }

    private void playerteleport(Player shooter, Location location) {
        shooter.teleport(location);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        bm.setDisplayName(ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN + "" + ChatColor.BOLD + "Приветствуем на нашем сервере!"));
        bm.addPage("Введение. О сервере - ...");
        bm.addPage("Приятной игры на нашем проекте! Конец.");
        bm.addPage("English text");
        bm.setAuthor("NullPtr");
        bm.setLore(java.util.Arrays.asList("This book contains " + bm.getPageCount() + " pages."));
        book.setItemMeta(bm);
        player.getInventory().addItem(book);

        ItemStack bow = new ItemStack(Material.BOW, 1);
        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
        ItemMeta itemMeta = bow.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "Teleport Bow");
        itemMeta.setUnbreakable(true);
        bow.setItemMeta(itemMeta);
        player.getInventory().addItem(bow);

        // Спавн куриц
        Location player_location = player.getLocation().add(0, 20, 0);
        player.sendMessage(ChatColor.RED + "Food has been spawned for you. Welcome to our server " + player.getName());
        for (int i = 0; i < 3; i++) {
           // spawn(player_location);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata("teleport_bow")) {
            Player shooter = (Player) event.getEntity().getShooter();
            Vector direction = shooter.getLocation().getDirection();
            Location location = event.getEntity().getLocation();
            location.setDirection(direction);
            playerteleport(shooter, location);
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntityType() == EntityType.ARROW) {
            if (event.getEntity().getShooter() instanceof Player) {
                Player shooter = (Player) event.getEntity().getShooter();
                ItemStack bowItem = shooter.getInventory().getItemInMainHand();
                if (bowItem.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Teleport Bow")) {
                    event.getEntity().setMetadata("teleport_bow", new FixedMetadataValue(this, true));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        Player player = event.getPlayer();
        player.sendMessage(msg);

        // Добавьте код для обработки других команд или сообщений, если необходимо.
        // Например, разделение команд и подкоманд для дальнейшей обработки.
        // String[] cmds = msg.split(" ");
        // String cmd = cmds[0].toLowerCase();
        // String subcmd = cmds.length > 1 ? cmds[1].toLowerCase() : "";
        // if (cmd.equals("cmd_name")) {
        //     // выполнение команды
        // } else if (cmd.equals("another_cmd")) {
        //     // выполнение другой команды
        // }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1));
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        if (msg.equalsIgnoreCase("/health")) {
            //health(event.getPlayer());
            event.setCancelled(true); // Отменяем выполнение команды /health
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        if (blockType == Material.DIAMOND_ORE) {
            player.sendMessage(ChatColor.GREEN + "Вы нашли алмазную руду!");
        }
    }

    // ... (existing code remains the same)

    private void addPotion(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 1));
    }

  /*  private void explosion(Player caller, String label, String[] params) {
        Location location = location(caller);
        float power = 2.0f;
        if (params != null && params.length > 0) {
            try {
                power = Float.parseFloat(params[0]);
            } catch (NumberFormatException e) {
                // обработка ошибки, если параметр power не является числом
            }
        }
        location.getWorld().createExplosion(location, power, true);
    }

    private void bolt(Player caller, String label, String[] params) {
        Location location = location(caller);
        location.getWorld().strikeLightning(location);
    }

    private Player getPlayer(String name) {
        return server.getPlayer(name);
    }*/

   /* public void fireworks(World world, double x, double y, double z, int power, boolean withTrail) {
        Firework fw = (Firework) world.spawnEntity(new Location(world, x, y, z), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(power);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.BLUE, Color.RED).trail(withTrail).build());
        fw.setFireworkMeta(fwm);
    }*/

    public void particle(World world, double x, double y, double z, Particle particleType,
                         int count, double offsetX, double offsetY, double offsetZ,
                         double speed, Object data) {
        world.spawnParticle(particleType, x, y, z, count, offsetX, offsetY, offsetZ, speed, data);
    }

    public void respawnMessage(Player player) {
        getServer().broadcastMessage(ChatColor.GREEN.toString() + "Respawn starts. " +
                player.getName() + " died at location " + player.getLocation().getX() +
                " " + player.getLocation().getY() + " " + player.getLocation().getZ());
    }

    public void boomCommand(Player caller) {
        //explosion(caller.getLocation(), 2);
      //  fireworks(caller.getLocation(), 0, 3, true);
        caller.sendMessage(ChatColor.RED.toString() + "BOOM!!!");
    }

  /*  public void column(Player caller) {
        Location beginning = lookingat(caller.getLocation());
        double[] position = {beginning.getX() + 10, beginning.getY(), beginning.getZ()};
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 100; i++) {
                setBlock(new Location(world, position[0], position[1], position[2]), Material.WOOD);
                position[randInt(0, 2)] += randInt(-1, 1);
                position[1] += 1;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public void thor(Player caller) {
        particle(caller.getWorld(), caller.getLocation().getX(), caller.getLocation().getY(),
                caller.getLocation().getZ(), Particle.SPELL, 1, 0.0, 0.0, 0.0, 1.0, null);
        //bolt(caller.getLocation());
    }
}
