package gregtech.loaders.postload.recipes;

import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.GTPlusPlus;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.recipe.RecipeMaps.chemicalReactorRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.HALF_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.STACKS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeConstants.UniversalChemical;
import static gtPlusPlus.core.material.MaterialMisc.SODIUM_NITRATE;
import static net.minecraftforge.fluids.FluidRegistry.getFluidStack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import bartworks.system.material.WerkstoffLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsKevlar;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.items.CombType;
import gregtech.loaders.misc.GTBees;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.material.MaterialsElements;

public class ChemicalRecipes implements Runnable {

    @Override
    public void run() {
        singleBlockOnly();
        multiblockOnly();
        registerBoth();

        polymerizationRecipes();
    }

    public void registerBoth() {
        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.paper, 1), new ItemStack(Items.string, 1))
            .itemOutputs(GTModHandler.getIC2Item("dynamite", 1))
            .fluidInputs(Materials.Glyceryl.getFluid(500))
            .duration(8 * SECONDS)
            .eut(4)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 4),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Indium, 1))
            .fluidInputs(new FluidStack(ItemList.sIndiumConcentrate, 8_000))
            .fluidOutputs(new FluidStack(ItemList.sLeadZincSolution, 8_000))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 36),
                GTUtility.getIntegratedCircuit(9))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Indium, 1))
            .fluidInputs(new FluidStack(ItemList.sIndiumConcentrate, 72_000))
            .fluidOutputs(new FluidStack(ItemList.sLeadZincSolution, 72_000))
            .duration(22 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // Platinum Group Sludge chain

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Pentlandite, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.PlatinumGroupSludge, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(new FluidStack(ItemList.sNickelSulfate, 2_000))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Chalcopyrite, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.PlatinumGroupSludge, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(new FluidStack(ItemList.sBlueVitriol, 2_000))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        // Fe + 3HCl = FeCl3 + 3H

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 1), ItemList.Cell_Empty.get(3))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 3))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(3_000))
            .fluidOutputs(Materials.IronIIIChloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedGold, 8),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 8))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Thaumium, 16))
            .fluidInputs(GTModHandler.getIC2Coolant(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(getModItem(GalaxySpace.ID, "item.UnknowCrystal", 4), Materials.Osmiridium.getDust(2))
            .itemOutputs(ItemList.Circuit_Chip_Stemcell.get(64))
            .fluidInputs(Materials.GrowthMediumSterilized.getFluid(1_000))
            .fluidOutputs(getFluidStack("bacterialsludge", 1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Chip_Stemcell.get(32),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CosmicNeutronium, 4))
            .itemOutputs(ItemList.Circuit_Chip_Biocell.get(32))
            .fluidInputs(Materials.BioMediumSterilized.getFluid(2_000))
            .fluidOutputs(getFluidStack("mutagen", 2_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.sugar), GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Plastic, 1))
            .itemOutputs(ItemList.GelledToluene.get(2))
            .fluidInputs(new FluidStack(ItemList.sToluene, 133))
            .duration(7 * SECONDS)
            .eut(192)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Items.sugar, 9), GTOreDictUnificator.get(OrePrefixes.dust, Materials.Plastic, 1))
            .itemOutputs(ItemList.GelledToluene.get(18))
            .fluidInputs(new FluidStack(ItemList.sToluene, 1_197))
            .duration(1 * MINUTES + 3 * SECONDS)
            .eut(192)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.GelledToluene.get(4), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(new ItemStack(Blocks.tnt, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(250))
            .duration(10 * SECONDS)
            .eut(24)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.GelledToluene.get(4), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTModHandler.getIC2Item("industrialTnt", 1))
            .fluidInputs(new FluidStack(ItemList.sNitrationMixture, 200))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(150))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1),
                Materials.Empty.getCells(1))
            .fluidInputs(Materials.NatruralGas.getGas(16_000))
            .fluidOutputs(Materials.Gas.getGas(16_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.NatruralGas, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Gas, 2))
            .fluidInputs(Materials.Hydrogen.getGas(250))
            .fluidOutputs(Materials.HydricSulfide.getGas(125))
            .duration(1 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1),
                Materials.Empty.getCells(1))
            .fluidInputs(Materials.SulfuricGas.getGas(16_000))
            .fluidOutputs(Materials.Gas.getGas(16_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricGas, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Gas, 2))
            .fluidInputs(Materials.Hydrogen.getGas(250))
            .fluidOutputs(Materials.HydricSulfide.getGas(125))
            .duration(1 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1),
                Materials.Empty.getCells(1))
            .fluidInputs(Materials.SulfuricNaphtha.getFluid(12_000))
            .fluidOutputs(Materials.Naphtha.getFluid(12_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricNaphtha, 3),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Naphtha, 3))
            .fluidInputs(Materials.Hydrogen.getGas(500))
            .fluidOutputs(Materials.HydricSulfide.getGas(250))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1),
                Materials.Empty.getCells(1))
            .fluidInputs(Materials.SulfuricLightFuel.getFluid(12_000))
            .fluidOutputs(Materials.LightFuel.getFluid(12_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricLightFuel, 3),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.LightFuel, 3))
            .fluidInputs(Materials.Hydrogen.getGas(500))
            .fluidOutputs(Materials.HydricSulfide.getGas(250))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.HydricSulfide, 1),
                Materials.Empty.getCells(1))
            .fluidInputs(Materials.SulfuricHeavyFuel.getFluid(8_000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(8_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricHeavyFuel, 1),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.HeavyFuel, 1))
            .fluidInputs(Materials.Hydrogen.getGas(250))
            .fluidOutputs(Materials.HydricSulfide.getGas(125))
            .duration(1 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Potassium, 1))
            .fluidInputs(Materials.Naphtha.getFluid(576))
            .fluidOutputs(Materials.Polycaprolactam.getMolten(9 * INGOTS))
            .duration(32 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 9),
                GTUtility.getIntegratedCircuit(9))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Potassium, 1))
            .fluidInputs(Materials.Naphtha.getFluid(5_184))
            .fluidOutputs(Materials.Polycaprolactam.getMolten(1 * STACKS + 17 * INGOTS))
            .duration(4 * MINUTES + 48 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        for (Fluid tFluid : new Fluid[] { FluidRegistry.WATER, GTModHandler.getDistilledWater(1L)
            .getFluid() }) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.TricalciumPhosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Phosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 3))
                .itemOutputs(ItemList.IC2_Fertilizer.get(1))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(1))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.TricalciumPhosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(4))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Phosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 3))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.TricalciumPhosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(4))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Phosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 3))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glauconite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glauconite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.TricalciumPhosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(4))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glauconite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Phosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glauconite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 3))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glauconite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GlauconiteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GlauconiteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.TricalciumPhosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(4))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GlauconiteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Phosphate, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(3))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GlauconiteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 3))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GlauconiteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 1))
                .itemOutputs(ItemList.IC2_Fertilizer.get(2))
                .fluidInputs(new FluidStack(tFluid, 1_000))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(UniversalChemical);
        }

        // 3quartz dust + Na + H2O = 3quartz gem (Na loss

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 3))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 3))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Quartzite, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.Quartzite, 3))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 3))
            .fluidInputs(GTModHandler.getDistilledWater(1_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 3))
            .fluidInputs(GTModHandler.getDistilledWater(1_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Quartzite, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.Quartzite, 3))
            .fluidInputs(GTModHandler.getDistilledWater(1_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // 3UO2 + 4Al = 3U + 2Al2O3

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Uraninite, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Uranium, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 10))
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // UO2 + 2Mg = U + 2MgO

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Uraninite, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Uranium, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // Ca + C + 3O = CaCO3

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 5))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C + 4H = CH4

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(1), GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Hydrogen.getGas(4_000))
            .fluidOutputs(Materials.Methane.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // TiO2 + 2C + 4Cl = TiCl4 + 2CO

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Rutile, 3),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Carbon, 2))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.CarbonMonoxide, 2))
            .fluidInputs(Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.Titaniumtetrachloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Rutile, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 2))
            .fluidInputs(Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.Titaniumtetrachloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Rutile, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 2))
            .fluidInputs(Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(2_000), Materials.Titaniumtetrachloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // 4Na + 2MgCl2 = 2Mg + 4NaCl

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 4),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesiumchloride, 6))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 8))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // rubber

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.RawRubber, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidOutputs(Materials.Rubber.getMolten(9 * INGOTS))
            .duration(30 * SECONDS)
            .eut(16)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.RawRubber, 1),
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Sulfur, 1))
            .fluidOutputs(Materials.Rubber.getMolten(1 * INGOTS))
            .duration(5 * SECONDS)
            .eut(16)
            .addTo(UniversalChemical);

        // vanilla recipe

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Gold, 8),
                new ItemStack(Items.melon, 1, 32767))
            .itemOutputs(new ItemStack(Items.speckled_melon, 1, 0))
            .duration(50)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Gold, 8),
                new ItemStack(Items.carrot, 1, 32767))
            .itemOutputs(new ItemStack(Items.golden_carrot, 1, 0))
            .duration(50)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 8),
                new ItemStack(Items.apple, 1, 32767))
            .itemOutputs(new ItemStack(Items.golden_apple, 1, 0))
            .duration(50)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.block, Materials.Gold, 8),
                new ItemStack(Items.apple, 1, 32767))
            .itemOutputs(new ItemStack(Items.golden_apple, 1, 1))
            .duration(50)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Blaze, 1),
                GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderPearl, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Blaze, 1),
                new ItemStack(Items.slime_ball, 1, 32767))
            .itemOutputs(new ItemStack(Items.magma_cream, 1, 0))
            .duration(50)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // 1/9U +Air ==Pu== 0.1Rn

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Plutonium, 8),
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Uranium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Plutonium, 8))
            .fluidInputs(Materials.Air.getGas(1_000))
            .fluidOutputs(Materials.Radon.getGas(100))
            .duration(10 * MINUTES)
            .eut(8)
            .addTo(UniversalChemical);

        // Silicon Line
        // SiO2 + 2Mg = 2MgO + Si

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(5 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(5 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Quartzite, 6),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(5 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(5 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Jasper, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(5 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Opal, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 2))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 4))
            .duration(5 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // 3SiF4 + 4Al = 3Si + 4AlF3

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 4),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.AluminiumFluoride, 16))
            .fluidInputs(Materials.SiliconTetrafluoride.getGas(3_000))
            .duration(30 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // SiO2 + 4HF = SiF4 + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 3),
                GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.HydrofluoricAcid.getFluid(4_000))
            .fluidOutputs(Materials.SiliconTetrafluoride.getGas(1_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.NetherQuartz, 3),
                GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.HydrofluoricAcid.getFluid(4_000))
            .fluidOutputs(Materials.SiliconTetrafluoride.getGas(1_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 3),
                GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.HydrofluoricAcid.getFluid(4_000))
            .fluidOutputs(Materials.SiliconTetrafluoride.getGas(1_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Quartzite, 6),
                GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.HydrofluoricAcid.getFluid(4_000))
            .fluidOutputs(Materials.SiliconTetrafluoride.getGas(1_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // 4Na + SiCl4 = 4NaCl + Si

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 4),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 8))
            .fluidInputs(Materials.SiliconTetrachloride.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // HSiCl3 + 2H = 3HCl + Si

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1), ItemList.Cell_Empty.get(2))
            .fluidInputs(Materials.Trichlorosilane.getFluid(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(3_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Trichlorosilane, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1), ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Hydrogen.getGas(2_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(3_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // 4HSiCl3 = 3SiCl4 + SiH4

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Cell_Empty.get(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Silane, 1))
            .fluidInputs(Materials.Trichlorosilane.getFluid(4_000))
            .fluidOutputs(Materials.SiliconTetrachloride.getFluid(3_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // SiH4 = Si + 4H

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1))
            .fluidInputs(Materials.Silane.getGas(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // Ca + 2H = CaH2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calciumhydride, 3))
            .fluidInputs(Materials.Hydrogen.getGas(2_000))
            .duration(20 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // Si + 4Cl = SiCl4

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.SiliconTetrachloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // 2Na + S = Na2S

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Sodium.getDust(2), Materials.Sulfur.getDust(1))
            .itemOutputs(Materials.SodiumSulfide.getDust(3))
            .duration(60)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // H2S + H2O + (O2) = 0.5H2SO4(Diluted) ( S loss

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(750))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydricSulfide.getGas(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(750))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        // Ni + 4CO = Ni(CO)4

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.CarbonMonoxide, 4))
            .itemOutputs(ItemList.Cell_Empty.get(4))
            .fluidOutputs(MaterialsKevlar.NickelTetracarbonyl.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 1),
                GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.CarbonMonoxide.getGas(4_000))
            .fluidOutputs(MaterialsKevlar.NickelTetracarbonyl.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 1), ItemList.Cell_Empty.get(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.NickelTetracarbonyl, 1))
            .fluidInputs(Materials.CarbonMonoxide.getGas(4_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(chemicalReactorRecipes);

        // C2H4O + H2O = C2H6O2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.EthyleneOxide, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(MaterialsKevlar.Ethyleneglycol.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // C2H4 + O = C2H4O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Ethylene, 2),
                GTUtility.getIntegratedCircuit(4))
            .itemOutputs(ItemList.Cell_Empty.get(2))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(MaterialsKevlar.Acetaldehyde.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1),
                GTUtility.getIntegratedCircuit(5))
            .itemOutputs(ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Ethylene.getGas(2_000))
            .fluidOutputs(MaterialsKevlar.Acetaldehyde.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // NiAl3 + 2NaOH + 2H2O = NiAl + 2NaAlO2 + 6H

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.ingot, MaterialsKevlar.NickelAluminide, 4),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumHydroxide, 6))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.RaneyNickelActivated, 2),
                Materials.SodiumAluminate.getDust(8))
            .fluidInputs(Materials.Water.getFluid(2_000))
            .fluidOutputs(Materials.Hydrogen.getGas(6_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(UniversalChemical);

        // Cu + O = CuO

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CupricOxide, 2),
                ItemList.Cell_Empty.get(1))
            .duration(5 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.CupricOxide, 2))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(5 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // 2Bi + 3O = Bi2O3

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Bismuth, 4),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 6))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.BismuthIIIOxide, 10),
                ItemList.Cell_Empty.get(6))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Bismuth, 4),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.BismuthIIIOxide, 10))
            .fluidInputs(Materials.Oxygen.getGas(6_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        // C4H6O2 + CNH5 = C5H9NO + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.Methylamine, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.GammaButyrolactone, 1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.NMethylIIPyrrolidone, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 8),
                GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.Chlorine.getGas(16_000))
            .fluidOutputs(MaterialsKevlar.SulfurDichloride.getFluid(8_000))
            .duration(40 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // SCl2 + SO3 = SO2 + SOCl2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfurTrioxide, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.SulfurDichloride, 1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.ThionylChloride, 1),
                ItemList.Cell_Empty.get(1))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // C8H10 + 6O =CoC22H14O4= C8H6O4 + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.IVDimethylbenzene, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.CobaltIINaphthenate, 41))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.TerephthalicAcid, 1))
            .fluidInputs(Materials.Oxygen.getGas(6_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // 2CH4 + C6H6 = C8H10 + 4H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(2), GTUtility.getIntegratedCircuit(13))
            .itemOutputs(MaterialsKevlar.IIIDimethylbenzene.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), GTUtility.getIntegratedCircuit(14))
            .itemOutputs(MaterialsKevlar.IIIDimethylbenzene.getCells(1))
            .fluidInputs(Materials.Methane.getGas(2_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // 2CH4 + C6H6 = C8H10 + 4H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(2), GTUtility.getIntegratedCircuit(15))
            .itemOutputs(MaterialsKevlar.IVDimethylbenzene.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), GTUtility.getIntegratedCircuit(16))
            .itemOutputs(MaterialsKevlar.IVDimethylbenzene.getCells(1))
            .fluidInputs(Materials.Methane.getGas(2_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsKevlar.CobaltIIHydroxide.getDust(5), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(MaterialsKevlar.CobaltIINaphthenate.getDust(41))
            .fluidInputs(MaterialsKevlar.NaphthenicAcid.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsKevlar.CobaltIIAcetate.getDust(15), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(MaterialsKevlar.CobaltIINaphthenate.getDust(41))
            .fluidInputs(MaterialsKevlar.NaphthenicAcid.getFluid(1_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_500))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // Co + 2HNO3 = Co(NO3)2 + 2H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Cobalt.getDust(1), Materials.NitricAcid.getCells(2))
            .itemOutputs(
                MaterialsKevlar.CobaltIINitrate.getDust(9),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // Co(NO3)2 + 2KOH = CoH2O2 + 2KNO3

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsKevlar.CobaltIINitrate.getDust(9),
                getModItem(NewHorizonsCoreMod.ID, "item.PotassiumHydroxideDust", 6L, 0))
            .itemOutputs(MaterialsKevlar.CobaltIIHydroxide.getDust(5), Materials.Saltpeter.getDust(10))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // CoO + 2C2H4O2 = CoC4H6O4 + 2H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CobaltOxide.getDust(2), Materials.AceticAcid.getCells(2))
            .itemOutputs(MaterialsKevlar.CobaltIIAcetate.getDust(15), ItemList.Cell_Empty.get(2))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phosphorus.getDust(1), GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Chlorine.getGas(3_000))
            .fluidOutputs(MaterialsKevlar.PhosphorusTrichloride.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phosphorus.getDust(9), GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.Chlorine.getGas(27_000))
            .fluidOutputs(MaterialsKevlar.PhosphorusTrichloride.getFluid(9_000))
            .duration(1 * MINUTES + 15 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // Na + H = NaH

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Sodium.getDust(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(MaterialsKevlar.SodiumHydride.getDust(2))
            .fluidInputs(Materials.Hydrogen.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // CH3ONa + H2O = CH4O + NaOH

        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsKevlar.SodiumMethoxide.getDust(6), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.SodiumHydroxide.getDust(3))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // K + HNO3 = KNO3 + H (not real, but gameplay

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Potassium.getDust(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Saltpeter.getDust(5))
            .fluidInputs(Materials.NitricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(1_000))
            .duration(5 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // CH3COOH + CH3OH = CH3COOCH3 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.MethylAcetate.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.MethylAcetate.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.MethylAcetate.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.MethylAcetate.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.MethylAcetate.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.MethylAcetate.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.MethylAcetate.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.MethylAcetate.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // CO and CO2 recipes

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(1), GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getGems(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getDust(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getGems(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getDust(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(1), GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getGems(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getDust(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getGems(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getDust(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Ash.getDustTiny(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(1))
            .fluidInputs(Materials.CarbonDioxide.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(2_000))
            .duration(40 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getGems(9), GTUtility.getIntegratedCircuit(9))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(9_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(9_000))
            .duration(36 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getDust(9), GTUtility.getIntegratedCircuit(9))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(9_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(9_000))
            .duration(36 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getGems(9), GTUtility.getIntegratedCircuit(9))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(9_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(9_000))
            .duration(36 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getDust(9), GTUtility.getIntegratedCircuit(9))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(9_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(9_000))
            .duration(36 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getGems(9), GTUtility.getIntegratedCircuit(8))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(18_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(9_000))
            .duration(18 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Coal.getDust(9), GTUtility.getIntegratedCircuit(8))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(18_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(9_000))
            .duration(18 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getGems(9), GTUtility.getIntegratedCircuit(8))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(18_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(9_000))
            .duration(18 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Charcoal.getDust(9), GTUtility.getIntegratedCircuit(8))
            .itemOutputs(Materials.Ash.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(18_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(9_000))
            .duration(18 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // CO + 4H = CH3OH

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonMonoxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(4_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(4), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(4))
            .fluidInputs(Materials.CarbonMonoxide.getGas(1_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonMonoxide.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Methanol.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(4_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(4), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Methanol.getCells(1), Materials.Empty.getCells(3))
            .fluidInputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        // CO2 + 6H = CH3OH + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonDioxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(6_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(6), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(5))
            .fluidInputs(Materials.CarbonDioxide.getGas(1_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonDioxide.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(6_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(6), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(6))
            .fluidInputs(Materials.CarbonDioxide.getGas(1_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonDioxide.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Methanol.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(6_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(6), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Methanol.getCells(1), Materials.Empty.getCells(5))
            .fluidInputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        // CH3OH + CO = CH3COOH

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.CarbonMonoxide.getGas(1_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonMonoxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.AceticAcid.getCells(1))
            .fluidInputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.CarbonMonoxide.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.AceticAcid.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // CH2CH2 + 2O = CH3COOH

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), GTUtility.getIntegratedCircuit(8))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(2), GTUtility.getIntegratedCircuit(8))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), GTUtility.getIntegratedCircuit(19))
            .itemOutputs(Materials.AceticAcid.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(2), GTUtility.getIntegratedCircuit(19))
            .itemOutputs(Materials.AceticAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // O + C2H4O2 + C2H4 = C4H6O2 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(4))
            .fluidInputs(
                Materials.Oxygen.getGas(1_000),
                Materials.Ethylene.getGas(1_000),
                Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.VinylAcetate.getFluid(1_000), Materials.Water.getFluid(1_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), Materials.AceticAcid.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.VinylAcetate.getFluid(1_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), Materials.Oxygen.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.VinylAcetate.getFluid(1_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), Materials.Ethylene.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.VinylAcetate.getFluid(1_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        // Ethanol -> Ethylene (Intended loss for Sulfuric Acid)

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethanol.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ethylene.getCells(1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfuricAcid.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ethylene.getCells(1))
            .fluidInputs(Materials.Ethanol.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethanol.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Ethylene.getGas(1_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfuricAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(1))
            .fluidInputs(Materials.Ethanol.getFluid(1_000))
            .fluidOutputs(Materials.Ethylene.getGas(1_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // H2O + Na = NaOH + H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Sodium.getDust(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.SodiumHydroxide.getDust(3))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // H + Cl = HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // NaOH + HCl = NaCl + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(3))
            .itemOutputs(Materials.Salt.getDust(2))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1000))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        // C3H6 + 2Cl = HCl + C3H5Cl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Propene.getGas(1_000))
            .fluidOutputs(Materials.AllylChloride.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.AllylChloride.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.AllylChloride.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Propene.getGas(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.AllylChloride.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // 2Cl + H2O = HCl + HClO (Intended loss)

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.DilutedHydrochloricAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.HypochlorousAcid.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.DilutedHydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.HypochlorousAcid.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HypochlorousAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HypochlorousAcid.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // HClO + NaOH + C3H5Cl = C3H5ClO + NaCl·H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HypochlorousAcid.getCells(1), Materials.SodiumHydroxide.getDust(3))
            .itemOutputs(Materials.SaltWater.getCells(1))
            .fluidInputs(Materials.AllylChloride.getFluid(1_000))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(3), Materials.AllylChloride.getCells(1))
            .itemOutputs(Materials.SaltWater.getCells(1))
            .fluidInputs(Materials.HypochlorousAcid.getFluid(1_000))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Water.getCells(2))
            .fluidInputs(Materials.Glycerol.getFluid(1_000))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Glycerol.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Water.getCells(2))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Epichlorohydrin.getCells(1))
            .fluidInputs(Materials.Glycerol.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Glycerol.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Epichlorohydrin.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Glycerol.getFluid(1_000))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Glycerol.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Epichlorohydrin.getCells(1))
            .fluidInputs(Materials.Glycerol.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Glycerol.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Epichlorohydrin.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // P4O10 + 6H2O = 4H3PO4

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PhosphorousPentoxide.getDust(14))
            .fluidInputs(Materials.Water.getFluid(6_000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(4_000))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C9H12 + 2O = C6H6O + C3H6O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Cumene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Phenol.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.Acetone.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Phenol.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Cumene.getFluid(1_000))
            .fluidOutputs(Materials.Acetone.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Cumene.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Acetone.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.Phenol.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Acetone.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Cumene.getFluid(1_000))
            .fluidOutputs(Materials.Phenol.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C15H16O2 + 2C3H5ClO + 2NaOH = C15H14O2(C3H5O)2 + 2NaCl·H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(6), Materials.Epichlorohydrin.getCells(2))
            .itemOutputs(Materials.SaltWater.getCells(2))
            .fluidInputs(Materials.BisphenolA.getFluid(1_000))
            .fluidOutputs(Materials.Epoxid.getMolten(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // CH4O + HCl = CH3Cl + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Chloromethane.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.Chloromethane.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Chloromethane.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Chloromethane.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Chloromethane.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.Chloromethane.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Chloromethane.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Chloromethane.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Methane.getGas(1_000))
            .fluidOutputs(Materials.Chloromethane.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.Chloromethane.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Chloromethane.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Methane.getGas(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Chloromethane.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // Cl6 + CH4 = CHCl3 + 3HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(6), GTUtility.getIntegratedCircuit(3))
            .itemOutputs(Materials.HydrochloricAcid.getCells(3), Materials.Empty.getCells(3))
            .fluidInputs(Materials.Methane.getGas(1_000))
            .fluidOutputs(Materials.Chloroform.getFluid(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(6), GTUtility.getIntegratedCircuit(13))
            .itemOutputs(Materials.Chloroform.getCells(1), Materials.Empty.getCells(5))
            .fluidInputs(Materials.Methane.getGas(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(3_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(1), GTUtility.getIntegratedCircuit(13))
            .itemOutputs(Materials.Chloroform.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(6_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(3_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // H + F = HF

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Fluorine.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(1_000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Fluorine.getGas(1_000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Fluorine.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HydrofluoricAcid.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HydrofluoricAcid.getCells(1))
            .fluidInputs(Materials.Fluorine.getGas(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // 4HF + 2CHCl3 = C2F4 + 6HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chloroform.getCells(2), Materials.HydrofluoricAcid.getCells(4))
            .itemOutputs(Materials.HydrochloricAcid.getCells(6))
            .fluidOutputs(Materials.Tetrafluoroethylene.getGas(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chloroform.getCells(2), Materials.Empty.getCells(4))
            .itemOutputs(Materials.HydrochloricAcid.getCells(6))
            .fluidInputs(Materials.HydrofluoricAcid.getFluid(4_000))
            .fluidOutputs(Materials.Tetrafluoroethylene.getGas(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrofluoricAcid.getCells(4), Materials.Empty.getCells(2))
            .itemOutputs(Materials.HydrochloricAcid.getCells(6))
            .fluidInputs(Materials.Chloroform.getFluid(2_000))
            .fluidOutputs(Materials.Tetrafluoroethylene.getGas(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrofluoricAcid.getCells(4), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Tetrafluoroethylene.getCells(1), Materials.Empty.getCells(3))
            .fluidInputs(Materials.Chloroform.getFluid(2_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(6_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chloroform.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Tetrafluoroethylene.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydrofluoricAcid.getFluid(4_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(6_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // Si + 2CH3Cl = C2H6Cl2Si

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Silicon.getDust(1), GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Chloromethane.getGas(2_000))
            .fluidOutputs(Materials.Dimethyldichlorosilane.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Dimethyldichlorosilane.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Dimethyldichlorosilane.getFluid(1_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Dimethyldichlorosilane.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3), Materials.DilutedHydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3), Materials.DilutedHydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Dimethyldichlorosilane.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Polydimethylsiloxane.getDust(9), Materials.Sulfur.getDust(1))
            .fluidOutputs(Materials.Silicone.getMolten(9 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // Potassium Nitride
        // K + HNO3 = KNO3 + H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Potassium.getDust(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.PotassiumNitrade.getDust(5))
            .fluidInputs(Materials.NitricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(1_000))
            .duration(5 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // Chromium Trioxide
        // CrO2 + O = CrO3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.ChromiumDioxide.getDust(3), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.ChromiumTrioxide.getDust(4))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // Nitrochlorobenzene
        // C6H5Cl + HNO3 = C6H4ClNO2 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorobenzene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Nitrochlorobenzene.getCells(1))
            .fluidInputs(Materials.NitrationMixture.getFluid(2_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorobenzene.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(1))
            .fluidInputs(Materials.NitrationMixture.getFluid(2_000))
            .fluidOutputs(Materials.Nitrochlorobenzene.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrationMixture.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Nitrochlorobenzene.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Chlorobenzene.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrationMixture.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Chlorobenzene.getFluid(1_000))
            .fluidOutputs(Materials.Nitrochlorobenzene.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // C6H6 + 2CH4 = C8H10 + 4H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Dimethylbenzene.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Dimethylbenzene.getCells(1))
            .fluidInputs(Materials.Methane.getGas(2_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // Phthalic Acid
        // C8H10 + 6O =K2Cr2O7= C8H6O4 + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Dimethylbenzene.getCells(1), Materials.Potassiumdichromate.getDustTiny(1))
            .itemOutputs(Materials.PhthalicAcid.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(6_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(6), Materials.Potassiumdichromate.getDustTiny(1))
            .itemOutputs(Materials.PhthalicAcid.getCells(1), ItemList.Cell_Empty.get(5))
            .fluidInputs(Materials.Dimethylbenzene.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Dimethylbenzene.getCells(9), Materials.Potassiumdichromate.getDust(1))
            .itemOutputs(Materials.PhthalicAcid.getCells(9))
            .fluidInputs(Materials.Oxygen.getGas(54_000))
            .fluidOutputs(Materials.Water.getFluid(18_000))
            .duration(45 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(54), Materials.Potassiumdichromate.getDust(1))
            .itemOutputs(Materials.PhthalicAcid.getCells(9), ItemList.Cell_Empty.get(45))
            .fluidInputs(Materials.Dimethylbenzene.getFluid(9_000))
            .fluidOutputs(Materials.Water.getFluid(18_000))
            .duration(45 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(chemicalReactorRecipes);

        // These following recipes are broken in element term.
        // But they are kept in gamewise, too much existed setup will be broken.
        // Dichlorobenzidine

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Copper.getDustTiny(1), GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Nitrochlorobenzene.getFluid(2_000))
            .fluidOutputs(Materials.Dichlorobenzidine.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Copper.getDust(1), GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.Nitrochlorobenzene.getFluid(18_000))
            .fluidOutputs(Materials.Dichlorobenzidine.getFluid(9_000))
            .duration(1 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(UniversalChemical);

        // Diphenyl Isophthalate

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PhthalicAcid.getCells(1), Materials.SulfuricAcid.getCells(1))
            .itemOutputs(Materials.Diphenylisophthalate.getCells(1), ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Phenol.getFluid(2_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PhthalicAcid.getCells(1), Materials.Phenol.getCells(2))
            .itemOutputs(Materials.Diphenylisophthalate.getCells(1), ItemList.Cell_Empty.get(2))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfuricAcid.getCells(1), Materials.Phenol.getCells(2))
            .itemOutputs(Materials.Diphenylisophthalate.getCells(1), ItemList.Cell_Empty.get(2))
            .fluidInputs(Materials.PhthalicAcid.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(chemicalReactorRecipes);

        // Diaminobenzidin

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(2), Materials.Zinc.getDust(1))
            .itemOutputs(Materials.Diaminobenzidin.getCells(1), ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Dichlorobenzidine.getFluid(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(UniversalChemical);

        // Polybenzimidazole
        // C12H14N4 + C20H14O4 = C20H12N4 + 2C6H6O + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Diphenylisophthalate.getCells(1), Materials.Diaminobenzidin.getCells(1))
            .itemOutputs(Materials.Phenol.getCells(2))
            .fluidOutputs(Materials.Polybenzimidazole.getMolten(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 1))
            .itemOutputs(getModItem(Railcraft.ID, "glass", 6))
            .fluidInputs(Materials.Glass.getMolten(6 * INGOTS))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // NH3 + 2CH4O = C2H7N + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(2))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .fluidOutputs(Materials.Dimethylamine.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Dimethylamine.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Dimethylamine.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(2_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(2), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .fluidOutputs(Materials.Dimethylamine.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methanol.getCells(2), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Dimethylamine.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Dimethylamine.getCells(1))
            .fluidInputs(Materials.Methanol.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // NH3 + HClO = NH2Cl + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.HypochlorousAcid.getFluid(1_000))
            .fluidOutputs(Materials.Chloramine.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HypochlorousAcid.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .fluidOutputs(Materials.Chloramine.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Chloramine.getCells(1))
            .fluidInputs(Materials.HypochlorousAcid.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HypochlorousAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Chloramine.getCells(1))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.HypochlorousAcid.getFluid(1_000))
            .fluidOutputs(Materials.Chloramine.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HypochlorousAcid.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .fluidOutputs(Materials.Chloramine.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Chloramine.getCells(1))
            .fluidInputs(Materials.HypochlorousAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HypochlorousAcid.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Chloramine.getCells(1))
            .fluidInputs(Materials.Ammonia.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // 2NO2 = N2O4

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.NitrogenDioxide.getGas(2_000))
            .fluidOutputs(Materials.DinitrogenTetroxide.getGas(1_000))
            .duration(32 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrogenDioxide.getCells(2), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidOutputs(Materials.DinitrogenTetroxide.getGas(1_000))
            .duration(32 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrogenDioxide.getCells(2), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.DinitrogenTetroxide.getCells(1), Materials.Empty.getCells(1))
            .duration(32 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // 2NH3 + 5O = 2NO + 3H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(4), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.NitricOxide.getCells(4))
            .fluidInputs(Materials.Oxygen.getGas(10_000))
            .fluidOutputs(Materials.Water.getFluid(6_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(10), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.NitricOxide.getCells(4), Materials.Empty.getCells(6))
            .fluidInputs(Materials.Ammonia.getGas(4_000))
            .fluidOutputs(Materials.Water.getFluid(6_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(10), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Water.getCells(6), Materials.Empty.getCells(4))
            .fluidInputs(Materials.Ammonia.getGas(4_000))
            .fluidOutputs(Materials.NitricOxide.getGas(4_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(4), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.NitricOxide.getCells(4))
            .fluidInputs(Materials.Oxygen.getGas(10_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(10), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.NitricOxide.getCells(4), Materials.Empty.getCells(6))
            .fluidInputs(Materials.Ammonia.getGas(4_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(10), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Empty.getCells(10))
            .fluidInputs(Materials.Ammonia.getGas(4_000))
            .fluidOutputs(Materials.NitricOxide.getGas(4_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // NO + O = NO2

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitricOxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.NitricOxide.getGas(1_000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitricOxide.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.NitrogenDioxide.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.NitrogenDioxide.getCells(1))
            .fluidInputs(Materials.NitricOxide.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // H2O + 3NO2 = 2HNO3 + NO

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.NitricOxide.getCells(1))
            .fluidInputs(Materials.NitrogenDioxide.getGas(3_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrogenDioxide.getCells(3), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.NitricOxide.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrogenDioxide.getCells(3), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.NitricAcid.getCells(2), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.NitricOxide.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // S + 2H = H2S

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Sulfur.getDust(1), GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Hydrogen.getGas(2_000))
            .fluidOutputs(Materials.HydricSulfide.getGas(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // S + 2O = SO2

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Sulfur.getDust(1), GTUtility.getIntegratedCircuit(3))
            .fluidInputs(Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // H2S + 3O = SO2 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(3), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.HydricSulfide.getGas(1_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.SulfurDioxide.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(3), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.SulfurDioxide.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.HydricSulfide.getGas(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(3), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Empty.getCells(3))
            .fluidInputs(Materials.HydricSulfide.getGas(1_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.SulfurDioxide.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(3), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.SulfurDioxide.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.HydricSulfide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // 2H2S + SO2 = 3S + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfurDioxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Sulfur.getDust(3), Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydricSulfide.getGas(2_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Sulfur.getDust(3), Materials.Empty.getCells(2))
            .fluidInputs(Materials.SulfurDioxide.getGas(1_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfurDioxide.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Sulfur.getDust(3), Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydricSulfide.getGas(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydricSulfide.getCells(2), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Sulfur.getDust(3), Materials.Empty.getCells(2))
            .fluidInputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        // SO2 + O = SO3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.SulfurDioxide.getGas(1_000))
            .fluidOutputs(Materials.SulfurTrioxide.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfurDioxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.SulfurTrioxide.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.SulfurTrioxide.getCells(1))
            .fluidInputs(Materials.SulfurDioxide.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfurDioxide.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.SulfurTrioxide.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // SO3 + H2O = H2SO4

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.SulfurTrioxide.getGas(1_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfurTrioxide.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.SulfuricAcid.getCells(1))
            .fluidInputs(Materials.SulfurTrioxide.getGas(1_000))
            .duration(16 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfurTrioxide.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.SulfuricAcid.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(8)
            .addTo(UniversalChemical);

        // C2H4 + 2Cl = C2H3Cl + HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.VinylChloride.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.VinylChloride.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.VinylChloride.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.VinylChloride.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C2H4O2 =H2SO4= C2H2O + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ethenone.getCells(1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfuricAcid.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Ethenone.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.AceticAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Ethenone.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SulfuricAcid.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(1))
            .fluidInputs(Materials.AceticAcid.getFluid(1_000))
            .fluidOutputs(Materials.Ethenone.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        // C2H2O + 8HNO3 = 2CN4O8 + 9H2O
        // Chemically this recipe is wrong, but kept for minimizing breaking change.

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethenone.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Tetranitromethane.getCells(2))
            .fluidInputs(Materials.NitricAcid.getFluid(8_000))
            .fluidOutputs(Materials.Water.getFluid(9_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethenone.getCells(1), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.NitricAcid.getFluid(8_000))
            .fluidOutputs(Materials.Tetranitromethane.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitricAcid.getCells(8), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Tetranitromethane.getCells(2), Materials.Empty.getCells(6))
            .fluidInputs(Materials.Ethenone.getGas(1_000))
            .fluidOutputs(Materials.Water.getFluid(9_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitricAcid.getCells(8), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Tetranitromethane.getCells(2), Materials.Empty.getCells(6))
            .fluidInputs(Materials.Ethenone.getGas(1_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitricAcid.getCells(8), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.Empty.getCells(8))
            .fluidInputs(Materials.Ethenone.getGas(1_000))
            .fluidOutputs(Materials.Tetranitromethane.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitricAcid.getCells(8), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Water.getCells(9))
            .fluidInputs(Materials.Ethenone.getGas(1_000))
            .fluidOutputs(Materials.Tetranitromethane.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethenone.getCells(1), Materials.NitricAcid.getCells(8))
            .itemOutputs(Materials.Water.getCells(9))
            .fluidOutputs(Materials.Tetranitromethane.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        // C3H6 + C2H4 = C5H8 + 2H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(2))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.Isoprene.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(2))
            .fluidInputs(Materials.Propene.getGas(1_000))
            .fluidOutputs(Materials.Isoprene.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Isoprene.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Isoprene.getCells(1))
            .fluidInputs(Materials.Propene.getGas(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Empty.getCells(1), GTUtility.getIntegratedCircuit(5))
            .itemOutputs(Materials.Methane.getCells(1))
            .fluidInputs(Materials.Propene.getGas(2_000))
            .fluidOutputs(Materials.Isoprene.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(2), GTUtility.getIntegratedCircuit(5))
            .itemOutputs(Materials.Methane.getCells(1), Materials.Empty.getCells(1))
            .fluidOutputs(Materials.Isoprene.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Empty.getCells(1), GTUtility.getIntegratedCircuit(15))
            .itemOutputs(Materials.Isoprene.getCells(1))
            .fluidInputs(Materials.Propene.getGas(2_000))
            .fluidOutputs(Materials.Methane.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(2), GTUtility.getIntegratedCircuit(15))
            .itemOutputs(Materials.Isoprene.getCells(1), Materials.Empty.getCells(1))
            .fluidOutputs(Materials.Methane.getGas(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Cell_Air.get(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.RawRubber.getDust(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Isoprene.getFluid(144))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.RawRubber.getDust(3), Materials.Empty.getCells(2))
            .fluidInputs(Materials.Isoprene.getFluid(288))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Isoprene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.RawRubber.getDust(7), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Air.getGas(14_000))
            .duration(56 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Isoprene.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.RawRubber.getDust(21), Materials.Empty.getCells(2))
            .fluidInputs(Materials.Oxygen.getGas(14_000))
            .duration(1 * MINUTES + 52 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Styrene.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Styrene.getCells(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(2_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.RawStyreneButadieneRubber.getDust(9), Materials.Sulfur.getDust(1))
            .fluidOutputs(Materials.StyreneButadieneRubber.getMolten(9 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C6H6 + 4Cl = C6H4Cl2 + 2HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Dichlorobenzene.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(4), GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.Dichlorobenzene.getCells(1), Materials.Empty.getCells(3))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(4), GTUtility.getIntegratedCircuit(12))
            .itemOutputs(Materials.HydrochloricAcid.getCells(2), Materials.Empty.getCells(2))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Dichlorobenzene.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumSulfide.getDust(3), ItemList.Cell_Air.get(8))
            .itemOutputs(Materials.Salt.getDust(4), Materials.Empty.getCells(8))
            .fluidInputs(Materials.Dichlorobenzene.getFluid(1_000))
            .fluidOutputs(Materials.PolyphenyleneSulfide.getMolten(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumSulfide.getDust(3), Materials.Oxygen.getCells(8))
            .itemOutputs(Materials.Salt.getDust(4), Materials.Empty.getCells(8))
            .fluidInputs(Materials.Dichlorobenzene.getFluid(1_000))
            .fluidOutputs(Materials.PolyphenyleneSulfide.getMolten(1_500))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // NaCl + H2SO4 = NaHSO4 + HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Salt.getDust(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.SodiumBisulfate.getDust(7))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // NaOH + H2SO4 = NaHSO4 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(3), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.SodiumBisulfate.getDust(7))
            .fluidInputs(Materials.SulfuricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // Biodiesel recipes

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.Methanol.getCells(1))
            .itemOutputs(Materials.Glycerol.getCells(1))
            .fluidInputs(Materials.SeedOil.getFluid(6_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(6_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.SeedOil.getCells(6))
            .itemOutputs(Materials.BioDiesel.getCells(6))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.Glycerol.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.Methanol.getCells(1))
            .itemOutputs(Materials.Glycerol.getCells(1))
            .fluidInputs(Materials.FishOil.getFluid(6_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(6_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.FishOil.getCells(6))
            .itemOutputs(Materials.BioDiesel.getCells(6))
            .fluidInputs(Materials.Methanol.getFluid(1_000))
            .fluidOutputs(Materials.Glycerol.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.Ethanol.getCells(1))
            .itemOutputs(Materials.Glycerol.getCells(1))
            .fluidInputs(Materials.SeedOil.getFluid(6_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(6_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.SeedOil.getCells(6))
            .itemOutputs(Materials.BioDiesel.getCells(6))
            .fluidInputs(Materials.Ethanol.getFluid(1_000))
            .fluidOutputs(Materials.Glycerol.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.Ethanol.getCells(1))
            .itemOutputs(Materials.Glycerol.getCells(1))
            .fluidInputs(Materials.FishOil.getFluid(6_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(6_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDustTiny(1), Materials.FishOil.getCells(6))
            .itemOutputs(Materials.BioDiesel.getCells(6))
            .fluidInputs(Materials.Ethanol.getFluid(1_000))
            .fluidOutputs(Materials.Glycerol.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.Methanol.getCells(9))
            .itemOutputs(Materials.Glycerol.getCells(9))
            .fluidInputs(Materials.SeedOil.getFluid(54_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(54_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.SeedOil.getCells(54))
            .itemOutputs(Materials.BioDiesel.getCells(54))
            .fluidInputs(Materials.Methanol.getFluid(9_000))
            .fluidOutputs(Materials.Glycerol.getFluid(9_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.Methanol.getCells(9))
            .itemOutputs(Materials.Glycerol.getCells(9))
            .fluidInputs(Materials.FishOil.getFluid(54_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(54_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.FishOil.getCells(54))
            .itemOutputs(Materials.BioDiesel.getCells(54))
            .fluidInputs(Materials.Methanol.getFluid(9_000))
            .fluidOutputs(Materials.Glycerol.getFluid(9_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.Ethanol.getCells(9))
            .itemOutputs(Materials.Glycerol.getCells(9))
            .fluidInputs(Materials.SeedOil.getFluid(54_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(54_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.SeedOil.getCells(54))
            .itemOutputs(Materials.BioDiesel.getCells(54))
            .fluidInputs(Materials.Ethanol.getFluid(9_000))
            .fluidOutputs(Materials.Glycerol.getFluid(9_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.Ethanol.getCells(9))
            .itemOutputs(Materials.Glycerol.getCells(9))
            .fluidInputs(Materials.FishOil.getFluid(54_000))
            .fluidOutputs(Materials.BioDiesel.getFluid(54_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(1), Materials.FishOil.getCells(54))
            .itemOutputs(Materials.BioDiesel.getCells(54))
            .fluidInputs(Materials.Ethanol.getFluid(9_000))
            .fluidOutputs(Materials.Glycerol.getFluid(9_000))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        // C3H8O3 + 3HNO3 =H2SO4= C3H5N3O9 + 3H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Glycerol.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Glyceryl.getCells(1))
            .fluidInputs(Materials.NitrationMixture.getFluid(6_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(3_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrationMixture.getCells(6), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Glyceryl.getCells(1), Materials.Empty.getCells(5))
            .fluidInputs(Materials.Glycerol.getFluid(1_000))
            .fluidOutputs(Materials.DilutedSulfuricAcid.getFluid(3_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrationMixture.getCells(6), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(3), Materials.Empty.getCells(3))
            .fluidInputs(Materials.Glycerol.getFluid(1_000))
            .fluidOutputs(Materials.Glyceryl.getFluid(1_000))
            .duration(9 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // CaO + CO2 = CaCO3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Quicklime.getDust(2))
            .itemOutputs(Materials.Calcite.getDust(5))
            .fluidInputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Calcite.getDust(5), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Quicklime.getDust(2))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // MgO + CO2 = MgCO3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Magnesia.getDust(2))
            .itemOutputs(Materials.Magnesite.getDust(5))
            .fluidInputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Magnesite.getDust(5), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Magnesia.getDust(2))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C6H6 + 2Cl = C6H5Cl + HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Chlorobenzene.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(2_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Chlorobenzene.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Chlorobenzene.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // C6H5Cl + H2O = C6H6O + HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.DilutedHydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Chlorobenzene.getFluid(1_000))
            .fluidOutputs(Materials.Phenol.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorobenzene.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.DilutedHydrochloricAcid.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.Phenol.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Phenol.getCells(1))
            .fluidInputs(Materials.Chlorobenzene.getFluid(1_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorobenzene.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Phenol.getCells(1))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        // C6H5Cl + NaOH = C6H6O + NaCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(12), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Salt.getDust(8))
            .fluidInputs(Materials.Chlorobenzene.getFluid(4_000))
            .fluidOutputs(Materials.Phenol.getFluid(4_000))
            .duration(48 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        // Oxide Recipe

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2), Materials.Antimony.getDust(2))
            .itemOutputs(Materials.AntimonyTrioxide.getDust(5))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .duration(20 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2), Materials.Lead.getDust(1))
            .itemOutputs(Materials.Massicot.getDust(2))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(20 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2), Materials.Arsenic.getDust(2))
            .itemOutputs(Materials.ArsenicTrioxide.getDust(5))
            .fluidInputs(Materials.Oxygen.getGas(3_000))
            .duration(20 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2), Materials.Cobalt.getDust(1))
            .itemOutputs(Materials.CobaltOxide.getDust(2))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(20 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2), Materials.Zinc.getDust(1))
            .itemOutputs(Materials.Zincite.getDust(2))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(20 * TICKS)
            .eut(30)
            .addTo(UniversalChemical);

        // CaSi2 + 2HCl = 2Si + CaCl2 + 2H

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CalciumDisilicide, 3),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 2),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.dust), 3, 63))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(2_000))
            .fluidOutputs(Materials.Hydrogen.getGas(2_000))
            .duration(45 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // SiCl4 + 2Zn = 2ZnCl2 + Si

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Zinc, 2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.dust), 6, 10052))
            .fluidInputs(Materials.SiliconTetrachloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // C4H8O + 2H =Pd= C4H10O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.Butyraldehyde, 1),
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Palladium, 1))
            .itemOutputs(ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Hydrogen.getGas(2_000))
            .fluidOutputs(new FluidStack(FluidRegistry.getFluid("butanol"), 1000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(UniversalChemical);

        // 4CH2O + C2H4O =NaOH= C5H12O4 + CO

        GTValues.RA.stdBuilder()
            .itemInputs( // very poor way of looking for it, but getModItem on GT++ within GT5U jar is prohibited now,
                // and i don't feel like reworking GT++ cell registration for now
                GameRegistry.findItemStack(GTPlusPlus.ID, "Formaldehyde", 4),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumHydroxide, 1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.Pentaerythritol, 21),
                Materials.Empty.getCells(4))
            .fluidInputs(MaterialsKevlar.Acetaldehyde.getGas(1_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // 4CH2O + C2H4O =NaOH= C5H12O4 + CO

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.Acetaldehyde, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumHydroxide, 1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.Pentaerythritol, 21),
                Materials.Empty.getCells(1))
            .fluidInputs(new FluidStack(FluidRegistry.getFluid("fluid.formaldehyde"), 4_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        // CaC2 + 2H2O = Ca(OH)2 + C2H2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.CalciumCarbide, 3),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(new ItemStack(ModItems.dustCalciumHydroxide, 5))
            .fluidInputs(Materials.Water.getFluid(2_000))
            .fluidOutputs(MaterialsKevlar.Acetylene.getGas(1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(UniversalChemical);

        // Co(NO3)2 + 2NaOH = Co(OH)2 + 2NaNO3

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsKevlar.CobaltIINitrate.getDust(9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumHydroxide, 6))
            .itemOutputs(MaterialsKevlar.CobaltIIHydroxide.getDust(5), SODIUM_NITRATE.getDust(10))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(UniversalChemical);

        if (Forestry.isModLoaded()) {

            // Americium comb processing

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.AMERICIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Americium, 1))
                .fluidInputs(Materials.Helium.getPlasma(8_175))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.AMERICIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Americium, 2))
                .fluidInputs(Materials.Nitrogen.getPlasma(1_211))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_UV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.AMERICIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Americium, 4))
                .fluidInputs(Materials.Silver.getPlasma(310))
                .duration(7 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_UHV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.AMERICIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Americium, 8))
                .fluidInputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getPlasma(), 29))
                .duration(3 * SECONDS + 15 * TICKS)
                .eut(TierEU.RECIPE_UEV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.AMERICIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Americium, 16))
                .fluidInputs(Materials.Thorium.getPlasma(68))
                .duration(1 * SECONDS + 17 * TICKS)
                .eut(TierEU.RECIPE_UIV)
                .addTo(UniversalChemical);

            // Europium comb processing

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.EUROPIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Europium, 1))
                .fluidInputs(Materials.Helium.getPlasma(606))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LuV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.EUROPIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Europium, 2))
                .fluidInputs(Materials.Nitrogen.getPlasma(180))
                .duration(7 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_ZPM)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.EUROPIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Europium, 4))
                .fluidInputs(Materials.Silver.getPlasma(54))
                .duration(3 * SECONDS + 15 * TICKS)
                .eut(TierEU.RECIPE_UV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.EUROPIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Europium, 8))
                .fluidInputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getPlasma(), 6))
                .duration(1 * SECONDS + 17 * TICKS)
                .eut(TierEU.RECIPE_UHV)
                .addTo(UniversalChemical);

            GTValues.RA.stdBuilder()
                .itemInputs(GTBees.combs.getStackForType(CombType.EUROPIUM, 4))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Europium, 16))
                .fluidInputs(Materials.Thorium.getPlasma(18))
                .duration(18 * TICKS)
                .eut(TierEU.RECIPE_UEV)
                .addTo(UniversalChemical);
        }
    }

    public void addDefaultPolymerizationRecipes(Fluid aBasicMaterial, ItemStack aBasicMaterialCell, Fluid aPolymer) {
        // Oxygen/Titaniumtetrafluoride -> +50% Output each
        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Cell_Air.get(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(new FluidStack(aBasicMaterial, 1 * INGOTS))
            .fluidOutputs(new FluidStack(aPolymer, 1 * INGOTS))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(new FluidStack(aBasicMaterial, 1 * INGOTS))
            .fluidOutputs(new FluidStack(aPolymer, 3 * HALF_INGOTS))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(aBasicMaterialCell, GTUtility.getIntegratedCircuit(9))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Air.getGas(14_000))
            .fluidOutputs(new FluidStack(aPolymer, 1_000))
            .duration(56 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(aBasicMaterialCell, GTUtility.getIntegratedCircuit(9))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(7_000))
            .fluidOutputs(new FluidStack(aPolymer, 1_500))
            .duration(56 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(UniversalChemical);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                new FluidStack(aBasicMaterial, 14 * INGOTS),
                Materials.Air.getGas(7_500),
                Materials.Titaniumtetrachloride.getFluid(100))
            .fluidOutputs(new FluidStack(aPolymer, 3_240))
            .duration(40 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                new FluidStack(aBasicMaterial, 14 * INGOTS),
                Materials.Oxygen.getGas(7_500),
                Materials.Titaniumtetrachloride.getFluid(100))
            .fluidOutputs(new FluidStack(aPolymer, 4_320))
            .duration(40 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(multiblockChemicalReactorRecipes);

    }

    public void polymerizationRecipes() {
        addDefaultPolymerizationRecipes(
            Materials.VinylAcetate.mFluid,
            Materials.VinylAcetate.getCells(1),
            Materials.PolyvinylAcetate.mFluid);

        addDefaultPolymerizationRecipes(
            Materials.Ethylene.mGas,
            Materials.Ethylene.getCells(1),
            Materials.Plastic.mStandardMoltenFluid);

        addDefaultPolymerizationRecipes(
            Materials.Tetrafluoroethylene.mGas,
            Materials.Tetrafluoroethylene.getCells(1),
            Materials.Polytetrafluoroethylene.mStandardMoltenFluid);

        addDefaultPolymerizationRecipes(
            Materials.VinylChloride.mGas,
            Materials.VinylChloride.getCells(1),
            Materials.PolyvinylChloride.mStandardMoltenFluid);

        addDefaultPolymerizationRecipes(
            Materials.Styrene.mFluid,
            Materials.Styrene.getCells(1),
            Materials.Polystyrene.mStandardMoltenFluid);
    }

    public void singleBlockOnly() {

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.GasolineRaw.getCells(10), Materials.Toluene.getCells(1))
            .itemOutputs(Materials.GasolineRegular.getCells(11))
            .duration(10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(2))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.Styrene.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(2))
            .fluidInputs(Materials.Benzene.getFluid(1_000))
            .fluidOutputs(Materials.Styrene.getFluid(1_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Methane.getCells(1), Materials.Empty.getCells(2))
            .itemOutputs(Materials.HydrochloricAcid.getCells(3))
            .fluidInputs(Materials.Chlorine.getGas(6_000))
            .fluidOutputs(Materials.Chloroform.getFluid(1_000))
            .duration(4 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Silicon.getDust(1), Materials.Chloromethane.getCells(2))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidOutputs(Materials.Dimethyldichlorosilane.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Dimethyldichlorosilane.getCells(1), Materials.Water.getCells(1))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3), Materials.Empty.getCells(2))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(96)
            .addTo(chemicalReactorRecipes);

        // Ca5(PO4)3Cl + 5H2SO4 + 10H2O = 5CaSO4(H2O)2 + HCl + 3H3PO4

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Apatite.getDust(9), Materials.SulfuricAcid.getCells(5))
            .itemOutputs(Materials.HydrochloricAcid.getCells(1), Materials.Empty.getCells(4))
            .fluidInputs(Materials.Water.getFluid(10_000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(3_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // 10O + 4P = P4O10

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phosphorus.getDust(4))
            .itemOutputs(Materials.PhosphorousPentoxide.getDust(14))
            .fluidInputs(Materials.Oxygen.getGas(10_000))
            .duration(2 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // HCl + C3H8O3 = C3H5ClO + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), Materials.Glycerol.getCells(1))
            .itemOutputs(Materials.Water.getCells(2))
            .fluidOutputs(Materials.Epichlorohydrin.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // H2O + Cl =Hg= HClO + H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(10), Materials.Mercury.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(10), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Water.getFluid(10_000))
            .fluidOutputs(Materials.HypochlorousAcid.getFluid(10_000))
            .duration(30 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(10), Materials.Mercury.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(10), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Chlorine.getGas(10_000))
            .fluidOutputs(Materials.HypochlorousAcid.getFluid(10_000))
            .duration(30 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Chlorine.getCells(1), Materials.Water.getCells(1))
            .itemOutputs(Materials.Hydrogen.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Mercury.getFluid(100))
            .fluidOutputs(Materials.HypochlorousAcid.getFluid(1_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        // P + 3Cl = PCl3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phosphorus.getDust(1), Materials.Chlorine.getCells(3))
            .itemOutputs(ItemList.Cell_Empty.get(3))
            .fluidOutputs(MaterialsKevlar.PhosphorusTrichloride.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.EthyleneOxide, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Water, 5))
            .itemOutputs(ItemList.Cell_Empty.get(6))
            .fluidInputs(Materials.Dimethyldichlorosilane.getFluid(4_000))
            .fluidOutputs(MaterialsKevlar.SiliconOil.getFluid(5_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.EthyleneOxide, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Dimethyldichlorosilane, 4))
            .itemOutputs(ItemList.Cell_Empty.get(5))
            .fluidInputs(Materials.Water.getFluid(5_000))
            .fluidOutputs(MaterialsKevlar.SiliconOil.getFluid(5_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1),
                GTUtility.getIntegratedCircuit(2))
            .itemOutputs(ItemList.Cell_Empty.get(1))
            .fluidInputs(MaterialsKevlar.EthyleneOxide.getGas(1_000))
            .fluidOutputs(MaterialsKevlar.Ethyleneglycol.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsKevlar.CobaltIIHydroxide.getDust(5), MaterialsKevlar.NaphthenicAcid.getCells(1))
            .itemOutputs(MaterialsKevlar.CobaltIINaphthenate.getDust(41), ItemList.Cell_Empty.get(1))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsKevlar.CobaltIIAcetate.getDust(15), MaterialsKevlar.NaphthenicAcid.getCells(1))
            .itemOutputs(MaterialsKevlar.CobaltIINaphthenate.getDust(41), ItemList.Cell_Empty.get(1))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_500))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Chlorine, 4))
            .itemOutputs(ItemList.Cell_Empty.get(4))
            .fluidOutputs(Materials.SiliconTetrachloride.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Galena, 3),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Sphalerite, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(4_000))
            .fluidOutputs(new FluidStack(ItemList.sIndiumConcentrate, 8_000))
            .duration(3 * SECONDS)
            .eut(150)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Methane.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(4_000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // O + 2H = H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1),
                GTUtility.getIntegratedCircuit(22))
            .itemOutputs(ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Hydrogen.getGas(2_000))
            .fluidOutputs(GTModHandler.getDistilledWater(1_000))
            .duration(10 * TICKS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 1),
                GTUtility.getIntegratedCircuit(22))
            .itemOutputs(ItemList.Cell_Empty.get(1))
            .fluidInputs(Materials.Oxygen.getGas(500))
            .fluidOutputs(GTModHandler.getDistilledWater(500))
            .duration(5 * TICKS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // Si + 4Cl = SiCl4

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1), ItemList.Cell_Empty.get(2))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 2))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(3_000))
            .fluidOutputs(Materials.Trichlorosilane.getFluid(1_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Silane, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1), ItemList.Cell_Empty.get(1))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.cell, Materials.Silane, 1), ItemList.Cell_Empty.get(3))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconSG, 1),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 4))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // S + 2Cl = SCl2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 8),
                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Chlorine, 16))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.SulfurDichloride, 8),
                ItemList.Cell_Empty.get(8))
            .duration(40 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 8), ItemList.Cell_Empty.get(8))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.cell, MaterialsKevlar.SulfurDichloride, 8))
            .fluidInputs(Materials.Chlorine.getGas(16_000))
            .duration(40 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // C6H6 + C3H6 = C9H12

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Propene.getCells(8), Materials.PhosphoricAcid.getCells(1))
            .itemOutputs(Materials.Empty.getCells(9))
            .fluidInputs(Materials.Benzene.getFluid(8_000))
            .fluidOutputs(Materials.Cumene.getFluid(8_000))
            .duration(1 * MINUTES + 36 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PhosphoricAcid.getCells(1), Materials.Benzene.getCells(8))
            .itemOutputs(Materials.Empty.getCells(9))
            .fluidInputs(Materials.Propene.getGas(8_000))
            .fluidOutputs(Materials.Cumene.getFluid(8_000))
            .duration(1 * MINUTES + 36 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), Materials.Propene.getCells(1))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidInputs(Materials.PhosphoricAcid.getFluid(125))
            .fluidOutputs(Materials.Cumene.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // C3H6O + 2C6H6O =HCl= C15H16O2 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Acetone.getCells(1), Materials.Phenol.getCells(2))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.BisphenolA.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), Materials.Acetone.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Phenol.getFluid(2_000))
            .fluidOutputs(Materials.BisphenolA.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phenol.getCells(2), Materials.HydrochloricAcid.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.Acetone.getFluid(1_000))
            .fluidOutputs(Materials.BisphenolA.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // N + 3H = NH3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Nitrogen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(3_000))
            .fluidOutputs(Materials.Ammonia.getGas(1_000))
            .duration(16 * SECONDS)
            .eut(TierEU.HV * 3 / 4)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(3), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(3))
            .fluidInputs(Materials.Nitrogen.getGas(1_000))
            .fluidOutputs(Materials.Ammonia.getGas(1_000))
            .duration(16 * SECONDS)
            .eut(TierEU.HV * 3 / 4)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Nitrogen.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Ammonia.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(3_000))
            .duration(16 * SECONDS)
            .eut(TierEU.HV * 3 / 4)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Hydrogen.getCells(3), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.Ammonia.getCells(1), Materials.Empty.getCells(2))
            .fluidInputs(Materials.Nitrogen.getGas(1_000))
            .duration(16 * SECONDS)
            .eut(TierEU.HV * 3 / 4)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.Water.getCells(2))
            .fluidInputs(Materials.Methanol.getFluid(2_000))
            .fluidOutputs(Materials.Dimethylamine.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ammonia.getCells(4), Materials.Empty.getCells(2))
            .itemOutputs(Materials.Water.getCells(6))
            .fluidInputs(Materials.Oxygen.getGas(10_000))
            .fluidOutputs(Materials.NitricOxide.getGas(4_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.NitricAcid.getCells(2))
            .fluidInputs(Materials.NitrogenDioxide.getGas(3_000))
            .fluidOutputs(Materials.NitricOxide.getGas(1_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // 2NO2 + O + H2O = 2HNO3

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.NitrogenDioxide.getCells(2), Materials.Oxygen.getCells(1))
            .itemOutputs(Materials.Empty.getCells(3))
            .fluidInputs(Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), Materials.Water.getCells(1))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidInputs(Materials.NitrogenDioxide.getGas(2_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Water.getCells(1), Materials.NitrogenDioxide.getCells(2))
            .itemOutputs(Materials.Empty.getCells(3))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(2_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Sulfur.getDust(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.HydricSulfide.getCells(1))
            .fluidInputs(Materials.Hydrogen.getGas(2_000))
            .duration(3 * SECONDS)
            .eut(8)
            .addTo(chemicalReactorRecipes);

        // C2H4 + HCl + O = C2H3Cl + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethylene.getCells(1), Materials.HydrochloricAcid.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.VinylChloride.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.HydrochloricAcid.getCells(1), Materials.Oxygen.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Ethylene.getGas(1_000))
            .fluidOutputs(Materials.VinylChloride.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), Materials.Ethylene.getCells(1))
            .itemOutputs(Materials.Water.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.VinylChloride.getGas(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Butadiene.getCells(1), ItemList.Cell_Air.get(5))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(9), Materials.Empty.getCells(6))
            .fluidInputs(Materials.Styrene.getFluid(350))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Butadiene.getCells(1), Materials.Oxygen.getCells(5))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(13), Materials.Empty.getCells(6))
            .fluidInputs(Materials.Styrene.getFluid(350))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Styrene.getCells(1), ItemList.Cell_Air.get(15))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(27), Materials.Empty.getCells(16))
            .fluidInputs(Materials.Butadiene.getGas(3_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Styrene.getCells(1), Materials.Oxygen.getCells(15))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(41), Materials.Empty.getCells(16))
            .fluidInputs(Materials.Butadiene.getGas(3_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Styrene.getCells(1), Materials.Butadiene.getCells(3))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(27), Materials.Empty.getCells(4))
            .fluidInputs(Materials.Air.getGas(15_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Styrene.getCells(1), Materials.Butadiene.getCells(3))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(41), Materials.Empty.getCells(4))
            .fluidInputs(Materials.Oxygen.getGas(15_000))
            .duration(24 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Benzene.getCells(1), Materials.Empty.getCells(1))
            .itemOutputs(Materials.HydrochloricAcid.getCells(2))
            .fluidInputs(Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.Dichlorobenzene.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Glycerol.getCells(1), Materials.Empty.getCells(2))
            .itemOutputs(Materials.DilutedSulfuricAcid.getCells(3))
            .fluidInputs(Materials.NitrationMixture.getFluid(6_000))
            .fluidOutputs(Materials.Glyceryl.getFluid(1_000))
            .duration(9 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(12), Materials.Empty.getCells(4))
            .itemOutputs(Materials.Salt.getDust(8), Materials.Phenol.getCells(4))
            .fluidInputs(Materials.Chlorobenzene.getFluid(4_000))
            .duration(48 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(12), Materials.Chlorobenzene.getCells(4))
            .itemOutputs(Materials.Salt.getDust(8), Materials.Phenol.getCells(4))
            .duration(48 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // Recipes for gasoline
        // 2N + O = N2O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Nitrogen.getCells(2), Materials.Oxygen.getCells(1))
            .itemOutputs(Materials.NitrousOxide.getCells(1), Materials.Empty.getCells(2))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Nitrogen.getCells(2), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(2))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.NitrousOxide.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Nitrogen.getCells(2), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.NitrousOxide.getCells(1), Materials.Empty.getCells(1))
            .fluidInputs(Materials.Oxygen.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.Empty.getCells(1))
            .fluidInputs(Materials.Nitrogen.getGas(2_000))
            .fluidOutputs(Materials.NitrousOxide.getGas(1_000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Oxygen.getCells(1), GTUtility.getIntegratedCircuit(11))
            .itemOutputs(Materials.NitrousOxide.getCells(1))
            .fluidInputs(Materials.Nitrogen.getGas(2_000))
            .duration(10 * SECONDS)
            .eut(30)
            .addTo(chemicalReactorRecipes);

        // C2H6O + C4H8 = C6H14O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Ethanol.getCells(1), Materials.Butene.getCells(1))
            .itemOutputs(Materials.AntiKnock.getCells(1), Materials.Empty.getCells(1))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        // Potassium Dichromate
        // 2KNO3 + 2CrO3 = K2Cr2O7 + 2NO + 3O

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Saltpeter.getDust(10), Materials.ChromiumTrioxide.getDust(8))
            .itemOutputs(Materials.Potassiumdichromate.getDust(11))
            .fluidOutputs(Materials.NitricOxide.getGas(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PotassiumNitrade.getDust(10), Materials.ChromiumTrioxide.getDust(8))
            .itemOutputs(Materials.Potassiumdichromate.getDust(11))
            .fluidOutputs(Materials.NitricOxide.getGas(2_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(chemicalReactorRecipes);
    }

    public void multiblockOnly() {

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(22))
            .fluidInputs(Materials.Hydrogen.getGas(16_000), Materials.Oxygen.getGas(8_000))
            .fluidOutputs(GTModHandler.getDistilledWater(8_000))
            .duration(4 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.PotassiumNitrade.getDust(10), Materials.ChromiumTrioxide.getDust(8))
            .itemOutputs(Materials.Potassiumdichromate.getDust(11))
            .fluidOutputs(Materials.NitricOxide.getGas(2_000), Materials.Oxygen.getGas(3_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Saltpeter.getDust(10), Materials.ChromiumTrioxide.getDust(8))
            .itemOutputs(Materials.Potassiumdichromate.getDust(11))
            .fluidOutputs(Materials.NitricOxide.getGas(2_000), Materials.Oxygen.getGas(3_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // Potassium Dichromate shortcut
        // 2 Cr + 6O + 10 Saltpeter/Potassium Dichromate = 10 K2Cr2O7 + 2NO + 3O

        GTValues.RA.stdBuilder()
            .itemInputs(
                Materials.PotassiumNitrade.getDust(64),
                Materials.PotassiumNitrade.getDust(64),
                Materials.PotassiumNitrade.getDust(32),
                Materials.Chrome.getDust(2 * 16),
                GTUtility.getIntegratedCircuit(11))
            .itemOutputs(
                Materials.Potassiumdichromate.getDust(64),
                Materials.Potassiumdichromate.getDust(64),
                Materials.Potassiumdichromate.getDust(48))
            .fluidInputs(Materials.Oxygen.getGas(96_000))
            .fluidOutputs(Materials.NitricOxide.getGas(32_000), Materials.Oxygen.getGas(48_000))
            .duration(2 * MINUTES + 8 * SECONDS)
            .eut((int) GTValues.VP[7])
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                Materials.Saltpeter.getDust(64),
                Materials.Saltpeter.getDust(64),
                Materials.Saltpeter.getDust(32),
                Materials.Chrome.getDust(2 * 16),
                GTUtility.getIntegratedCircuit(11))
            .itemOutputs(
                Materials.Potassiumdichromate.getDust(64),
                Materials.Potassiumdichromate.getDust(64),
                Materials.Potassiumdichromate.getDust(48))
            .fluidInputs(Materials.Oxygen.getGas(96_000))
            .fluidOutputs(Materials.NitricOxide.getGas(32_000), Materials.Oxygen.getGas(48_000))
            .duration(2 * MINUTES + 8 * SECONDS)
            .eut((int) GTValues.VP[7])
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Benzene.getFluid(1_000), Materials.Methane.getGas(2_000))
            .fluidOutputs(Materials.Dimethylbenzene.getFluid(1_000), Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Galena, 3),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Sphalerite, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(4_000))
            .fluidOutputs(new FluidStack(ItemList.sIndiumConcentrate, 8_000))
            .duration(3 * SECONDS)
            .eut(150)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Galena, 27),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Sphalerite, 9))
            .fluidInputs(Materials.SulfuricAcid.getFluid(36_000))
            .fluidOutputs(new FluidStack(ItemList.sIndiumConcentrate, 72_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Pentlandite, 9))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.PlatinumGroupSludge, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(9_000))
            .fluidOutputs(new FluidStack(ItemList.sNickelSulfate, 18_000))
            .duration(1 * SECONDS + 5 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Chalcopyrite, 9))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.PlatinumGroupSludge, 1))
            .fluidInputs(Materials.SulfuricAcid.getFluid(9_000))
            .fluidOutputs(new FluidStack(ItemList.sBlueVitriol, 18_000))
            .duration(1 * SECONDS + 5 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Plutonium, 64),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Uranium, 1),
                GTUtility.getIntegratedCircuit(8))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Plutonium, 64))
            .fluidInputs(Materials.Air.getGas(8_000))
            .fluidOutputs(Materials.Radon.getGas(800))
            .duration(1 * MINUTES + 15 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // 3SiO2 + 4Al = 3Si + 2Al2O3

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 4))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 10))
            .duration(10 * TICKS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        // 10Si + 30HCl -> 0.3 SiH2Cl2 + 9 HSiCl3 + 0.3 SiCl4 + 0.2 Si2Cl6 + 20.4H

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 10))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(30_000))
            .fluidOutputs(
                Materials.Trichlorosilane.getFluid(9_000),
                Materials.SiliconTetrachloride.getFluid(300),
                Materials.Hexachlorodisilane.getFluid(200),
                Materials.Dichlorosilane.getGas(300),
                Materials.Hydrogen.getGas(20_400))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // 2CO + 2C3H6 + 4H =RhHCO(P(C6H5)3)3= C4H8O + C4H8O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(4), MaterialsKevlar.OrganorhodiumCatalyst.getDustTiny(1))
            .fluidInputs(
                Materials.Hydrogen.getGas(4_000),
                Materials.Propene.getGas(2_000),
                Materials.CarbonMonoxide.getGas(2_000))
            .fluidOutputs(
                MaterialsKevlar.Butyraldehyde.getFluid(1_000),
                MaterialsKevlar.Isobutyraldehyde.getFluid(1_000))
            .duration(15 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), MaterialsKevlar.OrganorhodiumCatalyst.getDust(1))
            .fluidInputs(
                Materials.Hydrogen.getGas(36_000),
                Materials.Propene.getGas(18_000),
                Materials.CarbonMonoxide.getGas(18_000))
            .fluidOutputs(
                MaterialsKevlar.Butyraldehyde.getFluid(9_000),
                MaterialsKevlar.Isobutyraldehyde.getFluid(9_000))
            .duration(1 * MINUTES + 40 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // C2H4 + O =Al2O3,Ag= C2H4O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silver, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 1))
            .fluidInputs(Materials.Ethylene.getGas(1_000), Materials.Oxygen.getGas(1_000))
            .fluidOutputs(MaterialsKevlar.EthyleneOxide.getGas(1_000))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silver, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 9))
            .fluidInputs(Materials.Ethylene.getGas(9_000), Materials.Oxygen.getGas(9_000))
            .fluidOutputs(MaterialsKevlar.EthyleneOxide.getGas(9_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                MaterialsKevlar.EthyleneOxide.getGas(1_000),
                Materials.Dimethyldichlorosilane.getFluid(4_000),
                Materials.Water.getFluid(5_000))
            .fluidOutputs(MaterialsKevlar.SiliconOil.getFluid(5_000))
            .duration(15 * TICKS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(8))
            .fluidInputs(
                MaterialsKevlar.EthyleneOxide.getGas(9_000),
                Materials.Dimethyldichlorosilane.getFluid(36_000),
                Materials.Water.getFluid(45_000))
            .fluidOutputs(MaterialsKevlar.SiliconOil.getFluid(45_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // NH3 + CH4O =SiO2,Al2O3= CH5N + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 1))
            .fluidInputs(Materials.Methanol.getFluid(1_000), Materials.Ammonia.getGas(1_000))
            .fluidOutputs(MaterialsKevlar.Methylamine.getGas(1_000), Materials.Water.getFluid(1_000))
            .duration(1 * MINUTES + 15 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.KevlarCatalyst, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.Pentaerythritol, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.DiphenylmethaneDiisocyanate, 5))
            .fluidInputs(MaterialsKevlar.Ethyleneglycol.getFluid(4_000), MaterialsKevlar.SiliconOil.getFluid(1_000))
            .fluidOutputs(MaterialsKevlar.PolyurethaneResin.getFluid(1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.KevlarCatalyst, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.Pentaerythritol, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.DiphenylmethaneDiisocyanate, 45))
            .fluidInputs(MaterialsKevlar.Ethyleneglycol.getFluid(36_000), MaterialsKevlar.SiliconOil.getFluid(9_000))
            .fluidOutputs(MaterialsKevlar.PolyurethaneResin.getFluid(9_000))
            .duration(1 * MINUTES + 15 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        // 3NH3 + 6CH4O =Al2O3,SiO2= CH5N + C2H7N + C3H9N + 6H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 1))
            .fluidInputs(Materials.Methanol.getFluid(6_000), Materials.Ammonia.getGas(3_000))
            .fluidOutputs(
                MaterialsKevlar.Methylamine.getGas(1_000),
                Materials.Dimethylamine.getGas(1_000),
                MaterialsKevlar.Trimethylamine.getGas(1_000),
                Materials.Water.getFluid(6_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminiumoxide, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 9))
            .fluidInputs(Materials.Methanol.getFluid(54_000), Materials.Ammonia.getGas(27_000))
            .fluidOutputs(
                MaterialsKevlar.Methylamine.getGas(9_000),
                Materials.Dimethylamine.getGas(9_000),
                MaterialsKevlar.Trimethylamine.getGas(9_000),
                Materials.Water.getFluid(54_000))
            .duration(2 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // 18SOCl2 + 5C10H10O4 + 6CO2 = 7C8H4Cl2O2 + 22HCl + 18SO2

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.TerephthaloylChloride, 64),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.TerephthaloylChloride, 48))
            .fluidInputs(
                MaterialsKevlar.ThionylChloride.getFluid(18_000),
                MaterialsKevlar.DimethylTerephthalate.getFluid(5_000),
                Materials.CarbonDioxide.getGas(6_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(22_000), Materials.SulfurDioxide.getGas(18_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // 2CH4O + C8H6O4 =H2SO4= C10H10O4 + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                MaterialsKevlar.TerephthalicAcid.getFluid(1_000),
                Materials.Methanol.getFluid(2_000),
                Materials.SulfuricAcid.getFluid(2_000))
            .fluidOutputs(
                MaterialsKevlar.DimethylTerephthalate.getFluid(1_000),
                Materials.DilutedSulfuricAcid.getFluid(2_000))
            .duration(12 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(
                MaterialsKevlar.TerephthalicAcid.getFluid(9_000),
                Materials.Methanol.getFluid(18_000),
                Materials.SulfuricAcid.getFluid(18_000))
            .fluidOutputs(
                MaterialsKevlar.DimethylTerephthalate.getFluid(9_000),
                Materials.DilutedSulfuricAcid.getFluid(18_000))
            .duration(1 * MINUTES + 27 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(Materials.Benzene.getFluid(1_000), Materials.Methane.getGas(2_000))
            .fluidOutputs(MaterialsKevlar.IIIDimethylbenzene.getFluid(1_000), Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(3))
            .fluidInputs(Materials.Benzene.getFluid(1_000), Materials.Methane.getGas(2_000))
            .fluidOutputs(MaterialsKevlar.IVDimethylbenzene.getFluid(1_000), Materials.Hydrogen.getGas(4_000))
            .duration(3 * MINUTES + 20 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.CobaltIIHydroxide, 45))
            .itemOutputs(
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(49))
            .fluidInputs(MaterialsKevlar.NaphthenicAcid.getFluid(10_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.CobaltIIAcetate, 64),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.CobaltIIAcetate, 64),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.CobaltIIAcetate, 7))
            .itemOutputs(
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(64),
                MaterialsKevlar.CobaltIINaphthenate.getDust(49))
            .fluidInputs(MaterialsKevlar.NaphthenicAcid.getFluid(10_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(15_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // PCl3 + 3C6H5Cl + 6Na = 6NaCl + C18H15P

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 6))
            .itemOutputs(MaterialsKevlar.Triphenylphosphene.getDust(34), Materials.Salt.getDust(12))
            .fluidInputs(MaterialsKevlar.PhosphorusTrichloride.getFluid(1_000), Materials.Chlorobenzene.getFluid(3_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // 4NaH + C3H9BO3 = NaBH4 + 3CH3ONa

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1), MaterialsKevlar.SodiumHydride.getDust(8))
            .itemOutputs(MaterialsKevlar.SodiumBorohydride.getDust(6), MaterialsKevlar.SodiumMethoxide.getDust(18))
            .fluidInputs(MaterialsKevlar.TrimethylBorate.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), MaterialsKevlar.SodiumHydride.getDust(64))
            .itemOutputs(
                MaterialsKevlar.SodiumBorohydride.getDust(48),
                MaterialsKevlar.SodiumMethoxide.getDust(64),
                MaterialsKevlar.SodiumMethoxide.getDust(64),
                MaterialsKevlar.SodiumMethoxide.getDust(16))
            .fluidInputs(MaterialsKevlar.TrimethylBorate.getFluid(8_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // 2CH3COOH = CH3COCH3 + CO2 + H

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.copyAmount(0, Materials.Calcium.getDust(1)), GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.AceticAcid.getFluid(2_000))
            .fluidOutputs(
                Materials.Acetone.getFluid(1_000),
                Materials.CarbonDioxide.getGas(1_000),
                Materials.Water.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // C + 4H + O = CH3OH

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(1), GTUtility.getIntegratedCircuit(23))
            .fluidInputs(Materials.Hydrogen.getGas(4_000), Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.Methanol.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(96)
            .addTo(multiblockChemicalReactorRecipes);

        // This recipe collides with one for Vinyl Chloride
        // 2C + 4H + 2O = CH3COOH

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Carbon.getDust(2), GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Hydrogen.getGas(4_000), Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // 2CO + 4H = CH3COOH

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.CarbonMonoxide.getGas(2_000), Materials.Hydrogen.getGas(4_000))
            .fluidOutputs(Materials.AceticAcid.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(8))
            .fluidInputs(Materials.Hydrogen.getGas(9_000), Materials.Chlorine.getGas(9_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(9_000))
            .duration(7 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                Materials.Chlorine.getGas(10_000),
                Materials.Water.getFluid(10_000),
                Materials.Mercury.getFluid(1_000))
            .fluidOutputs(Materials.HypochlorousAcid.getFluid(10_000), Materials.Hydrogen.getGas(10_000))
            .duration(30 * SECONDS)
            .eut(8)
            .addTo(multiblockChemicalReactorRecipes);

        // H2O + 4Cl + C3H6 + NaOH = C3H5ClO + NaCl·H2O + 2HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(3), GTUtility.getIntegratedCircuit(23))
            .fluidInputs(
                Materials.Propene.getGas(1_000),
                Materials.Chlorine.getGas(4_000),
                Materials.Water.getFluid(1_000))
            .fluidOutputs(
                Materials.Epichlorohydrin.getFluid(1_000),
                Materials.SaltWater.getFluid(1_000),
                Materials.HydrochloricAcid.getFluid(2_000))
            .duration(32 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // H2O + 2Cl + C3H6 + NaOH =Hg= C3H5ClO + NaCl·H2O + 2H

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(3), GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Propene.getGas(1_000),
                Materials.Chlorine.getGas(2_000),
                Materials.Water.getFluid(1_000),
                Materials.Mercury.getFluid(100))
            .fluidOutputs(
                Materials.Epichlorohydrin.getFluid(1_000),
                Materials.SaltWater.getFluid(1_000),
                Materials.Hydrogen.getGas(2_000))
            .duration(32 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // HClO + 2Cl + C3H6 + NaOH = C3H5ClO + NaCl·H2O + HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(3), GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Propene.getGas(1_000),
                Materials.Chlorine.getGas(2_000),
                Materials.HypochlorousAcid.getFluid(1_000))
            .fluidOutputs(
                Materials.Epichlorohydrin.getFluid(1_000),
                Materials.SaltWater.getFluid(1_000),
                Materials.HydrochloricAcid.getFluid(1_000))
            .duration(32 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Apatite.getDust(9))
            .itemOutputs(Materials.Gypsum.getDust(40))
            .fluidInputs(Materials.SulfuricAcid.getFluid(5_000), Materials.Water.getFluid(10_000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(3_000), Materials.HydrochloricAcid.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phosphorus.getDust(4), GTUtility.getIntegratedCircuit(1))
            .itemOutputs(Materials.PhosphorousPentoxide.getDust(14))
            .fluidInputs(Materials.Oxygen.getGas(10_000))
            .duration(2 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // 2P + 5O + 3H2O = 2H3PO4

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Phosphorus.getDust(1), GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Oxygen.getGas(2500), Materials.Water.getFluid(1_500))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                Materials.Propene.getGas(8_000),
                Materials.Benzene.getFluid(8_000),
                Materials.PhosphoricAcid.getFluid(1_000))
            .fluidOutputs(Materials.Cumene.getFluid(8_000))
            .duration(1 * MINUTES + 36 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Propene.getGas(1_000),
                Materials.Benzene.getFluid(1_000),
                Materials.PhosphoricAcid.getFluid(100),
                Materials.Oxygen.getGas(2_000))
            .fluidOutputs(Materials.Phenol.getFluid(1_000), Materials.Acetone.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                Materials.Acetone.getFluid(1_000),
                Materials.Phenol.getFluid(2_000),
                Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(Materials.BisphenolA.getFluid(1_000), Materials.Water.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(6), GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Acetone.getFluid(1_000),
                Materials.Phenol.getFluid(2_000),
                Materials.HydrochloricAcid.getFluid(1_000),
                Materials.Epichlorohydrin.getFluid(2_000))
            .fluidOutputs(Materials.Epoxid.getMolten(1_000), Materials.SaltWater.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.Hydrogen.getGas(9_000), Materials.Fluorine.getGas(9_000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(9_000))
            .duration(7 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.HydrofluoricAcid.getFluid(4_000),
                Materials.Methane.getGas(2_000),
                Materials.Chlorine.getGas(12_000))
            .fluidOutputs(Materials.Tetrafluoroethylene.getGas(1_000), Materials.HydrochloricAcid.getFluid(12_000))
            .duration(27 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Silicon.getDust(1), GTUtility.getIntegratedCircuit(24))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3))
            .fluidInputs(
                Materials.Methane.getGas(2_000),
                Materials.Chlorine.getGas(4_000),
                Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(2_000), Materials.DilutedHydrochloricAcid.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(96)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Silicon.getDust(1), GTUtility.getIntegratedCircuit(24))
            .itemOutputs(Materials.Polydimethylsiloxane.getDust(3))
            .fluidInputs(Materials.Methanol.getFluid(2_000), Materials.HydrochloricAcid.getFluid(2_000))
            .fluidOutputs(Materials.DilutedHydrochloricAcid.getFluid(2_000))
            .duration(24 * SECONDS)
            .eut(96)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(Materials.Nitrogen.getGas(1_000), Materials.Hydrogen.getGas(3_000))
            .fluidOutputs(Materials.Ammonia.getGas(1_000))
            .duration(16 * SECONDS)
            .eut(TierEU.HV * 3 / 4)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Nitrogen.getGas(10_000), Materials.Hydrogen.getGas(30_000))
            .fluidOutputs(Materials.Ammonia.getGas(10_000))
            .duration(2 * MINUTES + 40 * SECONDS)
            .eut(TierEU.HV * 3 / 4)
            .addTo(multiblockChemicalReactorRecipes);

        // 2NH3 + 7O = N2O4 + 3H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(23))
            .fluidInputs(Materials.Ammonia.getGas(2_000), Materials.Oxygen.getGas(7_000))
            .fluidOutputs(Materials.DinitrogenTetroxide.getGas(1_000), Materials.Water.getFluid(3_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // 7O + 6H + 2N = N2O4 + 3H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(23))
            .fluidInputs(
                Materials.Nitrogen.getGas(2_000),
                Materials.Hydrogen.getGas(6_000),
                Materials.Oxygen.getGas(7_000))
            .fluidOutputs(Materials.DinitrogenTetroxide.getGas(1_000), Materials.Water.getFluid(3_000))
            .duration(55 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.Oxygen.getGas(100_000), Materials.Ammonia.getGas(36_000))
            .fluidOutputs(Materials.NitricOxide.getGas(36_000), Materials.Water.getFluid(54_000))
            .duration(8 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(8))
            .fluidInputs(Materials.Oxygen.getGas(100_000), Materials.Ammonia.getGas(36_000))
            .fluidOutputs(Materials.NitricOxide.getGas(36_000))
            .duration(8 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.NitricOxide.getGas(9_000), Materials.Oxygen.getGas(9_000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(9_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.NitrogenDioxide.getGas(27_000), Materials.Water.getFluid(9_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(18_000), Materials.NitricOxide.getGas(9_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(21))
            .fluidInputs(
                Materials.Hydrogen.getGas(3_000),
                Materials.Nitrogen.getGas(1_000),
                Materials.Oxygen.getGas(4_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(1_000), Materials.Water.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Ammonia.getGas(1_000), Materials.Oxygen.getGas(4_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(1_000), Materials.Water.getFluid(1_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.NitrogenDioxide.getGas(2_000),
                Materials.Oxygen.getGas(1_000),
                Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.NitricAcid.getFluid(2_000))
            .duration(16 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), Materials.Sulfur.getDust(9))
            .fluidInputs(Materials.Hydrogen.getGas(18_000))
            .fluidOutputs(Materials.HydricSulfide.getGas(9_000))
            .duration(4 * TICKS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), Materials.Sulfur.getDust(9))
            .fluidInputs(Materials.Oxygen.getGas(18_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(9_000))
            .duration(4 * TICKS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.HydricSulfide.getGas(9_000), Materials.Oxygen.getGas(27_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(9_000), Materials.Water.getFluid(9_000))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(8))
            .fluidInputs(Materials.HydricSulfide.getGas(9_000), Materials.Oxygen.getGas(27_000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(9_000))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(7))
            .itemOutputs(Materials.Sulfur.getDust(27))
            .fluidInputs(Materials.SulfurDioxide.getGas(9_000), Materials.HydricSulfide.getGas(18_000))
            .duration(3 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.SulfurTrioxide.getGas(9_000), Materials.Water.getFluid(9_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(9_000))
            .duration(13 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // S + O3 + H2O = H2SO4

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24), Materials.Sulfur.getDust(1))
            .fluidInputs(Materials.Oxygen.getGas(3_000), Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(7), Materials.Sulfur.getDust(9))
            .fluidInputs(Materials.Oxygen.getGas(27_000), Materials.Water.getFluid(9_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(9_000))
            .duration(13 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // H2S + O4 = H2SO4

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.HydricSulfide.getGas(1_000), Materials.Oxygen.getGas(4_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(1_000))
            .duration(24 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // SO2 + O + H2O = H2SO4

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.SulfurDioxide.getGas(1_000),
                Materials.Oxygen.getGas(1_000),
                Materials.Water.getFluid(1_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(
                Materials.SulfurDioxide.getGas(9_000),
                Materials.Oxygen.getGas(9_000),
                Materials.Water.getFluid(9_000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(9_000))
            .duration(7 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                Materials.HydrochloricAcid.getFluid(1_000),
                Materials.Ethylene.getGas(1_000),
                Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.VinylChloride.getGas(1_000), Materials.Water.getFluid(1_000))
            .duration(8 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Chlorine.getGas(2_000),
                Materials.Ethylene.getGas(2_000),
                Materials.Oxygen.getGas(1_000))
            .fluidOutputs(Materials.VinylChloride.getGas(2_000), Materials.Water.getFluid(1_000))
            .duration(12 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.RawRubber.getDust(18))
            .fluidInputs(
                Materials.Isoprene.getFluid(1728),
                Materials.Air.getGas(6_000),
                Materials.Titaniumtetrachloride.getFluid(80))
            .duration(32 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .itemOutputs(Materials.RawRubber.getDust(24))
            .fluidInputs(
                Materials.Isoprene.getFluid(1728),
                Materials.Oxygen.getGas(6_000),
                Materials.Titaniumtetrachloride.getFluid(80))
            .duration(32 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(3))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(1))
            .fluidInputs(Materials.Styrene.getFluid(36), Materials.Butadiene.getGas(108), Materials.Air.getGas(2_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(3))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(3))
            .fluidInputs(
                Materials.Styrene.getFluid(36),
                Materials.Butadiene.getGas(108),
                Materials.Oxygen.getGas(2_000))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(4))
            .itemOutputs(
                Materials.RawStyreneButadieneRubber.getDust(22),
                Materials.RawStyreneButadieneRubber.getDustSmall(2))
            .fluidInputs(
                Materials.Styrene.getFluid(540),
                Materials.Butadiene.getGas(1620),
                Materials.Titaniumtetrachloride.getFluid(100),
                Materials.Air.getGas(15_000))
            .duration(32 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(4))
            .itemOutputs(Materials.RawStyreneButadieneRubber.getDust(30))
            .fluidInputs(
                Materials.Styrene.getFluid(540),
                Materials.Butadiene.getGas(1620),
                Materials.Titaniumtetrachloride.getFluid(100),
                Materials.Oxygen.getGas(7_500))
            .duration(32 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), Materials.Salt.getDust(18))
            .itemOutputs(Materials.SodiumBisulfate.getDust(63))
            .fluidInputs(Materials.SulfuricAcid.getFluid(9_000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(9_000))
            .duration(6 * SECONDS + 15 * TICKS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), Materials.SodiumHydroxide.getDust(27))
            .itemOutputs(Materials.SodiumBisulfate.getDust(63))
            .fluidInputs(Materials.SulfuricAcid.getFluid(9_000))
            .fluidOutputs(Materials.Water.getFluid(9_000))
            .duration(6 * SECONDS + 15 * TICKS)
            .eut(TierEU.RECIPE_MV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Benzene.getFluid(1_000),
                Materials.Chlorine.getGas(2_000),
                Materials.Water.getFluid(1_000))
            .fluidOutputs(
                Materials.Phenol.getFluid(1_000),
                Materials.HydrochloricAcid.getFluid(1_000),
                Materials.DilutedHydrochloricAcid.getFluid(1_000))
            .duration(28 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        // C6H6 + 2Cl + NaOH = C6H6O + NaCl + HCl

        GTValues.RA.stdBuilder()
            .itemInputs(Materials.SodiumHydroxide.getDust(6), GTUtility.getIntegratedCircuit(24))
            .itemOutputs(Materials.Salt.getDust(4))
            .fluidInputs(Materials.Benzene.getFluid(2_000), Materials.Chlorine.getGas(4_000))
            .fluidOutputs(Materials.Phenol.getFluid(2_000), Materials.HydrochloricAcid.getFluid(2_000))
            .duration(56 * SECONDS)
            .eut(30)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.LightFuel.getFluid(20_000), Materials.HeavyFuel.getFluid(4_000))
            .fluidOutputs(Materials.Fuel.getFluid(24_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Fuel.getFluid(10_000), Materials.Tetranitromethane.getFluid(200))
            .fluidOutputs(Materials.NitroFuel.getFluid(10_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.BioDiesel.getFluid(10_000), Materials.Tetranitromethane.getFluid(400))
            .fluidOutputs(Materials.NitroFuel.getFluid(9_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // CH4 + 2H2O = CO2 + 8H

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(11))
            .fluidInputs(Materials.Methane.getGas(5_000), GTModHandler.getDistilledWater(10_000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(5_000), Materials.Hydrogen.getGas(40_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // CH4 + H2O = CO + 6H

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(12))
            .fluidInputs(Materials.Methane.getGas(5_000), GTModHandler.getDistilledWater(5_000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(5_000), Materials.Hydrogen.getGas(30_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Nitrogen.getGas(20_000), Materials.Oxygen.getGas(10_000))
            .fluidOutputs(Materials.NitrousOxide.getGas(10_000))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.Naphtha.getFluid(16_000),
                Materials.Gas.getGas(2_000),
                Materials.Methanol.getFluid(1_000),
                Materials.Acetone.getFluid(1_000))
            .fluidOutputs(Materials.GasolineRaw.getFluid(20_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.GasolineRaw.getFluid(10_000), Materials.Toluene.getFluid(1_000))
            .fluidOutputs(Materials.GasolineRegular.getFluid(11_000))
            .duration(10 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(
                Materials.GasolineRegular.getFluid(20_000),
                Materials.Octane.getFluid(2_000),
                Materials.NitrousOxide.getGas(6_000),
                Materials.Toluene.getFluid(1_000),
                Materials.AntiKnock.getFluid(3_000))
            .fluidOutputs(Materials.GasolinePremium.getFluid(32_000))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C2H6O + C4H8 = C6H14O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Ethanol.getFluid(1_000), Materials.Butene.getGas(1_000))
            .fluidOutputs(Materials.AntiKnock.getFluid(1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // CH4O + C4H8 = C5H12O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Methanol.getFluid(1_000), Materials.Butene.getGas(1_000))
            .fluidOutputs(Materials.MTBEMixture.getGas(1_000))
            .duration(20 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(24))
            .fluidInputs(Materials.Methanol.getFluid(1_000), Materials.Butane.getGas(1_000))
            .fluidOutputs(Materials.MTBEMixtureAlt.getGas(1_000))
            .duration(20 * TICKS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // CH2O + 2C6H7N + HCl = C13H14N2(HCl) + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                new FluidStack(FluidRegistry.getFluid("fluid.formaldehyde"), 1_000),
                new FluidStack(FluidRegistry.getFluid("aniline"), 2_000),
                Materials.HydrochloricAcid.getFluid(1_000))
            .fluidOutputs(MaterialsKevlar.DiaminodiphenylmethanMixture.getFluid(1_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C6H5NO2 + 6H =Pd= C6H7N + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Palladium, 1))
            .fluidInputs(
                new FluidStack(FluidRegistry.getFluid("nitrobenzene"), 9000),
                Materials.Hydrogen.getGas(54_000))
            .fluidOutputs(Materials.Water.getFluid(18_000), new FluidStack(FluidRegistry.getFluid("aniline"), 9_000))
            .duration(45 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C6H6 + HNO3 =H2SO4= C6H5NO2 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                Materials.Benzene.getFluid(5_000),
                Materials.SulfuricAcid.getFluid(3_000),
                Materials.NitricAcid.getFluid(5_000),
                GTModHandler.getDistilledWater(10_000))
            .fluidOutputs(
                new FluidStack(FluidRegistry.getFluid("nitrobenzene"), 5_000),
                Materials.DilutedSulfuricAcid.getFluid(3_000))
            .duration(6 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(multiblockChemicalReactorRecipes);

        // C13H14N2(HCl) + 2COCl2 = C15H10N2O2(5HCl)

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                MaterialsKevlar.DiaminodiphenylmethanMixture.getFluid(1_000),
                new FluidStack(FluidRegistry.getFluid("phosgene"), 2_000))
            .fluidOutputs(MaterialsKevlar.DiphenylmethaneDiisocyanateMixture.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Palladium, 1))
            .fluidInputs(MaterialsKevlar.Butyraldehyde.getFluid(9_000), Materials.Hydrogen.getGas(18_000))
            .fluidOutputs(new FluidStack(FluidRegistry.getFluid("butanol"), 9_000))
            .duration(4 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1), GTOreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.KevlarCatalyst, 1))
            .fluidInputs(
                new FluidStack(FluidRegistry.getFluid("butanol"), 2_000),
                new FluidStack(FluidRegistry.getFluid("propionicacid"), 1_000),
                Materials.IronIIIChloride.getFluid(100))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9), GTOreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 9))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.KevlarCatalyst, 9))
            .fluidInputs(
                new FluidStack(FluidRegistry.getFluid("butanol"), 18_000),
                new FluidStack(FluidRegistry.getFluid("propionicacid"), 9_000),
                Materials.IronIIIChloride.getFluid(900))
            .duration(3 * MINUTES + 45 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C2H4 + CO + H2O =C4NiO= C3H6O2

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                Materials.Ethylene.getGas(1_000),
                Materials.CarbonMonoxide.getGas(1_000),
                MaterialsKevlar.NickelTetracarbonyl.getFluid(100),
                Materials.Water.getFluid(1_000))
            .fluidOutputs(new FluidStack(FluidRegistry.getFluid("propionicacid"), 1_000))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(
                Materials.Ethylene.getGas(9_000),
                Materials.CarbonMonoxide.getGas(9_000),
                MaterialsKevlar.NickelTetracarbonyl.getFluid(900),
                Materials.Water.getFluid(9_000))
            .fluidOutputs(new FluidStack(FluidRegistry.getFluid("propionicacid"), 9_000))
            .duration(1 * MINUTES + 15 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C6H7N + HNO3 =H2SO4,C4H6O3= C6H6N2O2 + H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                new FluidStack(FluidRegistry.getFluid("aniline"), 1_000),
                new FluidStack(FluidRegistry.getFluid("molten.aceticanhydride"), 100),
                Materials.NitrationMixture.getFluid(2_000))
            .fluidOutputs(MaterialsKevlar.IVNitroaniline.getFluid(1_000), Materials.DilutedSulfuricAcid.getFluid(1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(
                new FluidStack(FluidRegistry.getFluid("aniline"), 9_000),
                new FluidStack(FluidRegistry.getFluid("molten.aceticanhydride"), 900),
                Materials.NitrationMixture.getFluid(18_000))
            .fluidOutputs(MaterialsKevlar.IVNitroaniline.getFluid(9_000), Materials.DilutedSulfuricAcid.getFluid(9_000))
            .duration(1 * MINUTES + 40 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C6H6N2O2 + 6H =Pd,NO2= C6H8N2 + 2H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Palladium, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.ParaPhenylenediamine, 16))
            .fluidInputs(
                Materials.NitrogenDioxide.getGas(100),
                Materials.Hydrogen.getGas(6_000),
                MaterialsKevlar.IVNitroaniline.getFluid(1_000))
            .fluidOutputs(Materials.Water.getFluid(2_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        // C4H10O2 =Cu= C4H6O2 + 4H

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1))
            .fluidInputs(new FluidStack(FluidRegistry.getFluid("1,4-butanediol"), 1_000))
            .fluidOutputs(MaterialsKevlar.GammaButyrolactone.getFluid(1_000), Materials.Hydrogen.getGas(4_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 9))
            .fluidInputs(new FluidStack(FluidRegistry.getFluid("1,4-butanediol"), 9_000))
            .fluidOutputs(MaterialsKevlar.GammaButyrolactone.getFluid(9_000), Materials.Hydrogen.getGas(36_000))
            .duration(35 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // 2CH2O + C2H2 =SiO2,CuO,Bi2O3= C4H6O2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CupricOxide, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.BismuthIIIOxide, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 1))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.IIButinIIVdiol, 12))
            .fluidInputs(
                MaterialsKevlar.Acetylene.getGas(1_000),
                new FluidStack(FluidRegistry.getFluid("fluid.formaldehyde"), 2_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.CupricOxide, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.BismuthIIIOxide, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 9))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.IIButinIIVdiol, 64),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.IIButinIIVdiol, 44))
            .fluidInputs(
                MaterialsKevlar.Acetylene.getGas(9_000),
                new FluidStack(FluidRegistry.getFluid("fluid.formaldehyde"), 18_000))
            .duration(2 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // C4H6O2 + 4H =NiAl= C4H10O2

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.IIButinIIVdiol, 12),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.RaneyNickelActivated, 1))
            .fluidInputs(Materials.Hydrogen.getGas(4_000))
            .fluidOutputs(new FluidStack(FluidRegistry.getFluid("1,4-butanediol"), 1_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                WerkstoffLoader.CalciumChloride.get(OrePrefixes.dust, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.ParaPhenylenediamine, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.TerephthaloylChloride, 9))
            .fluidInputs(MaterialsKevlar.NMethylIIPyrrolidone.getFluid(1_000))
            .fluidOutputs(
                MaterialsKevlar.LiquidCrystalKevlar.getFluid(9_000),
                Materials.DilutedHydrochloricAcid.getFluid(2_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                WerkstoffLoader.CalciumChloride.get(OrePrefixes.dust, 7),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.ParaPhenylenediamine, 63),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.TerephthaloylChloride, 63))
            .fluidInputs(MaterialsKevlar.NMethylIIPyrrolidone.getFluid(7_000))
            .fluidOutputs(
                MaterialsKevlar.LiquidCrystalKevlar.getFluid(63_000),
                Materials.DilutedHydrochloricAcid.getFluid(14_000))
            .duration(2 * MINUTES + 55 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        // Na2B4O7(H2O)10 + 2HCl = 2NaCl + 4H3BO3 + 5H2O

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Borax, 23))
            .itemOutputs(Materials.Salt.getDust(4))
            .fluidInputs(Materials.HydrochloricAcid.getFluid(2_000))
            .fluidOutputs(new FluidStack(FluidRegistry.getFluid("boricacid"), 4_000), Materials.Water.getFluid(5_000))
            .duration(40 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(multiblockChemicalReactorRecipes);

        // H3BO3 + 3CH4O =H2SO4= C3H9BO3 + 3H2O

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(
                Materials.Methanol.getFluid(3_000),
                new FluidStack(FluidRegistry.getFluid("boricacid"), 1_000),
                Materials.SulfuricAcid.getFluid(6_000))
            .fluidOutputs(
                Materials.DilutedSulfuricAcid.getFluid(6_000),
                MaterialsKevlar.TrimethylBorate.getFluid(1_000))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(9))
            .fluidInputs(
                Materials.Methanol.getFluid(27_000),
                new FluidStack(FluidRegistry.getFluid("boricacid"), 9_000),
                Materials.SulfuricAcid.getFluid(54_000))
            .fluidOutputs(
                Materials.DilutedSulfuricAcid.getFluid(54_000),
                MaterialsKevlar.TrimethylBorate.getFluid(9_000))
            .duration(3 * MINUTES + 45 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        // RhCl3 + 3C18H15P + 3NaBH4 + CO = RhC55H46P3O + 3NaCl + 3B + 11H

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.dust, MaterialsKevlar.RhodiumChloride, 4),
                MaterialsKevlar.Triphenylphosphene.getDust(64),
                MaterialsKevlar.Triphenylphosphene.getDust(38),
                MaterialsKevlar.SodiumBorohydride.getDust(18))
            .itemOutputs(
                MaterialsKevlar.OrganorhodiumCatalyst.getDust(64),
                MaterialsKevlar.OrganorhodiumCatalyst.getDust(42),
                Materials.Salt.getDust(6),
                Materials.Boron.getDust(3))
            .fluidInputs(Materials.CarbonMonoxide.getGas(1_000))
            .fluidOutputs(Materials.Hydrogen.getGas(11_000))
            .duration(40 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        // 2NaOH + N2H4 =Mn= 2N + 2H2O + 2NaH

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                Materials.SodiumHydroxide.getDust(6),
                Materials.Manganese.getDustTiny(1))
            .itemOutputs(MaterialsKevlar.SodiumHydride.getDust(4))
            .fluidInputs(new FluidStack(FluidRegistry.getFluid("fluid.hydrazine"), 1_000))
            .fluidOutputs(Materials.Nitrogen.getGas(2_000), Materials.Water.getFluid(2_000))
            .duration(10 * TICKS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                Materials.SodiumHydroxide.getDust(54),
                Materials.Manganese.getDust(1))
            .itemOutputs(MaterialsKevlar.SodiumHydride.getDust(36))
            .fluidInputs(new FluidStack(FluidRegistry.getFluid("fluid.hydrazine"), 9_000))
            .fluidOutputs(Materials.Nitrogen.getGas(18_000), Materials.Water.getFluid(18_000))
            .duration(3 * SECONDS + 10 * TICKS)
            .eut(TierEU.RECIPE_EV)
            .addTo(multiblockChemicalReactorRecipes);

    }
}
