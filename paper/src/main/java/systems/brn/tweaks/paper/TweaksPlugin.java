package systems.brn.tweaks.paper;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TweaksPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TweakListener(this), this);
        getServer().getOnlinePlayers().forEach(LeashPackets::inject);
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            LeashPackets.eject(player);
        }
    }
}
