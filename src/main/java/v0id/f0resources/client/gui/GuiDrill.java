package v0id.f0resources.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import v0id.api.f0resources.data.F0RTextures;
import v0id.f0resources.config.F0RConfig;
import v0id.f0resources.inventory.ContainerDrill;
import v0id.f0resources.tile.TileDrill;

import java.util.List;

public class GuiDrill extends GuiContainer
{
    public TileDrill tile;

    public GuiDrill(InventoryPlayer playerInv, TileDrill drill)
    {
        super(new ContainerDrill(playerInv, drill));
        this.tile = drill;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(F0RTextures.guiDrill);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        // Energy Bar
        float powerValue = (float)this.tile.energyStorage.getEnergyStored() / this.tile.energyStorage.getMaxEnergyStored();
        this.drawTexturedModalRect(i + 14, j + 14 + (int)((1 - powerValue) * 62), 176, (int) ((1 - powerValue) * 62), 4, (int) (powerValue * 62));

        // Mining Progress Bar
        float progressValue = this.tile.minedMultiplier / this.tile.getRequiredProgress();
        this.drawTexturedModalRect(i + 45, j + 68, 0, 166, (int) (progressValue * 86.0f), 3);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 7 && mouseX <= i + 25 && mouseY >= j + 7 && mouseY <= j + 83)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("txt.f0r.rfStored", this.tile.energyStorage.getEnergyStored(), this.tile.energyStorage.getMaxEnergyStored()));
            this.drawHoveringText(lines, mouseX, mouseY);
        }
        if (mouseX >= i + 40 && mouseX <= i + 136 && mouseY >= j + 63 && mouseY <= j + 76)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("txt.f0r.miningProgress", this.tile.minedMultiplier, this.tile.getRequiredProgress()));
            this.drawHoveringText(lines, mouseX, mouseY);
        }
    }
}

