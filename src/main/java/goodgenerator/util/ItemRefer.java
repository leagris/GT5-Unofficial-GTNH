package goodgenerator.util;

import static goodgenerator.loader.Loaders.AMForge;
import static goodgenerator.loader.Loaders.AMGenerator;
import static goodgenerator.loader.Loaders.CT;
import static goodgenerator.loader.Loaders.CompAssline;
import static goodgenerator.loader.Loaders.FRF;
import static goodgenerator.loader.Loaders.FRF_Casings;
import static goodgenerator.loader.Loaders.FRF_Coil_1;
import static goodgenerator.loader.Loaders.FRF_Coil_2;
import static goodgenerator.loader.Loaders.FRF_Coil_3;
import static goodgenerator.loader.Loaders.FRF_Coil_4;
import static goodgenerator.loader.Loaders.Generator_Diesel;
import static goodgenerator.loader.Loaders.LES;
import static goodgenerator.loader.Loaders.LFC;
import static goodgenerator.loader.Loaders.MAR;
import static goodgenerator.loader.Loaders.MAR_Casing;
import static goodgenerator.loader.Loaders.PA;
import static goodgenerator.loader.Loaders.SCTurbine;
import static goodgenerator.loader.Loaders.UCFE;
import static goodgenerator.loader.Loaders.XHE;
import static goodgenerator.loader.Loaders.YFT;
import static goodgenerator.loader.Loaders._null_;
import static goodgenerator.loader.Loaders.advancedFuelRod;
import static goodgenerator.loader.Loaders.advancedRadiationProtectionPlate;
import static goodgenerator.loader.Loaders.aluminumNitride;
import static goodgenerator.loader.Loaders.antimatterAnnihilationMatrix;
import static goodgenerator.loader.Loaders.antimatterContainmentCasing;
import static goodgenerator.loader.Loaders.compactFusionCoil;
import static goodgenerator.loader.Loaders.componentAssemblylineCasing;
import static goodgenerator.loader.Loaders.enrichedNaquadahMass;
import static goodgenerator.loader.Loaders.essentiaCell;
import static goodgenerator.loader.Loaders.essentiaFilterCasing;
import static goodgenerator.loader.Loaders.essentiaOutputHatch;
import static goodgenerator.loader.Loaders.essentiaOutputHatch_ME;
import static goodgenerator.loader.Loaders.fieldRestrictingGlass;
import static goodgenerator.loader.Loaders.fluidCore;
import static goodgenerator.loader.Loaders.gravityStabilizationCasing;
import static goodgenerator.loader.Loaders.highDensityPlutonium;
import static goodgenerator.loader.Loaders.highDensityPlutoniumNugget;
import static goodgenerator.loader.Loaders.highDensityThorium;
import static goodgenerator.loader.Loaders.highDensityThoriumNugget;
import static goodgenerator.loader.Loaders.highDensityUranium;
import static goodgenerator.loader.Loaders.highDensityUraniumNugget;
import static goodgenerator.loader.Loaders.highEnergyMixture;
import static goodgenerator.loader.Loaders.huiCircuit;
import static goodgenerator.loader.Loaders.impreciseUnitCasing;
import static goodgenerator.loader.Loaders.inverter;
import static goodgenerator.loader.Loaders.magicCasing;
import static goodgenerator.loader.Loaders.magneticFluxCasing;
import static goodgenerator.loader.Loaders.microHeater;
import static goodgenerator.loader.Loaders.naquadahMass;
import static goodgenerator.loader.Loaders.naquadriaMass;
import static goodgenerator.loader.Loaders.neutronSource;
import static goodgenerator.loader.Loaders.plasticCase;
import static goodgenerator.loader.Loaders.preciseUnitCasing;
import static goodgenerator.loader.Loaders.pressureResistantWalls;
import static goodgenerator.loader.Loaders.protomatterActivationCoil;
import static goodgenerator.loader.Loaders.quartzCrystalResonator;
import static goodgenerator.loader.Loaders.quartzWafer;
import static goodgenerator.loader.Loaders.radiationProtectionPlate;
import static goodgenerator.loader.Loaders.radiationProtectionSteelFrame;
import static goodgenerator.loader.Loaders.radioactiveWaste;
import static goodgenerator.loader.Loaders.rawAtomicSeparationCatalyst;
import static goodgenerator.loader.Loaders.rawCylinder;
import static goodgenerator.loader.Loaders.saltyRoot;
import static goodgenerator.loader.Loaders.specialCeramics;
import static goodgenerator.loader.Loaders.specialCeramicsPlate;
import static goodgenerator.loader.Loaders.speedingPipe;
import static goodgenerator.loader.Loaders.supercriticalFluidTurbineCasing;
import static goodgenerator.loader.Loaders.titaniumPlatedCylinder;
import static goodgenerator.loader.Loaders.wrappedPlutoniumIngot;
import static goodgenerator.loader.Loaders.wrappedThoriumIngot;
import static goodgenerator.loader.Loaders.wrappedUraniumIngot;
import static goodgenerator.loader.Loaders.yottaFluidTankCasing;
import static goodgenerator.loader.Loaders.yottaFluidTankCell;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import gregtech.api.util.GTUtility;

public final class ItemRefer {

    public static ItemRefer NULL = getItemStack(_null_);

    public static ItemRefer Radiation_Protection_Plate = getItemStack(radiationProtectionPlate);
    public static ItemRefer Wrapped_Uranium_Ingot = getItemStack(wrappedUraniumIngot);
    public static ItemRefer High_Density_Uranium_Nugget = getItemStack(highDensityUraniumNugget);
    public static ItemRefer High_Density_Uranium = getItemStack(highDensityUranium);
    public static ItemRefer Wrapped_Thorium_Ingot = getItemStack(wrappedThoriumIngot);
    public static ItemRefer High_Density_Thorium_Nugget = getItemStack(highDensityThoriumNugget);
    public static ItemRefer High_Density_Thorium = getItemStack(highDensityThorium);
    public static ItemRefer Wrapped_Plutonium_Ingot = getItemStack(wrappedPlutoniumIngot);
    public static ItemRefer High_Density_Plutonium_Nugget = getItemStack(highDensityPlutoniumNugget);
    public static ItemRefer High_Density_Plutonium = getItemStack(highDensityPlutonium);
    public static ItemRefer Raw_Atomic_Separation_Catalyst = getItemStack(rawAtomicSeparationCatalyst);
    public static ItemRefer Advanced_Radiation_Protection_Plate = getItemStack(advancedRadiationProtectionPlate);
    public static ItemRefer Aluminum_Nitride_Dust = getItemStack(aluminumNitride);
    public static ItemRefer Special_Ceramics_Dust = getItemStack(specialCeramics);
    public static ItemRefer Special_Ceramics_Plate = getItemStack(specialCeramicsPlate);
    public static ItemRefer Radioactive_Waste = getItemStack(radioactiveWaste);
    public static ItemRefer Plastic_Case = getItemStack(plasticCase);
    public static ItemRefer Quartz_Wafer = getItemStack(quartzWafer);
    public static ItemRefer Micro_Heater = getItemStack(microHeater);
    public static ItemRefer Quartz_Crystal_Resonator = getItemStack(quartzCrystalResonator);
    public static ItemRefer Inverter = getItemStack(inverter);
    public static ItemRefer Neutron_Source = getItemStack(neutronSource);
    public static ItemRefer Naquadah_Mass = getItemStack(naquadahMass);
    public static ItemRefer Enriched_Naquadah_Mass = getItemStack(enrichedNaquadahMass);
    public static ItemRefer Naquadria_Mass = getItemStack(naquadriaMass);
    public static ItemRefer Advanced_Fuel_Rod = getItemStack(advancedFuelRod);
    public static ItemRefer Fluid_Storage_Core_T1 = getItemStack(fluidCore, 0);
    public static ItemRefer Fluid_Storage_Core_T2 = getItemStack(fluidCore, 1);
    public static ItemRefer Fluid_Storage_Core_T3 = getItemStack(fluidCore, 2);
    public static ItemRefer Fluid_Storage_Core_T4 = getItemStack(fluidCore, 3);
    public static ItemRefer Fluid_Storage_Core_T5 = getItemStack(fluidCore, 4);
    public static ItemRefer Fluid_Storage_Core_T6 = getItemStack(fluidCore, 5);
    public static ItemRefer Fluid_Storage_Core_T7 = getItemStack(fluidCore, 6);
    public static ItemRefer Fluid_Storage_Core_T8 = getItemStack(fluidCore, 7);
    public static ItemRefer Fluid_Storage_Core_T9 = getItemStack(fluidCore, 8);
    public static ItemRefer Fluid_Storage_Core_T10 = getItemStack(fluidCore, 9);
    public static ItemRefer High_Energy_Mixture = getItemStack(highEnergyMixture);
    public static ItemRefer Salty_Root = getItemStack(saltyRoot);
    public static ItemRefer HiC_T1 = getItemStack(huiCircuit, 0);
    public static ItemRefer HiC_T2 = getItemStack(huiCircuit, 1);
    public static ItemRefer HiC_T3 = getItemStack(huiCircuit, 2);
    public static ItemRefer HiC_T4 = getItemStack(huiCircuit, 3);
    public static ItemRefer HiC_T5 = getItemStack(huiCircuit, 4);

    public static ItemRefer Field_Restriction_Casing = getItemStack(MAR_Casing);
    public static ItemRefer Naquadah_Fuel_Refinery_Casing = getItemStack(FRF_Casings);
    public static ItemRefer Field_Restriction_Coil_T1 = getItemStack(FRF_Coil_1);
    public static ItemRefer Field_Restriction_Coil_T2 = getItemStack(FRF_Coil_2);
    public static ItemRefer Field_Restriction_Coil_T3 = getItemStack(FRF_Coil_3);
    public static ItemRefer Field_Restriction_Coil_T4 = getItemStack(FRF_Coil_4);
    public static ItemRefer Radiation_Proof_Steel_Frame_Box = getItemStack(radiationProtectionSteelFrame);
    public static ItemRefer Field_Restriction_Glass = getItemStack(fieldRestrictingGlass);
    public static ItemRefer Raw_Cylinder = getItemStack(rawCylinder);
    public static ItemRefer Titanium_Plated_Cylinder = getItemStack(titaniumPlatedCylinder);
    public static ItemRefer Magic_Casing = getItemStack(magicCasing);
    public static ItemRefer Speeding_Pipe = getItemStack(speedingPipe);
    public static ItemRefer Essentia_Cell_T1 = getItemStack(essentiaCell, 0);
    public static ItemRefer Essentia_Cell_T2 = getItemStack(essentiaCell, 1);
    public static ItemRefer Essentia_Cell_T3 = getItemStack(essentiaCell, 2);
    public static ItemRefer Essentia_Cell_T4 = getItemStack(essentiaCell, 3);
    public static ItemRefer YOTTank_Casing = getItemStack(yottaFluidTankCasing);
    public static ItemRefer YOTTank_Cell_T1 = getItemStack(yottaFluidTankCell, 0);
    public static ItemRefer YOTTank_Cell_T2 = getItemStack(yottaFluidTankCell, 1);
    public static ItemRefer YOTTank_Cell_T3 = getItemStack(yottaFluidTankCell, 2);
    public static ItemRefer YOTTank_Cell_T4 = getItemStack(yottaFluidTankCell, 3);
    public static ItemRefer YOTTank_Cell_T5 = getItemStack(yottaFluidTankCell, 4);
    public static ItemRefer YOTTank_Cell_T6 = getItemStack(yottaFluidTankCell, 5);
    public static ItemRefer YOTTank_Cell_T7 = getItemStack(yottaFluidTankCell, 6);
    public static ItemRefer YOTTank_Cell_T8 = getItemStack(yottaFluidTankCell, 7);
    public static ItemRefer YOTTank_Cell_T9 = getItemStack(yottaFluidTankCell, 8);
    public static ItemRefer YOTTank_Cell_T10 = getItemStack(yottaFluidTankCell, 9);
    public static ItemRefer SC_Turbine_Casing = getItemStack(supercriticalFluidTurbineCasing);
    public static ItemRefer Pressure_Resistant_Wall = getItemStack(pressureResistantWalls);
    public static ItemRefer Imprecise_Electronic_Unit = getItemStack(impreciseUnitCasing, 0);
    public static ItemRefer Precise_Electronic_Unit_T1 = getItemStack(preciseUnitCasing, 0);
    public static ItemRefer Precise_Electronic_Unit_T2 = getItemStack(preciseUnitCasing, 1);
    public static ItemRefer Precise_Electronic_Unit_T3 = getItemStack(preciseUnitCasing, 2);
    public static ItemRefer Precise_Electronic_Unit_T4 = getItemStack(preciseUnitCasing, 3);
    public static ItemRefer Compact_Fusion_Coil_T0 = getItemStack(compactFusionCoil, 0);
    public static ItemRefer Compact_Fusion_Coil_T1 = getItemStack(compactFusionCoil, 1);
    public static ItemRefer Compact_Fusion_Coil_T2 = getItemStack(compactFusionCoil, 2);
    public static ItemRefer Compact_Fusion_Coil_T3 = getItemStack(compactFusionCoil, 3);
    public static ItemRefer Compact_Fusion_Coil_T4 = getItemStack(compactFusionCoil, 4);
    public static ItemRefer Essentia_Filter_Casing = getItemStack(essentiaFilterCasing);
    public static ItemRefer Essentia_Output_Hatch = getItemStack(essentiaOutputHatch);
    public static ItemRefer Essentia_Output_Hatch_ME = getItemStack(essentiaOutputHatch_ME);

    public static ItemRefer Large_Naquadah_Reactor = getItemStack(MAR);
    public static ItemRefer Naquadah_Fuel_Refinery = getItemStack(FRF);
    public static ItemRefer Universal_Chemical_Fuel_Engine = getItemStack(UCFE);
    public static ItemRefer YOTTank = getItemStack(YFT);
    public static ItemRefer Combustion_Generator_EV = getItemStack(Generator_Diesel[0]);
    public static ItemRefer Combustion_Generator_IV = getItemStack(Generator_Diesel[1]);
    public static ItemRefer SC_Fluid_Turbine = getItemStack(SCTurbine);
    public static ItemRefer Extreme_Heat_Exchanger = getItemStack(XHE);
    public static ItemRefer Precise_Assembler = getItemStack(PA);
    public static ItemRefer Compact_Fusion_MK1 = getItemStack(LFC[0]);
    public static ItemRefer Compact_Fusion_MK2 = getItemStack(LFC[1]);
    public static ItemRefer Compact_Fusion_MK3 = getItemStack(LFC[2]);
    public static ItemRefer Compact_Fusion_MK4 = getItemStack(LFC[3]);
    public static ItemRefer Compact_Fusion_MK5 = getItemStack(LFC[4]);
    public static ItemRefer Large_Essentia_Smeltery = getItemStack(LES);
    public static ItemRefer Coolant_Tower = getItemStack(CT);
    public static ItemRefer Component_Assembly_Line = getItemStack(CompAssline);

    public static ItemRefer Compassline_Casing_LV = getItemStack(componentAssemblylineCasing, 0);
    public static ItemRefer Compassline_Casing_MV = getItemStack(componentAssemblylineCasing, 1);
    public static ItemRefer Compassline_Casing_HV = getItemStack(componentAssemblylineCasing, 2);
    public static ItemRefer Compassline_Casing_EV = getItemStack(componentAssemblylineCasing, 3);
    public static ItemRefer Compassline_Casing_IV = getItemStack(componentAssemblylineCasing, 4);
    public static ItemRefer Compassline_Casing_LuV = getItemStack(componentAssemblylineCasing, 5);
    public static ItemRefer Compassline_Casing_ZPM = getItemStack(componentAssemblylineCasing, 6);
    public static ItemRefer Compassline_Casing_UV = getItemStack(componentAssemblylineCasing, 7);
    public static ItemRefer Compassline_Casing_UHV = getItemStack(componentAssemblylineCasing, 8);
    public static ItemRefer Compassline_Casing_UEV = getItemStack(componentAssemblylineCasing, 9);
    public static ItemRefer Compassline_Casing_UIV = getItemStack(componentAssemblylineCasing, 10);
    public static ItemRefer Compassline_Casing_UMV = getItemStack(componentAssemblylineCasing, 11);
    public static ItemRefer Compassline_Casing_UXV = getItemStack(componentAssemblylineCasing, 12);
    public static ItemRefer Compassline_Casing_MAX = getItemStack(componentAssemblylineCasing, 13);

    public static ItemRefer AntimatterForge = getItemStack(AMForge);
    public static ItemRefer AntimatterGenerator = getItemStack(AMGenerator);
    public static ItemRefer AntimatterContainmentCasing = getItemStack(antimatterContainmentCasing);
    public static ItemRefer GravityStabilizationCasing = getItemStack(gravityStabilizationCasing);
    public static ItemRefer MagneticFluxCasing = getItemStack(magneticFluxCasing);
    public static ItemRefer ProtomatterActivationCoil = getItemStack(protomatterActivationCoil);
    public static ItemRefer AntimatterAnnihilationMatrix = getItemStack(antimatterAnnihilationMatrix);

    private Item mItem = null;
    private Block mBlock = null;
    private ItemStack mItemStack = null;
    private int mMeta = 0;

    private static ItemRefer getItemStack(ItemStack itemStack) {
        if (itemStack == null) return NULL;
        return new ItemRefer(itemStack);
    }

    private static ItemRefer getItemStack(Item item) {
        return getItemStack(item, 0);
    }

    private static ItemRefer getItemStack(Item item, int meta) {
        if (item == null) return NULL;
        return new ItemRefer(item, meta);
    }

    private static ItemRefer getItemStack(Block block) {
        return getItemStack(block, 0);
    }

    private static ItemRefer getItemStack(Block block, int meta) {
        if (block == null) return NULL;
        return new ItemRefer(block, meta);
    }

    private ItemRefer(Item item, int meta) {
        mItem = item;
        mMeta = meta;
    }

    private ItemRefer(Block block, int meta) {
        mBlock = block;
        mMeta = meta;
    }

    private ItemRefer(ItemStack itemStack) {
        mItemStack = itemStack;
    }

    public ItemStack get(int amount) {
        if (mItem != null) return new ItemStack(mItem, amount, mMeta);
        if (mBlock != null) return new ItemStack(mBlock, amount, mMeta);
        if (mItemStack != null) return GTUtility.copyAmount(amount, mItemStack);
        return new ItemStack(_null_, amount, 0);
    }
}
