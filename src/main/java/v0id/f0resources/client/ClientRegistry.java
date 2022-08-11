package v0id.f0resources.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import v0id.api.f0resources.data.F0RBlocks;
import v0id.api.f0resources.data.F0RItems;
import v0id.api.f0resources.data.F0RRegistryNames;
import v0id.f0resources.client.render.tile.*;
import v0id.f0resources.config.F0RConfig;
import v0id.f0resources.item.ItemDrillHead;
import v0id.f0resources.tile.TileBurnerDrill;
import v0id.f0resources.tile.TileDrill;
import v0id.f0resources.tile.TileFluidPump;
import v0id.f0resources.tile.TileLiquidDrill;

@Mod.EventBusSubscriber(modid = F0RRegistryNames.MODID, value = { Side.CLIENT })
public class ClientRegistry
{
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event)
    {
        registerRenderers();
        registerStaticModel(F0RItems.prospectorsPick, new ModelResourceLocation(F0RItems.prospectorsPick.getRegistryName(), "inventory"));
        registerStaticModel(F0RItems.advancedProspectorsPick, new ModelResourceLocation(F0RItems.advancedProspectorsPick.getRegistryName(), "inventory"));
        registerStaticModel(F0RItems.scanner, new ModelResourceLocation(F0RItems.scanner.getRegistryName(), "inventory"));
        registerStaticModel(F0RItems.advancedScanner, new ModelResourceLocation(F0RItems.advancedScanner.getRegistryName(), "inventory"));
        registerStaticModel(F0RItems.dowsingRod, new ModelResourceLocation(F0RItems.dowsingRod.getRegistryName(), "inventory"));
        ItemDrillHead.allDrillHeads.stream().forEach(e -> registerStaticModel(e, new ModelResourceLocation(F0RRegistryNames.asLocation("item_drill_head"), "inventory")));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(F0RBlocks.drillComponent), 0, new ModelResourceLocation(F0RBlocks.drillComponent.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(F0RBlocks.burnerDrillComponent), 0, new ModelResourceLocation(F0RBlocks.burnerDrillComponent.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(F0RBlocks.pumpComponent), 0, new ModelResourceLocation(F0RBlocks.pumpComponent.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(F0RItems.oreVisualizer, 0, new ModelResourceLocation(F0RItems.oreVisualizer.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(F0RBlocks.liquidDrillComponent), 0, new ModelResourceLocation(F0RBlocks.liquidDrillComponent.getRegistryName(), "inventory"));
    }

    public static void registerRenderers()
    {
        net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(TileDrill.class, F0RConfig.useFastTESR ? new TESRDrill() : new TESRDrillSlow());
        net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(TileBurnerDrill.class, F0RConfig.useFastTESR ? new TESRBurnerDrill() : new TESRBurnerDrillSlow());
        net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(TileFluidPump.class, F0RConfig.useFastTESR ? new TESRPump() : new TESRPumpSlow());

        net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer(TileLiquidDrill.class, F0RConfig.useFastTESR ? new TESRLiquidDrill() : new TESRLiquidDrillSlow());
    }

    public static void registerColours()
    {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, index) -> stack.getItem() instanceof ItemDrillHead && index == 1 ? ((ItemDrillHead) stack.getItem()).material.color : -1, ItemDrillHead.allDrillHeads.toArray(new Item[0]));
    }

    public static void registerStaticModel(Item item, ModelResourceLocation staticLocation)
    {
        ModelLoader.setCustomMeshDefinition(item, i -> staticLocation);
        ModelBakery.registerItemVariants(item, staticLocation);
    }

    @SubscribeEvent
    public static void onTextureStich(TextureStitchEvent.Pre event)
    {
        TESRDrill.texture = event.getMap().registerSprite(new ResourceLocation("f0-resources", "blocks/drill"));
        TESRBurnerDrill.texture = event.getMap().registerSprite(new ResourceLocation("f0-resources", "blocks/burner_drill"));
        TESRPump.texture = event.getMap().registerSprite(new ResourceLocation("f0-resources", "blocks/fluid_pump"));

        TESRDrill.texture = event.getMap().registerSprite(new ResourceLocation("f0-resources", "blocks/liquid_drill"));
    }
}
