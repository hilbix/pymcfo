package net.geht.mc.forge.pymcfo;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = pymcfo.MODID, version = pymcfo.VERSION)
public class pymcfo
  {
  public static final String MODID = "pymcfo";
  public static final String VERSION = "0.0.0";

  @EventHandler
  public void init(FMLInitializationEvent event)
    {
      // some example CodeExecutor
      System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
  }
