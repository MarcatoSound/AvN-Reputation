package net.playavalon.avnrep.mythic;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import net.playavalon.avnrep.data.player.AvalonPlayer;
import net.playavalon.avnrep.data.player.Reputation;
import net.playavalon.avnrep.data.reputation.Faction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static net.playavalon.avnrep.AvNRep.plugin;

public class ReputationMechanic implements ITargetedEntitySkill {

    private MythicLineConfig config;
    protected Faction faction;
    protected double amount;

    public ReputationMechanic(MythicLineConfig config) {
        this.config = config;

        faction = plugin.repManager.get(config.getString(new String[] {"faction", "f"}, ""));
        amount = config.getDouble(new String[] {"amount", "a"}, 1);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {

        if (faction == null) return SkillResult.CONDITION_FAILED;

        LivingEntity bEntity = (LivingEntity) BukkitAdapter.adapt(target);
        if (!(bEntity instanceof Player)) return SkillResult.CONDITION_FAILED;
        Player player = (Player) bEntity;
        AvalonPlayer aPlayer = plugin.getAvalonPlayer(player);

        Reputation rep = aPlayer.getReputation(faction.getName());

        rep.addRepValue(amount);

        return SkillResult.SUCCESS;
    }

}
