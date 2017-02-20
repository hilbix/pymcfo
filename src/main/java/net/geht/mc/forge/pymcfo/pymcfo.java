package net.geht.mc.forge.pymcfo;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

@Mod(modid = pymcfo.MODID, version = pymcfo.VERSION)
public class pymcfo
  {
  public static final String MODID = Config.NAME;
  public static final String VERSION = Config.VERSION;

  @EventHandler
  public void init(FMLInitializationEvent event)
    {
      File dir = new File(Config.PYMCFO_SCRIPTS);

      for (File py : dir.listFiles(new CaseInsensitiveSuffixFileFilter(Config.PY_EXTENSION)))
        {
          try
            {
              new CodeExecutor(py);
            } catch (FileNotFoundException e)
            {
              e.printStackTrace();
            }
        }
      System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
  }
