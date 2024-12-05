package io.github.andrew6rant.bettertrimtooltips.mixin.client;

import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(ArmorTrim.class)
public abstract class ArmorTrimMixin {
	@Shadow
	@Final
	private boolean showInTooltip;

	@Shadow
	public abstract RegistryEntry<ArmorTrimPattern> getPattern();

	@Shadow
	public abstract RegistryEntry<ArmorTrimMaterial> getMaterial();

	/**
	 * @author Julienraptor01
	 * @reason change how the text is displayed in the tooltip until a more compatible method is found
	 */
	@Overwrite
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (showInTooltip) {
			Style style = this.getMaterial().value().description().getStyle();
			tooltip.accept(Text.literal("")
					.append(this.getPattern().value().description())
					.formatted(Formatting.GRAY)
					.append(ScreenTexts.space())
					.append(Text.literal("(")
							.setStyle(style)
							.append(this.getMaterial().value().description())
							.append(")")
							.setStyle(style)));
		}
	}
}