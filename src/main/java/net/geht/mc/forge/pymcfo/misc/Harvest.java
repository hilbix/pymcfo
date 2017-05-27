/* shamelessly stolen at https://minecraft.curseforge.com/projects/gentle-harvest/
 * until this works in Python, too.
 */
package net.geht.mc.forge.pymcfo.misc;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Harvest
  {
  @SubscribeEvent
  public void onPlayerInteract(RightClickBlock event)
    {
      EntityPlayer player = event.getEntityPlayer();
      if (player==null) return;                         // No player

      World world = player.getEntityWorld();
      if (world==null || world.isRemote) return;        // Run on the client, so it runs on non-forge-servers, too

      if (player.capabilities.isCreativeMode) return;   // In creative mode we are not harvesting
      if (player.isSneaking()) return;                  // Ignore while sneaking (SHIFT-Click)

      if (event.getHand()!=EnumHand.MAIN_HAND) return;  // Must be left click

      BlockPos    pos   = event.getPos();
      IBlockState state = world.getBlockState(pos);
      Block       block = state.getBlock();

      if (!(block instanceof BlockCrops)) return;        // must be Crops type

//      BlockCrops crop    = (BlockCrops) data;
//      ItemStack  seed    = crop.getItem(world, pos, block);
//      if (seed==null || seed.getItem()==null) return;

      PropertyInteger age = block instanceof BlockBeetroot ? BlockBeetroot.BEETROOT_AGE : BlockCrops.AGE;

      if (tryDropAndResetBlock(world, player, pos, state, block, age))
        event.setCanceled(true);

      player.swingArm(EnumHand.MAIN_HAND);
    }

  @SubscribeEvent
  public void onBlockBreak(BreakEvent event)
    {
      EntityPlayer player = event.getPlayer();
      if (player==null) return;                         // No player

      World world = player.getEntityWorld();
      if (world==null || world.isRemote) return;        // Run on the client, so it runs on non-forge-servers, too

      if (player.capabilities.isCreativeMode) return;   // In creative mode we are not harvesting
      if (player.isSneaking()) return;                  // Ignore while sneaking (SHIFT-Click)

      BlockPos    pos   = event.getPos();
      IBlockState state = world.getBlockState(pos);
      Block       block = state.getBlock();

      if (!(block instanceof BlockCocoa)) return;        // must be Cocoa type

//      BlockCocoa cocoa   = (BlockCocoa) data;

      if (tryDropAndResetBlock(world, player, pos, state, block, BlockCocoa.AGE))
        event.setCanceled(true);

//      player.swingArm(EnumHand.MAIN_HAND);
    }

  private boolean tryDropAndResetBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, Block block,
                                       IProperty<Integer> property)
    {
      int maxAge = Collections.max(property.getAllowedValues());
      int age    = state.getValue(property);
      if (age<maxAge)
        return false;

      int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
      block.dropBlockAsItem(world, pos, state, fortune);
      world.setBlockState(pos, state.withProperty(property, 0));
      return true;
    }
  }
