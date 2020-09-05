/*******************************************************************************
 * Copyright � 2019 | Crimz8n (Rafal Zelazko) | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/

package havook.gui.list;

import java.util.ArrayList;
import java.util.List;

import havook.feature.Mod;
import havook.manager.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class ModList extends GuiScrollingList {
	private List<Mod> ENTRIES = new ArrayList<Mod>();
	public Mod selection = null;
	public boolean hasSelectionUpdated = false;

	public ModList(int left, int top, int width, int height) {
		super(Minecraft.getMinecraft(), width, height, top, top + height, left, 25);
		updateEntries("");
	}

	public void updateEntries(String query) {
		selection = null;
		ENTRIES.clear();
		for (Mod mod : ModManager.MODS) {
			String compare = (mod.name + ";" + mod.description).replaceAll("\\s+", "").toLowerCase();
			if (compare.contains(query.replaceAll("\\s+", "").toLowerCase()))
				ENTRIES.add(mod);
		}
	}

	@Override
	protected void drawSlot(int slotIndex, int x, int y, int arg3, Tessellator tesselator) {
		Mod mod = ENTRIES.get(slotIndex);

		String state;
		if (mod.isEnabled())
			state = "\247a";
		else
			state = "\247c";
		Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer, state + mod.name,
				left + listWidth / 2,
				slotHeight / 2 + y - (int) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 1.5), 0xff00ff);
	}

	@Override
	protected void elementClicked(int slotIndex, boolean doubleClick) {
		Mod entry = ENTRIES.get(slotIndex);
		selection = entry;
		hasSelectionUpdated = true;

		if (doubleClick)
			entry.setEnabled(!entry.isEnabled());
	}

	@Override
	protected int getSize() {
		return ENTRIES.size();
	}

	@Override
	protected boolean isSelected(int slotIndex) {
		return ENTRIES.get(slotIndex) == selection;
	}

	@Override
	protected void drawBackground() {
	}
}
