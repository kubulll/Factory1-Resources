package v0id.f0resources.item;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import v0id.api.f0resources.data.F0RCreativeTabs;
import v0id.api.f0resources.data.F0RRegistryNames;
import v0id.f0resources.config.DrillMaterialEntry;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDrillHead extends Item
{
    public final DrillMaterialEntry material;

    public boolean isIndestructible = false;

    public static final List<ItemDrillHead> allDrillHeads = Lists.newArrayList();

    public ItemDrillHead(DrillMaterialEntry entry)
    {
        super();
        allDrillHeads.add(this);
        this.material = entry;
        this.setRegistryName(F0RRegistryNames.asLocation("item_drill_head." + entry.name.toLowerCase().replace(' ', '_')));
        this.setTranslationKey("f0-resources.item.drill_head");
        this.setCreativeTab(F0RCreativeTabs.tabF0R);
        this.setMaxStackSize(1);

        if (entry.durability < 0) {
            isIndestructible = true;
            this.setMaxDamage(Integer.MAX_VALUE);
        } else {
            this.setMaxDamage(entry.durability);
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey(stack) + (this.material.isUnlocalized ? ('.' + this.material.name) : "");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return this.material.isUnlocalized ? super.getItemStackDisplayName(stack) : this.material.name;
    }



    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (!isIndestructible) {
            tooltip.add(I18n.format("txt.f0r.drillTooltip.durability") + ": §9" + (this.material.durability - stack.getItemDamage()) + "/" + this.material.durability);
        } else {
            tooltip.add(I18n.format("txt.f0r.drillTooltip.durability") + ": §5∞");
        }
        tooltip.add(I18n.format("txt.f0r.drillTooltip.speed") + ": " + this.material.speed);
        tooltip.add(I18n.format("txt.f0r.drillTooltip.energyMultiplier") + ": " + this.material.energyMultiplier);
        tooltip.add(I18n.format("txt.f0r.drillTooltip.tier") + ": " + this.material.tier);
    }
}
