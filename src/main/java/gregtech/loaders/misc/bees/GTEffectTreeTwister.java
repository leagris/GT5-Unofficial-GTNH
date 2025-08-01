package gregtech.loaders.misc.bees;

import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.TwilightForest;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import org.apache.commons.lang3.ArrayUtils;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IEffectData;
import gregtech.GTMod;
import gregtech.api.util.GTModHandler;

public class GTEffectTreeTwister extends GTAlleleEffect {

    private static final int[] ALLOWED_DIMS = { 2, // spectre
        112, // last millenium
        60, // bedrock
        69, // pocket plane
    };

    private static final ItemStack TF_TRANS_SAPLING = GTModHandler
        .getModItem(TwilightForest.ID, "tile.TFSapling", 1, 6);
    private static final ItemStack BARN_SAPLING = GTModHandler.getModItem(GalaxySpace.ID, "barnardaCsapling", 1, 1);

    static {
        if (TF_TRANS_SAPLING == null) {
            GTMod.GT_FML_LOGGER.info("GTEffectTreeTwister(): Could not get ItemStack for BarnardaC sapling");
        }
        if (BARN_SAPLING == null) {
            GTMod.GT_FML_LOGGER.info("GTEffectTreeTwister(): Could not get ItemStack for BarnardaC sapling");
        }
    }

    public GTEffectTreeTwister() {
        super("effectTreetwister", false, 0);
    }

    public IEffectData validateStorage(IEffectData storedData) {
        return storedData; // unused for this effect
    }

    @Override
    protected IEffectData doEffectTickThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        if (TF_TRANS_SAPLING == null || BARN_SAPLING == null) {
            return storedData;
        }
        World world = housing.getWorld();
        if (!ArrayUtils.contains(ALLOWED_DIMS, world.provider.dimensionId)) {
            return storedData;
        }
        ChunkCoordinates coords = housing.getCoordinates();
        IBeeModifier beeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);

        // Get random coords within territory
        int xRange = (int) (beeModifier.getTerritoryModifier(genome, 1f) * genome.getTerritory()[0]);
        int yRange = (int) (beeModifier.getTerritoryModifier(genome, 1f) * genome.getTerritory()[1]);
        int zRange = (int) (beeModifier.getTerritoryModifier(genome, 1f) * genome.getTerritory()[2]);

        int xCoord = coords.posX + world.rand.nextInt(xRange) - xRange / 2;
        int yCoord = coords.posY + world.rand.nextInt(yRange) - yRange / 2;
        int zCoord = coords.posZ + world.rand.nextInt(zRange) - zRange / 2;

        ItemStack sourceBlock = new ItemStack(
            world.getBlock(xCoord, yCoord, zCoord),
            1,
            world.getBlockMetadata(xCoord, yCoord, zCoord));
        if (TF_TRANS_SAPLING.isItemEqual(sourceBlock)) {
            world.setBlock(
                xCoord,
                yCoord,
                zCoord,
                Block.getBlockFromItem(BARN_SAPLING.getItem()),
                BARN_SAPLING.getItemDamage(),
                2);
        }
        return storedData;
    }
}
