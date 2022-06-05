package flxxd.xCore.helpers;

import flxxd.xCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;

public class EventListener implements Listener {
    double[] tps = Bukkit.getServer().getTPS();
    double stringTPS = Math.ceil(tps[2]);

    @EventHandler
    public void onJoin (PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (stringTPS > 20) stringTPS = stringTPS-1;

        String title = Main.getValue("events.join.title")
                .replace("%user%", player.getName())
                .replace("%tps%", ("" + stringTPS)
                        .replace("[", "")
                        .replace("]", ""));
        String subtitle = Main.getValue("events.join.subtitle")
                .replace("%user%", player.getName())
                .replace("%tps%", ("" + stringTPS)
                        .replace("[", "")
                        .replace("]", ""));

        if (!Main.getValue("events.join.active").equals("disabled")) {
            player.sendTitle(title, subtitle);
        }

        if (e.getPlayer().isOp()) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (!Main.getValue("events.adminJoinEvent.active").equals("disabled")) {
                    pp.playSound(pp.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100.0F, 100.0F);
                    pp.sendTitle(Main.getValue("events.adminJoinEvent.title").replace("%user%", player.getName()), "");
                }
            }
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        if (stringTPS > 20) stringTPS = stringTPS-1;

        List<String> list = Main.getList("motd");
        String motd = list.get((int)Math.floor(Math.random() * (double)list.size()))
                .replace("%tps%", ("" + stringTPS)
                        .replace("[", "")
                        .replace("]", ""))
                .replace("&", "§");

        e.setMotd(motd);
    }
}

