package flxxd.xCore.commands;

import com.google.common.collect.Lists;
import flxxd.xCore.Main;
import flxxd.xCore.helpers.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.mrbrikster.chatty.api.ChattyApi;

import java.util.List;

public class Core extends AbstractCommand {
    public Core () {
        super("core");
    }

    @Override
    public void execute (CommandSender sender, String label, String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());
        assert player != null;

        if (args.length == 0) {
            player.sendMessage(Main.getValue("chat.core.help"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("xCore.reload")) {
                Main.getInstance().reloadConfig();

                player.sendActionBar(Main.getValue("actionBars.core.reload"));
                return;
            }
        }

        if (args[0].equalsIgnoreCase("broadcast") || args[0].equalsIgnoreCase("bc")) {
            if (player.hasPermission("xCore.broadcast") || player.hasPermission("xCore.bc")) {
                ChattyApi chatty = ChattyApi.get();
                String broadcast = Main.getValue("chat.broadcast.format")
                        .replace("%user%", sender.getName())
                        .replace("%text%", args[1]);

                chatty.getChat("global").get().sendMessage(broadcast);
                return;
            }
        }

        if (args[0].equalsIgnoreCase("motd")) {
            if (player.hasPermission("xCore.motd")) {
                List<String> list = Main.getList("motd");
                String msg = "";

                if (args.length == 1) {
                    player.sendActionBar(Main.getValue("actionBars.motd.argNotProvided"));
                    return;
                }

                for (String s : args) {
                    if (!s.equals(args[0])) {
                        if (s == args[args.length - 1]) {
                            msg = msg + s;
                        } else {
                            msg = msg + s + " ";
                        }
                    }
                }

                list.add(msg);

                Main.getInstance().getConfig().set("motd", list);
                Main.getInstance().saveConfig();

                player.sendActionBar(Main.getValue("actionBars.motd.new"));
                return;
            }
        }

        player.sendActionBar(Main.getValue("actionBars.global.unknownCommand"));
    }

    public List<String> complete (CommandSender sender, String[] args) {
        if (args.length == 1) return Lists.newArrayList("reload", "motd", "broadcast");
        return Lists.newArrayList();
    }
}
