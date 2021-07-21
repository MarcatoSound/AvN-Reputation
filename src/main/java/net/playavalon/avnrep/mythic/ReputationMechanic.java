package net.playavalon.avnrep.mythic;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.Reputation;
import net.playavalon.avnrep.data.reputation.Faction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static net.playavalon.avnrep.AvNRep.plugin;

public class ReputationMechanic extends SkillMechanic implements ITargetedEntitySkill {

    protected Faction faction;
    protected double amount;

    public ReputationMechanic(MythicLineConfig config) {
        super (config.getLine(), config);

        faction = plugin.repManager.get(config.getString(new String[] {"faction", "f"}, ""));
        amount = config.getDouble(new String[] {"amount", "a"}, 1);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {

        if (faction == null) return false;

        LivingEntity bEntity = (LivingEntity) BukkitAdapter.adapt(target);
        if (!(bEntity instanceof Player)) return false;
        Player player = (Player) bEntity;
        AvalonPlayer aPlayer = plugin.getAvalonPlayer(player);

        Reputation rep = aPlayer.getReputation(faction.getName());

        rep.addRepValue(amount);

        return true;
    }

}
