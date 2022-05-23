package tt432.millennium.devonly;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tt432.millennium.Millennium;

/**
 * @author DustW
 **/
public class DevRegistry {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Millennium.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Millennium.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Millennium.MOD_ID);


    public static final AllInOneObject SHIP_BLOCK = register(new AllInOneObject("ship_block",
            new ResourceLocation(Millennium.MOD_ID, "models/gltf/ship.gltf")));

    public static final AllInOneObject VEHICLE_BLOCK = register(new AllInOneObject("vehicle_block",
            new ResourceLocation(Millennium.MOD_ID, "models/gltf/vehicle.gltf")));

    public static final AllInOneObject PLACEHOLDER_BLOCK = register(new AllInOneObject("placeholder_block",
            new ResourceLocation(Millennium.MOD_ID, "models/gltf/placeholder.gltf")));

    public static final AllInOneObject KITCHENETTE_BLOCK = register(new AllInOneObject("kitchenette_block",
            new ResourceLocation(Millennium.MOD_ID, "models/gltf/kit/kitchenette.gltf")));

    public static final AllInOneObject MERCEDES_BLOCK = register(new AllInOneObject("mercedes_block",
            new ResourceLocation(Millennium.MOD_ID, "models/gltf/mercedes/mercedes.gltf")));


    public static final RegistryObject<Item> COCKTAIL = ITEMS.register("cocktail",
            () -> new Item(new Item.Properties()));


    private static AllInOneObject register(AllInOneObject object) {
        object.registerBlockEntity(BLOCK_ENTITIES);
        object.registerBlock(BLOCKS);
        object.registerItem(ITEMS);
        return object;
    }

    public static void register() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}
