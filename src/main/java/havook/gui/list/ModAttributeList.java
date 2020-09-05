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
import havook.feature.mod.ModAttribute;
import havook.feature.mod.ModAttributeBoolean;
import havook.feature.mod.ModAttributeDouble;
import havook.feature.mod.ModAttributeString;
import havook.manager.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class ModAttributeList extends GuiScrollingList {
	private List<ModAttribute> ENTRIES = new ArrayList<ModAttribute>();
	public ModAttribute selection = null;

	public ModAttributeList(int left, int top, int width, int height) {
		super(Minecraft.getMinecraft(), width, height, top, top + height, left, 25);
	}

	public void updateEntries(Mod mod) {
		selection = null;
		ENTRIES.clear();
		for (ModAttribute attribute : mod.ATTRIBUTES) {
			ENTRIES.add(attribute);
		}
	}

	@Override
	protected void drawSlot(int slotIndex, int x, int y, int arg3, Tessellator tesselator) {
		ModAttribute attribute = ENTRIES.get(slotIndex);

		String type = "";
		if (attribute instanceof ModAttributeBoolean)
			type = "\247d";
		else if (attribute instanceof ModAttributeDouble)
			type = "\2479";
		else if (attribute instanceof ModAttributeString)
			type = "\247a";

		Minecraft.getMinecraft().ingameGUI.drawCenteredString(Minecraft.getMinecraft().fontRenderer,
				type + attribute.name, left + listWidth / 2,
				slotHeight / 2 + y - (int) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 1.5), 0xffffff);
	}

	@Override
	protected void elementClicked(int slotIndex, boolean doubleClick) {
		ModAttribute entry = ENTRIES.get(slotIndex);
		selection = entry;
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
