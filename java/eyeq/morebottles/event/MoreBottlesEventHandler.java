package eyeq.morebottles.event;

import eyeq.morebottles.MoreBottles;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MoreBottlesEventHandler {
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        if(player.getEntityWorld().isRemote || player.isCreative()) {
            return;
        }
        if(!(event.getTarget() instanceof EntityCow)) {
            return;
        }

        EnumHand hand = event.getHand();
        ItemStack itemStack = player.getHeldItem(hand);
        if(itemStack.getItem() != Items.GLASS_BOTTLE) {
            return;
        }
        ItemStack milk = new ItemStack(MoreBottles.bottleMilk);
        itemStack.shrink(1);
        if(itemStack.getCount() < 1) {
            player.setHeldItem(hand , milk);
        } else if(!player.inventory.addItemStackToInventory(milk)) {
            player.dropItem(milk, false);
        }
    }
}
