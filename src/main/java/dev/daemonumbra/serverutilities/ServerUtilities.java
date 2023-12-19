package dev.daemonumbra.serverutilities;

import dev.daemonumbra.serverutilities.init.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ServerUtilities.MOD_ID)
public class ServerUtilities
{
    public static final String MOD_ID = "serverutilities";

    public ServerUtilities()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(Commands::registerCommands);
    }
}
