package systems.brn.tweaks.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class TweaksPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TweakListener(this), this);
    }
}
