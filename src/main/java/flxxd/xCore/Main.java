package flxxd.xCore;

import flxxd.xCore.commands.Core;
import flxxd.xCore.helpers.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable () {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        new Core();
    }

    @Override
    public void onDisable () {
        // Plugin shutdown logic
    }

    public static Main getInstance () {
        return instance;
    }
    public static String getValue (String value) {
        return Main.getInstance().getConfig().getString(value).replace("&", "§");
    }
    public static List<String> getList (String value) {
        return Main.getInstance().getConfig().getStringList(value);
    }
}
