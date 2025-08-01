package gregtech.loaders.preload;

import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.EnderIO;
import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TwilightForest;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableMap;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class LoaderGTItemData implements Runnable {

    @Override
    public void run() {
        GTLog.out.println("GTMod: Loading Item Data Tags");
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "item.giantPick", 1L, 0),
            new ItemData(Materials.Stone, 696729600L, new MaterialStack(Materials.Wood, 464486400L)));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "item.giantSword", 1L, 0),
            new ItemData(Materials.Stone, 464486400L, new MaterialStack(Materials.Wood, 232243200L)));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "tile.GiantLog", 1L, 32767),
            new ItemData(Materials.Wood, 232243200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "tile.GiantCobble", 1L, 32767),
            new ItemData(Materials.Stone, 232243200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "tile.GiantObsidian", 1L, 32767),
            new ItemData(Materials.Obsidian, 232243200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "item.minotaurAxe", 1L, 0),
            new ItemData(
                Materials.Diamond,
                14515200L,
                new MaterialStack(Materials.Wood, OrePrefixes.stick.mMaterialAmount * 2L)));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "item.armorShards", 1L, 0),
            new ItemData(Materials.Knightmetal, 403200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(TwilightForest.ID, "item.shardCluster", 1L, 0),
            new ItemData(Materials.Knightmetal, 3628800L));
        GTOreDictUnificator.addItemData(ItemList.TF_LiveRoot.get(1L), new ItemData(Materials.LiveRoot, 3628800L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 10),
            new ItemData(Materials.CertusQuartz, 1814400L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 11),
            new ItemData(Materials.NetherQuartz, 1814400L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 12),
            new ItemData(Materials.Fluix, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.quartz_block, 1, 32767), new ItemData(Materials.NetherQuartz, 14515200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1L, 32767),
            new ItemData(Materials.CertusQuartz, 14515200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1L, 32767),
            new ItemData(Materials.CertusQuartz, 14515200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1L, 32767),
            new ItemData(Materials.CertusQuartz, 14515200L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.wheat, 1, 32767), new ItemData(Materials.Wheat, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.hay_block, 1, 32767), new ItemData(Materials.Wheat, 32659200L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.snowball, 1, 32767), new ItemData(Materials.Snow, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.snow, 1, 32767), new ItemData(Materials.Snow, 14515200L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.glowstone, 1, 32767), new ItemData(Materials.Glowstone, 14515200L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.redstone_lamp, 1, 32767),
            new ItemData(
                Materials.Glowstone,
                14515200L,
                new MaterialStack(Materials.Redstone, OrePrefixes.dust.mMaterialAmount * 4L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.lit_redstone_lamp, 1, 32767),
            new ItemData(
                Materials.Glowstone,
                14515200L,
                new MaterialStack(Materials.Redstone, OrePrefixes.dust.mMaterialAmount * 4L)));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Forestry.ID, "craftingMaterial", 1L, 5),
            new ItemData(Materials.Ice, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.ice, 1, 32767), new ItemData(Materials.Ice, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.packed_ice, 1, 32767), new ItemData(Materials.Ice, 7257600L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.clay_ball, 1, 32767), new ItemData(Materials.Clay, 1814400L));
        GTOreDictUnificator.removeItemData(new ItemStack(Blocks.clay, 1, 0));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.clay, 1, 32767), new ItemData(Materials.Clay, 7257600L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.hardened_clay, 1, 32767), new ItemData(Materials.Clay, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stained_hardened_clay, 1, 32767), new ItemData(Materials.Clay, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.brick_block, 1, 32767), new ItemData(Materials.Brick, 3628800L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Uranium_238.get(1), new ItemData(Materials.Uranium, 3628800L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Uranium_235.get(1), new ItemData(Materials.Uranium235, 3628800L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Plutonium.get(1), new ItemData(Materials.Plutonium, 3628800L));
        GTOreDictUnificator
            .addItemData(ItemList.IC2_Uranium_235_Small.get(1), new ItemData(Materials.Uranium235, 403200L));
        GTOreDictUnificator
            .addItemData(ItemList.IC2_Plutonium_Small.get(1), new ItemData(Materials.Plutonium, 403200L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Iron.get(1L), new ItemData(Materials.Iron, 1814400L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Gold.get(1L), new ItemData(Materials.Gold, 1814400L));
        GTOreDictUnificator
            .addItemData(ItemList.IC2_Item_Casing_Bronze.get(1L), new ItemData(Materials.Bronze, 1814400L));
        GTOreDictUnificator
            .addItemData(ItemList.IC2_Item_Casing_Copper.get(1L), new ItemData(Materials.Copper, 1814400L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Tin.get(1L), new ItemData(Materials.Tin, 1814400L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Lead.get(1L), new ItemData(Materials.Lead, 1814400L));
        GTOreDictUnificator
            .addItemData(ItemList.IC2_Item_Casing_Steel.get(1L), new ItemData(Materials.Steel, 1814400L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.book, 1, 32767), new ItemData(Materials.Paper, 10886400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.written_book, 1, 32767), new ItemData(Materials.Paper, 10886400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.writable_book, 1, 32767), new ItemData(Materials.Paper, 10886400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.enchanted_book, 1, 32767), new ItemData(Materials.Paper, 10886400L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.golden_apple, 1, 1),
            new ItemData(Materials.Gold, OrePrefixes.block.mMaterialAmount * 8L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.golden_apple, 1, 0),
            new ItemData(Materials.Gold, OrePrefixes.ingot.mMaterialAmount * 8L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.golden_carrot, 1, 0),
            new ItemData(Materials.Gold, OrePrefixes.nugget.mMaterialAmount * 8L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.speckled_melon, 1, 0),
            new ItemData(Materials.Gold, OrePrefixes.nugget.mMaterialAmount * 8L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.minecart, 1), new ItemData(Materials.Iron, 18144000L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.iron_door, 1), new ItemData(Materials.Iron, 21772800L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.cauldron, 1), new ItemData(Materials.Iron, 25401600L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.iron_bars, 8, 32767), new ItemData(Materials.Iron, 10886400L));
        ImmutableMap.of(
            NewHorizonsCoreMod.ID,
            ImmutableMap.<String, Materials>builder()
                .put("item.AluminiumBars", Materials.Aluminium)
                .put("item.ChromeBars", Materials.Chrome)
                .put("item.ConductiveIronBars", Materials.ConductiveIron)
                .put("item.ElectricalSteelBars", Materials.ElectricalSteel)
                .put("item.EnderiumBars", Materials.Enderium)
                .put("item.EnderiumBaseBars", Materials.EnderiumBase)
                .put("item.EnergeticAlloyBars", Materials.EnergeticAlloy)
                .put("item.IridiumBars", Materials.Iridium)
                .put("item.NeutroniumBars", Materials.Neutronium)
                .put("item.OsmiumBars", Materials.Osmium)
                .put("item.PulsatingIronBars", Materials.PulsatingIron)
                .put("item.RedstoneAlloyBars", Materials.RedstoneAlloy)
                .put("item.SoulariumBars", Materials.Soularium)
                .put("item.StainlessSteelBars", Materials.StainlessSteel)
                .put("item.SteelBars", Materials.Steel)
                .put("item.TitaniumBars", Materials.Titanium)
                .put("item.TungstenBars", Materials.Tungsten)
                .put("item.TungstenSteelBars", Materials.TungstenSteel)
                .put("item.VibrantAlloyBars", Materials.VibrantAlloy)
                .build(),
            EnderIO.ID,
            ImmutableMap.<String, Materials>builder()
                .put("blockDarkIronBars", Materials.DarkSteel)
                .put("blockEndSteelBars", Materials.EndSteel)
                .put("blockSoulariumBars", Materials.Soularium)
                .build())
            .forEach(
                (modId, items) -> items
                    .forEach((item, material) -> registerMetalBarUnificationData(modId, item, material)));
        GTOreDictUnificator
            .addItemData(GTModHandler.getIC2Item("ironFurnace", 1L), new ItemData(Materials.Iron, 18144000L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Food_Can_Empty.get(1L), new ItemData(Materials.Tin, 1814400L));
        GTOreDictUnificator.addItemData(ItemList.IC2_Fuel_Rod_Empty.get(1L), new ItemData(Materials.Iron, 3628800L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.light_weighted_pressure_plate, 1, 32767),
            new ItemData(Materials.Gold, 7257600L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.heavy_weighted_pressure_plate, 1, 32767),
            new ItemData(Materials.Iron, 7257600L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Railcraft.ID, "anvil", 1L, 0),
            new ItemData(Materials.Steel, 108864000L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Railcraft.ID, "anvil", 1L, 1),
            new ItemData(Materials.Steel, 72576000L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Railcraft.ID, "anvil", 1L, 2),
            new ItemData(Materials.Steel, 36288000L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.anvil, 1, 0), new ItemData(Materials.Iron, 108864000L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.anvil, 1, 1), new ItemData(Materials.Iron, 72576000L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.anvil, 1, 2), new ItemData(Materials.Iron, 36288000L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.hopper, 1, 32767),
            new ItemData(Materials.Iron, 18144000L, new MaterialStack(Materials.Wood, 29030400L)));
        GTOreDictUnificator.addItemData(ItemList.Cell_Universal_Fluid.get(1L), new ItemData(Materials.Tin, 7257600L));
        GTOreDictUnificator.addItemData(ItemList.Cell_Empty.get(1L), new ItemData(Materials.Tin, 7257600L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.tripwire_hook, 1, 32767),
            new ItemData(
                Materials.Iron,
                OrePrefixes.ring.mMaterialAmount * 2L,
                new MaterialStack(Materials.Wood, 3628800L)));
        GTOreDictUnificator.addItemData(ItemList.Bottle_Empty.get(1L), new ItemData(Materials.Glass, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.potionitem, 1, 32767), new ItemData(Materials.Glass, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stained_glass, 1, 32767), new ItemData(Materials.Glass, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.glass, 1, 32767), new ItemData(Materials.Glass, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stained_glass_pane, 1, 32767), new ItemData(Materials.Glass, 1360800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.glass_pane, 1, 32767), new ItemData(Materials.Glass, 1360800L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.clock, 1, 32767),
            new ItemData(Materials.Gold, 14515200L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.compass, 1, 32767),
            new ItemData(Materials.Iron, 14515200L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.iron_horse_armor, 1, 32767),
            new ItemData(Materials.Iron, 29030400L, new MaterialStack(Materials.Leather, 21772800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.golden_horse_armor, 1, 32767),
            new ItemData(Materials.Gold, 29030400L, new MaterialStack(Materials.Leather, 21772800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Items.diamond_horse_armor, 1, 32767),
            new ItemData(Materials.Diamond, 29030400L, new MaterialStack(Materials.Leather, 21772800L)));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.leather, 1, 32767), new ItemData(Materials.Leather, 3628800L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.beacon, 1, 32767),
            new ItemData(
                Materials.NetherStar,
                3628800L,
                new MaterialStack(Materials.Obsidian, 10886400L),
                new MaterialStack(Materials.Glass, 18144000L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.enchanting_table, 1, 32767),
            new ItemData(
                Materials.Diamond,
                7257600L,
                new MaterialStack(Materials.Obsidian, 14515200L),
                new MaterialStack(Materials.Paper, 10886400L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.ender_chest, 1, 32767),
            new ItemData(Materials.EnderEye, 3628800L, new MaterialStack(Materials.Obsidian, 29030400L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.bookshelf, 1, 32767),
            new ItemData(Materials.Paper, 32659200L, new MaterialStack(Materials.Wood, 21772800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.lever, 1, 32767),
            new ItemData(Materials.Stone, 3628800L, new MaterialStack(Materials.Wood, 1814400L)));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.ice, 1, 32767), new ItemData(Materials.Ice, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.packed_ice, 1, 32767), new ItemData(Materials.Ice, 7257600L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.snow, 1, 32767), new ItemData(Materials.Snow, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.snowball, 1, 32767), new ItemData(Materials.Snow, 907200L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.snow_layer, 1, 32767), new ItemData(Materials.Snow, -1L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.sand, 1, 32767), new ItemData(Materials.Sand, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.sandstone, 1, 32767), new ItemData(Materials.Sand, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 0), new ItemData(Materials.Stone, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 8), new ItemData(Materials.Stone, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 0), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 8), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 1), new ItemData(Materials.Sand, 1814400L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 9), new ItemData(Materials.Sand, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 1), new ItemData(Materials.Sand, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 9), new ItemData(Materials.Sand, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 2), new ItemData(Materials.Wood, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 10), new ItemData(Materials.Wood, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 2), new ItemData(Materials.Wood, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 10), new ItemData(Materials.Wood, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 3), new ItemData(Materials.Stone, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 11), new ItemData(Materials.Stone, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 3), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 11), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 5), new ItemData(Materials.Stone, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_slab, 1, 13), new ItemData(Materials.Stone, 1814400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 5), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.double_stone_slab, 1, 13), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.stone, 1, 32767), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.furnace, 1, 32767), new ItemData(Materials.Stone, 29030400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.lit_furnace, 1, 32767), new ItemData(Materials.Stone, 29030400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stonebrick, 1, 32767), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.cobblestone, 1, 32767), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.mossy_cobblestone, 1, 32767), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_button, 1, 32767), new ItemData(Materials.Stone, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.stone_pressure_plate, 1, 32767), new ItemData(Materials.Stone, 7257600L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.ladder, 1, 32767), new ItemData(Materials.Wood, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.wooden_button, 1, 32767), new ItemData(Materials.Wood, 3628800L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.wooden_pressure_plate, 1, 32767), new ItemData(Materials.Wood, 7257600L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.fence, 1, 32767), new ItemData(Materials.Wood, 5443200L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.bowl, 1, 32767), new ItemData(Materials.Wood, 3628800L));
        GTOreDictUnificator.addItemData(new ItemStack(Items.sign, 1, 32767), new ItemData(Materials.Wood, 7257600L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Items.wooden_door, 1, 32767), new ItemData(Materials.Wood, 21772800L));
        GTOreDictUnificator.addItemData(new ItemStack(Blocks.chest, 1, 32767), new ItemData(Materials.Wood, 29030400L));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.trapped_chest, 1, 32767), new ItemData(Materials.Wood, 29030400L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.unlit_redstone_torch, 1, 32767),
            new ItemData(Materials.Wood, 1814400L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.redstone_torch, 1, 32767),
            new ItemData(Materials.Wood, 1814400L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.noteblock, 1, 32767),
            new ItemData(Materials.Wood, 29030400L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.jukebox, 1, 32767),
            new ItemData(Materials.Wood, 29030400L, new MaterialStack(Materials.Diamond, 3628800L)));
        GTOreDictUnificator
            .addItemData(new ItemStack(Blocks.crafting_table, 1, 32767), new ItemData(Materials.Wood, 14515200L));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.piston, 1, 32767),
            new ItemData(Materials.Stone, 14515200L, new MaterialStack(Materials.Wood, 10886400L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.sticky_piston, 1, 32767),
            new ItemData(Materials.Stone, 14515200L, new MaterialStack(Materials.Wood, 10886400L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.dispenser, 1, 32767),
            new ItemData(Materials.Stone, 25401600L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            new ItemStack(Blocks.dropper, 1, 32767),
            new ItemData(Materials.Stone, 25401600L, new MaterialStack(Materials.Redstone, 3628800L)));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Thaumcraft.ID, "ItemNuggetChicken", 1L, 32767),
            new ItemData(Materials.MeatCooked, 403200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Thaumcraft.ID, "ItemNuggetBeef", 1L, 32767),
            new ItemData(Materials.MeatCooked, 403200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Thaumcraft.ID, "ItemNuggetPork", 1L, 32767),
            new ItemData(Materials.MeatCooked, 403200L));
        GTOreDictUnificator.addItemData(
            GTModHandler.getModItem(Thaumcraft.ID, "ItemNuggetFish", 1L, 32767),
            new ItemData(Materials.MeatCooked, 403200L));

        for (ItemStack tItem : new ItemStack[] { GTModHandler.getModItem(TwilightForest.ID, "item.meefRaw", 1L, 0),
            GTModHandler.getModItem(TwilightForest.ID, "item.venisonRaw", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "muttonrawItem", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "turkeyrawItem", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "rabbitrawItem", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "venisonrawItem", 1L, 0), new ItemStack(Items.porkchop),
            new ItemStack(Items.beef), new ItemStack(Items.chicken), new ItemStack(Items.fish) }) {
            if (tItem != null) {
                GTOreDictUnificator.addItemData(
                    GTUtility.copyMetaData(32767, tItem),
                    new ItemData(Materials.MeatRaw, 3628800L, new MaterialStack(Materials.Bone, 403200L)));
            }
        }
        for (ItemStack tItem : new ItemStack[] { GTModHandler.getModItem(TwilightForest.ID, "item.meefSteak", 1L, 0),
            GTModHandler.getModItem(TwilightForest.ID, "item.venisonCooked", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "muttoncookedItem", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "turkeycookedItem", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "rabbitcookedItem", 1L, 0),
            GTModHandler.getModItem(PamsHarvestCraft.ID, "venisoncookedItem", 1L, 0),
            new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_chicken),
            new ItemStack(Items.cooked_fished) }) {
            if (tItem != null) {
                GTOreDictUnificator.addItemData(
                    GTUtility.copyMetaData(32767, tItem),
                    new ItemData(Materials.MeatCooked, 3628800L, new MaterialStack(Materials.Bone, 403200L)));
            }
        }
    }

    private static void registerMetalBarUnificationData(String modId, String itemName, Materials material) {
        GTOreDictUnificator
            .addItemData(GTModHandler.getModItem(modId, itemName, 8L, 0), new ItemData(material, 10886400L));
    }
}
