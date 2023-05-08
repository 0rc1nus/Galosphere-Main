package net.orcinus.galosphere.entities;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.api.Spectatable;
import net.orcinus.galosphere.api.SpectreBoundSpyglass;
import net.orcinus.galosphere.init.GEntityTypes;
import net.orcinus.galosphere.init.GNetwork;
import net.orcinus.galosphere.init.GSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class SpectatorVision extends AmbientCreature implements Spectatable {
    private static final EntityDataAccessor<Optional<UUID>> MANIPULATOR = SynchedEntityData.defineId(SpectatorVision.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(SpectatorVision.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SPECTATING_TICKS = SynchedEntityData.defineId(SpectatorVision.class, EntityDataSerializers.INT);

    public SpectatorVision(Level level) {
        super(GEntityTypes.SPECTATOR_VISION, level);
    }

    public SpectatorVision(EntityType<? extends AmbientCreature> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0).add(Attributes.FLYING_SPEED, 0.1F).add(Attributes.MOVEMENT_SPEED, 0.1F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MANIPULATOR, Optional.empty());
        this.entityData.define(PHASE, 0);
        this.entityData.define(SPECTATING_TICKS, 0);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isRemoved()) {
            int spectatableTime = this.getSpectatableTime();
            if (spectatableTime > 0) {
                this.setSpectatableTime(spectatableTime - 1);
            }
            if (this.getPhase() < 12 && this.tickCount % 5 == 0) {
                this.setPhase(this.getPhase() + 1);
            }
            if (!this.level.isClientSide() || this.matchesClientPlayerUUID()) {
                this.entityData.get(MANIPULATOR).ifPresent(this::spectateTick);
            }
        }
    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSpectatableTime(tag.getInt("SpectatingTicks"));
        this.setPhase(tag.getInt("Phase"));
        UUID uuid;
        if (tag.hasUUID("Manipulator")) {
            uuid = tag.getUUID("Manipulator");
        } else {
            String s = tag.getString("Manipulator");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }
        if (uuid != null) {
            this.setManipulatorUUID(uuid);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.getManipulatorUUID() != null) {
            this.entityData.get(MANIPULATOR).map(this.level::getPlayerByUUID).ifPresent(uuid -> this.copyPlayerRotation(this, uuid));
        } else {
            super.travel(vec3);
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getManipulatorUUID() != null) {
            tag.putUUID("Manipulator", this.getManipulatorUUID());
        }
        tag.putInt("SpectatingTicks", this.getSpectatableTime());
        tag.putInt("Phase", this.getPhase());
    }

    @Nullable
    public UUID getManipulatorUUID() {
        return this.entityData.get(MANIPULATOR).orElse(null);
    }

    public void setManipulatorUUID(@Nullable UUID uuid) {
        this.entityData.set(MANIPULATOR, Optional.ofNullable(uuid));
    }

    @Override
    public void spectateTick(UUID uuid) {
        Player player = this.level.getPlayerByUUID(uuid);
        if (player != null) {
            player.xxa = 0.0F;
            player.zza = 0.0F;
            player.setJumping(false);
            if (!this.level.isClientSide && (player.isShiftKeyDown() || this.getSpectatableTime() == 0)) {
                ((SpectreBoundSpyglass)player).setUsingSpectreBoundedSpyglass(false);
                player.playNotifySound(GSoundEvents.SPECTRE_MANIPULATE_END, getSoundSource(), 1, 1);
                this.setManipulatorUUID(null);
                if (player instanceof ServerPlayer serverPlayer) {
                    FriendlyByteBuf buf = PacketByteBufs.create();
                    buf.writeUUID(player.getUUID());
                    ServerPlayNetworking.send(serverPlayer, GNetwork.RESET_PERSPECTIVE, buf);
                }
                this.discard();
            }
        }
        if (!this.level.isClientSide() && player == null) {
            this.entityData.set(MANIPULATOR, Optional.empty());
        }
    }

    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    public void setPhase(int phase) {
        this.entityData.set(PHASE, phase);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public static SpectatorVision create(Level world, Vec3 blockPos, ServerPlayer serverPlayer, int ticks) {
        SpectatorVision spectatorVision = new SpectatorVision(world);
        spectatorVision.moveTo(blockPos.x, blockPos.y, blockPos.z);
        spectatorVision.setSpectatableTime(ticks);
        spectatorVision.setManipulatorUUID(serverPlayer.getUUID());
        return spectatorVision;
    }

    public int getSpectatableTime() {
        return this.entityData.get(SPECTATING_TICKS);
    }

    public void setSpectatableTime(int spectatableTime) {
        this.entityData.set(SPECTATING_TICKS, spectatableTime);
    }

}
