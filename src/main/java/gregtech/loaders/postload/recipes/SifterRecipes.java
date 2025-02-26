package gregtech.loaders.postload.recipes;

import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sSifterRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

public class SifterRecipes implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.gravel, 1, 0))
            .itemOutputs(
                new ItemStack(Items.flint, 1, 0),
                new ItemStack(Items.flint, 1, 0),
                new ItemStack(Items.flint, 1, 0),
                new ItemStack(Items.flint, 1, 0),
                new ItemStack(Items.flint, 1, 0),
                new ItemStack(Items.flint, 1, 0))
            .outputChances(10000, 9000, 8000, 6000, 3300, 2500)
            .duration(30 * SECONDS)
            .eut(16)
            .addTo(sSifterRecipes);

        GT_Values.RA.stdBuilder()
            .itemInputs(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Coal, 1L))
            .itemOutputs(
                new ItemStack(Items.coal, 1, 0),
                new ItemStack(Items.coal, 1, 0),
                new ItemStack(Items.coal, 1, 0),
                new ItemStack(Items.coal, 1, 0),
                new ItemStack(Items.coal, 1, 0),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L))
            .outputChances(10000, 9000, 8000, 7000, 6000, 5000)
            .duration(30 * SECONDS)
            .eut(16)
            .addTo(sSifterRecipes);
    }
}
