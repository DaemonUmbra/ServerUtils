package dev.elrol.serverutilities.init;

import dev.elrol.serverutilities.Main;
import dev.elrol.serverutilities.config.FeatureConfig;
import dev.elrol.serverutilities.libs.Logger;
import dev.elrol.serverutilities.libs.Methods;
import dev.elrol.serverutilities.libs.text.Msgs;
import dev.elrol.serverutilities.libs.text.TextUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerInit {

    private static final ScheduledThreadPoolExecutor EXECUTOR = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);

    private static final Runnable secondTask = () -> {
        //Logger.debug("Second Task");
        Main.serverData.tickJails();
    };

    private static final Runnable minuteTask = () -> {
        Logger.debug("Minute Task");
        Main.serverData.tickMutes();
        if(Main.bot.bot != null) {
            Main.bot.update();
        }
    };

    private static final Runnable fiveMinuteTask = () -> {
        Logger.debug("Five Minute Task");
        if(FeatureConfig.enable_economy.get()) Main.shopRegistry.save();
    };

    private static final Runnable clearlagTask = () -> {
        Logger.debug("Clearlag Timer Task");
        boolean hostile = FeatureConfig.clearlag_hostile.get();
        boolean passive = FeatureConfig.clearlag_passive.get();
        boolean items = FeatureConfig.clearlag_items.get();

        Main.mcServer.getPlayerList().getPlayers().forEach(
                p -> Main.textUtils.msg(p, Msgs.wipe_warn.get(
                hostile ? "Hostile" : "&8&mHostile",
                passive ? "Passive" : "&8&mPassive",
                items ? "Item" : "&8&mItem"
        )));
        EXECUTOR.schedule(() -> Methods.clearlag(hostile, passive, items),1, TimeUnit.MINUTES);
    };

    public static void init() {
        EXECUTOR.scheduleAtFixedRate(secondTask, 1, 1, TimeUnit.SECONDS);
        EXECUTOR.scheduleAtFixedRate(minuteTask, 1, 1, TimeUnit.MINUTES);
        EXECUTOR.scheduleAtFixedRate(fiveMinuteTask, 5, 5, TimeUnit.MINUTES);

        if(!FeatureConfig.auto_clearlag_enabled.get()) return;
        int freq = FeatureConfig.clearlag_frequency.get();
        EXECUTOR.scheduleAtFixedRate(clearlagTask, freq, freq, TimeUnit.MINUTES);
    }

    public static void shutdown() {
        EXECUTOR.shutdownNow();
    }

}
