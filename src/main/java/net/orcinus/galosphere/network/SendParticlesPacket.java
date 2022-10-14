package net.orcinus.galosphere.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class SendParticlesPacket {
    private final BlockPos blockPos;

    public SendParticlesPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public static SendParticlesPacket read(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        return new SendParticlesPacket(pos);
    }

    public static void write(SendParticlesPacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.blockPos);
    }

    public static void handle(SendParticlesPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            Optional.ofNullable(minecraft.level).ifPresent(world -> {
                RandomSource randomsource = world.getRandom();
                world.playLocalSound(packet.blockPos, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                boolean flag = world.getBlockState(packet.blockPos).isCollisionShapeFullBlock(world, packet.blockPos);
                int l2 = flag ? 40 : 20;
                float f9 = flag ? 0.45F : 0.25F;
                for(int k3 = 0; k3 < l2; ++k3) {
                    float f12 = 2.0F * randomsource.nextFloat() - 1.0F;
                    float f14 = 2.0F * randomsource.nextFloat() - 1.0F;
                    float f15 = 2.0F * randomsource.nextFloat() - 1.0F;
                    world.addParticle(ParticleTypes.GLOW, (double)packet.blockPos.getX() + 0.5D + (double)(f12 * f9), (double)packet.blockPos.getY() + 0.5D + (double)(f14 * f9), (double)packet.blockPos.getZ() + 0.5D + (double)(f15 * f9), (double)(f12 * 0.07F), (double)(f14 * 0.07F), (double)(f15 * 0.07F));
                }
                world.playLocalSound(packet.blockPos, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
