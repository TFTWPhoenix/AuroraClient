/*******************************************************************************
 * Copyright � 2019 | Crimz8n (Rafal Zelazko) | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/

package havook.feature.command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import havook.feature.Command;
import havook.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CopyDataCommand extends Command {
	public CopyDataCommand() {
		super("copydata", ".copydata", "Generates give command from held item's data.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length > 0) {
			ChatUtil.warning("Too many arguments.");
		}
		ItemStack stack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
		if (stack.isEmpty()) {
			ChatUtil.error("You must hold an item in your hand.");
			return;
		}
		int id = Item.getIdFromItem(stack.getItem());
		int amount = stack.getCount();
		int metadata = stack.getMetadata();
		String nbt = "";
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound().toString();
			nbt = nbt.replace("&", "&&").replace("\247", "&");
			nbt = " " + nbt;
		}

		String command = ".give " + id + " " + amount + " " + metadata + nbt;

		StringSelection selection = new StringSelection(command);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		ChatUtil.info("The command has been copied to clipboard.");

	}
}
