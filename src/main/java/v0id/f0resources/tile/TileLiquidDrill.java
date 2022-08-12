package v0id.f0resources.tile;

import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.ArrayUtils;
import v0id.f0resources.config.F0RConfig;
import v0id.f0resources.item.ItemDrillHead;
import v0id.f0resources.network.F0RNetwork;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class TileLiquidDrill extends AbstractDrill implements ITickable
{
    public static final IForgeRegistry<Block> BLOCK_REGISTRY = GameRegistry.findRegistry(Block.class);
    public FluidTank fluidTank = new FluidTank(F0RConfig.liquidDrillTankStorage)
    {
        @Override
        protected void onContentsChanged()
        {
            if (!TileLiquidDrill.this.world.isRemote)
            {
                F0RNetwork.sendFluidTank(TileLiquidDrill.this, this.fluid);
            }
        }
    };
    //public EnergyStorage energyStorage = new EnergyStorage(F0RConfig.drillEnergy);
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            if (!TileLiquidDrill.this.world.isRemote)
            {
                F0RNetwork.sendDrillItem(TileLiquidDrill.this);
            }
        }
    };

    @Override
    public boolean checkBase()
    {
        Block[] block = Arrays.stream(F0RConfig.requiredBlocksDrill).filter(s -> !Strings.isNullOrEmpty(s)).map(ResourceLocation::new).map(BLOCK_REGISTRY::getValue).filter(Objects::nonNull).toArray(Block[]::new);
        for (int dx = -1; dx <= 1; ++dx)
        {
            for (int dz = -1; dz <= 1; ++dz)
            {
                if (!ArrayUtils.contains(block, this.world.getBlockState(this.pos.add(dx, -1, dz)).getBlock()))
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean consumePower(boolean simulate) {

        boolean validFluid = false;

        for (int i = 0; i < F0RConfig.liquidDrillFuels.length; i++) {
            String[] splitStr = F0RConfig.liquidDrillFuels[i].split(";");

            if (this.fluidTank.getFluidAmount() > 0) {
                String fluidInsideTank = this.fluidTank.getFluid().getFluid().getName();
                if (splitStr[0].equals(fluidInsideTank)) {
                    validFluid = true;
                } else {
                    validFluid = false;
                }
            } else {
                return false;
            }

            if (validFluid) {
                int fuelConsumption = (int) ((float) Float.parseFloat(splitStr[1]) * ((ItemDrillHead) this.getDrillHead().getItem()).material.energyMultiplier);
                int clampedValue = Math.max(1, Math.min(Integer.MAX_VALUE, fuelConsumption));
                if (clampedValue <= this.fluidTank.getFluidAmount()) {
                    return this.fluidTank.drain(clampedValue, simulate).amount >= clampedValue;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    @Override
    public void spawnWorkingParticles()
    {
        this.world.spawnParticle(EnumParticleTypes.CRIT, this.pos.getX() + 0.5F + this.world.rand.nextDouble() - this.world.rand.nextDouble(), this.pos.getY(), this.pos.getZ() + 0.5F + this.world.rand.nextDouble() - this.world.rand.nextDouble(), this.world.rand.nextDouble() - this.world.rand.nextDouble(), 0.5F, this.world.rand.nextDouble() - this.world.rand.nextDouble());
    }

    @Override
    public ItemStack getDrillHead()
    {
        return this.inventory.getStackInSlot(0);
    }

    @Override
    public void setDrillHead(ItemStack is)
    {
        this.inventory.setStackInSlot(0, is);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fluidTank.readFromNBT(compound.getCompoundTag("tank"));
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);
        ret.setTag("tank", this.fluidTank.writeToNBT(new NBTTagCompound()));
        ret.setTag("inventory", this.inventory.serializeNBT());
        return ret;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.centerPos != BlockPos.ORIGIN) || (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.centerPos != BlockPos.ORIGIN) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.centerPos != BlockPos.ORIGIN ? this.isCenter ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidTank) : this.world.getTileEntity(this.centerPos).getCapability(capability, facing) : capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.centerPos != BlockPos.ORIGIN ? this.isCenter ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory) : this.world.getTileEntity(this.centerPos).getCapability(capability, facing) : super.getCapability(capability, facing);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.serializeNBT());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.serializeNBT();
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.deserializeNBT(tag);
    }

    @Override
    public boolean hasFastRenderer()
    {
        return F0RConfig.useFastTESR;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ() - 1, this.pos.getX() + 2, this.pos.getY() + 1, this.pos.getZ() + 2);
    }
}
