package gregtech.loaders.oreprocessing;

import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sHammerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ProcessingCrystallized implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingCrystallized() {
        OrePrefixes.crystal.add(this);
        OrePrefixes.crystalline.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        ItemStack aStack) {
        if (aMaterial.mMacerateInto == null) {
            return;
        }

        if (GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1) == null) {
            return;
        }

        GT_Values.RA.stdBuilder()
            .itemInputs(GT_Utility.copyAmount(1, aStack))
            .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L))
            .duration(10 * TICKS)
            .eut(16)
            .addTo(sHammerRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(GT_Utility.copyAmount(1, aStack))
            .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial.mMacerateInto, 1L))
            .duration(20 * SECONDS)
            .eut(2)
            .addTo(sMaceratorRecipes);

    }
}
