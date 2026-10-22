# Tweaks

Turns a handful of hard coded vanilla numbers into real gamerules, so you can change them with `/gamerule` instead of editing code. Runs on Fabric (Quilt too), Forge, NeoForge and Paper (Folia too). Only the server needs it, players join with a vanilla client. Releases are on [Modrinth](https://modrinth.com/project/vnAc26RE).

This branch is for Minecraft 26.2. `main` follows the newest version.

## Rules

| Rule | Default | What it does |
| --- | --- | --- |
| `entity_portal_cooldown` | 300 | Ticks before an entity can use a portal again. Applied when an entity loads or arrives in a world. |
| `ender_pearl_damage` | 5 | Damage taken when an ender pearl lands. 0 cancels it. |
| `ender_pearl_power` | 150 | Throw speed of ender pearls, in hundredths. |
| `ender_pearl_divergence` | 100 | How much pearls scatter, in hundredths. 0 is dead straight. |
| `ender_eye_pop_chance` | 20 | Percent chance that a thrown eye of ender breaks instead of dropping. |
| `critical_hit_multiplier` | 150 | Critical hit damage multiplier in hundredths. 150 is vanilla. |
| `shield_axe_cooldown` | 100 | Ticks a shield stays disabled after an axe hit. |
| `creeper_default_fuse_time` | 30 | Ticks a creeper takes to explode once it starts swelling. |
| `leash_break_distance` | 12 | Blocks at which a leash snaps. |
| `leash_elastic_distance` | 6 | Blocks at which a leash starts pulling its mob. |

Values are plain integers. Anything that would be a decimal is stored in hundredths, so `ender_pearl_power` set to 150 means 1.5.

## Layout

- `common/`: the `Rules` class, the mixins and the lang file. Compiled straight into each mod loader module through a shared source dir.
- `fabric/`: Fabric and Quilt jar. No entrypoint, the mixins do everything.
- `neoforge/`: NeoForge jar with an empty `@Mod` class.
- `forge/`: Forge jar with an empty `@Mod` class.
- `paper/`: the Paper and Folia plugin. Separate code, Paper has no mixins.

Built against:

| Component | Version |
| --- | --- |
| Minecraft | 26.2 |
| Fabric loader | 0.19.5 |
| NeoForge | 26.2.0.93 |
| Forge | 65.1.5 |
| Paper | 26.2.build.134-stable |

## How it works

On the mod loaders every module uses Mojang names, so one set of mixins covers all of them. Vanilla keeps gamerules in a registry from 1.21.11. `GameRules.registerInteger` is private, so `GameRulesInvoker` exposes it and `GameRulesMixin` calls `Rules.register()` at the end of the `GameRules` static initializer. Gamerule ids go through `Identifier`, which only allows lowercase, so the rules are snake_case on this version.

The behaviour changes are small mixins that read the rule from the level instead of using the constant:

- `BlocksAttacksMixin`: shield cooldown
- `CreeperMixin`: sets the fuse in the constructor
- `EnderPearlDamageMixin`: swaps the damage argument in `onHit`
- `EnderpearlItemMixin`: redirects the throw to use the power and divergence rules
- `EntityPortalMixin`: portal cooldown
- `EyeOfEnderMixin`: sets `surviveAfterDeath` from the pop chance
- `LeashableMixin`: leash distances
- `PlayerMixin`: critical hit multiplier

Shield cooldown is not a constant anymore, the axe's disable time and the shield's scale come from data components. `BlocksAttacksMixin` scales the seconds passed into `BlocksAttacks.disable` by `shield_axe_cooldown / 100`, so 100 stays vanilla for an axe.

Leash distances come from `Leashable.leashSnapDistance()` and `leashElasticDistance()`, which default to 12 and 6. `LeashableMixin` redirects those two calls in `tickLeash` and only replaces the value when it is still the default, so mobs that override them keep their own.

On Paper there are no mixins, so `paper/` does the same things with Bukkit events and a few NMS calls. The rules have to exist before Paper builds its command tree, so they are registered by reflection from a `PluginBootstrap`, not from `onLoad`. Pearl damage is matched with `DamageType.ENDER_PEARL`. Vanilla still breaks a leash at its own distance, so when the break event fires inside your custom limit the plugin ties it back on the next tick. `LeashPackets` adds a netty handler to each player connection and drops the unleash packet and the lead sounds for that mob while it is stretched, which hides the flicker and the repeated tie and break noise.

## Building

JDK 25 to run Gradle. The jars target Java 25. Minecraft 26.x is unobfuscated and needs Java 25. Fabric builds with the no-remap Loom plugin, so there is no remap step.

```
./gradlew build
```

Jars end up in `fabric/build/libs`, `forge/build/libs`, `neoforge/build/libs` and `paper/build/libs`.

## Testing

Mixin targets are strings, so a clean compile says very little. Boot every target and read the rules back:

```
./gradlew :fabric:runServer
```

The same works for `:forge:runServer` and `:neoforge:runServer`.

Put `eula=true` in the run folder, set `enable-rcon=true` with a port and password in `server.properties`, then `gamerule <rule>` over RCON or in the console should print the defaults above. Grep the log for `Mixin apply` and `InvalidInjection`. Paper has no dev run task, drop the jar from `paper/build/libs` into a Paper server.

If a dev server complains about a missing Java version, point `-Dorg.gradle.java.installations.paths` at the JDKs you have.

## Porting to another version

Branch from the nearest version, bump `gradle.properties` and check that every artifact exists. Then compile `common`, and for each mixin that fails or does not apply, run `javap -p -c` on the Mojang named jar to see what changed. The usual suspects are the gamerule API, the shield, the leash, `EyeOfEnder.signalTo`, and the package of the ender pearl entity. Boot all four targets before calling it done.

## Branches

Every supported Minecraft version has a branch named after it, and `main` is the newest one. New work lands on `main` first and gets cherry picked back. When a newer Minecraft version comes out, `main` is first cut into a branch named after its version and then moves on.

| Branch | Minecraft |
| --- | --- |
| `1.20.1` | 1.20.1 |
| `1.21.1` | 1.21.1 |
| `1.21.3` | 1.21.3 |
| `1.21.4` | 1.21.4 |
| `1.21.5` | 1.21.5 |
| `1.21.8` | 1.21.8 |
| `1.21.10` | 1.21.10 |
| `1.21.11` | 1.21.11 |
| `26.1.2` | 26.1.2 |
| `26.2` | 26.2 |
| `main` | 26.3 |

There is nothing for 1.21.6, 1.21.7, 1.21.9 or anything between 1.20.2 and 1.20.6 yet. The code from before the multi loader rewrite, a Fabric only mod, is behind the `legacy-fabric` tag.

## Known issues

A vanilla client may stop drawing a lead once it is stretched past the vanilla distance. Fixing that needs a client mod.

## Contributing

Open an issue or a pull request against the branch for the version you care about, or `main` for the newest. Commits are small and use `feat:`, `fix:`, `chore:`, `refactor:`, `build:` or `docs:`. The code has no comments, please keep it that way.

## License

MIT, see `LICENSE`.
