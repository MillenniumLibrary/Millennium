package tt432.millennium.devonly;

import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class AllInOneObject {
    public static final List<AllInOneObject> list = new ArrayList<>();

    String name;
    public ResourceLocation model;
    BlockEntityType.BlockEntitySupplier<? extends ModelBlockEntity> factory;

    public AllInOneObject(String name, ResourceLocation model) {
        this.name = name;
        this.model = model;
        list.add(this);
    }

    public RegistryObject<Block> block;
    public RegistryObject<BlockEntityType<? extends ModelBlockEntity>> beType;

    public void registerBlockEntity(DeferredRegister<BlockEntityType<?>> register) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, name);
        beType = register.register(name, () -> BlockEntityType.Builder.of((factory = (pos, state) ->
                new ModelBlockEntity(beType.get(), pos, state)), block.get()).build(type));
    }

    public void registerBlock(DeferredRegister<Block> register) {
        block = register.register(name, () -> new BaseBlock(BlockBehaviour.Properties.of(Material.STONE)) {
            @Override
            public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                return factory.create(pPos, pState);
            }
        });
    }

    public void registerItem(DeferredRegister<Item> register) {
        register.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    }
}
