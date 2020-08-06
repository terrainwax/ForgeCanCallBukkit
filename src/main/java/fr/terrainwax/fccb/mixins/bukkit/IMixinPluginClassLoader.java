package fr.terrainwax.fccb.mixins.bukkit;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.lang.reflect.Method;


@Mixin(targets = "org.bukkit.plugin.java.PluginClassLoader", remap = false)
public class IMixinPluginClassLoader {

    @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
    public void injectToConstructor(JavaPluginLoader loader, ClassLoader parent, PluginDescriptionFile description, File dataFolder, File file, CallbackInfo ci)
    {
        if (parent != null && parent instanceof LaunchClassLoader)
        {
            try {
                Method methode = parent.getClass().getDeclaredMethod("addChild", ClassLoader.class);
                methode.invoke(parent, this);
            }catch(Exception e)
            {

            }
        }
    }
}
