package net.aurika.auspice.gui.objects;

import com.cryptomorin.xseries.XSound;
import net.aurika.abstraction.conditional.ConditionChain;
import net.aurika.auspice.gui.objects.inventory.GUIInventoryConstructor;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.TextObject;
import net.aurika.configuration.adapter.YamlWithDefaults;
import net.aurika.text.messenger.Messenger;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GUIObject {

  private final String name;
  private final YamlWithDefaults config;
  private final TextObject c;
  private final GUIInventoryConstructor d;
  private final @Unmodifiable Map<String, GUIOptionBuilder> options;
  private final @Unmodifiable List<Integer> interactableSlots;
  private final @Unmodifiable List<String> commands;
  private final FormBuilderObject h;
  private final XSound.Record sound;
  private final ConditionChain<Messenger> j;
  private final boolean k;
  private final TextObject message;

  public GUIObject(String var1, YamlWithDefaults var2, String message) {
    this.name = var1;
    this.config = var2;
    this.message = TextCompiler.compile(message);
    this.d = null;
    this.k = false;
    this.sound = null;
    this.commands = null;
    this.interactableSlots = null;
    this.h = null;
    this.j = null;
    this.options = null;
    this.c = null;
  }

  public GUIObject(String var1, YamlWithDefaults var2, TextObject var3, FormBuilderObject var4, GUIInventoryConstructor var5, Map<String, GUIOptionBuilder> var6, List<Integer> var7, List<String> var8, XSound.Record var9, ConditionChain<Messenger> var10, boolean var11, TextObject var12) {
    this.j = var10;
    this.name = var1;
    this.config = var2;
    this.c = var3;
    this.h = var4;
    this.d = var5;
    this.options = Collections.unmodifiableMap(var6);
    this.interactableSlots = Collections.unmodifiableList(var7);
    this.commands = Collections.unmodifiableList(var8);
    this.sound = var9;
    this.k = var11;
    this.message = var12;
  }

  public boolean isBroken() {
    return this.d == null;
  }

  public Inventory buildBukkitInventory(Player var1, TextPlaceholderProvider var2) {
    return this.d.create(var1, this.c.buildPlain(var2));
  }

  public FormBuilder<?, ?, ?> buildBedrockForm(Player var1, TextPlaceholderProvider var2) {
    return this.h.build(var2);
  }

  public ConditionChain<Messenger> getOpenConditions() {
    return this.j;
  }

  public boolean hasFormBuilder() {
    return this.h != null;
  }

  public FormBuilderObject getForm() {
    return this.h;
  }

  public boolean isCreativeDisallowed() {
    return this.k;
  }

  public @Unmodifiable List<String> getCommands() {
    return this.commands;
  }

  public TextObject getMessage() {
    return this.message;
  }

  public XSound.Record getSound() {
    return this.sound;
  }

  public @Unmodifiable List<Integer> getInteractableSlots() {
    return this.interactableSlots;
  }

  public GUIOptionBuilder getOption(String var1) {
    return (GUIOptionBuilder) this.options.get(var1);
  }

  public @Unmodifiable Map<String, GUIOptionBuilder> getOptions() {
    return this.options;
  }

  public YamlWithDefaults getConfig() {
    return this.config;
  }

  public String getName() {
    return this.name;
  }

}
