package com.zyberholes.procon.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;

import com.zyberholes.procon.ProConMod;
import com.zyberholes.procon.entities.EntityBolt;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, new RenderSnowball(ProConMod.bolt));
	}
	
}
