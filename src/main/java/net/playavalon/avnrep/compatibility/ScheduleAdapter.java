package net.playavalon.avnrep.compatibility;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

import static net.playavalon.avnrep.AvNRep.plugin;

public class ScheduleAdapter {

    @Getter @Setter private static boolean folia;


    //===============//
    // ASYNC METHODS //
    //===============//
    public static void runTaskAsync(Runnable task) {
        if (folia) {
            Bukkit.getAsyncScheduler().runNow(plugin, scheduledTask -> task.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, bukkitTask -> task.run());
        }
    }

    public static void runTaskLaterAsync(Runnable task, long delay) {
        if (folia) {
            long ms = delay * 50;
            Bukkit.getAsyncScheduler().runDelayed(plugin, scheduledTask -> task.run(), ms, TimeUnit.MILLISECONDS);
        } else {
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, bukkitTask -> task.run(), delay);
        }
    }

    public static void runTaskTimerAsync(Runnable task, long delay, long interval) {
        if (folia) {
            long msDelay = delay * 50;
            long msInterval = interval * 50;
            Bukkit.getAsyncScheduler().runAtFixedRate(plugin, scheduledTask -> task.run(), msDelay, msInterval, TimeUnit.MILLISECONDS);
        } else {
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, bukkitTask -> task.run(), delay, interval);
        }
    }

    //==============//
    // SYNC METHODS //
    //==============//
    public static void runTask(Runnable task) {
        if (folia) {
            Bukkit.getGlobalRegionScheduler().run(plugin, scheduledTask -> task.run());
        } else {
            Bukkit.getScheduler().runTask(plugin, bukkitTask -> task.run());
        }
    }

    public static void runTaskLater(Runnable task, long delay) {
        if (folia) {
            Bukkit.getGlobalRegionScheduler().runDelayed(plugin, scheduledTask -> task.run(), delay);
        } else {
            Bukkit.getScheduler().runTaskLater(plugin, bukkitTask -> task.run(), delay);
        }
    }

    public static void runTaskTimer(Runnable task, long delay, long interval) {
        if (folia) {
            Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, scheduledTask -> task.run(), delay, interval);
        } else {
            Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> task.run(), delay, interval);
        }
    }


}
