package modulardiversity.tile.base;

import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.IOType;
import hellfirepvp.modularmachinery.common.tiles.base.MachineComponentTile;
import hellfirepvp.modularmachinery.common.tiles.base.TileColorableMachineComponent;
import me.desht.pneumaticcraft.api.PneumaticRegistry;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerSupplier;
import me.desht.pneumaticcraft.api.tileentity.IPneumaticMachine;
import modulardiversity.components.requirements.RequirementMysticalMechanics;
import modulardiversity.util.ICraftingResourceHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TileEntityPneumaticBase extends TileColorableMachineComponent implements MachineComponentTile, ICraftingResourceHolder<RequirementMysticalMechanics.ResourceToken>, IPneumaticMachine, ITickable {
    private IOType ioType;

    public IAirHandler airHandler;
    public int tier;
    public int volume;

    public TileEntityPneumaticBase(IOType ioType, int tier, int volume) {
        this.ioType = ioType;
        this.tier = tier;
        this.volume = volume;
    }

    @Override
    public void setWorld(World worldIn) {
        super.setWorld(worldIn);
        airHandler = createAirHandler();
    }

    private IAirHandler createAirHandler() {
        IAirHandlerSupplier supplier = PneumaticRegistry.getInstance().getAirHandlerSupplier();
        switch(tier) {
            case(1):
                return supplier.createTierOneAirHandler(volume);
            case(2):
                return supplier.createTierTwoAirHandler(volume);
        }
        return null;
    }

    public IAirHandler getAirHandler(EnumFacing enumFacing) {
        return airHandler;
    }

    @Override
    public void validate() {
        super.validate();
        airHandler.validate(this);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound compound) {
        super.writeCustomNBT(compound);
        airHandler.writeToNBT(compound);
    }

    @Override
    public void readCustomNBT(NBTTagCompound compound) {
        super.readCustomNBT(compound);
        airHandler.readFromNBT(compound);
    }

    @Override
    public boolean consume(RequirementMysticalMechanics.ResourceToken token, boolean doConsume) {
        return false;
    }

    @Override
    public boolean generate(RequirementMysticalMechanics.ResourceToken token, boolean doGenerate) {
        return false;
    }

    public void onNeighborChange()
    {
        airHandler.onNeighborChange();
    }

    @Override
    public void update() {
        airHandler.update();
    }

    @Override
    public String getInputProblem(RequirementMysticalMechanics.ResourceToken token) {
        return "meklaser.input";
    }

    @Override
    public String getOutputProblem(RequirementMysticalMechanics.ResourceToken token) {
        return "meklaser.input";
    }
}
