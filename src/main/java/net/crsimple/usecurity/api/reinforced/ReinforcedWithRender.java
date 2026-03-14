package net.crsimple.usecurity.api.reinforced;

import net.minecraft.client.render.RenderLayer;

public interface ReinforcedWithRender extends Reinforced {
    RenderLayer getRenderLayer();

    interface Cutout extends ReinforcedWithRender {
        @Override
        default RenderLayer getRenderLayer() {
            return RenderLayer.getCutout();
        }
    }
    interface Translucent extends ReinforcedWithRender {
        @Override
        default RenderLayer getRenderLayer() {
            return RenderLayer.getTranslucent();
        }
    }
}
