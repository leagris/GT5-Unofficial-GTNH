package gregtech.loaders.preload;

import static gregtech.api.enums.FluidState.GAS;
import static gregtech.api.enums.FluidState.LIQUID;
import static gregtech.api.enums.FluidState.MOLTEN;
import static gregtech.api.enums.FluidState.PLASMA;
import static gregtech.api.enums.FluidState.SLURRY;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TwilightForest;
import static gregtech.api.recipe.RecipeMaps.fluidCannerRecipes;
import static gregtech.api.recipe.RecipeMaps.maceratorRecipes;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.Locale;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import codechicken.nei.api.API;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsKevlar;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.fluid.GTFluidFactory;
import gregtech.api.items.BlockLongDistancePipe;
import gregtech.api.items.GTGenericItem;
import gregtech.api.items.ItemBreederCell;
import gregtech.api.items.ItemCoolantCellIC;
import gregtech.api.items.ItemRadioactiveCellIC;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockBlackholeRenderer;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings10;
import gregtech.common.blocks.BlockCasings11;
import gregtech.common.blocks.BlockCasings12;
import gregtech.common.blocks.BlockCasings13;
import gregtech.common.blocks.BlockCasings2;
import gregtech.common.blocks.BlockCasings3;
import gregtech.common.blocks.BlockCasings4;
import gregtech.common.blocks.BlockCasings5;
import gregtech.common.blocks.BlockCasings6;
import gregtech.common.blocks.BlockCasings8;
import gregtech.common.blocks.BlockCasings9;
import gregtech.common.blocks.BlockCasingsNH;
import gregtech.common.blocks.BlockConcretes;
import gregtech.common.blocks.BlockCyclotronCoils;
import gregtech.common.blocks.BlockDrone;
import gregtech.common.blocks.BlockFrameBox;
import gregtech.common.blocks.BlockGlass1;
import gregtech.common.blocks.BlockGranites;
import gregtech.common.blocks.BlockLaser;
import gregtech.common.blocks.BlockMachines;
import gregtech.common.blocks.BlockMetal;
import gregtech.common.blocks.BlockNanoForgeRenderer;
import gregtech.common.blocks.BlockOres;
import gregtech.common.blocks.BlockReinforced;
import gregtech.common.blocks.BlockStones;
import gregtech.common.blocks.BlockTintedIndustrialGlass;
import gregtech.common.blocks.BlockWormholeRender;
import gregtech.common.blocks.TileEntityOres;
import gregtech.common.items.ItemAdvancedSensorCard;
import gregtech.common.items.ItemDepletedCell;
import gregtech.common.items.ItemFluidDisplay;
import gregtech.common.items.ItemIntegratedCircuit;
import gregtech.common.items.ItemMagLevHarness;
import gregtech.common.items.ItemNeutronReflector;
import gregtech.common.items.ItemSensorCard;
import gregtech.common.items.ItemTierDrone;
import gregtech.common.items.ItemVolumetricFlask;
import gregtech.common.items.ItemWirelessHeadphones;
import gregtech.common.items.MetaGeneratedItem01;
import gregtech.common.items.MetaGeneratedItem02;
import gregtech.common.items.MetaGeneratedItem03;
import gregtech.common.items.MetaGeneratedItem98;
import gregtech.common.items.MetaGeneratedItem99;
import gregtech.common.items.MetaGeneratedTool01;
import gregtech.common.tileentities.render.TileEntityBlackhole;
import gregtech.common.tileentities.render.TileEntityDrone;
import gregtech.common.tileentities.render.TileEntityLaser;
import gregtech.common.tileentities.render.TileEntityNanoForgeRenderer;
import gregtech.common.tileentities.render.TileEntityWormhole;

public class LoaderGTBlockFluid implements Runnable {

    @Override
    public void run() {
        Materials.Water.mFluid = FluidRegistry.getFluid("water");
        Materials.Ice.mFluid = FluidRegistry.getFluid("water");
        Materials.Lava.mFluid = FluidRegistry.getFluid("lava");

        GTLog.out.println("GTMod: Register Books.");

        GTUtility.getWrittenBook(
            "Manual_Printer",
            "Printer Manual V2.0",
            "Gregorius Techneticies",
            "This Manual explains the different Functionalities the GregTech Printing Factory has built in, which are not in NEI. Most got NEI Support now, but there is still some left without it.",
            "1. Coloring Items and Blocks: You know those Crafting Recipes, which have a dye surrounded by 8 Item to dye them? Or the ones which have just one Item and one Dye in the Grid? Those two Recipe Types can be cheaply automated using the Printer.",
            "The Colorization Functionality even optimizes the Recipes, which normally require 8 Items + 1 Dye to 1 Item and an 8th of the normally used Dye in Fluid Form, isn't that awesome?",
            "2. Copying Books: This Task got slightly harder. The first Step is putting the written and signed Book inside the Scanner with a Data Stick ready to receive the Data.",
            "Now insert the Stick into the Data Slot of the Printer and add 3 pieces of Paper together with 144 Liters of actual Ink Fluid. Water mixed and chemical Dyes won't work on Paper without messing things up!",
            "You got a stack of Pages for your new Book, just put them into the Assembler with some Glue and a piece of Leather for the Binding, and you receive an identical copy of the Book, which would stack together with the original.",
            "3. Renaming Items: This Functionality is no longer Part of the Printer. There is now a Name Mold for the Forming Press to imprint a Name into an Item, just rename the Mold in an Anvil and use it in the Forming Press on any Item.",
            "4. Crafting of Books, Maps, Nametags etc etc etc: Those Recipes moved to other Machines, just look them up in NEI.");

        GTUtility.getWrittenBook(
            "Manual_Punch_Cards",
            "Punch Card Manual V0.0",
            "Gregorius Techneticies",
            "This Manual will explain the Functionality of the Punch Cards, once they are fully implemented. And no, they won't be like the IRL Punch Cards. This is just a current Idea Collection.",
            "(i1&&i2)?o1=15:o1=0;=10",
            "ignore all Whitespace Characters, use Long for saving the Numbers",
            "&& || ^^ & | ^ ! ++ -- + - % / // * ** << >> >>> < > <= >= == !=  ~ ( ) ?: , ; ;= ;=X; = i0 i1 i2 i3 i4 i5 o0 o1 o2 o3 o4 o5 v0 v1 v2 v3 v4 v5 v6 v7 v8 v9 m0 m1 m2 m3 m4 m5 m6 m7 m8 m9 A B C D E F",
            "'0' = false, 'everything but 0' = true, '!' turns '0' into '1' and everything else into '0'",
            "',' is just a separator for multiple executed Codes in a row.",
            "';' means that the Program waits until the next tick before continuing. ';=10' and ';=10;' both mean that it will wait 10 Ticks instead of 1. And ';=0' or anything < 0 will default to 0.",
            "If the '=' Operator is used within Brackets, it returns the value the variable has been set to.",
            "The Program saves the Char Index of the current Task, the 10 Variables (which reset to 0 as soon as the Program Loop stops), the 10 Member Variables and the remaining waiting Time in its NBT.",
            "A = 10, B = 11, C = 12, D = 13, E = 14, F = 15, just for Hexadecimal Space saving, since Redstone has only 4 Bits.",
            "For implementing Loops you just need 1 Punch Card per Loop, these Cards can restart once they are finished, depending on how many other Cards there are in the Program Loop you inserted your Card into, since it will process them procedurally.",
            "A Punch Card Processor can run up to four Loops, each with the length of seven Punch Cards, parallel.",
            "Why does the Punch Card need Ink to be made, you ask? Because the empty one needs to have some lines on, and the for the punched one it prints the Code to execute in a human readable format on the Card.");

        GTUtility.getWrittenBook(
            "Manual_Microwave",
            "Microwave Oven Manual",
            "Kitchen Industries",
            "Congratulations, you inserted a random seemingly empty Book into the Microwave and these Letters appeared out of nowhere.",
            "You just got a Microwave Oven and asked yourself 'why do I even need it?'. It's simple, the Microwave can cook for just 128 EU and at an insane speed. Not even a normal E-furnace can do it that fast and cheap!",
            "This is the cheapest and fastest way to cook for you. That is why the Microwave Oven can be found in almost every Kitchen (see www.youwannabuyakitchen.ly).",
            "Long time exposure to Microwaves can cause Cancer, but we doubt Steve lives long enough to die because of that.",
            "Do not insert any Metals. It might result in an Explosion.",
            "Do not dry Animals with it. It will result in a Hot Dog, no matter which Animal you put into it.",
            "Do not insert inflammable Objects. The Oven will catch on Fire.",
            "Do not insert Explosives such as Eggs. Just don't.");

        GTLog.out.println("GTMod: Register Items.");

        new ItemIntegratedCircuit();
        new MetaGeneratedItem01();
        new MetaGeneratedItem02();
        new MetaGeneratedItem03();
        // GT_MetaGenerated_Item_98 is initialized in GTProxy.onPostInitialization()
        // because we need to wait for fluids to be registered.
        // Pre-initialization needs to happen before then, though, because the cell icons get deleted at some point
        // between load and post-load.
        MetaGeneratedItem98.preInit();
        new MetaGeneratedItem99();
        new MetaGeneratedTool01();
        new ItemFluidDisplay();
        new ItemWirelessHeadphones();
        new ItemMagLevHarness();

        // Tiered recipe materials actually appear to be set in MTEBasicMachineWithRecipe, making these
        // unused
        ItemList.Rotor_LV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1L));
        ItemList.Rotor_MV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L));
        ItemList.Rotor_HV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L));
        ItemList.Rotor_EV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1L));
        ItemList.Rotor_IV.set(GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L));

        ItemList.VOLUMETRIC_FLASK.set(new ItemVolumetricFlask("Volumetric_Flask", "Volumetric flask", 1_000));

        if (Mods.IC2NuclearControl.isModLoaded()) {
            ItemList.NC_SensorCard.set(new ItemSensorCard("sensorcard", "GregTech Sensor Card"));
            ItemList.NC_AdvancedSensorCard.set(new ItemAdvancedSensorCard());
        } else {
            ItemList.NC_SensorCard
                .set(new GTGenericItem("sensorcard", "GregTech Sensor Card", "Nuclear Control not installed"));
            ItemList.NC_AdvancedSensorCard.set(
                new GTGenericItem(
                    "advancedsensorcard",
                    "GregTech Advanced Sensor Card",
                    "Nuclear Control not installed"));
        }

        ItemList.Neutron_Reflector.set(new ItemNeutronReflector("neutronreflector", "Iridium Neutron Reflector", 0));
        ItemList.Reactor_Coolant_He_1
            .set(new ItemCoolantCellIC("60k_Helium_Coolantcell", "60k He Coolant Cell", 60_000));
        ItemList.Reactor_Coolant_He_3
            .set(new ItemCoolantCellIC("180k_Helium_Coolantcell", "180k He Coolant Cell", 180_000));
        ItemList.Reactor_Coolant_He_6
            .set(new ItemCoolantCellIC("360k_Helium_Coolantcell", "360k He Coolant Cell", 360_000));
        ItemList.Reactor_Coolant_NaK_1
            .set(new ItemCoolantCellIC("60k_NaK_Coolantcell", "60k NaK Coolant Cell", 60_000));
        ItemList.Reactor_Coolant_NaK_3
            .set(new ItemCoolantCellIC("180k_NaK_Coolantcell", "180k NaK Coolant Cell", 180_000));
        ItemList.Reactor_Coolant_NaK_6
            .set(new ItemCoolantCellIC("360k_NaK_Coolantcell", "360k NaK Coolant Cell", 360_000));

        ItemList.Reactor_Coolant_Sp_1
            .set(new ItemCoolantCellIC("180k_Space_Coolantcell", "180k Sp Coolant Cell", 180_000));

        ItemList.Reactor_Coolant_Sp_2
            .set(new ItemCoolantCellIC("360k_Space_Coolantcell", "360k Sp Coolant Cell", 360_000));

        ItemList.Reactor_Coolant_Sp_3
            .set(new ItemCoolantCellIC("540k_Space_Coolantcell", "540k Sp Coolant Cell", 540_000));

        ItemList.Reactor_Coolant_Sp_6
            .set(new ItemCoolantCellIC("1080k_Space_Coolantcell", "1080k Sp Coolant Cell", 1_080_000));

        ItemList.neutroniumHeatCapacitor
            .set(new ItemCoolantCellIC("neutroniumHeatCapacitor", "1G Neutronium Heat Capacitor", 1_000_000_000));

        // Lithium
        ItemList.DepletedRodLithium.set(new ItemDepletedCell("depletedRodLithium", "Fuel Rod (Tritium)", 1));
        ItemList.RodLithium.set(
            new ItemBreederCell(
                "rodLithium",
                "Fuel Rod (Lithium)",
                "Source of tritium",
                3_000,
                1,
                10_000,
                () -> ItemList.DepletedRodLithium.get(1)));

        // Glowstone
        ItemList.DepletedRodGlowstone.set(new ItemDepletedCell("depletedRodGlowstone", "Fuel Rod (Sunnarium)", 1));
        ItemList.RodGlowstone.set(
            new ItemBreederCell(
                "rodGlowstone",
                "Fuel Rod (Glowstone)",
                "Source of sunnarium",
                3_000,
                1,
                10_000,
                () -> ItemList.DepletedRodGlowstone.get(1)));

        // Thorium
        ItemList.DepletedRodThorium.set(new ItemDepletedCell("depletedRodThorium", "Fuel Rod (Depleted Thorium)", 1));
        ItemList.DepletedRodThorium2
            .set(new ItemDepletedCell("depletedRodThorium2", "Dual Fuel Rod (Depleted Thorium)", 1));
        ItemList.DepletedRodThorium4
            .set(new ItemDepletedCell("depletedRodThorium4", "Quad Fuel Rod (Depleted Thorium)", 1));

        ItemList.RodThorium.set(
            new ItemRadioactiveCellIC(
                "rodThorium",
                "Fuel Rod (Thorium)",
                1,
                50_000,
                0.4F,
                0,
                0.25F,
                ItemList.DepletedRodThorium.get(1),
                false));
        ItemList.RodThorium2.set(
            new ItemRadioactiveCellIC(
                "rodThorium2",
                "Dual Fuel Rod (Thorium)",
                2,
                50_000,
                0.4F,
                0,
                0.25F,
                ItemList.DepletedRodThorium2.get(1),
                false));
        ItemList.RodThorium4.set(
            new ItemRadioactiveCellIC(
                "rodThorium4",
                "Quad Fuel Rod (Thorium)",
                4,
                50_000,
                0.4F,
                0,
                0.25F,
                ItemList.DepletedRodThorium4.get(1),
                false));

        // Uranium
        ItemList.DepletedRodUranium.set(new ItemDepletedCell("depletedRodUranium", "Fuel Rod (Depleted Uranium)", 1));
        ItemList.DepletedRodUranium2
            .set(new ItemDepletedCell("depletedRodUranium2", "Dual Fuel Rod (Depleted Uranium)", 1));
        ItemList.DepletedRodUranium4
            .set(new ItemDepletedCell("depletedRodUranium4", "Quad Fuel Rod (Depleted Uranium)", 1));

        ItemList.RodUranium.set(
            new ItemRadioactiveCellIC(
                "rodUranium",
                "Fuel Rod (Uranium)",
                1,
                20_000,
                2F,
                1,
                1F,
                ItemList.DepletedRodUranium.get(1),
                false));
        ItemList.RodUranium2.set(
            new ItemRadioactiveCellIC(
                "rodUranium2",
                "Dual Fuel Rod (Uranium)",
                2,
                20_000,
                2F,
                1,
                1F,
                ItemList.DepletedRodUranium2.get(1),
                false));
        ItemList.RodUranium4.set(
            new ItemRadioactiveCellIC(
                "rodUranium4",
                "Quad Fuel Rod (Uranium)",
                4,
                20_000,
                2F,
                1,
                1F,
                ItemList.DepletedRodUranium4.get(1),
                false));

        // MOX
        ItemList.DepletedRodMOX.set(new ItemDepletedCell("depletedRodMOX", "Fuel Rod (Depleted MOX)", 1));
        ItemList.DepletedRodMOX2.set(new ItemDepletedCell("depletedRodMOX2", "Dual Fuel Rod (Depleted MOX)", 1));
        ItemList.DepletedRodMOX4.set(new ItemDepletedCell("depletedRodMOX4", "Quad Fuel Rod (Depleted MOX)", 1));

        ItemList.RodMOX.set(
            new ItemRadioactiveCellIC(
                "rodMOX",
                "Fuel Rod (Mox)",
                1,
                10_000,
                2F,
                1,
                1F,
                ItemList.DepletedRodMOX.get(1),
                true));
        ItemList.RodMOX2.set(
            new ItemRadioactiveCellIC(
                "rodMOX2",
                "Dual Fuel Rod (Mox)",
                2,
                10_000,
                2F,
                1,
                1F,
                ItemList.DepletedRodMOX2.get(1),
                true));
        ItemList.RodMOX4.set(
            new ItemRadioactiveCellIC(
                "rodMOX4",
                "Quad Fuel Rod (Mox)",
                4,
                10_000,
                2F,
                1,
                1F,
                ItemList.DepletedRodMOX4.get(1),
                true));

        // High Density Uranium
        ItemList.DepletedRodHighDensityUranium.set(
            new ItemDepletedCell("depletedRodHighDensityUranium", "Fuel Rod (Depleted High Density Uranium)", 100));
        ItemList.DepletedRodHighDensityUranium2.set(
            new ItemDepletedCell(
                "depletedRodHighDensityUranium2",
                "Dual Fuel Rod (Depleted High Density Uranium)",
                200));
        ItemList.DepletedRodHighDensityUranium4.set(
            new ItemDepletedCell(
                "depletedRodHighDensityUranium4",
                "Quad Fuel Rod (Depleted High Density Uranium)",
                400));

        ItemList.RodHighDensityUranium.set(
            new ItemRadioactiveCellIC(
                "rodHighDensityUranium",
                "Fuel Rod (High Density Uranium)",
                1,
                70_000,
                100,
                800,
                4,
                ItemList.DepletedRodHighDensityUranium.get(1),
                false));
        ItemList.RodHighDensityUranium2.set(
            new ItemRadioactiveCellIC(
                "rodHighDensityUranium2",
                "Dual Fuel Rod (High Density Uranium)",
                2,
                70_000,
                100,
                1_600,
                4,
                ItemList.DepletedRodHighDensityUranium2.get(1),
                false));
        ItemList.RodHighDensityUranium4.set(
            new ItemRadioactiveCellIC(
                "rodHighDensityUranium4",
                "Quad Fuel Rod (High Density Uranium)",
                4,
                70_000,
                100,
                3_200,
                4,
                ItemList.DepletedRodHighDensityUranium4.get(1),
                false));

        // High Density Plutonium
        ItemList.DepletedRodHighDensityPlutonium.set(
            new ItemDepletedCell("depletedRodHighDensityPlutonium", "Fuel Rod (Depleted High Density Plutonium)", 120));
        ItemList.DepletedRodHighDensityPlutonium2.set(
            new ItemDepletedCell(
                "depletedRodHighDensityPlutonium2",
                "Dual Fuel Rod (Depleted High Density Plutonium)",
                240));
        ItemList.DepletedRodHighDensityPlutonium4.set(
            new ItemDepletedCell(
                "depletedRodHighDensityPlutonium4",
                "Quad Fuel Rod (Depleted High Density Plutonium)",
                480));

        ItemList.RodHighDensityPlutonium.set(
            new ItemRadioactiveCellIC(
                "rodHighDensityPlutonium",
                "Fuel Rod (High Density Plutonium)",
                1,
                70_000,
                50,
                1_000,
                4,
                ItemList.DepletedRodHighDensityPlutonium.get(1),
                true));
        ItemList.RodHighDensityPlutonium2.set(
            new ItemRadioactiveCellIC(
                "rodHighDensityPlutonium2",
                "Dual Fuel Rod (High Density Plutonium)",
                2,
                70_000,
                50,
                2_000,
                4,
                ItemList.DepletedRodHighDensityPlutonium2.get(1),
                true));
        ItemList.RodHighDensityPlutonium4.set(
            new ItemRadioactiveCellIC(
                "rodHighDensityPlutonium4",
                "Quad Fuel Rod (High Density Plutonium)",
                4,
                70_000,
                50,
                4_000,
                4,
                ItemList.DepletedRodHighDensityPlutonium4.get(1),
                true));

        // Excited Uranium
        ItemList.DepletedRodExcitedUranium
            .set(new ItemDepletedCell("depletedRodExcitedUranium", "Fuel Rod (Depleted Excited Uranium)", 800));
        ItemList.DepletedRodExcitedUranium2
            .set(new ItemDepletedCell("depletedRodExcitedUranium2", "Dual Fuel Rod (Depleted Excited Uranium)", 1_600));
        ItemList.DepletedRodExcitedUranium4
            .set(new ItemDepletedCell("depletedRodExcitedUranium4", "Quad Fuel Rod (Depleted Excited Uranium)", 3_200));

        ItemList.RodExcitedUranium.set(
            new ItemRadioactiveCellIC(
                "rodExcitedUranium",
                "Fuel Rod (Excited Uranium)",
                1,
                6_000,
                1_200,
                8_000,
                64,
                ItemList.DepletedRodExcitedUranium.get(1),
                false));
        ItemList.RodExcitedUranium2.set(
            new ItemRadioactiveCellIC(
                "rodExcitedUranium2",
                "Dual Fuel Rod (Excited Uranium)",
                2,
                6_000,
                1_200,
                8_000,
                64,
                ItemList.DepletedRodExcitedUranium2.get(1),
                false));
        ItemList.RodExcitedUranium4.set(
            new ItemRadioactiveCellIC(
                "rodExcitedUranium4",
                "Quad Fuel Rod (Excited Uranium)",
                4,
                6_000,
                1_200,
                8_000,
                64,
                ItemList.DepletedRodExcitedUranium4.get(1),
                false));

        // Excited Plutonium
        ItemList.DepletedRodExcitedPlutonium
            .set(new ItemDepletedCell("depletedRodExcitedPlutonium", "Fuel Rod (Depleted Excited Plutonium)", 1_000));
        ItemList.DepletedRodExcitedPlutonium2.set(
            new ItemDepletedCell("depletedRodExcitedPlutonium2", "Dual Fuel Rod (Depleted Excited Plutonium)", 2_000));
        ItemList.DepletedRodExcitedPlutonium4.set(
            new ItemDepletedCell("depletedRodExcitedPlutonium4", "Quad Fuel Rod (Depleted Excited Plutonium)", 4_000));

        ItemList.RodExcitedPlutonium.set(
            new ItemRadioactiveCellIC(
                "rodExcitedPlutonium",
                "Fuel Rod (Excited Plutonium)",
                1,
                10_000,
                1_600,
                10_000,
                64,
                ItemList.DepletedRodExcitedPlutonium.get(1),
                true));
        ItemList.RodExcitedPlutonium2.set(
            new ItemRadioactiveCellIC(
                "rodExcitedPlutonium2",
                "Fuel Rod (Excited Plutonium)",
                2,
                10_000,
                1_600,
                10_000,
                64,
                ItemList.DepletedRodExcitedPlutonium2.get(1),
                true));
        ItemList.RodExcitedPlutonium4.set(
            new ItemRadioactiveCellIC(
                "rodExcitedPlutonium4",
                "Fuel Rod (Excited Plutonium)",
                4,
                10_000,
                1_600,
                10_000,
                64,
                ItemList.DepletedRodExcitedPlutonium4.get(1),
                true));

        // Naquadah
        ItemList.DepletedRodNaquadah
            .set(new ItemDepletedCell("depletedRodNaquadah", "Fuel Rod (Depleted Naquadah)", 1));
        ItemList.DepletedRodNaquadah2
            .set(new ItemDepletedCell("depletedRodNaquadah2", "Dual Fuel Rod (Depleted Naquadah)", 1));
        ItemList.DepletedRodNaquadah4
            .set(new ItemDepletedCell("depletedRodNaquadah4", "Quad Fuel Rod (Depleted Naquadah)", 1));
        ItemList.DepletedRodNaquadah32.set(new ItemDepletedCell("depletedRodNaquadah32", "The Core (Depleted)", 32));

        ItemList.RodNaquadah.set(
            new ItemRadioactiveCellIC(
                "rodNaquadah",
                "Fuel Rod (Naquadah)",
                1,
                100_000,
                4F,
                1,
                1F,
                ItemList.DepletedRodNaquadah.get(1),
                false));
        ItemList.RodNaquadah2.set(
            new ItemRadioactiveCellIC(
                "rodNaquadah2",
                "Dual Fuel Rod (Naquadah)",
                2,
                100_000,
                4F,
                1,
                1F,
                ItemList.DepletedRodNaquadah2.get(1),
                false));
        ItemList.RodNaquadah4.set(
            new ItemRadioactiveCellIC(
                "rodNaquadah4",
                "Quad Fuel Rod (Naquadah)",
                4,
                100_000,
                4F,
                1,
                1F,
                ItemList.DepletedRodNaquadah4.get(1),
                false));
        ItemList.RodNaquadah32.set(
            new ItemRadioactiveCellIC(
                "rodNaquadah32",
                "The Core",
                32,
                100_000,
                8F,
                32,
                1F,
                ItemList.DepletedRodNaquadah32.get(1),
                false));

        // Naquadria
        ItemList.DepletedRodNaquadria
            .set(new ItemDepletedCell("depletedRodNaquadria", "Fuel Rod (Depleted Naquadria)", 1));
        ItemList.DepletedRodNaquadria2
            .set(new ItemDepletedCell("depletedRodNaquadria2", "Dual Fuel Rod (Depleted Naquadria)", 1));
        ItemList.DepletedRodNaquadria4
            .set(new ItemDepletedCell("depletedRodNaquadria4", "Quad Fuel Rod (Depleted Naquadria)", 1));

        ItemList.RodNaquadria.set(
            new ItemRadioactiveCellIC(
                "rodNaquadria",
                "Fuel Rod (Naquadria)",
                1,
                100_000,
                4F,
                1,
                1F,
                ItemList.DepletedRodNaquadria.get(1),
                true));
        ItemList.RodNaquadria2.set(
            new ItemRadioactiveCellIC(
                "rodNaquadria2",
                "Dual Fuel Rod (Naquadria)",
                2,
                100_000,
                4F,
                1,
                1F,
                ItemList.DepletedRodNaquadria2.get(1),
                true));
        ItemList.RodNaquadria4.set(
            new ItemRadioactiveCellIC(
                "rodNaquadria4",
                "Quad Fuel Rod (Naquadria)",
                4,
                100_000,
                4F,
                1,
                1F,
                ItemList.DepletedRodNaquadria4.get(1),
                true));

        // Tiberium
        ItemList.DepletedRodTiberium
            .set(new ItemDepletedCell("depletedRodTiberium", "Fuel Rod (Depleted Tiberium)", 1));
        ItemList.DepletedRodTiberium2
            .set(new ItemDepletedCell("depletedRodTiberium2", "Dual Fuel Rod (Depleted Tiberium)", 1));
        ItemList.DepletedRodTiberium4
            .set(new ItemDepletedCell("depletedRodTiberium4", "Quad Fuel Rod (Depleted Tiberium)", 1));

        ItemList.RodTiberium.set(
            new ItemRadioactiveCellIC(
                "rodTiberium",
                "Fuel Rod (Tiberium)",
                1,
                50_000,
                2F,
                1,
                0.5F,
                ItemList.DepletedRodTiberium.get(1),
                false));
        ItemList.RodTiberium2.set(
            new ItemRadioactiveCellIC(
                "rodTiberium2",
                "Dual Fuel Rod (Tiberium)",
                2,
                50_000,
                2F,
                1,
                0.5F,
                ItemList.DepletedRodTiberium2.get(1),
                false));
        ItemList.RodTiberium4.set(
            new ItemRadioactiveCellIC(
                "rodTiberium4",
                "Quad Fuel Rod (Tiberium)",
                4,
                50_000,
                2F,
                1,
                0.5F,
                ItemList.DepletedRodTiberium4.get(1),
                false));

        GTLog.out.println("GTMod: Adding Blocks.");
        GregTechAPI.sBlockMachines = new BlockMachines();
        GregTechAPI.sBlockCasings1 = new BlockCasings1();
        GregTechAPI.sBlockCasings2 = new BlockCasings2();
        GregTechAPI.sBlockCasings3 = new BlockCasings3();
        GregTechAPI.sBlockCasings4 = new BlockCasings4();
        GregTechAPI.sBlockCasings5 = new BlockCasings5();
        GregTechAPI.sBlockCasings6 = new BlockCasings6();
        GregTechAPI.sBlockCasings8 = new BlockCasings8();
        GregTechAPI.sBlockCasings9 = new BlockCasings9();
        GregTechAPI.sBlockCasings10 = new BlockCasings10();
        GregTechAPI.sBlockCasings11 = new BlockCasings11();
        GregTechAPI.sBlockCasings12 = new BlockCasings12();
        GregTechAPI.sBlockCasings13 = new BlockCasings13();
        GregTechAPI.sBlockCasingsNH = new BlockCasingsNH();
        GregTechAPI.sBlockGranites = new BlockGranites();
        GregTechAPI.sBlockLongDistancePipes = new BlockLongDistancePipe();
        GregTechAPI.sBlockConcretes = new BlockConcretes();
        GregTechAPI.sBlockStones = new BlockStones();
        GregTechAPI.sBlockOres1 = new BlockOres();
        GregTechAPI.sBlockFrames = new BlockFrameBox();
        GregTechAPI.sDroneRender = new BlockDrone();
        GregTechAPI.sBlockGlass1 = new BlockGlass1();
        GregTechAPI.sBlockTintedGlass = new BlockTintedIndustrialGlass();
        GregTechAPI.sLaserRender = new BlockLaser();
        GregTechAPI.sWormholeRender = new BlockWormholeRender();
        GregTechAPI.sBlackholeRender = new BlockBlackholeRenderer();
        GregTechAPI.nanoForgeRender = new BlockNanoForgeRenderer();

        // meta ID order, DO NOT CHANGE ORDER

        GregTechAPI.sBlockMetal1 = new BlockMetal(
            "gt.blockmetal1",
            new Materials[] { Materials.Adamantium, Materials.Aluminium, Materials.Americium, Materials.AnnealedCopper,
                Materials.Antimony, Materials.Arsenic, Materials.AstralSilver, Materials.BatteryAlloy,
                Materials.Beryllium, Materials.Bismuth, Materials.BismuthBronze, Materials.BlackBronze,
                Materials.BlackSteel, Materials.BlueAlloy, Materials.BlueSteel, Materials.Brass },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS1);

        GregTechAPI.sBlockMetal2 = new BlockMetal(
            "gt.blockmetal2",
            new Materials[] { Materials.Bronze, Materials.Caesium, Materials.Cerium, Materials.Chrome,
                Materials.ChromiumDioxide, Materials.Cobalt, Materials.CobaltBrass, Materials.Copper,
                Materials.Cupronickel, Materials.DamascusSteel, Materials.DarkIron, Materials.DeepIron, Materials.Desh,
                Materials.Duranium, Materials.Dysprosium, Materials.Electrum },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS2);

        GregTechAPI.sBlockMetal3 = new BlockMetal(
            "gt.blockmetal3",
            new Materials[] { Materials.ElectrumFlux, Materials.Enderium, Materials.Erbium, Materials.Europium,
                Materials.FierySteel, Materials.Gadolinium, Materials.Gallium, Materials.Holmium, Materials.HSLA,
                Materials.Indium, Materials.InfusedGold, Materials.Invar, Materials.Iridium, Materials.IronMagnetic,
                Materials.IronWood, Materials.Kanthal },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS3);

        GregTechAPI.sBlockMetal4 = new BlockMetal(
            "gt.blockmetal4",
            new Materials[] { Materials.Knightmetal, Materials.Lanthanum, Materials.Lead, Materials.Lutetium,
                Materials.Magnalium, Materials.Magnesium, Materials.Manganese, Materials.MeteoricIron,
                Materials.MeteoricSteel, Materials.Trinium, Materials.Mithril, Materials.Molybdenum, Materials.Naquadah,
                Materials.NaquadahAlloy, Materials.NaquadahEnriched, Materials.Naquadria },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS4);

        GregTechAPI.sBlockMetal5 = new BlockMetal(
            "gt.blockmetal5",
            new Materials[] { Materials.Neodymium, Materials.NeodymiumMagnetic, Materials.Neutronium,
                Materials.Nichrome, Materials.Nickel, Materials.Niobium, Materials.NiobiumNitride,
                Materials.NiobiumTitanium, Materials.Osmiridium, Materials.Osmium, Materials.Palladium,
                Materials.PigIron, Materials.Platinum, Materials.Plutonium, Materials.Plutonium241,
                Materials.Praseodymium },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS5);

        GregTechAPI.sBlockMetal6 = new BlockMetal(
            "gt.blockmetal6",
            new Materials[] { Materials.Promethium, Materials.RedAlloy, Materials.RedSteel, Materials.RoseGold,
                Materials.Rubidium, Materials.Samarium, Materials.Scandium, Materials.ShadowIron, Materials.ShadowSteel,
                Materials.Silicon, Materials.Silver, Materials.SolderingAlloy, Materials.StainlessSteel,
                Materials.Steel, Materials.SteelMagnetic, Materials.SterlingSilver },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS6);

        GregTechAPI.sBlockMetal7 = new BlockMetal(
            "gt.blockmetal7",
            new Materials[] { Materials.Sunnarium, Materials.Tantalum, Materials.Tellurium, Materials.Terbium,
                Materials.Thaumium, Materials.Thorium, Materials.Thulium, Materials.Tin, Materials.TinAlloy,
                Materials.Titanium, Materials.Tritanium, Materials.Tungsten, Materials.TungstenSteel, Materials.Ultimet,
                Materials.Uranium, Materials.Uranium235 },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS7);

        GregTechAPI.sBlockMetal8 = new BlockMetal(
            "gt.blockmetal8",
            new Materials[] { Materials.Vanadium, Materials.VanadiumGallium, Materials.WroughtIron, Materials.Ytterbium,
                Materials.Yttrium, Materials.YttriumBariumCuprate, Materials.Zinc, Materials.TungstenCarbide,
                Materials.VanadiumSteel, Materials.HSSG, Materials.HSSE, Materials.HSSS, Materials.Steeleaf,
                Materials.Ichorium, Materials.Firestone, Materials.Shadow },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS8);

        GregTechAPI.sBlockGem1 = new BlockMetal(
            "gt.blockgem1",
            new Materials[] { Materials.InfusedAir, Materials.Amber, Materials.Amethyst, Materials.InfusedWater,
                Materials.BlueTopaz, Materials.CertusQuartz, Materials.Dilithium, Materials.EnderEye,
                Materials.EnderPearl, Materials.FoolsRuby, Materials.Force, Materials.Forcicium, Materials.Forcillium,
                Materials.GreenSapphire, Materials.InfusedFire, Materials.Jasper },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS9);

        GregTechAPI.sBlockGem2 = new BlockMetal(
            "gt.blockgem2",
            new Materials[] { Materials.Lazurite, Materials.Lignite, Materials.Monazite, Materials.Niter,
                Materials.Olivine, Materials.Opal, Materials.InfusedOrder, Materials.InfusedEntropy,
                Materials.TricalciumPhosphate, Materials.Quartzite, Materials.GarnetRed, Materials.Ruby,
                Materials.Sapphire, Materials.Sodalite, Materials.Tanzanite, Materials.InfusedEarth },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS10);

        GregTechAPI.sBlockGem3 = new BlockMetal(
            "gt.blockgem3",
            new Materials[] { Materials.Topaz, Materials.Vinteum, Materials.GarnetYellow, Materials.NetherStar,
                Materials.Charcoal, Materials.Blaze },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS11);

        GregTechAPI.sBlockMetal9 = new BlockMetal(
            "gt.blockmetal9",
            new Materials[] { Materials.Cryolite, Materials.SiliconSG, MaterialsKevlar.NickelAluminide,
                MaterialsUEVplus.SpaceTime, MaterialsUEVplus.TranscendentMetal, Materials.Oriharukon,
                MaterialsUEVplus.WhiteDwarfMatter, MaterialsUEVplus.BlackDwarfMatter, MaterialsUEVplus.Universium,
                MaterialsUEVplus.Eternity, MaterialsUEVplus.MagMatter, MaterialsUEVplus.SixPhasedCopper,
                Materials.HellishMetal, MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter },
            OrePrefixes.block,
            gregtech.api.enums.Textures.BlockIcons.STORAGE_BLOCKS12);

        GregTechAPI.sBlockReinforced = new BlockReinforced("gt.blockreinforced");

        GTLog.out.println("GTMod: Register TileEntities.");

        BaseMetaTileEntity tBaseMetaTileEntity = GregTechAPI.constructBaseMetaTileEntity();

        GTLog.out.println("GTMod: Registering the BaseMetaTileEntity.");
        GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity");
        FMLInterModComms.sendMessage(
            AppliedEnergistics2.ID,
            "whitelist-spatial",
            tBaseMetaTileEntity.getClass()
                .getName());

        GTLog.out.println("GTMod: Registering the DroneRender.");
        GameRegistry.registerTileEntity(TileEntityDrone.class, "DroneRender");

        GTLog.out.println("GTMod: Registering the LaserRender.");
        GameRegistry.registerTileEntity(TileEntityLaser.class, "LaserRenderer");

        GTLog.out.println("GTMod: Registering the WormholeRender.");
        GameRegistry.registerTileEntity(TileEntityWormhole.class, "WormholeRender");

        GTLog.out.println("GTMod: Registering the BlackholeRender.");
        GameRegistry.registerTileEntity(TileEntityBlackhole.class, "BlackholeRender");

        GTLog.out.println("GTMod: Registering the NanoForgeRender.");
        GameRegistry.registerTileEntity(TileEntityNanoForgeRenderer.class, "NanoForgeRender");

        GTLog.out.println("GTMod: Registering the BaseMetaPipeEntity.");
        GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
        FMLInterModComms.sendMessage(AppliedEnergistics2.ID, "whitelist-spatial", BaseMetaPipeEntity.class.getName());

        GTLog.out.println("GTMod: Registering the Ore TileEntity.");
        GameRegistry.registerTileEntity(TileEntityOres.class, "GT_TileEntity_Ores");
        FMLInterModComms.sendMessage(AppliedEnergistics2.ID, "whitelist-spatial", TileEntityOres.class.getName());

        GTLog.out.println("GTMod: Registering Fluids.");
        Materials.ConstructionFoam.mFluid = GTUtility.getFluidForFilledItem(GTModHandler.getIC2Item("CFCell", 1L), true)
            .getFluid();
        Materials.UUMatter.mFluid = GTUtility.getFluidForFilledItem(GTModHandler.getIC2Item("uuMatterCell", 1L), true)
            .getFluid();

        GTFluidFactory.builder("Air")
            .withLocalizedName("Air")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Air)
            .registerContainers(ItemList.Cell_Air.get(1L), ItemList.Cell_Empty.get(1L), 2000);
        GTFluidFactory.builder("LiquidOxygen")
            .withLocalizedName("Liquid Oxygen")
            .withStateAndTemperature(GAS, 60)
            .buildAndRegister()
            .configureMaterials(Materials.LiquidOxygen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LiquidOxygen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("LiquidNitrogen")
            .withLocalizedName("Liquid Nitrogen")
            .withStateAndTemperature(GAS, 77)
            .buildAndRegister()
            .configureMaterials(Materials.LiquidNitrogen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LiquidNitrogen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("LiquidAir")
            .withLocalizedName("Liquid Air")
            .withStateAndTemperature(LIQUID, 77)
            .buildAndRegister()
            .configureMaterials(Materials.LiquidAir)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LiquidAir, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Oxygen")
            .withLocalizedName("Oxygen")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Oxygen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Hydrogen")
            .withLocalizedName("Hydrogen")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Hydrogen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Deuterium")
            .withLocalizedName("Deuterium")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Deuterium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Deuterium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Tritium")
            .withLocalizedName("Tritium")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Tritium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Tritium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Helium")
            .withLocalizedName("Helium")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Helium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Helium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Argon")
            .withLocalizedName("Argon")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Argon)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Argon, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Radon")
            .withLocalizedName("Radon")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Radon)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Radon, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("Fluorine")
            .withLocalizedName("Fluorine")
            .withStateAndTemperature(GAS, 53)
            .buildAndRegister()
            .configureMaterials(Materials.Fluorine)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Fluorine, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Titaniumtetrachloride")
            .withLocalizedName("Titaniumtetrachloride")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Titaniumtetrachloride)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Titaniumtetrachloride, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Helium-3")
            .withLocalizedName("Helium-3")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Helium_3)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Helium_3, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Methane")
            .withLocalizedName("Methane")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Methane)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Methane, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Nitrogen")
            .withLocalizedName("Nitrogen")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Nitrogen)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Nitrogen, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("NitrogenDioxide")
            .withLocalizedName("Nitrogen Dioxide")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NitrogenDioxide)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NitrogenDioxide, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Steam")
            .withLocalizedName("Steam")
            .withStateAndTemperature(GAS, 375)
            .buildAndRegister()
            .configureMaterials(Materials.Water)
            .registerBContainers(GTModHandler.getIC2Item("steamCell", 1), Materials.Empty.getCells(1));

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Empty.getCells(1))
            .itemOutputs(GTModHandler.getIC2Item("steamCell", 1))
            .fluidInputs(Materials.Steam.getGas(1_000))
            .duration(16 * TICKS)
            .eut(1)
            .addTo(fluidCannerRecipes);

        Materials.Ice.mGas = Materials.Water.mGas;
        Materials.Water.mGas.setTemperature(375)
            .setGaseous(true);

        ItemList.sOilExtraHeavy = GTFluidFactory.builder("liquid_extra_heavy_oil")
            .withLocalizedName("Very Heavy Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilExtraHeavy)
            .registerBContainers(Materials.OilExtraHeavy.getCells(1), Materials.Empty.getCells(1))
            .asFluid();
        ItemList.sEpichlorhydrin = GTFluidFactory.builder("liquid_epichlorhydrin")
            .withLocalizedName("Epichlorohydrin")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Epichlorohydrin)
            .registerBContainers(Materials.Epichlorohydrin.getCells(1), Materials.Empty.getCells(1))
            .asFluid();
        ItemList.sDrillingFluid = GTFluidFactory.of("liquid_drillingfluid", "Drilling Fluid", LIQUID, 295);
        ItemList.sToluene = GTFluidFactory.builder("liquid_toluene")
            .withLocalizedName("Toluene")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Toluene)
            .registerBContainers(Materials.Toluene.getCells(1), Materials.Empty.getCells(1))
            .asFluid();
        ItemList.sNitrationMixture = GTFluidFactory.builder("liquid_nitrationmixture")
            .withLocalizedName("Nitration Mixture")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NitrationMixture)
            .registerBContainers(Materials.NitrationMixture.getCells(1), Materials.Empty.getCells(1))
            .asFluid();

        GTFluidFactory.builder("liquid_heavy_oil")
            .withLocalizedName("Heavy Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilHeavy)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.OilHeavy, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_medium_oil")
            .withLocalizedName("Raw Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilMedium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.OilMedium, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_light_oil")
            .withLocalizedName("Light Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.OilLight)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.OilLight, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("gas_natural_gas")
            .withLocalizedName("Natural Gas")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NatruralGas)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NatruralGas, 1L),
                ItemList.Cell_Empty.get(1L));
        ItemList.sHydricSulfur = GTFluidFactory.builder("liquid_hydricsulfur")
            .withLocalizedName("Hydrogen Sulfide")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.HydricSulfide)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1L),
                ItemList.Cell_Empty.get(1L))
            .asFluid();
        GTFluidFactory.builder("gas_sulfuricgas")
            .withLocalizedName("Sulfuric Gas")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricGas)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricGas, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("gas_gas")
            .withLocalizedName("Refinery Gas")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Gas)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Gas, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_sulfuricnaphtha")
            .withLocalizedName("Sulfuric Naphtha")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricNaphtha)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricNaphtha, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_sufluriclight_fuel")
            .withLocalizedName("Sulfuric Light Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricLightFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricLightFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_sulfuricheavy_fuel")
            .withLocalizedName("Sulfuric Heavy Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricHeavyFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricHeavyFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_naphtha")
            .withLocalizedName("Naphtha")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Naphtha)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Naphtha, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_light_fuel")
            .withLocalizedName("Light Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.LightFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LightFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_heavy_fuel")
            .withLocalizedName("Heavy Fuel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.HeavyFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HeavyFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("liquid_lpg")
            .withLocalizedName("LPG")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.LPG)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.LPG, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("charcoal_byproducts")
            .withTextureName("molten.autogenerated")
            .withLocalizedName("Charcoal Byproducts")
            .withColorRGBA(Materials.CharcoalByproducts.mRGBa)
            .withStateAndTemperature(GAS, 775)
            .buildAndRegister()
            .configureMaterials(Materials.CharcoalByproducts)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.CharcoalByproducts, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("molten.bisphenol_a")
            .withTextureName("molten.autogenerated")
            .withLocalizedName("Molten Bisphenol A")
            .withColorRGBA(Materials.BisphenolA.mRGBa)
            .withStateAndTemperature(LIQUID, 432)
            .buildAndRegister()
            .configureMaterials(Materials.BisphenolA)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.BisphenolA, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("UUAmplifier")
            .withLocalizedName("UU Amplifier")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.UUAmplifier)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.UUAmplifier, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Chlorine")
            .withLocalizedName("Chlorine")
            .withStateAndTemperature(GAS, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Chlorine)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Chlorine, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Mercury")
            .withLocalizedName("Mercury")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Mercury)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Mercury, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("NitroFuel")
            .withLocalizedName("Cetane-Boosted Diesel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.NitroFuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NitroFuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("SodiumPersulfate")
            .withLocalizedName("Sodium Persulfate")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SodiumPersulfate)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SodiumPersulfate, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("Glyceryl")
            .withLocalizedName("Glyceryl Trinitrate")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Glyceryl)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Glyceryl, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("lubricant")
            .withLocalizedName("Lubricant")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Lubricant)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Lubricant, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("creosote")
            .withLocalizedName("Creosote Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Creosote)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Creosote, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("seedoil")
            .withLocalizedName("Seed Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SeedOil)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SeedOil, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("fishoil")
            .withLocalizedName("Fish Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.FishOil)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.FishOil, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("oil")
            .withLocalizedName("Oil")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Oil)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oil, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("fuel")
            .withLocalizedName("Diesel")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Fuel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Fuel, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("for.honey")
            .withLocalizedName("Honey")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Honey)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Honey, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("biomass")
            .withLocalizedName("Biomass")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Biomass)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Biomass, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("bioethanol")
            .withLocalizedName("Bio Ethanol")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Ethanol)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Ethanol, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("sulfuricacid")
            .withLocalizedName("Sulfuric Acid")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.SulfuricAcid)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricAcid, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("milk")
            .withLocalizedName("Milk")
            .withStateAndTemperature(LIQUID, 290)
            .buildAndRegister()
            .configureMaterials(Materials.Milk)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Milk, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("mcguffium")
            .withLocalizedName("Mc Guffium 239")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.McGuffium239)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.McGuffium239, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("refinedGlue")
            .withLocalizedName("Refined Glue")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Glue)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Glue, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("hotfryingoil")
            .withLocalizedName("Hot Frying Oil")
            .withStateAndTemperature(LIQUID, 400)
            .buildAndRegister()
            .configureMaterials(Materials.FryingOilHot)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.FryingOilHot, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("DimensionallyTranscendentResidue")
            .withLocalizedName("Dimensionally Transcendent Residue")
            .withStateAndTemperature(LIQUID, 2000000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.DimensionallyTranscendentResidue)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.DimensionallyTranscendentResidue, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTCC")
            .withLocalizedName("Excited Dimensionally Transcendent Crude Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.ExcitedDTCC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.ExcitedDTCC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTPC")
            .withLocalizedName("Excited Dimensionally Transcendent Prosaic Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.ExcitedDTPC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.ExcitedDTPC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTRC")
            .withLocalizedName("Excited Dimensionally Transcendent Resplendent Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.ExcitedDTRC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.ExcitedDTRC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTEC")
            .withLocalizedName("Excited Dimensionally Transcendent Exotic Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.ExcitedDTEC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.ExcitedDTEC, 1L),
                ItemList.Cell_Empty.get(1L));
        GTFluidFactory.builder("ExcitedDTSC")
            .withLocalizedName("Excited Dimensionally Transcendent Stellar Catalyst")
            .withStateAndTemperature(LIQUID, 500000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.ExcitedDTSC)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.ExcitedDTSC, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(MaterialsUEVplus.RawStarMatter.mName)
            .withLocalizedName(MaterialsUEVplus.RawStarMatter.mLocalizedName)
            .withStateAndTemperature(LIQUID, 10_000_000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.RawStarMatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.RawStarMatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(MaterialsUEVplus.Space.mName)
            .withLocalizedName(MaterialsUEVplus.Space.mLocalizedName)
            .withStateAndTemperature(MOLTEN, 0)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.Space)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.Space, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder(MaterialsUEVplus.Time.mName)
            .withLocalizedName(MaterialsUEVplus.Time.mLocalizedName)
            .withStateAndTemperature(MOLTEN, 0)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.Time)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.Time, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("PrimordialMatter")
            .withLocalizedName(MaterialsUEVplus.PrimordialMatter.mLocalizedName)
            .withStateAndTemperature(LIQUID, 2_000_000_000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.PrimordialMatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.PrimordialMatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("QuarkGluonPlasma")
            .withLocalizedName(MaterialsUEVplus.QuarkGluonPlasma.mLocalizedName)
            .withStateAndTemperature(LIQUID, 2_000_000_000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.QuarkGluonPlasma)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.QuarkGluonPlasma, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("PhononMedium")
            .withLocalizedName(MaterialsUEVplus.PhononMedium.mLocalizedName)
            .withStateAndTemperature(LIQUID, 500)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.PhononMedium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.PhononMedium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("PhononCrystalSolution")
            .withLocalizedName(MaterialsUEVplus.PhononCrystalSolution.mLocalizedName)
            .withStateAndTemperature(LIQUID, 500)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.PhononCrystalSolution)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.PhononCrystalSolution, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("antimatter")
            .withLocalizedName(MaterialsUEVplus.Antimatter.mLocalizedName)
            .withStateAndTemperature(LIQUID, 1000000)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.Antimatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.Antimatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("protomatter")
            .withLocalizedName(MaterialsUEVplus.Protomatter.mLocalizedName)
            .withStateAndTemperature(LIQUID, 1)
            .buildAndRegister()
            .configureMaterials(MaterialsUEVplus.Protomatter)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsUEVplus.Protomatter, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.infinity")
            .withLocalizedName("Infinity Plasma")
            .withStateAndTemperature(PLASMA, 10000)
            .buildAndRegister()
            .configureMaterials(Materials.Infinity)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Infinity, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.bedrockium")
            .withLocalizedName("Bedrockium Plasma")
            .withStateAndTemperature(PLASMA, 10000)
            .buildAndRegister()
            .configureMaterials(Materials.Bedrockium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Bedrockium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.cosmicneutronium")
            .withLocalizedName("Cosmic Neutronium Plasma")
            .withStateAndTemperature(PLASMA, 10000)
            .buildAndRegister()
            .configureMaterials(Materials.CosmicNeutronium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.CosmicNeutronium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("molten.cosmicneutronium")
            .withLocalizedName("Molten Cosmic Neutronium")
            .withStateAndTemperature(MOLTEN, 9900)
            .buildAndRegister()
            .configureMaterials(Materials.CosmicNeutronium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.CosmicNeutronium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("plasma.ichorium")
            .withLocalizedName("Ichorium Plasma")
            .withStateAndTemperature(PLASMA, 9000)
            .buildAndRegister()
            .configureMaterials(Materials.Ichorium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Ichorium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("molten.ichorium")
            .withLocalizedName("Molten Ichorium")
            .withStateAndTemperature(MOLTEN, 9000)
            .buildAndRegister()
            .configureMaterials(Materials.Ichorium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Ichorium, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("fieryblood")
            .withLocalizedName("Fiery Blood")
            .withStateAndTemperature(LIQUID, 6400)
            .buildAndRegister()
            .configureMaterials(Materials.FierySteel)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.FierySteel, 1L),
                ItemList.Cell_Empty.get(1L));

        GTFluidFactory.builder("holywater")
            .withLocalizedName("Holy Water")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.HolyWater)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HolyWater, 1L),
                ItemList.Cell_Empty.get(1L));
        if (ItemList.TF_Vial_FieryBlood.get(1L) != null) {
            FluidContainerRegistry.registerFluidContainer(
                new FluidContainerRegistry.FluidContainerData(
                    Materials.FierySteel.getFluid(250),
                    ItemList.TF_Vial_FieryBlood.get(1L),
                    ItemList.Bottle_Empty.get(1L)));
        }

        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.Milk.getFluid(1_000),
                GTOreDictUnificator.get(OrePrefixes.bucket, Materials.Milk, 1L),
                GTOreDictUnificator.get(OrePrefixes.bucket, Materials.Empty, 1L)));
        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.Milk.getFluid(250),
                ItemList.Bottle_Milk.get(1L),
                ItemList.Bottle_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.HolyWater.getFluid(250),
                ItemList.Bottle_Holy_Water.get(1L),
                ItemList.Bottle_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.McGuffium239.getFluid(250),
                ItemList.McGuffium_239.get(1L),
                ItemList.Bottle_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.Fuel.getFluid(100L),
                ItemList.Tool_Lighter_Invar_Full.get(1L),
                ItemList.Tool_Lighter_Invar_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                Materials.Fuel.getFluid(1_000),
                ItemList.Tool_Lighter_Platinum_Full.get(1L),
                ItemList.Tool_Lighter_Platinum_Empty.get(1L)));

        Dyes.dyeBlack.addFluidDye(GTFluidFactory.of("squidink", "Squid Ink", LIQUID, 295));
        Dyes.dyeBlue.addFluidDye(GTFluidFactory.of("indigo", "Indigo Dye", LIQUID, 295));
        for (byte i = 0; i < Dyes.VALUES.length; i = (byte) (i + 1)) {
            Dyes tDye = Dyes.VALUES[i];
            tDye.addFluidDye(
                GTFluidFactory.builder(
                    "dye.watermixed." + tDye.name()
                        .toLowerCase(Locale.ENGLISH))
                    .withTextureName("dyes")
                    .withLocalizedName("Water Mixed " + tDye.mName + " Dye")
                    .withColorRGBA(tDye.getRGBA())
                    .withStateAndTemperature(LIQUID, 295)
                    .buildAndRegister()
                    .asFluid());
            tDye.addFluidDye(
                GTFluidFactory.builder(
                    "dye.chemical." + tDye.name()
                        .toLowerCase(Locale.ENGLISH))
                    .withTextureName("dyes")
                    .withLocalizedName("Chemical " + tDye.mName + " Dye")
                    .withColorRGBA(tDye.getRGBA())
                    .withStateAndTemperature(LIQUID, 295)
                    .buildAndRegister()
                    .registerContainers(ItemList.SPRAY_CAN_DYES[i].get(1L), ItemList.Spray_Empty.get(1L), 16 * INGOTS)
                    .asFluid());
        }
        GTFluidFactory.builder("ice")
            .withLocalizedName("Crushed Ice")
            .withStateAndTemperature(SLURRY, 270)
            .buildAndRegister()
            .configureMaterials(Materials.Ice)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Ice, 1L),
                ItemList.Cell_Empty.get(1L));
        Materials.Water.mSolid = Materials.Ice.mSolid;

        GTFluidFactory.builder("molten.glass")
            .withLocalizedName("Molten Glass")
            .withStateAndTemperature(MOLTEN, 1500)
            .buildAndRegister()
            .configureMaterials(Materials.Glass)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Glass, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("molten.redstone")
            .withLocalizedName("Molten Redstone")
            .withStateAndTemperature(MOLTEN, 500)
            .buildAndRegister()
            .configureMaterials(Materials.Redstone)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Redstone, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("molten.blaze")
            .withLocalizedName("Molten Blaze")
            .withStateAndTemperature(MOLTEN, 6400)
            .buildAndRegister()
            .configureMaterials(Materials.Blaze)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Blaze, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);
        GTFluidFactory.builder("wet.concrete")
            .withLocalizedName("Wet Concrete")
            .withStateAndTemperature(MOLTEN, 300)
            .buildAndRegister()
            .configureMaterials(Materials.Concrete)
            .registerContainers(
                GTOreDictUnificator.get(OrePrefixes.cellMolten, Materials.Concrete, 1L),
                ItemList.Cell_Empty.get(1L),
                1 * INGOTS);

        for (Materials tMaterial : Materials.values()) {
            if ((tMaterial.mStandardMoltenFluid == null) && (tMaterial.contains(SubTag.SMELTING_TO_FLUID))
                && (!tMaterial.contains(SubTag.NO_SMELTING))) {
                GTMod.proxy.addAutogeneratedMoltenFluid(tMaterial);
                if ((tMaterial.mSmeltInto != tMaterial) && (tMaterial.mSmeltInto.mStandardMoltenFluid == null)) {
                    GTMod.proxy.addAutogeneratedMoltenFluid(tMaterial.mSmeltInto);
                }
            }
            if (tMaterial.mElement != null || (tMaterial.mHasPlasma && !tMaterial.mIconSet.is_custom)) {
                GTMod.proxy.addAutogeneratedPlasmaFluid(tMaterial);
            }
            if (tMaterial.hasCorrespondingFluid()) {
                GTMod.proxy.addAutoGeneratedCorrespondingFluid(tMaterial);
            }
            if (tMaterial.hasCorrespondingGas()) {
                GTMod.proxy.addAutoGeneratedCorrespondingGas(tMaterial);
            }
            if (tMaterial.canBeCracked()) {
                GTMod.proxy.addAutoGeneratedHydroCrackedFluids(tMaterial);
                GTMod.proxy.addAutoGeneratedSteamCrackedFluids(tMaterial);
            }
        }

        GTFluidFactory.builder("potion.awkward")
            .withLocalizedName("Awkward Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.thick")
            .withLocalizedName("Thick Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 32), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.mundane")
            .withLocalizedName("Mundane Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 64), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.damage")
            .withLocalizedName("Harming Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8204), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.damage.strong")
            .withLocalizedName("Strong Harming Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8236), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.damage.splash")
            .withLocalizedName("Splash Harming Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16396), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.damage.strong.splash")
            .withLocalizedName("Strong Splash Harming Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16428), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.health")
            .withLocalizedName("Healing Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8197), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.health.strong")
            .withLocalizedName("Strong Healing Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8229), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.health.splash")
            .withLocalizedName("Splash Healing Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16389), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.health.strong.splash")
            .withLocalizedName("Strong Splash Healing Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16421), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.speed")
            .withLocalizedName("Swiftness Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8194), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.speed.strong")
            .withLocalizedName("Strong Swiftness Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8226), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.speed.long")
            .withLocalizedName("Stretched Swiftness Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8258), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.speed.splash")
            .withLocalizedName("Splash Swiftness Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16386), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.speed.strong.splash")
            .withLocalizedName("Strong Splash Swiftness Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16418), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.speed.long.splash")
            .withLocalizedName("Stretched Splash Swiftness Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16450), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.strength")
            .withLocalizedName("Strength Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8201), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.strength.strong")
            .withLocalizedName("Strong Strength Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8233), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.strength.long")
            .withLocalizedName("Stretched Strength Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8265), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.strength.splash")
            .withLocalizedName("Splash Strength Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16393), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.strength.strong.splash")
            .withLocalizedName("Strong Splash Strength Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16425), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.strength.long.splash")
            .withLocalizedName("Stretched Splash Strength Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16457), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.regen")
            .withLocalizedName("Regenerating Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8193), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.regen.strong")
            .withLocalizedName("Strong Regenerating Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8225), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.regen.long")
            .withLocalizedName("Stretched Regenerating Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8257), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.regen.splash")
            .withLocalizedName("Splash Regenerating Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16385), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.regen.strong.splash")
            .withLocalizedName("Strong Splash Regenerating Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16417), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.regen.long.splash")
            .withLocalizedName("Stretched Splash Regenerating Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16449), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.poison")
            .withLocalizedName("Poisonous Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8196), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.poison.strong")
            .withLocalizedName("Strong Poisonous Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8228), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.poison.long")
            .withLocalizedName("Stretched Poisonous Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8260), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.poison.splash")
            .withLocalizedName("Splash Poisonous Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16388), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.poison.strong.splash")
            .withLocalizedName("Strong Splash Poisonous Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16420), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.poison.long.splash")
            .withLocalizedName("Stretched Splash Poisonous Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16452), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.fireresistance")
            .withLocalizedName("Fire Resistant Brew")
            .withStateAndTemperature(LIQUID, 375)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8195), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.fireresistance.long")
            .withLocalizedName("Stretched Fire Resistant Brew")
            .withStateAndTemperature(LIQUID, 375)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8259), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.fireresistance.splash")
            .withLocalizedName("Splash Fire Resistant Brew")
            .withStateAndTemperature(LIQUID, 375)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16387), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.fireresistance.long.splash")
            .withLocalizedName("Stretched Splash Fire Resistant Brew")
            .withStateAndTemperature(LIQUID, 375)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16451), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.nightvision")
            .withLocalizedName("Night Vision Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8198), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.nightvision.long")
            .withLocalizedName("Stretched Night Vision Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8262), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.nightvision.splash")
            .withLocalizedName("Splash Night Vision Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16390), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.nightvision.long.splash")
            .withLocalizedName("Stretched Splash Night Vision Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16454), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.weakness")
            .withLocalizedName("Weakening Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8200), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.weakness.long")
            .withLocalizedName("Stretched Weakening Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8264), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.weakness.splash")
            .withLocalizedName("Splash Weakening Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16392), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.weakness.long.splash")
            .withLocalizedName("Stretched Splash Weakening Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16456), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.slowness")
            .withLocalizedName("Lame Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8202), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.slowness.long")
            .withLocalizedName("Stretched Lame Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8266), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.slowness.splash")
            .withLocalizedName("Splash Lame Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16394), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.slowness.long.splash")
            .withLocalizedName("Stretched Splash Lame Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16458), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.waterbreathing")
            .withLocalizedName("Fishy Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8205), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.waterbreathing.long")
            .withLocalizedName("Stretched Fishy Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8269), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.waterbreathing.splash")
            .withLocalizedName("Splash Fishy Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16397), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.waterbreathing.long.splash")
            .withLocalizedName("Stretched Splash Fishy Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16461), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.invisibility")
            .withLocalizedName("Invisible Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8206), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.invisibility.long")
            .withLocalizedName("Stretched Invisible Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 8270), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.invisibility.splash")
            .withLocalizedName("Splash Invisible Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16398), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.invisibility.long.splash")
            .withLocalizedName("Stretched Splash Invisible Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(new ItemStack(Items.potionitem, 1, 16462), ItemList.Bottle_Empty.get(1L));

        GTFluidFactory.builder("potion.purpledrink")
            .withLocalizedName("Purple Drink")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Purple_Drink.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.grapejuice")
            .withLocalizedName("Grape Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Grape_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.wine")
            .withLocalizedName("Wine")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Wine.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.vinegar")
            .withLocalizedName("Vinegar")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .configureMaterials(Materials.Vinegar)
            .registerPContainers(ItemList.Bottle_Vinegar.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.potatojuice")
            .withLocalizedName("Potato Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Potato_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.vodka")
            .withLocalizedName("Vodka")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Vodka.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.leninade")
            .withLocalizedName("Leninade")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Leninade.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.mineralwater")
            .withLocalizedName("Mineral Water")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Mineral_Water.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.saltywater")
            .withLocalizedName("Salty Water")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Salty_Water.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.reedwater")
            .withLocalizedName("Reed Water")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Reed_Water.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.rum")
            .withLocalizedName("Rum")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Rum.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.piratebrew")
            .withLocalizedName("Pirate Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Pirate_Brew.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.hopsjuice")
            .withLocalizedName("Hops Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Hops_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.darkbeer")
            .withLocalizedName("Dark Beer")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Dark_Beer.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.dragonblood")
            .withLocalizedName("Dragon Blood")
            .withStateAndTemperature(LIQUID, 375)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Dragon_Blood.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.wheatyjuice")
            .withLocalizedName("Wheaty Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Wheaty_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.scotch")
            .withLocalizedName("Scotch")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Scotch.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.glenmckenner")
            .withLocalizedName("Glen McKenner")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Glen_McKenner.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.wheatyhopsjuice")
            .withLocalizedName("Wheaty Hops Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Wheaty_Hops_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.beer")
            .withLocalizedName("Beer")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Beer.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.chillysauce")
            .withLocalizedName("Chilly Sauce")
            .withStateAndTemperature(LIQUID, 375)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Chilly_Sauce.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.hotsauce")
            .withLocalizedName("Hot Sauce")
            .withStateAndTemperature(LIQUID, 380)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Hot_Sauce.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.diabolosauce")
            .withLocalizedName("Diabolo Sauce")
            .withStateAndTemperature(LIQUID, 385)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Diabolo_Sauce.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.diablosauce")
            .withLocalizedName("Diablo Sauce")
            .withStateAndTemperature(LIQUID, 390)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Diablo_Sauce.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.diablosauce.strong")
            .withLocalizedName("Old Man Snitches glitched Diablo Sauce")
            .withStateAndTemperature(LIQUID, 999)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Snitches_Glitch_Sauce.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.applejuice")
            .withLocalizedName("Apple Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Apple_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.cider")
            .withLocalizedName("Cider")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Cider.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.goldenapplejuice")
            .withLocalizedName("Golden Apple Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Golden_Apple_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.goldencider")
            .withLocalizedName("Golden Cider")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Golden_Cider.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.idunsapplejuice")
            .withLocalizedName("Idun's Apple Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Iduns_Apple_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.notchesbrew")
            .withLocalizedName("Notches Brew")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Notches_Brew.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.lemonjuice")
            .withLocalizedName("Lemon Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Lemon_Juice.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.limoncello")
            .withLocalizedName("Limoncello")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Limoncello.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.lemonade")
            .withLocalizedName("Lemonade")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Lemonade.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.alcopops")
            .withLocalizedName("Alcopops")
            .withStateAndTemperature(LIQUID, 275)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Alcopops.get(1L), ItemList.Bottle_Empty.get(1L));
        GTFluidFactory.builder("potion.cavejohnsonsgrenadejuice")
            .withLocalizedName("Cave Johnsons Grenade Juice")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.Bottle_Cave_Johnsons_Grenade_Juice.get(1L), ItemList.Bottle_Empty.get(1L));

        GTFluidFactory.builder("potion.coffee")
            .withLocalizedName("Coffee")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Coffee.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.sweetcoffee")
            .withLocalizedName("Sweet Coffee")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Sweet_Coffee.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.Latte")
            .withLocalizedName("Latte")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Latte.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.sweetlatte")
            .withLocalizedName("Sweet Latte")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Sweet_Latte.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.sweetjesuslatte")
            .withLocalizedName("Sweet Jesus Latte")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Sweet_Jesus_Latte.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.darkchocolatemilk")
            .withLocalizedName("Dark Chocolate Milk")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Dark_Chocolate_Milk.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.chocolatemilk")
            .withLocalizedName("Chocolate Milk")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Chocolate_Milk.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.tea")
            .withLocalizedName("Tea")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Tea.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.sweettea")
            .withLocalizedName("Sweet Tea")
            .withStateAndTemperature(LIQUID, 295)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Sweet_Tea.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("potion.icetea")
            .withLocalizedName("Ice Tea")
            .withStateAndTemperature(LIQUID, 255)
            .buildAndRegister()
            .registerPContainers(ItemList.ThermosCan_Ice_Tea.get(1L), ItemList.ThermosCan_Empty.get(1L));
        GTFluidFactory.builder("liquid_sodium")
            .withLocalizedName("Liquid Sodium")
            .withStateAndTemperature(LIQUID, 495)
            .buildAndRegister()
            .configureMaterials(Materials.Sodium)
            .registerBContainers(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Sodium, 1L),
                ItemList.Cell_Empty.get(1L));

        FluidContainerRegistry.registerFluidContainer(
            new FluidContainerRegistry.FluidContainerData(
                FluidRegistry.getFluidStack("potion.poison.strong", 750),
                ItemList.IC2_Spray_WeedEx.get(1L),
                ItemList.Spray_Empty.get(1L)));

        if (!GTValues.D1) {
            if (Mods.NotEnoughItems.isModLoaded()) {
                GTLog.out.println("GTMod: Hiding certain Items from NEI.");
                API.hideItem(ItemList.Display_Fluid.getWildcard(1L));
            }
        }

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.gravel, 1, WILDCARD))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1L), new ItemStack(Items.flint, 1))
            .outputChances(10000, 1000)
            .duration(20 * SECONDS)
            .eut(2)
            .addTo(maceratorRecipes);

        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.FierySteel,
            GTModHandler.getModItem(TwilightForest.ID, "item.fieryIngot", 1L, 0));
        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.Knightmetal,
            GTModHandler.getModItem(TwilightForest.ID, "item.knightMetal", 1L, 0));
        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.Steeleaf,
            GTModHandler.getModItem(TwilightForest.ID, "item.steeleafIngot", 1L, 0));
        GTOreDictUnificator.set(
            OrePrefixes.ingot,
            Materials.IronWood,
            GTModHandler.getModItem(TwilightForest.ID, "item.ironwoodIngot", 1L, 0));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedAir, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 0));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedFire, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 1));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedWater, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 2));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedEarth, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 3));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedOrder, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 4));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.InfusedEntropy, GTModHandler.getModItem(Thaumcraft.ID, "ItemShard", 1L, 5));
        GTOreDictUnificator
            .set(OrePrefixes.nugget, Materials.Mercury, GTModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 5));
        GTOreDictUnificator
            .set(OrePrefixes.nugget, Materials.Thaumium, GTModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 6));
        GTOreDictUnificator
            .set(OrePrefixes.ingot, Materials.Thaumium, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 2));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.Mercury, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 3));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.Amber, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 6));
        GTOreDictUnificator
            .set(OrePrefixes.gem, Materials.Firestone, GTModHandler.getModItem(Railcraft.ID, "firestone.raw", 1L));

        GTOreDictUnificator
            .set(OrePrefixes.nugget, Materials.Void, GTModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 7));
        GTOreDictUnificator
            .set(OrePrefixes.ingot, Materials.Void, GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 1L, 16));

        // Special secondary oredict for Sodium Hydroxide
        GTOreDictUnificator.registerOre("dustSodiumHydroxide", Materials.SodiumHydroxide.getDust(1));
        GTOreDictUnificator.registerOre("dustSmallSodiumHydroxide", Materials.SodiumHydroxide.getDustSmall(1));
        GTOreDictUnificator.registerOre("dustTinySodiumHydroxide", Materials.SodiumHydroxide.getDustTiny(1));

        GTOreDictUnificator.set(
            OrePrefixes.dust,
            Materials.Cocoa,
            GTModHandler.getModItem(PamsHarvestCraft.ID, "cocoapowderItem", 1L, 0));
        GTOreDictUnificator.set(OrePrefixes.dust, Materials.Coffee, ItemList.IC2_CoffeePowder.get(1L));

        GregTechAPI.registerMachineBlock(GTUtility.getBlockFromStack(GTModHandler.getIC2Item("reinforcedGlass", 0)), 0);

        GregTechAPI.sSolenoidCoilCasings = new BlockCyclotronCoils();
        ItemList.TierdDrone0
            .set(new ItemTierDrone("tierdDrone0", "Drone (Level 1)", "Quadcopter Stable Small Aircraft", 1));
        ItemList.TierdDrone1
            .set(new ItemTierDrone("tierdDrone1", "Drone (Level 2)", "Dual Turbo High-Ejection Medium Aircraft", 2));
        ItemList.TierdDrone2
            .set(new ItemTierDrone("tierdDrone2", "Drone (Level 3)", "Single Engine Anti-Gravity Large Aircraft", 3));

    }
}
