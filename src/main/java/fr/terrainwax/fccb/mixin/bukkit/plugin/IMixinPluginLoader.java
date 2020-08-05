package fr.terrainwax.fccb.mixin.bukkit.plugin;

import fr.terrainwax.fccb.IAddChild;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.bukkit.plugin.Plugin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.lang.reflect.Method;

@Mixin(org.bukkit.plugin.java.JavaPluginLoader.class)
public abstract class IMixinPluginLoader {

    @Inject(method = "loadPlugin", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    public void preAdd(File file, CallbackInfoReturnable<Plugin> cir, ClassLoader loader)
    {
        if (this.getClass().getClassLoader() != null && this.getClass().getClassLoader() instanceof LaunchClassLoader)
        {
            try {
                Method methode = this.getClass().getClassLoader().getClass().getDeclaredMethod("addChild", ClassLoader.class);
                methode.invoke(this.getClass().getClassLoader(), loader);
            }catch(Exception e)
            {

            }
        }
    }
}
