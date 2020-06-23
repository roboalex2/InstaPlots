package at.instaplots.commands;

import at.instabase.apis.UUIDFetcher;
import at.instaplots.Main;
import at.instaplots.plot.PlotManager;
import com.google.inject.Inject;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CmdPlot implements CommandExecutor {

    private Main plugin;
    private PlotManager plotMgnt;

    private static final int DEFAULT_PLOT_SIZE = 30;

    @Inject
    public CmdPlot(Main plugin, PlotManager plotMgnt) {
        this.plugin = plugin;
        this.plotMgnt = plotMgnt;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        final Player player = sender instanceof Player ? (Player) sender : null;

        if(args.length == 0) {
            // TODO Open GUI or show help.
            return true;
        }

        if("create".equalsIgnoreCase(args[0]) || "c".equalsIgnoreCase(args[0])) {
            try {
                createPlot(sender, player, args);
            } catch (Exception e) {
                sender.sendMessage("§7[§e!§7] Erstellen nicht möglich. " + e.getMessage());
            }
        } else if("home".equalsIgnoreCase(args[0]) || "h".equalsIgnoreCase(args[0])) {

        }



        return true;
    }


    private void createPlot(CommandSender sender, Player player, String[] args) {
        if((!sender.hasPermission("instaplots.create")) || player == null){
            sendPermissionDeniedMessage(sender);
            return;
        }

        if(args.length > 1 && sender.hasPermission("instaplots.create.others")) {
            (new BukkitRunnable() {
                @Override
                public void run() {
                    UUID target = null;
                    try {
                        target = UUIDFetcher.getUUID(args[1]);
                    } catch (Exception e) {
                        sendPlayerNotFoundMessage(sender);
                        return;
                    }
                    int size = DEFAULT_PLOT_SIZE;
                    if(args.length >= 3) {
                        try {
                            size = Integer.parseInt(args[2]);
                            if(size <= 0) throw new NumberFormatException("Darf nicht kleiner oder gleich 0 sein.");
                        } catch (NumberFormatException e) {
                            sendInvalidNumberMessage(sender);
                            return;
                        }
                    }
                    plotMgnt.createPlot(target, player.getLocation(), size);
                }
            }).runTaskAsynchronously(plugin);
        } else {
            (new BukkitRunnable() {
                @Override
                public void run() {
                    plotMgnt.createPlot(player.getUniqueId(), player.getLocation(), DEFAULT_PLOT_SIZE);
                }
            }).runTaskAsynchronously(plugin);
        }
    }


    private void sendPermissionDeniedMessage(CommandSender sender) {
        sendMessage(sender, "§7[§e!§7] §cDu hast keine Berechtigung für den Befehl.");
    }

    private void sendPlayerNotFoundMessage(CommandSender sender) {
        sendMessage(sender, "§7[§e!§7] §cDer angegebene Spieler wurde nicht gefunden.");
    }

    private void sendInvalidNumberMessage(CommandSender sender) {
        sendMessage(sender, "§7[§e!§7] §cEs wurde keine gültige Zahl eingegeben.");
    }

    private void sendMessage(CommandSender sender, String message) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                sender.sendMessage(message);
            }
        }).runTask(plugin);
    }
}
