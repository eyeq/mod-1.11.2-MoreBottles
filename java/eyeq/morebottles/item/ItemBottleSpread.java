package eyeq.morebottles.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import eyeq.util.item.UItemBottle;

public class ItemBottleSpread extends UItemBottle {
    @Override
    protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
        super.onFoodEaten(itemStack, world, player);
        if(world.isRemote) {
            return;
        }
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().expand(4.0, 2.0, 4.0));
        for(PotionEffect potion : player.getActivePotionEffects()) {
            for(EntityLivingBase entity : entities) {
                PotionEffect newPotion = new PotionEffect(potion.getPotion(), potion.getDuration() / 20, potion.getAmplifier());
                entity.addPotionEffect(newPotion);
            }
        }
    }
}
