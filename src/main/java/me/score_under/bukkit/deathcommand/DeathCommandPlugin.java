package me.score_under.bukkit.deathcommand;

import java.util.Set;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin to execute a command on player death, as console
 * 
 * @author Score_Under
 */
public class DeathCommandPlugin extends JavaPlugin implements Listener {
    private CommandSender mySender;
    private String command;

    @Override
    public void onEnable() {
        mySender = new DeathCommandSender();
        final FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        command = config.getString("command");
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        final Player deadPerson = (Player)event.getEntity(); //I see dead people
        
        if (deadPerson.hasPermission("deathcommand.bypass")) {
            return;
        }
        
        final String personalizedCommand = command.replaceAll("<player>", deadPerson.getName());
        getServer().dispatchCommand(mySender, personalizedCommand);
    }
    
    public class DeathCommandSender implements ConsoleCommandSender {
        private final PermissibleBase perms = new PermissibleBase(this);

        public void sendMessage(final String msg) {
            getLogger().info("Got message from command: " + msg);
        }

        public Server getServer() {
            return getServer();
        }

        public String getName() {
            return "DeathCommand";
        }

        public boolean isPermissionSet(final String perm) {
            return perms.isPermissionSet(perm);
        }

        public boolean isPermissionSet(final Permission perm) {
            return perms.isPermissionSet(perm);
        }

        public boolean hasPermission(final String perm) {
            return perms.hasPermission(perm);
        }

        public boolean hasPermission(final Permission perm) {
            return perms.hasPermission(perm);
        }

        public PermissionAttachment addAttachment(final Plugin plugin) {
            return perms.addAttachment(plugin);
        }

        public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value) {
            return perms.addAttachment(plugin, name, value);
        }

        public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value, final int ticks) {
            return perms.addAttachment(plugin, name, value, ticks);
        }

        public PermissionAttachment addAttachment(final Plugin plugin, final int i) {
            return perms.addAttachment(plugin, i);
        }

        public void removeAttachment(final PermissionAttachment pa) {
            perms.removeAttachment(pa);
        }

        public void recalculatePermissions() {
            perms.recalculatePermissions();
        }

        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
            return perms.getEffectivePermissions();
        }

        public boolean isOp() {
            return true;
        }

        public void setOp(final boolean op) {
            throw new UnsupportedOperationException("Can't set op status of DeathCommand!");
        }
    }
}
