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

*Repository*
```
<repository>
    <id>AvN-Rep</id>
    <url>https://raw.github.com/destradious/AvN-Reputation/mvn-repo/</url>
</repository>
```
*Dependency*
```
<dependency>
    <groupId>net.playavalon</groupId>
    <artifactId>AvN-Rep</artifactId>
    <version>0.1</version>
</dependency>
```

### Creating a custom reputation trigger
Avalon Reputation comes with an incredibly easy way to create your own reputation triggers. All that you need to do is extend the ReputationTrigger object, provide a trigger label, and add an event you want to catch. Let's take a look.
