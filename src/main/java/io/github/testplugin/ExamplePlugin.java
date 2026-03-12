package io.github.testplugin;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.testplugin.commands.TestCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        //register events
        Bukkit.getPluginManager().registerEvents(this, this);

        //register commands
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            //register test command
            LiteralCommandNode<CommandSourceStack> flyCommand = TestCommand.createCommand().build();
            commands.registrar().register(flyCommand);
        });
    }

//    @override
//    public void onDisable() {
//        //save settings
//    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }
}