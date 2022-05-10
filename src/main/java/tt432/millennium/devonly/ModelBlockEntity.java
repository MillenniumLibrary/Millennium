package tt432.millennium.devonly;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author DustW
 **/
public class ModelBlockEntity extends BlockEntity {
    public ModelBlockEntity(BlockEntityType<? extends ModelBlockEntity> type, BlockPos pWorldPosition, BlockState pBlockState) {
        super(type, pWorldPosition, pBlockState);
    }
}
