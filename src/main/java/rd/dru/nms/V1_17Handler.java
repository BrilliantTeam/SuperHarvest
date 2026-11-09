package rd.dru.nms;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import rd.dru.PlayerManager;
import rd.dru.SuperHarvest;
import rd.dru.PlayerManager.OptionType;

public class V1_17Handler implements NMSHandler, Listener{
	
	@Override
	public boolean breakBlock(Player p, Block b) {
		// TODO Auto-generated method stub
		return p.breakBlock(b);
	}
	
	@Override
	public ItemStack getItemInHand(Player p) {
		// TODO Auto-generated method stub
		return p.getInventory().getItemInMainHand();
	}
	
	@Override
	public void crackBlock(Block b, Material type) {
		// TODO Auto-generated method stub
		b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation().add(0.5,0.5,0.5), 25, 1, 0.1,
				0.1, 0.1, type.createBlockData());
	} 
	
	@Override
	public void crackCrop(Block b, Material type) {
		if(type.equals(Material.NETHER_WART))
			crackBlock(b, Material.NETHER_WART);
		else
			crackBlock(b, Material.LEGACY_CROPS);
	}
	
	@Override
	public void playSound(Block b, NSound sound) {
		// TODO Auto-generated method stub
//		Sound e;
//		switch(sound) {
//		case Farm:
//			if(b.getType().equals(Material.NETHER_WART))
//				e = Sound.BLOCK_NETHER_WART_BREAK;
//			else
//				e = Sound.BLOCK_CROP_BREAK;
//			break;
//		case Ore:
//			e = Sound.BLOCK_STONE_BREAK;
//			break;
//		case Tree:
//			e = Sound.BLOCK_WOOD_BREAK;	
//			break;
//		default:
//			e = Sound.BLOCK_GRASS_BREAK;
//			break;
//			
//		}
		b.getWorld().playSound(b.getLocation(), b.getBlockData().getSoundGroup().getBreakSound(), 1, 1);	
	}
	
	@Override
	public void playSound(Block b, Material sound) {
		b.getWorld().playSound(b.getLocation(), sound.createBlockData().getSoundGroup().getBreakSound(), 1, 1);	
	}
	
	@Override
	public boolean isCrop(Block b) {
		// TODO Auto-generated method stub
		return b.getBlockData() instanceof Ageable;
	}
	
	@Override
	public boolean isFarmLnad(Block b) {
		// TODO Auto-generated method stub
		return b.getType().equals(Material.FARMLAND)||b.getType().equals(Material.SOUL_SAND);
	}
	
	@Override
	public boolean canCropHarvest(Block b) {
		if(!(SuperHarvest.nms.isCrop(b)))
			return false;
		Ageable age = (Ageable)b.getBlockData();
		return age.getAge()== age.getMaximumAge();
		
	}

	@Override
	public void actionBarMes(Player p, String mes) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(mes));
	}

	@Override
	public void titleBarMes(Player p, String mes) {
		p.sendTitle(" ", mes, 0, 20, 10);
	}
	
	@Override
	public void init(SuperHarvest sh) {
		Bukkit.getPluginManager().registerEvents(this, sh);
		Bukkit.getOnlinePlayers().forEach(p->onJoin(new PlayerJoinEvent(p, "player join")));
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		PersistentDataContainer data = p.getPersistentDataContainer();
		data.set(new NamespacedKey(SuperHarvest.getInstance(), "Farming"), PersistentDataType.BYTE, convert(PlayerManager.isEnable(p, OptionType.Farming)));
		data.set(new NamespacedKey(SuperHarvest.getInstance(), "Logging"), PersistentDataType.BYTE, convert(PlayerManager.isEnable(p, OptionType.Logging)));
		data.set(new NamespacedKey(SuperHarvest.getInstance(), "Mining"), PersistentDataType.BYTE, convert(PlayerManager.isEnable(p, OptionType.Mining)));
		data.set(new NamespacedKey(SuperHarvest.getInstance(), "Mode"), PersistentDataType.BYTE, convert(PlayerManager.isSneakingMode(p)));
	}
	
	private byte convert(boolean bt) {
		return (byte) (bt ? 1 : 0);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PersistentDataContainer data = p.getPersistentDataContainer();
		if(data.has(new NamespacedKey(SuperHarvest.getInstance(), "Farming"),PersistentDataType.BYTE)) {
			PlayerManager.set(p, OptionType.Farming, data.get(new NamespacedKey(SuperHarvest.getInstance(), "Farming"), PersistentDataType.BYTE)==1);
			PlayerManager.set(p, OptionType.Logging, data.get(new NamespacedKey(SuperHarvest.getInstance(), "Logging"), PersistentDataType.BYTE)==1);
			PlayerManager.set(p, OptionType.Mining, data.get(new NamespacedKey(SuperHarvest.getInstance(), "Mining"), PersistentDataType.BYTE)==1);

			if(PlayerManager.isSneakingMode(p)!=(data.get(new NamespacedKey(SuperHarvest.getInstance(), "Mode"), PersistentDataType.BYTE)==1))
				PlayerManager.toggleMode(p);
		}
	}


	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p->{
			onLeave(new PlayerQuitEvent(p, "player leave"));	
		});
	}
}
