package systems.brn.tweaks.paper;

import net.minecraft.world.level.GameRules;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.BiConsumer;

public final class Rules {
    public static GameRules.Key<GameRules.IntegerValue> ENTITY_PORTAL_COOLDOWN;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_PEARL_DAMAGE;
    public static GameRules.Key<GameRules.IntegerValue> SHIELD_AXE_COOLDOWN;
    public static GameRules.Key<GameRules.IntegerValue> CRITICAL_MULTIPLIER;
    public static GameRules.Key<GameRules.IntegerValue> CREEPER_FUSE_TIME;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_PEARL_POWER;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_PEARL_DIVERGENCE;
    public static GameRules.Key<GameRules.IntegerValue> ENDER_EYE_POP_CHANCE;
    public static GameRules.Key<GameRules.IntegerValue> LEASH_BREAK_DISTANCE;
    public static GameRules.Key<GameRules.IntegerValue> LEASH_ELASTIC_DISTANCE;

    private Rules() {
    }

    public static void register() throws ReflectiveOperationException {
        Method register = method(GameRules.class, GameRules.Key.class, String.class, GameRules.Category.class, GameRules.Type.class);
        Method create = method(GameRules.IntegerValue.class, GameRules.Type.class, int.class, BiConsumer.class);

        ENTITY_PORTAL_COOLDOWN = integer(register, create, "entityPortalCooldown", GameRules.Category.MOBS, 300, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DAMAGE = integer(register, create, "enderPearlDamage", GameRules.Category.PLAYER, 5, 0, Integer.MAX_VALUE);
        SHIELD_AXE_COOLDOWN = integer(register, create, "shieldAxeCooldown", GameRules.Category.PLAYER, 100, 0, Integer.MAX_VALUE);
        CRITICAL_MULTIPLIER = integer(register, create, "criticalHitMultiplier", GameRules.Category.PLAYER, 150, 0, Integer.MAX_VALUE);
        CREEPER_FUSE_TIME = integer(register, create, "creeperDefaultFuseTime", GameRules.Category.MOBS, 30, 0, Integer.MAX_VALUE);
        ENDER_PEARL_POWER = integer(register, create, "enderPearlPower", GameRules.Category.PLAYER, 150, 0, Integer.MAX_VALUE);
        ENDER_PEARL_DIVERGENCE = integer(register, create, "enderPearlDivergence", GameRules.Category.PLAYER, 100, 0, Integer.MAX_VALUE);
        ENDER_EYE_POP_CHANCE = integer(register, create, "enderEyePopChance", GameRules.Category.PLAYER, 20, 0, 100);
        LEASH_BREAK_DISTANCE = integer(register, create, "leashBreakDistance", GameRules.Category.MOBS, 10, 0, Integer.MAX_VALUE);
        LEASH_ELASTIC_DISTANCE = integer(register, create, "leashElasticDistance", GameRules.Category.MOBS, 6, 0, Integer.MAX_VALUE);
    }

    private static Method method(Class<?> owner, Class<?> returnType, Class<?>... parameters) throws NoSuchMethodException {
        for (Method method : owner.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == returnType && Arrays.equals(method.getParameterTypes(), parameters)) {
                method.setAccessible(true);
                return method;
            }
        }
        throw new NoSuchMethodException(owner.getName());
    }

    @SuppressWarnings("unchecked")
    private static GameRules.Key<GameRules.IntegerValue> integer(Method register, Method create, String name, GameRules.Category category, int def, int min, int max) throws ReflectiveOperationException {
        try {
            Object type = create.invoke(null, def, (BiConsumer<Object, Object>) (server, value) -> {
            });
            return (GameRules.Key<GameRules.IntegerValue>) register.invoke(null, name, category, type);
        } catch (InvocationTargetException e) {
            throw e;
        }
    }

    public static int get(World world, GameRules.Key<GameRules.IntegerValue> key) {
        return ((CraftWorld) world).getHandle().getGameRules().getInt(key);
    }
}
