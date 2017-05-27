package net.geht.mc.forge.pymcfo;

import net.geht.mc.forge.pymcfo.misc.Harvest;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.minecraftforge.common.MinecraftForge.*;

@Mod(modid = pymcfo.MODID, version = pymcfo.VERSION)
public class pymcfo
  {
  public static final String MODID = Version.NAME;
  public static final String VERSION = Version.VERSION;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event)
    {
      EVENT_BUS.register(new Harvest());
      System.out.println("Harvest registered");
    }

  @EventHandler
  public void init(FMLInitializationEvent event) throws InterruptedException
    {
      System.out.println("starting: "+MODID);

      new DirectoryExecutor(Config.PYMCFO_INIT).join();
      System.out.println("startup complete: "+MODID);
    }
  }
