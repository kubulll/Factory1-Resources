package v0id.f0resources.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import v0id.f0resources.client.gui.*;
import v0id.f0resources.inventory.*;
import v0id.f0resources.tile.TileBurnerDrill;
import v0id.f0resources.tile.TileDrill;
import v0id.f0resources.tile.TileFluidPump;
import v0id.f0resources.tile.TileLiquidDrill;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID)
        {
            case 0:
            {
                return new ContainerDrill(player.inventory, (TileDrill) tile);
            }

            case 1:
            {
                return new ContainerBurnerDrill(player.inventory, (TileBurnerDrill) tile);
            }

            case 2:
            {
                return new ContainerOreVisualizer(player.inventory);
            }

            case 3:
            {
                return new ContainerPump(player.inventory, (TileFluidPump) tile);
            }



            case 4:
            {
                return new ContainerLiquidDrill(player.inventory, (TileLiquidDrill) tile);
            }
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.isBlockLoaded(new BlockPos(x, y, z)) ? world.getTileEntity(new BlockPos(x, y, z)) : null;
        switch (ID)
        {
            case 0:
            {
                return new GuiDrill(player.inventory, (TileDrill) tile);
            }

            case 1:
            {
                return new GuiBurnerDrill(player.inventory, (TileBurnerDrill) tile);
            }

            case 2:
            {
                return new GuiOreVisualizer(player.inventory);
            }

            case 3:
            {
                return new GuiPump(player.inventory, (TileFluidPump) tile);
            }



            case 4:
            {
                return new GuiLiquidDrill(player.inventory, (TileLiquidDrill) tile);
            }
        }

        return null;
    }
}
