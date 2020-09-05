/*******************************************************************************
 * Copyright � 2019 | Crimz8n (Rafal Zelazko) | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/

package havook.feature.mod.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import havook.feature.Mod;
import havook.feature.mod.ModAttributeBoolean;
import havook.feature.mod.ModCategoryEnum;
import havook.manager.FriendManager;
import havook.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class PlayerESPMod extends Mod {
	public transient ModAttributeBoolean tracers = new ModAttributeBoolean("Tracers", true);
	public transient ModAttributeBoolean showInvisibleEntities = new ModAttributeBoolean("ShowInvisibleEntities",
			true);

	private transient List<Entity> ENTITIES = new ArrayList<Entity>();
	private transient int BOX = 0;

	public PlayerESPMod() {
		super("playeresp", "Player ESP", "Highlights nearby players.", ModCategoryEnum.RENDER);

		addAttrib(tracers);
		addAttrib(showInvisibleEntities);
	}

	@Override
	public void onEnable() {
		BOX = GL11.glGenLists(1);
		GL11.glNewList(BOX, GL11.GL_COMPILE);
		RenderUtil.drawOutlinedBox(new AxisAlignedBB(-0.5, 0, -0.5, 0.5, 1, 0.5));
		GL11.glEndList();
	}

	@Override
	public void onDisable() {
		GL11.glDeleteLists(BOX, 1);
	}

	@Override
	public void onLocalPlayerUpdate() {
		ENTITIES.clear();
		for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {
			if (entity.isDead || entity.getHealth() < 0)
				continue;
			if (entity == Minecraft.getMinecraft().player)
				continue;
			if (entity.isInvisible() && !showInvisibleEntities.value)
				continue;
			ENTITIES.add(entity);
		}
	}

	@Override
	public void onRenderWorldLast(float partialTicks) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LINE_BIT | GL11.GL_CURRENT_BIT);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(2);

		GL11.glPushMatrix();
		GL11.glTranslated(-Minecraft.getMinecraft().getRenderManager().renderPosX,
				-Minecraft.getMinecraft().getRenderManager().renderPosY,
				-Minecraft.getMinecraft().getRenderManager().renderPosZ);

		RenderUtil.drawESPBoxes(ENTITIES, BOX, partialTicks);
		if (tracers.value)
			RenderUtil.drawESPTracers(ENTITIES);

		GL11.glPopMatrix();

		GL11.glPopAttrib();
	}
}
