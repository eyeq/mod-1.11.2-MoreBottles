package eyeq.morebottles;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.item.IItemUsePotion;
import eyeq.util.item.crafting.FuelHandler;
import eyeq.util.item.crafting.ShapelessPotionRecipe;
import eyeq.util.item.crafting.UCraftingManager;
import eyeq.util.oredict.CategoryTypes;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import eyeq.morebottles.event.MoreBottlesEventHandler;
import eyeq.morebottles.item.ItemBottleChange;
import eyeq.morebottles.item.ItemBottleSpread;
import eyeq.morebottles.item.ItemBottleExtend;
import eyeq.util.item.UItemBottle;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.morebottles.MoreBottles.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class MoreBottles {
    public static final String MOD_ID = "eyeq_morebottles";

    @Mod.Instance(MOD_ID)
    public static MoreBottles instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item bottleLava;
    public static Item bottleMilk;
    public static Item bottleRedstone;
    public static Item bottleGlowstone;
    public static Item bottleGunpowder;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MoreBottlesEventHandler());
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        bottleLava = new UItemBottle().setFire(6, 1.0F).setUnlocalizedName("bottleLava");
        bottleMilk = new UItemBottle().setClearPotionType(IItemUsePotion.ClearPotionType.CLEAR_ALL).setUnlocalizedName("bottleMilk");
        bottleRedstone = new ItemBottleExtend().setUnlocalizedName("bottleRedstone");
        bottleGlowstone = new ItemBottleChange().setUnlocalizedName("bottleGlowstone");
        bottleGunpowder = new ItemBottleSpread().setUnlocalizedName("bottleGunpowder");

        GameRegistry.register(bottleLava, resource.createResourceLocation("bottle_lava"));
        GameRegistry.register(bottleMilk, resource.createResourceLocation("bottle_milk"));
        GameRegistry.register(bottleRedstone, resource.createResourceLocation("bottle_redstone_dust"));
        GameRegistry.register(bottleGlowstone, resource.createResourceLocation("bottle_glowstone_dust"));
        GameRegistry.register(bottleGunpowder, resource.createResourceLocation("bottle_gunpowder"));

        UOreDictionary.registerOre(CategoryTypes.MILK, "bottle", bottleMilk);
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(UCraftingManager.getRecipeBottle(new ItemStack(bottleLava), Items.LAVA_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeBottle(new ItemStack(bottleLava, 2), Items.LAVA_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeBottle(new ItemStack(bottleLava, 3), Items.LAVA_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeBottle(new ItemStack(bottleMilk), Items.MILK_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeBottle(new ItemStack(bottleMilk, 2), Items.MILK_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeBottle(new ItemStack(bottleMilk, 3), Items.MILK_BUCKET));

        GameRegistry.addRecipe(new ShapelessPotionRecipe(new ItemStack(bottleRedstone), PotionTypes.WATER, Items.REDSTONE));
        GameRegistry.addRecipe(new ShapelessPotionRecipe(new ItemStack(bottleGlowstone), PotionTypes.WATER, Items.GLOWSTONE_DUST));
        GameRegistry.addRecipe(new ShapelessPotionRecipe(new ItemStack(bottleGunpowder), PotionTypes.WATER, Items.GUNPOWDER));

        GameRegistry.registerFuelHandler(new FuelHandler(bottleLava, 6600));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(bottleLava);
        UModelLoader.setCustomModelResourceLocation(bottleMilk);
        UModelLoader.setCustomModelResourceLocation(bottleRedstone);
        UModelLoader.setCustomModelResourceLocation(bottleGlowstone);
        UModelLoader.setCustomModelResourceLocation(bottleGunpowder);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-MoreBottles");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, bottleLava, "Bottle of Lava");
        language.register(LanguageResourceManager.JA_JP, bottleLava, "溶岩入り瓶");
        language.register(LanguageResourceManager.EN_US, bottleMilk, "Bottle of Milk");
        language.register(LanguageResourceManager.JA_JP, bottleMilk, "牛乳入り瓶");
        language.register(LanguageResourceManager.EN_US, bottleRedstone, "Potion of Extend");
        language.register(LanguageResourceManager.JA_JP, bottleRedstone, "延長のポーション");
        language.register(LanguageResourceManager.EN_US, bottleGlowstone, "Potion of Change");
        language.register(LanguageResourceManager.JA_JP, bottleGlowstone, "変換のポーション");
        language.register(LanguageResourceManager.EN_US, bottleGunpowder, "Potion of Spread");
        language.register(LanguageResourceManager.JA_JP, bottleGunpowder, "伝播のポーション");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, bottleLava, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, bottleMilk, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, bottleRedstone, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, bottleGlowstone, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, bottleGunpowder, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
