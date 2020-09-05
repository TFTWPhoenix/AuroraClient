/*******************************************************************************
 * Copyright � 2019 | Crimz8n (Rafal Zelazko) | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/

package havook.handler;

import org.lwjgl.input.Mouse;

import havook.Havook;
import havook.manager.FriendManager;
import havook.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;

public class MouseInputHandler {
	private boolean isFriendClickActive = false;

	@SubscribeEvent
	public void onMouseInput(MouseInputEvent event) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			if (!isFriendClickActive && Mouse.isButtonDown(2) && Minecraft.getMinecraft().objectMouseOver != null) {
				isFriendClickActive = true;
				Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
				if (entity == null)
					return;
				if (entity instanceof EntityPlayer) {
					if (entity.isDead || ((EntityLivingBase) entity).getHealth() < 0)
						return;
					if (entity == Minecraft.getMinecraft().player)
						return;
				} else
					return;

				if (!FriendManager.isFriend(entity.getName())) {
					if (FriendManager.addFriend(entity.getName())) {
						ChatUtil.info("Added \2477" + entity.getName() + "\247e to friends.");
					}
				} else {
					if (FriendManager.removeFriend(entity.getName())) {
						ChatUtil.info("Removed \2477" + entity.getName() + " \247efrom friends.");
					}
				}
			} else if (!Mouse.isButtonDown(2)) {
				isFriendClickActive = false;
			}
		}
	}
}
