package toxiceverglades.gen;

import static toxiceverglades.gen.WorldGenEvergladesBase.debugWorldGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.util.GTLog;
import gregtech.api.util.StringUtils;
import gregtech.common.blocks.BlockOres;
import gregtech.common.blocks.TileEntityOres;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.material.Material;
import toxiceverglades.dimension.DimensionEverglades;

public class WorldGenEvergladesOreLayer extends WorldGenEverglades {

    public static ArrayList<WorldGenEvergladesOreLayer> sList = new ArrayList<>();
    public static int sWeight = 0;
    public final short mMinY;
    public final short mMaxY;
    public final short mWeight;
    public final short mDensity;
    public final short mSize;
    public Block mPrimaryMeta;
    public Block mSecondaryMeta;
    public Block mBetweenMeta;
    public Block mSporadicMeta;
    public final Material mPrimary;
    public final Material mSecondary;
    public final Material mBetween;
    public final Material mSporadic;

    // public final String mBiome;
    public final String mRestrictBiome;
    public final boolean mOverworld;
    public final boolean mNether;
    public final boolean mEnd;
    public static final int WRONG_BIOME = 0;
    public static final int WRONG_DIMENSION = 1;
    public static final int NO_ORE_IN_BOTTOM_LAYER = 2;
    public static final int NO_OVERLAP = 3;
    public static final int ORE_PLACED = 4;
    public static final int NO_OVERLAP_AIR_BLOCK = 5;

    public final String aTextWorldgen = "worldgen.";

    public WorldGenEvergladesOreLayer(String aName, int aMinY, int aMaxY, int aWeight, int aDensity, int aSize,
        Material aPrimary, Material aSecondary, Material aBetween, Material aSporadic) {
        this(
            aName,
            true,
            aMinY,
            aMaxY,
            aWeight,
            aDensity,
            aSize,
            false,
            false,
            false,
            false,
            false,
            false,
            aPrimary,
            aSecondary,
            aBetween,
            aSporadic);
    }

    public WorldGenEvergladesOreLayer(String aName, boolean aDefault, int aMinY, int aMaxY, int aWeight, int aDensity,
        int aSize, boolean aOverworld, boolean aNether, boolean aEnd, boolean GC_UNUSED1, boolean GC_UNUSED2,
        boolean GC_UNUSED3, Material aPrimary, Material aSecondary, Material aBetween, Material aSporadic) {
        super(aName, sList, aDefault);
        Logger.WORLD("Creating Ore Layer Object");
        this.mOverworld = aOverworld;
        this.mNether = aNether;
        this.mEnd = aEnd;
        this.mMinY = 5;
        this.mMaxY = 14;
        this.mWeight = (short) aWeight;
        this.mDensity = (short) aDensity;
        this.mSize = (short) Math.max(1, aSize);
        this.mPrimary = aPrimary;
        this.mSecondary = aSecondary;
        this.mBetween = aBetween;
        this.mSporadic = aSporadic;
        this.mPrimaryMeta = aPrimary.getOreBlock(1);
        this.mSecondaryMeta = aSecondary.getOreBlock(1);
        this.mBetweenMeta = aBetween.getOreBlock(1);
        this.mSporadicMeta = aSporadic.getOreBlock(1);
        this.mRestrictBiome = "None";

        if (this.mEnabled) {
            sWeight += this.mWeight;
        }
    }

    public int executeWorldgenChunkified(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX,
        int aChunkZ, int aSeedX, int aSeedZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider) {
        // Debug Handler
        // This handles Variables that are null during Init
        if (this.mPrimaryMeta == Blocks.stone || this.mSecondaryMeta == Blocks.stone
            || this.mBetweenMeta == Blocks.stone
            || this.mSporadicMeta == Blocks.stone) {
            this.mPrimaryMeta = this.mPrimary.getOreBlock(1);
            this.mSecondaryMeta = this.mSecondary.getOreBlock(1);
            this.mBetweenMeta = this.mBetween.getOreBlock(1);
            this.mSporadicMeta = this.mSporadic.getOreBlock(1);
            Logger.WORLD(
                "[Vein Generator] An Ore in a Vein had defaulted back to a default value, so they have now been reset to correct values.");
        }

        if (mWorldGenName.equals("vein0")) {
            if (debugWorldGen) GTLog.out.println(" NoOresInVein-vein0");
            // This is a special empty orevein
            Logger.WORLD("[World Generation Debug] Special Empty Vein placed.");
            return ORE_PLACED;
        }
        if (aDimensionType != DimensionEverglades.DIMID) {
            /*
             * // Debug code, but spams log if (debugWorldGen) { GTLog.out.println( "Wrong dimension" ); }
             */
            Logger.WORLD("[World Generation Debug] Wrong dimension.");
            return WRONG_DIMENSION;
        }
        if (!this.mRestrictBiome.equals("None") && !(this.mRestrictBiome.equals(aBiome))) {
            return WRONG_BIOME;
        }
        int[] placeCount = new int[4];

        int tMinY = mMinY + aRandom.nextInt(mMaxY - mMinY - 5);
        // Determine West/East ends of orevein
        int wXVein = aSeedX - aRandom.nextInt(mSize); // West side
        int eXVein = aSeedX + 16 + aRandom.nextInt(mSize);
        // Limit Orevein to only blocks present in current chunk
        int wX = Math.max(wXVein, aChunkX + 2); // Bias placement by 2 blocks to prevent worldgen cascade.
        int eX = Math.min(eXVein, aChunkX + 2 + 16);
        if (wX >= eX) { // No overlap between orevein and this chunk exists in X
            Block tBlock = aWorld.getBlock(aChunkX + 8, tMinY, aChunkZ + 8);
            if (tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, Blocks.stone)
                || tBlock
                    .isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, DimensionEverglades.blockSecondLayer)
                || tBlock
                    .isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, DimensionEverglades.blockMainFiller)
                || tBlock.isReplaceableOreGen(
                    aWorld,
                    aChunkX + 8,
                    tMinY,
                    aChunkZ + 8,
                    DimensionEverglades.blockSecondaryFiller)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, Blocks.netherrack)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, Blocks.end_stone)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, GregTechAPI.sBlockGranites)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, GregTechAPI.sBlockStones)) {
                // Didn't reach, but could have placed. Save orevein for future use.
                return NO_OVERLAP;
            } else {
                // Didn't reach, but couldn't place in test spot anywys, try for another orevein
                return NO_OVERLAP_AIR_BLOCK;
            }
        }
        // Determine North/Sound ends of orevein
        int nZVein = aSeedZ - aRandom.nextInt(mSize);
        int sZVein = aSeedZ + 16 + aRandom.nextInt(mSize);

        int nZ = Math.max(nZVein, aChunkZ + 2); // Bias placement by 2 blocks to prevent worldgen cascade.
        int sZ = Math.min(sZVein, aChunkZ + 2 + 16);
        if (nZ >= sZ) { // No overlap between orevein and this chunk exists in Z
            Block tBlock = aWorld.getBlock(aChunkX + 8, tMinY, aChunkZ + 8);
            if (tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, Blocks.stone)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, Blocks.netherrack)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, Blocks.end_stone)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, GregTechAPI.sBlockGranites)
                || tBlock.isReplaceableOreGen(aWorld, aChunkX + 8, tMinY, aChunkZ + 8, GregTechAPI.sBlockStones)) {
                // Didn't reach, but could have placed. Save orevein for future use.
                return NO_OVERLAP;
            } else {
                // Didn't reach, but couldn't place in test spot anywys, try for another orevein
                return NO_OVERLAP_AIR_BLOCK;
            }
        }

        if (debugWorldGen) {
            String tDimensionName = aWorld.provider.getDimensionName();
            GTLog.out.print(
                "Trying Orevein:" + this.mWorldGenName
                    + " Dimension="
                    + tDimensionName
                    + " mX="
                    + aChunkX / 16
                    + " mZ="
                    + aChunkZ / 16
                    + " oreseedX="
                    + aSeedX / 16
                    + " oreseedZ="
                    + aSeedZ / 16
                    + " cY="
                    + tMinY);
        }
        double dx = aChunkX / 16 - aSeedX / 16;
        double dz = aChunkZ / 16 - aSeedZ / 16;
        // Adjust the density down the more chunks we are away from the oreseed. The 5 chunks surrounding the seed
        // should always be max density due to truncation of Math.sqrt().
        int localDensity = (Math.max(1, this.mDensity / (int) Math.sqrt(2 + dx * dx + dz * dz)));

        // To allow for early exit due to no ore placed in the bottom layer (probably because we are in the sky), unroll
        // 1 pass through the loop
        // Now we do bottom-level-first oregen, and work our way upwards.
        int level = tMinY - 1; // Dunno why, but the first layer is actually played one below tMinY. Go figure.
        for (int tX = wX; tX < eX; tX++) {
            int placeX = Math
                .max(1, Math.max(MathHelper.abs_int(wXVein - tX), MathHelper.abs_int(eXVein - tX)) / localDensity);
            for (int tZ = nZ; tZ < sZ; tZ++) {
                int placeZ = Math
                    .max(1, Math.max(MathHelper.abs_int(sZVein - tZ), MathHelper.abs_int(nZVein - tZ)) / localDensity);
                if (((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                    && (this.mSecondaryMeta != null)) {
                    if (setOreBlock(aWorld, tX, level, tZ, this.mSecondaryMeta, false, false)) {
                        placeCount[1]++;
                    }
                } else
                    if ((aRandom.nextInt(7) == 0) && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mSporadicMeta != null)) { // Sporadics are only 1 per vertical column normally,
                            // reduce by 1/7 to
                            // compensate
                            if (setOreBlock(aWorld, tX, level, tZ, this.mSporadicMeta, false, false)) placeCount[3]++;
                        }
            }
        }
        /*
         * if ((placeCount[1]+placeCount[3])==0) { if (debugWorldGen) GTLog.out.println( " No ore in bottom layer" );
         * return NO_ORE_IN_BOTTOM_LAYER; // Exit early, didn't place anything in the bottom layer }
         */
        Logger.WORLD("[World Generation Debug] Trying to set Ores?");
        for (level = tMinY; level < (tMinY - 1 + 3); level++) {
            for (int tX = wX; tX < eX; tX++) {
                int placeX = Math
                    .max(1, Math.max(MathHelper.abs_int(wXVein - tX), MathHelper.abs_int(eXVein - tX)) / localDensity);
                for (int tZ = nZ; tZ < sZ; tZ++) {
                    int placeZ = Math.max(
                        1,
                        Math.max(MathHelper.abs_int(sZVein - tZ), MathHelper.abs_int(nZVein - tZ)) / localDensity);
                    if (((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mSecondaryMeta != null)) {
                        if (setOreBlock(aWorld, tX, level, tZ, this.mSecondaryMeta, false, false)) {
                            placeCount[1]++;
                        }
                    } else if ((aRandom.nextInt(7) == 0)
                        && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mSporadicMeta != null)) { // Sporadics are only 1 per vertical column normally,
                            // reduce by 1/7 to
                            // compensate
                            if (setOreBlock(aWorld, tX, level, tZ, this.mSporadicMeta, false, false)) placeCount[3]++;
                        }
                }
            }
        }
        // Low Middle layer is between + sporadic
        // level should be = tMinY-1+3 from end of for loop
        for (int tX = wX; tX < eX; tX++) {
            int placeX = Math
                .max(1, Math.max(MathHelper.abs_int(wXVein - tX), MathHelper.abs_int(eXVein - tX)) / localDensity);
            for (int tZ = nZ; tZ < sZ; tZ++) {
                int placeZ = Math
                    .max(1, Math.max(MathHelper.abs_int(sZVein - tZ), MathHelper.abs_int(nZVein - tZ)) / localDensity);
                if ((aRandom.nextInt(2) == 0) && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                    && (this.mBetweenMeta != null)) { // Between are only 1 per vertical column, reduce by 1/2 to
                    // compensate
                    if (setOreBlock(aWorld, tX, level, tZ, this.mBetweenMeta, false, false)) {
                        placeCount[2]++;
                    }
                } else
                    if ((aRandom.nextInt(7) == 0) && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mSporadicMeta != null)) { // Sporadics are only 1 per vertical column normally,
                            // reduce by 1/7 to
                            // compensate
                            if (setOreBlock(aWorld, tX, level, tZ, this.mSporadicMeta, false, false)) placeCount[3]++;
                        }
            }
        }
        // High Middle layer is between + primary + sporadic
        level++; // Increment level to next layer
        for (int tX = wX; tX < eX; tX++) {
            int placeX = Math
                .max(1, Math.max(MathHelper.abs_int(wXVein - tX), MathHelper.abs_int(eXVein - tX)) / localDensity);
            for (int tZ = nZ; tZ < sZ; tZ++) {
                int placeZ = Math
                    .max(1, Math.max(MathHelper.abs_int(sZVein - tZ), MathHelper.abs_int(nZVein - tZ)) / localDensity);
                if ((aRandom.nextInt(2) == 0) && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                    && (this.mBetweenMeta != null)) { // Between are only 1 per vertical column, reduce by 1/2 to
                    // compensate
                    if (setOreBlock(aWorld, tX, level, tZ, this.mBetweenMeta, false, false)) {
                        placeCount[2]++;
                    }
                } else if (((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                    && (this.mPrimaryMeta != null)) {
                        if (setOreBlock(aWorld, tX, level, tZ, this.mPrimaryMeta, false, false)) {
                            placeCount[0]++;
                        }
                    } else
                    if ((aRandom.nextInt(7) == 0) && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mSporadicMeta != null)) { // Sporadics are only 1 per vertical column normally,
                            // reduce by 1/7 to
                            // compensate
                            if (setOreBlock(aWorld, tX, level, tZ, this.mSporadicMeta, false, false)) placeCount[3]++;
                        }
            }
        }
        // Top two layers are primary + sporadic
        level++; // Increment level to next layer
        for (; level < (tMinY + 6); level++) { // should do two layers
            for (int tX = wX; tX < eX; tX++) {
                int placeX = Math
                    .max(1, Math.max(MathHelper.abs_int(wXVein - tX), MathHelper.abs_int(eXVein - tX)) / localDensity);
                for (int tZ = nZ; tZ < sZ; tZ++) {
                    int placeZ = Math.max(
                        1,
                        Math.max(MathHelper.abs_int(sZVein - tZ), MathHelper.abs_int(nZVein - tZ)) / localDensity);
                    if (((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mPrimaryMeta != null)) {
                        if (setOreBlock(aWorld, tX, level, tZ, this.mPrimaryMeta, false, false)) {
                            placeCount[0]++;
                        }
                    } else if ((aRandom.nextInt(7) == 0)
                        && ((aRandom.nextInt(placeZ) == 0) || (aRandom.nextInt(placeX) == 0))
                        && (this.mSporadicMeta != null)) { // Sporadics are only 1 per vertical column normally,
                            // reduce by 1/7 to
                            // compensate
                            if (setOreBlock(aWorld, tX, level, tZ, this.mSporadicMeta, false, false)) placeCount[3]++;
                        }
                }
            }
        }
        if (debugWorldGen) {
            String tDimensionName = aWorld.provider.getDimensionName();
            GTLog.out.println(
                "Generated Orevein:" + this.mWorldGenName
                    + " Dimension="
                    + tDimensionName
                    + " mX="
                    + aChunkX / 16
                    + " mZ="
                    + aChunkZ / 16
                    + " oreseedX="
                    + aSeedX / 16
                    + " oreseedZ="
                    + aSeedZ / 16
                    + " cY="
                    + tMinY
                    + " wXVein"
                    + wXVein
                    + " eXVein"
                    + eXVein
                    + " nZVein"
                    + nZVein
                    + " sZVein"
                    + sZVein
                    + " locDen="
                    + localDensity
                    + " Den="
                    + this.mDensity
                    + " Sec="
                    + placeCount[1]
                    + " Spo="
                    + placeCount[3]
                    + " Bet="
                    + placeCount[2]
                    + " Pri="
                    + placeCount[0]);
        }
        // Something (at least the bottom layer must have 1 block) must have been placed, return true
        return ORE_PLACED;
    }

    private String ore1String = "unset";
    private String ore2String = "unset";
    private String ore3String = "unset";
    private String ore4String = "unset";
    Map<Materials, String> gtOreMap = new HashMap<>();

    public boolean setOreBlock(World aWorld, int aX, int aY, int aZ, Block aMetaData, boolean isSmallOre, boolean air) {
        if (!air) {
            aY = Math.min(aWorld.getActualHeight(), Math.max(aY, 1));
        }

        // Set GT ORE
        if (aMetaData instanceof BlockOres) {
            if (ore1String.equals("unset")) {
                ore1String = StringUtils.sanitizeString(
                    this.mPrimary.getLocalizedName()
                        .toLowerCase());
            }
            if (ore2String.equals("unset")) {
                ore2String = StringUtils.sanitizeString(
                    this.mSecondaryMeta.getLocalizedName()
                        .toLowerCase());
            }
            if (ore3String.equals("unset")) {
                ore3String = StringUtils.sanitizeString(
                    this.mBetweenMeta.getLocalizedName()
                        .toLowerCase());
            }
            if (ore4String.equals("unset")) {
                ore4String = StringUtils.sanitizeString(
                    this.mSporadicMeta.getLocalizedName()
                        .toLowerCase());
            }

            String fString;
            if (this.mPrimaryMeta == aMetaData) {
                for (Materials f : Materials.values()) {
                    if (!gtOreMap.containsKey(f)) {
                        gtOreMap.put(f, StringUtils.sanitizeString(f.mName.toLowerCase()));
                    }
                    fString = gtOreMap.get(f);
                    if (fString.contains(ore1String)) {
                        int r = f.mMetaItemSubID;
                        if (TileEntityOres.setOreBlock(aWorld, aX, aY, aZ, r, false)) {
                            return true;
                        }
                    }
                }
            }
            if (this.mSecondaryMeta == aMetaData) {
                for (Materials f : Materials.values()) {
                    if (!gtOreMap.containsKey(f)) {
                        gtOreMap.put(f, StringUtils.sanitizeString(f.mName.toLowerCase()));
                    }
                    fString = gtOreMap.get(f);
                    if (fString.contains(ore2String)) {
                        int r = f.mMetaItemSubID;
                        if (TileEntityOres.setOreBlock(aWorld, aX, aY, aZ, r, false)) {
                            return true;
                        }
                    }
                }
            }
            if (this.mBetweenMeta == aMetaData) {
                for (Materials f : Materials.values()) {
                    if (!gtOreMap.containsKey(f)) {
                        gtOreMap.put(f, StringUtils.sanitizeString(f.mName.toLowerCase()));
                    }
                    fString = gtOreMap.get(f);
                    if (fString.contains(ore3String)) {
                        int r = f.mMetaItemSubID;
                        if (TileEntityOres.setOreBlock(aWorld, aX, aY, aZ, r, false)) {
                            return true;
                        }
                    }
                }
            }
            if (this.mSporadicMeta == aMetaData) {
                for (Materials f : Materials.values()) {
                    if (!gtOreMap.containsKey(f)) {
                        gtOreMap.put(f, StringUtils.sanitizeString(f.mName.toLowerCase()));
                    }
                    fString = gtOreMap.get(f);
                    if (fString.contains(ore4String)) {
                        int r = f.mMetaItemSubID;
                        if (TileEntityOres.setOreBlock(aWorld, aX, aY, aZ, r, false)) {
                            return true;
                        }
                    }
                }
            }
        }

        Block tBlock = aWorld.getBlock(aX, aY, aZ);
        if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.stone)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.sand)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.dirt)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTechAPI.sBlockGranites)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTechAPI.sBlockStones)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, DimensionEverglades.blockSecondLayer)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, DimensionEverglades.blockMainFiller)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, DimensionEverglades.blockSecondaryFiller)
            || tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.sandstone)) {

            return aWorld.setBlock(aX, aY, aZ, aMetaData, 0, 3);
        }
        return false;
    }
}
