package modulardiversity.tile;

import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.IOType;
import modulardiversity.block.prop.EmberHatchSize;
import modulardiversity.components.MachineComponents;
import modulardiversity.components.requirements.RequirementEmber;
import modulardiversity.tile.base.TileEntityEmber;
import modulardiversity.util.ICraftingResourceHolder;

import javax.annotation.Nullable;

public class TileEmberOutputHatch extends TileEntityEmber {
    public TileEmberOutputHatch() { super(); }

    public TileEmberOutputHatch(EmberHatchSize size)
    {
        super(size);
    }

    @Nullable
    @Override
    public MachineComponent provideComponent() {
        return new MachineComponents.EmberHatch(IOType.OUTPUT) {
            @Override
            public ICraftingResourceHolder<RequirementEmber.ResourceToken> getContainerProvider() {
                return TileEmberOutputHatch.this;
            }
        };
    }
}
