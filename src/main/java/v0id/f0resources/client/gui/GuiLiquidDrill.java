package v0id.f0resources.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import v0id.api.f0resources.data.F0RTextures;
import v0id.f0resources.inventory.ContainerLiquidDrill;
import v0id.f0resources.tile.TileLiquidDrill;

import java.util.List;

public class GuiLiquidDrill extends GuiContainer
{
    public TileLiquidDrill tile;

    public GuiLiquidDrill(InventoryPlayer playerInv, TileLiquidDrill drill)
    {
        super(new ContainerLiquidDrill(playerInv, drill));
        this.tile = drill;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(F0RTextures.guiLiquidDrill);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        float fuelValue = (float)this.tile.fluidTank.getFluidAmount() / this.tile.fluidTank.getCapacity();
        this.drawTexturedModalRect(i + 14, j + 14 + (int)((1 - fuelValue) * 62), 176, (int) ((1 - fuelValue) * 62), 12, (int) (fuelValue * 62));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 7 && mouseX <= i + 33 && mouseY >= j + 7 && mouseY <= j + 83)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("txt.f0r.liquidFuelStored", this.tile.fluidTank.getFluidAmount(), this.tile.fluidTank.getCapacity()));
            this.drawHoveringText(lines, mouseX, mouseY);
        }
    }
}

