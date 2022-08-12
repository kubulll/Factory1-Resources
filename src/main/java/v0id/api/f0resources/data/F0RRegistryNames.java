package v0id.api.f0resources.data;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class F0RRegistryNames
{
    public static final String MODID                                                            = "f0-resources";
    public static final String MOD_NAME = "Factory1 - Resources";
    public static final String VERSION = "2.1.0";
    public static final String MCVERSION = "1.12.2";

    public static final String
        drillComponent                                                                          = "drill_component",
        drillPart                                                                               = "drill_part",
        burnerDrillComponent                                                                    = "burner_drill_component",
        burnerDrillPart                                                                         = "burner_drill_part",
        pumpComponent                                                                           = "pump_component",
        pumpPart                                                                                = "pump_part",

        liquidDrillComponent                                                                    = "liquid_drill_component",
        liquidDrillPart                                                                         = "liquid_drill_part";

    public static final String
        prospectorsPick                                                                         = "item_prospectors_pick",
        advancedProspectorsPick                                                                 = "item_advanced_prospectors_pick",
        scanner                                                                                 = "item_scanner",
        advancedScanner                                                                         = "item_advanced_scanner",
        oreVisualizer                                                                           = "item_ore_visualizer",
        dowsingRod                                                                              = "item_dowsing_rod";

    private static final Map<String, ResourceLocation> cache = Maps.newHashMap();

    public static ResourceLocation asLocation(String name)
    {
        return asLocation(name, false);
    }

    public static ResourceLocation asLocation(String name, boolean doCache)
    {
        if (doCache)
        {
            if (!cache.containsKey(name))
            {
                cache.put(name, new ResourceLocation(MODID, name));
            }

            return cache.get(name);
        }
        else
        {
            return new ResourceLocation(MODID, name);
        }
    }
}
