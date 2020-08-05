package fr.terrainwax.fccb;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class ForgeCanCallBukkit implements IFMLLoadingPlugin {

    public ForgeCanCallBukkit() throws Exception {
        try {
            this.getClass().getClassLoader().getClass().getDeclaredMethod("addChild", ClassLoader.class);
        } catch (NoSuchMethodException e) {
            throw new Exception("You need to install custom Minecraft LaunchWrapper - see github readme");
        }
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.fccb.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
