package gtPlusPlus.xmod.gregtech.api.metatileentity.implementations;

import static gregtech.api.enums.Textures.BlockIcons.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.ArrayUtils;

import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.MetaGeneratedTool;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.render.RenderOverlay;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gregtech.api.util.GTUtilityClient;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.minecraft.BlockPos;
import gtPlusPlus.core.handler.PacketHandler;
import gtPlusPlus.core.lib.GTPPCore;
import gtPlusPlus.core.network.packet.PacketTurbineHatchUpdate;
import gtPlusPlus.core.util.math.MathUtils;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.turbines.MTELargerTurbineBase;

public class MTEHatchTurbine extends MTEHatch {

    public boolean mHasController = false;
    public boolean mUsingAnimation = true;
    private BlockPos mControllerLocation;
    public int mEUt = 0;

    protected final List<RenderOverlay.OverlayTicket> overlayTickets = new ArrayList<>();
    private boolean mFormed;
    private boolean mHasTurbine;

    public MTEHatchTurbine(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 16, "Turbine Rotor holder for XL Turbines");
    }

    public MTEHatchTurbine(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 1, aDescription, aTextures);
    }

    @Override
    public String[] getDescription() {
        return ArrayUtils.addAll(
            this.mDescriptionArray,
            "Right Click with a soldering iron to reset controller link",
            "Right Click with a wrench to remove turbine",
            "Right Click with a screwdriver for technical information",
            "Sneak + Right Click with a wrench to rotate",
            "Sneak + Right Click with a screwdriver to disable animations",
            GTPPCore.GT_Tooltip.get());
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(LARGETURBINE_NEW_ACTIVE5) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        if (hasTurbine()) return new ITexture[] { aBaseTexture, TextureFactory.of(LARGETURBINE_NEW_EMPTY5) };
        return new ITexture[] { aBaseTexture, TextureFactory.of(LARGETURBINE_NEW5) };
    }

    public int getEU() {
        return this.mEUt;
    }

    public void setEU(int aEU) {
        this.mEUt = aEU;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return facing.offsetY == 0;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return false;
    }

    public boolean hasTurbine() {
        if (getBaseMetaTileEntity().isServerSide()) {
            ItemStack aStack = this.mInventory[0];
            return MTELargerTurbineBase.isValidTurbine(aStack);
        }
        return mHasTurbine;
    }

    public ItemStack getTurbine() {
        if (hasTurbine()) {
            return this.mInventory[0];
        }
        return null;
    }

    public boolean canWork() {
        return hasTurbine();
    }

    public boolean insertTurbine(ItemStack aTurbine) {
        if (MTELargerTurbineBase.isValidTurbine(aTurbine)) {
            this.mInventory[0] = aTurbine;
            sendUpdate();
            return true;
        }
        return false;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEHatchTurbine(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        openGui(aPlayer);
        return true;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    public void damageTurbine(int aEUt, int damageFactorLow, float damageFactorHigh) {
        damageTurbine((long) aEUt, damageFactorLow, damageFactorHigh);
    }

    public void damageTurbine(long aEUt, int damageFactorLow, float damageFactorHigh) {
        if (hasTurbine() && MathUtils.randInt(0, 1) == 0) {
            ItemStack aTurbine = getTurbine();
            ((MetaGeneratedTool) aTurbine.getItem()).doDamage(
                aTurbine,
                (long) getDamageToComponent(aTurbine)
                    * (long) Math.min((float) aEUt / (float) damageFactorLow, Math.pow(aEUt, damageFactorHigh)));
        }
    }

    private int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("mHasController", mHasController);
        aNBT.setBoolean("mUsingAnimation", mUsingAnimation);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mHasController = aNBT.getBoolean("mHasController");
        mUsingAnimation = aNBT.getBoolean("mUsingAnimation");
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (this.mHasController) {
            if (aTick % 20 == 0) {
                boolean oActive = getBaseMetaTileEntity().isActive();
                boolean active = isControllerActive();
                getBaseMetaTileEntity().setActive(active);
                if (active != oActive) {
                    getBaseMetaTileEntity().issueClientUpdate();
                }
            }
        } else if (this.mControllerLocation != null) {
            // Weird Invalid State
            if (setController(mControllerLocation)) {
                // Valid
            }
        } else {
            // No Controller
        }
        if (this.mInventory[0] != null && this.mInventory[0].stackSize <= 0) this.mInventory[0] = null;
    }

    public boolean isControllerActive() {
        MTELargerTurbineBase x = getController();
        if (x != null) {
            // Logger.INFO("Checking Status of Controller. Running? "+(x.mEUt > 0));
            return x.lEUt > 0;
        }
        // Logger.INFO("Status of Controller failed, controller is null.");
        return false;
    }

    public MTELargerTurbineBase getController() {
        if (this.mHasController && this.mControllerLocation != null) {
            BlockPos p = mControllerLocation;
            // Logger.INFO(p.getLocationString());
            IGregTechTileEntity tTileEntity = getBaseMetaTileEntity().getIGregTechTileEntity(p.xPos, p.yPos, p.zPos);
            if (tTileEntity != null && tTileEntity.getMetaTileEntity() instanceof MTELargerTurbineBase) {
                return (MTELargerTurbineBase) tTileEntity.getMetaTileEntity();
            } else {
                if (tTileEntity == null) {
                    Logger.INFO("Controller MTE is null, somehow?");
                } else {
                    Logger.INFO("Controller is a different MTE to expected");
                }
            }
        }
        // Logger.INFO("Failed to Get Controller.");
        return null;
    }

    public boolean canSetNewController() {
        return (mControllerLocation == null) && !this.mHasController;
    }

    public boolean setController(BlockPos aPos) {
        clearController();
        if (canSetNewController()) {
            mControllerLocation = aPos;
            mHasController = true;
            Logger.INFO("Successfully injected controller into this Turbine Assembly Hatch.");
        }
        return mHasController;
    }

    public void clearController() {
        this.mControllerLocation = null;
        this.mHasController = false;
    }

    public IIconContainer[] getTurbineTextureActive() {
        return TURBINE_NEW_ACTIVE;
    }

    public IIconContainer[] getTurbineTextureFull() {
        return TURBINE_NEW;
    }

    public IIconContainer[] getTurbineTextureEmpty() {
        return TURBINE_NEW_EMPTY;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        setTurbineOverlay();
    }

    protected void setTurbineOverlay() {
        IGregTechTileEntity tile = getBaseMetaTileEntity();
        if (tile.isServerSide()) return;

        IIconContainer[] tTextures;
        if (tile.isActive()) tTextures = getTurbineTextureActive();
        else if (hasTurbine()) tTextures = getTurbineTextureFull();
        else tTextures = getTurbineTextureEmpty();

        GTUtilityClient.setTurbineOverlay(
            tile.getWorld(),
            tile.getXCoord(),
            tile.getYCoord(),
            tile.getZCoord(),
            ExtendedFacing.of(getBaseMetaTileEntity().getFrontFacing()),
            tTextures,
            overlayTickets);
    }

    @Override
    public void onTextureUpdate() {
        setTurbineOverlay();
    }

    public boolean usingAnimations() {
        return mUsingAnimation;
    }

    @Override
    public long getMinimumStoredEU() {
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int ordinalSide) {
        return GTValues.emptyIntArray;
    }

    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int ordinalSide) {
        return false;
    }

    public void setActive(boolean b) {
        this.getBaseMetaTileEntity()
            .setActive(b);
    }

    @Override
    public void setInventorySlotContents(int aIndex, ItemStack aStack) {
        super.setInventorySlotContents(aIndex, aStack);
        sendUpdate();
    }

    @Override
    public boolean allowCoverOnSide(ForgeDirection side, ItemStack coverItem) {
        return false;
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool) {
        if (!aPlayer.isSneaking()) {
            GTUtility.sendChatToPlayer(aPlayer, "Using Animations? " + usingAnimations());
            GTUtility.sendChatToPlayer(aPlayer, "Has Controller? " + this.mHasController);
            if (mHasController) {
                GTUtility.sendChatToPlayer(aPlayer, "Controller Location: " + mControllerLocation.getLocationString());
                GTUtility.sendChatToPlayer(aPlayer, "Controller Active? " + this.isControllerActive());
            }
            GTUtility.sendChatToPlayer(
                aPlayer,
                "Active? " + this.getBaseMetaTileEntity()
                    .isActive());
            GTUtility.sendChatToPlayer(aPlayer, "Has Turbine inserted? " + this.hasTurbine());
            if (this.hasTurbine()) {
                Materials aMat = MetaGeneratedTool.getPrimaryMaterial(getTurbine());
                String aSize = MTELargerTurbineBase
                    .getTurbineSizeString(MTELargerTurbineBase.getTurbineSize(getTurbine()));
                GTUtility.sendChatToPlayer(aPlayer, "Using: " + aMat.mLocalizedName + " " + aSize);
            }
        } else {
            this.mUsingAnimation = !mUsingAnimation;
            if (this.mUsingAnimation) {
                GTUtility.sendChatToPlayer(aPlayer, "Using Animated Turbine Texture.");
            } else {
                GTUtility.sendChatToPlayer(aPlayer, "Using Static Turbine Texture.");
            }
        }
    }

    @Override
    public boolean onWrenchRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer aPlayer, float aX,
        float aY, float aZ, ItemStack aTool) {
        if (this.getBaseMetaTileEntity()
            .isServerSide() && !aPlayer.isSneaking()) {
            if (aTool != null) {
                if (aTool.getItem() instanceof MetaGeneratedTool) {
                    return onToolClick(aTool, aPlayer, wrenchingSide);
                }
            }
        }
        return super.onWrenchRightClick(side, wrenchingSide, aPlayer, aX, aY, aZ, aTool);
    }

    @Override
    public boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer aPlayer,
        float aX, float aY, float aZ, ItemStack aTool) {
        if (this.getBaseMetaTileEntity()
            .isServerSide()) {
            if (aTool != null) {
                if (aTool.getItem() instanceof MetaGeneratedTool) {
                    return onToolClick(aTool, aPlayer, wrenchingSide);
                }
            }
        }
        return super.onSolderingToolRightClick(side, wrenchingSide, aPlayer, aX, aY, aZ, aTool);
    }

    public boolean onToolClick(ItemStack tCurrentItem, EntityPlayer aPlayer, ForgeDirection side) {
        if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sWrenchList)) {
            boolean aHasTurbine = this.hasTurbine();
            if (aPlayer.inventory.getFirstEmptyStack() >= 0 && aHasTurbine) {
                if (aPlayer.capabilities.isCreativeMode
                    || GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer)) {
                    aPlayer.inventory.addItemStackToInventory((this.getTurbine()));
                    this.mInventory[0] = null;
                    GTUtility.sendChatToPlayer(aPlayer, "Removed turbine with wrench.");
                    sendUpdate();
                    return true;
                }
            } else {
                GTUtility.sendChatToPlayer(
                    aPlayer,
                    aHasTurbine ? "Cannot remove turbine, no free inventory space." : "No turbine to remove.");
            }
        } else if (GTUtility.isStackInList(tCurrentItem, GregTechAPI.sSolderingToolList)) {
            if (mControllerLocation != null) {
                if (setController(mControllerLocation)) {
                    if (aPlayer.capabilities.isCreativeMode
                        || GTModHandler.damageOrDechargeItem(tCurrentItem, 1, 1000, aPlayer)) {
                        String tChat = "Trying to Reset linked Controller";
                        IGregTechTileEntity g = this.getBaseMetaTileEntity();
                        GTUtility.sendChatToPlayer(aPlayer, tChat);
                        GTUtility.sendSoundToPlayers(
                            g.getWorld(),
                            SoundResource.IC2_TOOLS_RUBBER_TRAMPOLINE,
                            1.0F,
                            -1,
                            g.getXCoord(),
                            g.getYCoord(),
                            g.getZCoord());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        SlotWidget slot = new SlotWidget(inventoryHandler, 0).setFilter(MTELargerTurbineBase::isValidTurbine);
        if (getBaseMetaTileEntity().isServerSide()) slot.setChangeListener(this::sendUpdate);
        builder.widget(
            slot.setAccess(false, true)
                .setPos(79, 34));
    }

    public void receiveUpdate(PacketTurbineHatchUpdate message) {
        mHasTurbine = message.isHasTurbine();
        mFormed = message.isFormed();
        if (message.getController() != null) clearController();
        else setController(message.getController());
        getBaseMetaTileEntity().issueTextureUpdate();
        setTurbineOverlay();
    }

    public void sendUpdate() {
        PacketTurbineHatchUpdate message = new PacketTurbineHatchUpdate();
        message.setX(getBaseMetaTileEntity().getXCoord());
        message.setY(getBaseMetaTileEntity().getYCoord());
        message.setZ(getBaseMetaTileEntity().getZCoord());
        message.setFormed(mHasController && getController().mMachine);
        message.setHasTurbine(hasTurbine());
        message.setController(mControllerLocation);
        PacketHandler.sendToAllAround(
            message,
            getBaseMetaTileEntity().getWorld().provider.dimensionId,
            getBaseMetaTileEntity().getXCoord(),
            getBaseMetaTileEntity().getYCoord(),
            getBaseMetaTileEntity().getZCoord(),
            64.0D);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        if (getBaseMetaTileEntity().isClientSide()) GTUtilityClient.clearTurbineOverlay(overlayTickets);
    }
}
