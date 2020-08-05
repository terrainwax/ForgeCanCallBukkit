package fr.terrainwax.fccb;

import net.minecraft.launchwrapper.Launch;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPluginLoader;


import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mod(modid = "fccb", name = "ForgeCanCallBukkit", version = "1.0.0", acceptableRemoteVersions = "*")
public class ForgeCanCallBukkit  {

    @Mod.EventHandler
    public void onServerLoaded(FMLServerStartedEvent event)
    {
        try {
            Field f = SimplePluginManager.class.getDeclaredField("fileAssociations");
            f.setAccessible(true);
            Map m = (Map) f.get(Bukkit.getPluginManager());
            m.forEach((pattern, pluginLoader) -> {
                try {
                    Field all = JavaPluginLoader.class.getDeclaredField("loaders");
                    all.setAccessible(true);
                    List loaders = (List) all.get(pluginLoader);
                    loaders.forEach((loader) -> {
                        Method methode = null;
                        try {
                            Field floaded = loader.getClass().getDeclaredField("classes");
                            floaded.setAccessible(true);
                            Map allLoaded = (Map) floaded.get(loader);
                            Field invalideField = LaunchClassLoader.class.getDeclaredField("invalidClasses");
                            invalideField.setAccessible(true);
                            Set invalide = (Set) invalideField.get(this.getClass().getClassLoader());
                            invalide.removeAll(allLoaded.keySet());
                            methode = this.getClass().getClassLoader().getClass().getDeclaredMethod("addChild", ClassLoader.class);
                            methode.invoke(this.getClass().getClassLoader(), loader);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
