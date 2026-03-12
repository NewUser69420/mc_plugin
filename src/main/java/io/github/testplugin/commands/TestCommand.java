package io.github.testplugin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TestCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("flyspeed")
                .then(Commands.argument("speed", FloatArgumentType.floatArg(0, 1.0f))
                        .executes(TestCommand::runFlySpeedLogic)
                );
    }

    private static int runFlySpeedLogic(CommandContext<CommandSourceStack> ctx) {
        float speed = FloatArgumentType.getFloat(ctx, "speed"); //get speed from context
        CommandSender sender = ctx.getSource().getSender();           //get sender
        Entity executor = ctx.getSource().getExecutor();              //get executor (might be sender)

        //check if executor is a player
        if (!(executor instanceof Player player)) {
            sender.sendPlainMessage("Only players can execute this command!");
            return Command.SINGLE_SUCCESS;
        }

        //do logic
        player.setFlySpeed(speed);

        if (sender == executor) {
            player.sendPlainMessage("Set your fly speed to " + speed);
            return Command.SINGLE_SUCCESS;
        }

        //if executed by something else than the sender
        sender.sendRichMessage("Set <playername>'s flight speed to " + speed);  //! '<playername>' is printed literal !
        player.sendPlainMessage("Flight speed has been set to " + speed);
        return Command.SINGLE_SUCCESS;
    }
}
