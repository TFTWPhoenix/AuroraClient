/*******************************************************************************
 * Copyright � 2019 | Crimz8n (Rafal Zelazko) | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/

package havook.feature;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import havook.feature.mod.ModAttribute;
import havook.feature.mod.ModCategoryEnum;
import havook.manager.ModManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public abstract class Mod {
	protected boolean enabled = false;
	public String id = "";
	public transient String name = "";
	public transient String description = "";
	public transient ModCategoryEnum category = null;
	@SerializedName("attributes")
	public List<ModAttribute> ATTRIBUTES = new ArrayList<ModAttribute>();

	public Mod(String id, String name, String description, ModCategoryEnum category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
	}

	public boolean isEnabled() {
		return enabled;

	}

	public void setEnabled(boolean enabled) {
		boolean lastState = this.enabled;
		this.enabled = enabled;
		if (enabled && lastState != enabled)onEnable();
		else onDisable();
		ModManager.saveMods();
	}

	public void addAttrib(ModAttribute attrib) {
		ATTRIBUTES.add(attrib);
	}

	public ModAttribute getAttribByName(String name) {
		for (ModAttribute attrib : ATTRIBUTES) {
			if (attrib.name.equalsIgnoreCase(name)) {
				return attrib;
			}
		}
		return null;
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public void onLocalPlayerUpdate() {
	}

	public void onRenderWorldLast(float partialTicks) {
	}

	public void onPlaySound(PlaySoundEvent event) {
	}
	
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
	}
	
	public void onRenderLivingSpecialsPre(RenderLivingEvent.Specials.Pre event) {
	}
}
