package systems.brn.tweaks.paper;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public class TweaksBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        try {
            Rules.register();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not register gamerules", e);
        }
    }
}
