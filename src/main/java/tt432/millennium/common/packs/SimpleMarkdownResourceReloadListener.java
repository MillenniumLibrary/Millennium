package tt432.millennium.common.packs;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Map -> 文件名 : 内容
 *
 * @author DustW
 */
public abstract class SimpleMarkdownResourceReloadListener extends SimplePreparableReloadListener<Map<ResourceLocation, String>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PATH_SUFFIX = ".md";
    private static final int PATH_SUFFIX_LENGTH = ".md".length();
    private final String directory;

    protected SimpleMarkdownResourceReloadListener(String directory) {
        this.directory = directory;
    }

    @Override
    protected @NotNull Map<ResourceLocation, String> prepare(ResourceManager pResourceManager, @NotNull ProfilerFiller profiler) {
        Map<ResourceLocation, String> map = Maps.newHashMap();
        int i = this.directory.length() + 1;

        for (ResourceLocation resourcelocation : pResourceManager.listResources(this.directory, file -> file.endsWith(PATH_SUFFIX))) {
            String s = resourcelocation.getPath();
            ResourceLocation name = new ResourceLocation(resourcelocation.getNamespace(), s.substring(i, s.length() - PATH_SUFFIX_LENGTH));

            try (Resource resource = pResourceManager.getResource(resourcelocation)) {
                try (InputStream inputstream = resource.getInputStream()) {
                    String content = IOUtils.toString(inputstream, StandardCharsets.UTF_8);
                    map.put(name, content);
                }
            } catch (IOException exception) {
                LOGGER.error("Couldn't parse data file {} from {}", name, resourcelocation);
            }
        }

        return map;
    }
}
