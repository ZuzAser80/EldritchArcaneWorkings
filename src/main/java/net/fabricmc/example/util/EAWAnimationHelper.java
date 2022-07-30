package net.fabricmc.example.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.util.HashSet;
import java.util.Set;

public class EAWAnimationHelper {
    private final static Set<EAWAnimationHelper> animations = new HashSet<>();

    private boolean isPlaying;
    private int animationLength;
    private int ticks;
    private float offsetPerTickX;
    private float offsetPerTickY;
    private float offsetPerTickZ;
    private float rotationPerTickX;
    private float rotationPerTickY;
    private float rotationPerTickZ;
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private float offsetX;
    private float offsetY;
    private float offsetZ;

    private EAWAnimationHelper(int animationTicks, float offsetPerTickX, float offsetPerTickY, float offsetPerTickZ) {
        this.isPlaying = true;
        this.animationLength = animationTicks;
        this.offsetPerTickX = offsetPerTickX;
        this.offsetPerTickY = offsetPerTickY;
        this.offsetPerTickZ = offsetPerTickZ;
    }

    private EAWAnimationHelper(int animationTicks, float offsetPerTickX, float offsetPerTickY, float offsetPerTickZ, float rotationPerTickX, float rotationPerTickY, float rotationPerTickZ) {
        this.isPlaying = true;
        this.animationLength = animationTicks;
        this.offsetPerTickX = offsetPerTickX;
        this.offsetPerTickY = offsetPerTickY;
        this.offsetPerTickZ = offsetPerTickZ;
        this.rotationPerTickX = rotationPerTickX;
        this.rotationPerTickY = rotationPerTickY;
        this.rotationPerTickZ = rotationPerTickZ;
    }

    private EAWAnimationHelper(int animationTicks, double rotationPerTickX, double rotationPerTickY, double rotationPerTickZ) {
        this.isPlaying = true;
        this.animationLength = animationTicks;
        this.rotationPerTickX = (float)rotationPerTickX;
        this.rotationPerTickY = (float)rotationPerTickY;
        this.rotationPerTickZ = (float)rotationPerTickZ;
    }

    public static EAWAnimationHelper begin(int animationTicks, float offsetPerTickX, float offsetPerTickY, float offsetPerTickZ) {
        EAWAnimationHelper animation = new EAWAnimationHelper(animationTicks, offsetPerTickX, offsetPerTickY, offsetPerTickZ);
        animations.add(animation);
        return animation;
    }

    public static EAWAnimationHelper begin(int animationTicks, double rotationPerTickX, double rotationPerTickY, double rotationPerTickZ) {
        EAWAnimationHelper animation = new EAWAnimationHelper(animationTicks, rotationPerTickX, rotationPerTickY, rotationPerTickZ);
        animations.add(animation);
        return animation;
    }

    @Environment(EnvType.CLIENT)
    public static void eventClient()
    {
        ClientTickEvents.END_CLIENT_TICK.register((client) ->
        {
            Set<EAWAnimationHelper> toRemove = new HashSet<>();
            animations.forEach((animation) -> {
                if(!animation.isPlaying && animation.ticks != 0)
                {
                    animation.ticks = 0;
                    toRemove.add(animation);
                }
                if(animation.isPlaying) {
                    if (animation.ticks <= animation.animationLength) {
                        animation.offsetX += animation.offsetPerTickX;
                        animation.offsetY += animation.offsetPerTickY;
                        animation.offsetZ += animation.offsetPerTickZ;
                        animation.rotationX += animation.rotationPerTickX;
                        animation.rotationY += animation.rotationPerTickY;
                        animation.rotationZ += animation.rotationPerTickZ;
                    } else {
                        animation.isPlaying = false;
                    }
                    animation.ticks++;
                }
            });
            if(!toRemove.isEmpty()) {
                animations.removeAll(toRemove);
            }
        });
    }

    public float getOffset(Direction.Axis axis){
        switch (axis) {
            case X -> { return offsetX; }
            case Y -> { return offsetY; }
            case Z -> { return offsetZ; }
        }
        return 0;
    }

    public Quaternion getQuaternion(Direction.Axis axis) {
        switch (axis) {
            case X -> { return new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(rotationX)); }
            case Y -> { return new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(rotationY)); }
            case Z -> { return new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(rotationZ)); }
        }
        return null;
    }

    public boolean getIsPlaying()
    {
        return this.isPlaying;
    }
}
