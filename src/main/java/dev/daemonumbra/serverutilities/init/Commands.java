package dev.daemonumbra.serverutilities.init;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;

public class Commands
{
    private static CommandNode<CommandSourceStack> rootCommand;
    public static void registerCommands(RegisterCommandsEvent event)
    {
        rootCommand = event.getDispatcher().register(LiteralArgumentBuilder.<CommandSourceStack>literal("serverutilities").executes((context) -> {
            context.getSource().sendSuccess(Component.literal("Test Successful"), true);
            return 0;
        }).then(
                LiteralArgumentBuilder.<CommandSourceStack>literal("subcommand").executes((context) -> {
                    context.getSource().sendSuccess(Component.literal("Subcommand Successful"), true);
                    return 0;
                })
        ));
        // Alias su to serverutiltiies
        event.getDispatcher().register(LiteralArgumentBuilder.<CommandSourceStack>literal("su").redirect(rootCommand).executes(rootCommand.getCommand()));
    }
}
