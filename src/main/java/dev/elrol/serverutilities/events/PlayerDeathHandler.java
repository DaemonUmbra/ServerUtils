package dev.elrol.serverutilities.events;

import dev.elrol.serverutilities.Main;
import dev.elrol.serverutilities.api.data.IPlayerData;
import dev.elrol.serverutilities.api.data.Location;
import dev.elrol.serverutilities.config.CommandConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerDeathHandler {
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IPlayerData data = Main.database.get(player.getUUID());
            if(CommandConfig.back_enable_on_death.get()) data.setPrevLoc(new Location(player));
        }
    }
}

