package me.desht.pneumaticcraft.api.tileentity;

import me.desht.pneumaticcraft.api.item.IUpgradeAcceptor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A way for you to access about everything you need from a pneumatic machine.
 * DO NOT IMPLEMENT THIS YOURSELF! Use AirHandlerSupplier to get an instance for your TileEntity,
 * and implement IPneumaticMachine instead.
 */

public interface IAirHandler extends IManoMeasurable, IUpgradeAcceptor {

    /**
     * -----------Needs to be forwarded by the implementing TileEntity's update() method.
     * Updates the pneumatic machine's logic like air dispersion and checking if it needs to explode.
     */
    void update();

    /**
     * -----------Needs to be forwarded by the implementing TileEntity.
     *
     * @param tag
     */
    void readFromNBT(NBTTagCompound tag);

    /**
     * -----------Needs to be forwarded by the implementing TileEntity.
     *
     * @param tag
     */
    void writeToNBT(NBTTagCompound tag);

    /**
     * -----------Needs to be forwarded by the implementing TileEntity with itself as parameter.
     *
     * @param parent TileEntity that is referencing this air handler.
     */
    void validate(TileEntity parent);

    /**
     * -----------Needs to be forwarded from the implementing _Block_! Forward the Block's "onNeighborChange" method to this handler.
     */
    void onNeighborChange();

    /**
     * Method to release air into the atmosphere. It takes air from a specific side, plays a sound effect, and spawns
     * smoke particles.  It automatically detects if it needs to release air (when under pressure), suck air (when in
     * vacuum) or do nothing.
     *
     * @param side this only affects the direction the steam is pointing.
     */
    void airLeak(EnumFacing side);

    /**
     * Returns a list of all the connecting pneumatics. It takes sides in account.
     */
    List<Pair<EnumFacing, IAirHandler>> getConnectedPneumatics();

    /**
     * Adds air to the tank of the given side of this TE.
     */
    void addAir(int amount);

    /**
     * Sets the volume of this TE's air tank. When the volume decreases the pressure will remain the same, meaning air will
     * be lost. When the volume increases, the air remains the same, meaning the pressure will drop.
     * Used in the Volume Upgrade calculations.
     * By default volume we mean the base volume. Volume added due to Volume Upgrades are added to this.
     *
     * @param defaultVolume
     */
    void setDefaultVolume(int defaultVolume);

    int getVolume();

    /**
     * Returns the actual pressure at which this TE will explode.
     *
     * @return
     */
    float getMaxPressure();
    
    /**
     * Returns the minimal pressure this machine could explode at.
     * @return
     */
    float getDangerPressure();
    
    /**
     * Returns the maximum pressure this machine could explode at.
     * @return
     */
    float getCriticalPressure();

    float getPressure();

    /**
     * Returns the amount of air (that has a relation to the pressure: air = pressure * volume)
     *
     * @return the air in this air handler
     */
    int getAir();

    /**
     * Deprecated method.  This applied in earlier versions of PneumaticCraft but should not be used now,
     * and does nothing useful.  Will be removed in a later release.
     *
     * @param upgradeSlots all upgrade slots stored in an array.
     */
    @Deprecated
    void setUpgradeSlots(int... upgradeSlots);

    /**
     * Deprecated method.  This applied in earlier versions of PneumaticCraft but should not be used now,
     * and does nothing useful.  Will be removed in a later release.
     */
    @Deprecated
    int[] getUpgradeSlots();

    World getWorld();

    BlockPos getPos();

    /**
     * Not necessary if you use validate().
     *
     * @param world
     */
    void setWorld(World world);

    /**
     * Not necessary if you use validate().
     *
     * @param pos
     */
    void setPos(BlockPos pos);

    /**
     * Not necessary if you use validate()
     *
     * @param machine
     */
    void setPneumaticMachine(IPneumaticMachine machine);

    /**
     * Not necessary if you use validate(), or when the parent doesn't implement IAirListener.
     *
     * @param airListener
     */
    void setAirListener(IAirListener airListener);

    /**
     * Creates an air connection with another handler. Can be used to connect up pneumatic machines that aren't neighboring, like AE2's P2P tunnels.
     * This is a custom method that isn't necessary 99% of the cases. Only when you want to connect two IAirHandler's that aren't adjacent to eachother in the world you should need this.
     *
     * @param otherHandler
     */
    void createConnection(@Nonnull IAirHandler otherHandler);

    /**
     * Remove a connection created with createConnection. You need to call this when one of the hosts of this IAirHandlers is invalidated.
     *
     * @param otherHandler
     */
    void removeConnection(@Nonnull IAirHandler otherHandler);

}
