package com.loader.loadingmod.common.items;

import com.loader.loadingmod.client.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TeleportWand extends Item{
	public TeleportWand(Properties properties) {
        super(properties);
    }

    // adds a tool tip when you hover over the item in your inventory and press shift
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (KeyboardHelper.isHoldingShift()){
            tooltip.add(new StringTextComponent("teleports you where you're looking"));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        // only allow the player to use it every 3 seconds
        playerIn.getCooldowns().addCooldown(this, 60);

        // get where the player is looking and move them there
        Vector3d lookPos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE).getLocation();
        playerIn.setPos(lookPos.x, lookPos.y, lookPos.z);

        // allow the teleport to cancel fall damage
        playerIn.fallDistance = 0F;

       // reduce durability
        ItemStack stack = playerIn.getItemInHand(handIn);
        stack.setDamageValue(stack.getDamageValue() + 1);

       // break if durability gets to 0
        if (stack.getDamageValue() == 0) stack.setCount(0);

        return super.use(worldIn, playerIn, handIn);
    }

      // the same as Item.rayTrace(World, PlayerEntity, FluidMode) but with a longer range
    protected static RayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        double range = 10; // player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue();;

        float f = player.xRot;
        float f1 = player.yRot;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vector3d vec3d1 = vec3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return worldIn.clip(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }
}
