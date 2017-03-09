package com.zyberholes.procon.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;

import com.zyberholes.procon.ProConMod;
import com.zyberholes.procon.entities.EntityThrowablePart;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowablePart.class, new RenderSnowball(ProConMod.itemBolt));
	}
	
}
