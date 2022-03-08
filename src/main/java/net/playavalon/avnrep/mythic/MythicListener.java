package net.playavalon.avnrep.mythic;

import io.lumine.mythic.api.skills.ISkillMechanic;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicListener implements Listener {

    @EventHandler
    public void onMythicMechanicEvent(MythicMechanicLoadEvent e) {

        if(e.getMechanicName().equalsIgnoreCase("REPUTATION") || e.getMechanicName().equalsIgnoreCase("REP"))	{
            ISkillMechanic mechanic = new ReputationMechanic(e.getConfig());
            e.register(mechanic);
        }

    }

}
