package eyeq.morebottles.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import eyeq.util.item.UItemBottle;

public class ItemBottleExtend extends UItemBottle {
    @Override
    protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
        super.onFoodEaten(itemStack, world, player);
        if(world.isRemote) {
            return;
        }
        for(PotionEffect potion : player.getActivePotionEffects()) {
            PotionEffect newPotion = new PotionEffect(potion.getPotion(), potion.getDuration() + 200, potion.getAmplifier());
            player.addPotionEffect(newPotion);
        }
    }
}
