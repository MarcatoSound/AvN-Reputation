package net.playavalon.avnrep.mythic;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicListener implements Listener {

    @EventHandler
    public void onMythicMechanicEvent(MythicMechanicLoadEvent e) {

        if(e.getMechanicName().equalsIgnoreCase("REPUTATION") || e.getMechanicName().equalsIgnoreCase("REP"))	{
            SkillMechanic mechanic = new ReputationMechanic(e.getConfig());
            e.register(mechanic);
        }

    }

}
