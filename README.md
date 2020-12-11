# AvN-Reputation
Avalon Reputation is a reputation management system initially created for the Avalon Network Minecraft servers. Originally a private plugin, it has since been tweaked to support even deeper customization and manipulation via its own API. 

## Reputation Factions
Reputation factions are "groups" that the player can earn reputation and reputation levels in. Each faction is capable of unique parameters, including sources of earning/losing reputation, their max reputation levels, and how much reputation is required to level-up with them. All factions are created in the main `config.yml`
```
Reputations:
  # The name of a reputation faction.
  Example:
    # The maximum reputation level a player can get with this faction.
    MaxLevel: 20
    # The minimum amount of reputation required to level up the first time.
    FirstLevelCost: 25
    # The maximum amount of reputation required to level up the last time.
    LastLevelCost: 5000
    # A curve that manipulates how steeply or inversely the reputation cost for each level increases.
    RepCurve: -0.65
    # A list of reputation triggers that manipulate how the player gains or loses reputation with this faction.
    Sources:
    - KILL_ENDERMAN:5
    - KILL_MONSTER:1
    - BREAK_SPAWNER:100
    - CRAFT_DIAMOND_SWORD:15
```

## For Developers

### Maven Dependency Information
To use Avalon Reputation in your own plugins, add the following repository and dependency.

```
<repository>
    <id>AvN-Rep</id>
    <url>https://raw.github.com/destradious/AvN-Reputation/mvn-repo/</url>
</repository>
```
```
<dependency>
    <groupId>net.playavalon</groupId>
    <artifactId>AvN-Rep</artifactId>
    <version>0.1</version>
</dependency>
```

### Creating a custom reputation trigger
Avalon Reputation comes with an incredibly easy way to create your own reputation triggers. All that you need to do is extend the ReputationTrigger object, provide a trigger label, and add an event you want to catch. Let's take a look.

```java
public class TriggerPlayerDeath extends ReputationTrigger {

    public TriggerPlayerDeath() {
        super("PLAYER_DEATH");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        updateRep(player);
    }
}
```
Here is a basic trigger for player deaths. This makes it so that, if a player dies, and a faction contains `- PLAYER_DEATH:-5` as one of its sources, it will subtract 5 from the player's reputation with that faction.

This can be used for any events, including custom events. 

### Trigger wildcards
Triggers also support certain wildcards, which are useful if you want to have alternate versions of the trigger. Let's look at the KILL_MOB trigger.
```java
public class TriggerKillMob extends ReputationTrigger {

    public TriggerKillMob() {
        super("KILL_[MOB]");
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent e) {
        LivingEntity ent = e.getEntity();
        if (ent.getKiller() == null) return;
        Player player = ent.getKiller();

        EntityType entType = ent.getType();

        String mobType = "KILL_" + Utils.getEntityGroup(ent).toUpperCase();

        updateRep(player, entType.name(), mobType);

    }

}
```
There's a few things to explain this time, so I'll start with the wildcard. In the "super" constructor, we surrounded `MOB` with `[]`. This makes it so that, if we provide it with a specific monster name, it will replace \[MOB\] with that monster name. 

The second parameter in the `updateRep` method would be the name of the specific monster that just died in this example. Let's say we wanted to give the player reputation if they killed a creeper. If the faction contains `- KILL_CREEPER:1` as one of its sources, then they will now gain +1 reputation with that faction for every creeper killed.

But what if we want to give the player reputation if they kill ANY mob? You don't need to create another custom trigger for this. Since the name we provided to the trigger is `KILL_[MOB]`, it will default to `KILL_MOB` in case it can't find this specific monster in the faction's sources.

### Extra trigger names

```java
updateRep(player, entType.name(), mobType);
```

You'll also notice that, in this example, a third parameter is provided to the `updateRep` method. This is an *additional* trigger name we want to use for this custom trigger. In this case, it's being used for a `KILL_MONSTER` or `KILL_ANIMAL` trigger, depending on what mob just died. (I used a utility method to determine this.) This ia an "extra" trigger name, and will be checked *after* the specific entity, but *before* the catch-all. In this example, the order would look something like this:
1. It checks for `KILL_[MOB]`, with \[MOB\] being replaced with the name of the mob that just died.
2. If it couldn't find `KILL_[MOB]`, it checks for either `KILL_MONSTER` or `KILL_ANIMAL` depending on what kind of mob this is.
3. If it couldn't find either of the other two, it checks for `KILL_MOB`

If you wanted to add multiple possible trigger names for this one trigger, just provide the third parameter with a `String[]` containing all the different trigger names. This can be used to create aliases for the trigger, such as `KILL` instead of `KILL_MOB`
