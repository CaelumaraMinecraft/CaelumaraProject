package net.aurika.kingdoms.auspice.costs;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.peacetreaties.data.WarPoint;
import net.aurika.kingdoms.auspice.costs.statistics.CurrencyEntry;
import net.aurika.kingdoms.auspice.util.NumberUtils;
import net.aurika.kingdoms.auspice.util.RelatedKingdoms;

public final class StandardCurrencies {
  public static final KingdomBankCurrency KINGDOM_BANK = new KingdomBankCurrency();
  public static final KingdomResourcePointsCurrency KINGDOM_RESOURCE_POINTS = new KingdomResourcePointsCurrency();
  public static final KingdomMaxLandsModifierCurrency KINGDOM_MAX_LANDS_MODIFIER = new KingdomMaxLandsModifierCurrency();

  public static final NationBankCurrency NATION_BANK = new NationBankCurrency();
  public static final NationResourcePointsCurrency NATION_RESOURCE_POINTS = new NationResourcePointsCurrency();

  public static final RelatedKingdomsWarPoints RELATED_KINGDOMS_WAR_POINTS = new RelatedKingdomsWarPoints();

  public static class KingdomBankCurrency implements Currency<Kingdom, Long> {

    @NotNull
    @Override
    public String getKey() {
      return "kingdom-bank";
    }

    @NotNull
    @Override
    public Class<Kingdom> getTargetClass() {
      return Kingdom.class;
    }

    @Override
    public CurrencyEntry<Kingdom, Long> getAmount(String amountString) {
      return new CurrencyEntry<>(this, Long.parseLong(amountString));
    }

    @Override
    public CurrencyEntry<Kingdom, Long> getAmount(Object amount) {
      Long i = NumberUtils.parseLong(amount);
      if (i != null) {
        return new CurrencyEntry<>(this, i);
      }
      return null;
    }

    @Override
    public boolean canExpend(Kingdom target, Long amount) {
      return target.getBank().has(amount);
    }

    @Override
    public void forceExpend(Kingdom target, Long amount) {
      target.getBank().subtract(amount);
    }

    @Override
    public boolean canRefund(Kingdom target, Long amount) {
      return true;
    }

    @Override
    public void forceRefund(Kingdom target, Long amount) {
      target.getBank().add(amount);
    }

  }

  public static class KingdomResourcePointsCurrency implements Currency<Kingdom, Long> {

    public KingdomResourcePointsCurrency() {
      super();
    }

    @NotNull
    @Override
    public String getKey() {
      return "kingdom-resource-points";
    }

    @NotNull
    @Override
    public Class<Kingdom> getTargetClass() {
      return Kingdom.class;
    }

    @Override
    public CurrencyEntry<Kingdom, Long> getAmount(String amountString) {
      return new CurrencyEntry<>(this, Long.parseLong(amountString));
    }

    @Override
    public CurrencyEntry<Kingdom, Long> getAmount(Object amount) {
      Long i = NumberUtils.parseLong(amount);
      if (i != null) {
        return new CurrencyEntry<>(this, i);
      }
      return null;
    }

    @Override
    public boolean canExpend(Kingdom target, Long amount) {
      return target.getResourcePoints().has(amount);
    }

    @Override
    public void forceExpend(Kingdom target, Long amount) {
      target.getResourcePoints().subtract(amount);
    }

    @Override
    public boolean canRefund(Kingdom target, Long amount) {
      return true;
    }

    @Override
    public void forceRefund(Kingdom target, Long amount) {
      target.getResourcePoints().add(amount);
    }

  }

  public static class KingdomMaxLandsModifierCurrency implements Currency<Kingdom, Integer> {

    @NotNull
    @Override
    public String getKey() {
      return "kingdom-max-lands-modifier";
    }

    @NotNull
    @Override
    public Class<Kingdom> getTargetClass() {
      return Kingdom.class;
    }

    @Override
    public CurrencyEntry<Kingdom, Integer> getAmount(String amountString) {
      return new CurrencyEntry<>(this, Integer.parseInt(amountString));
    }

    @Override
    public CurrencyEntry<Kingdom, Integer> getAmount(Object amount) {
      return new CurrencyEntry<>(this, NumberUtils.parseInt(amount));
    }

    @Override
    public boolean canExpend(Kingdom target, @NotNull Integer amount) {
      return target.getMaxLandsModifier() >= amount;
    }

    @Override
    public void forceExpend(Kingdom target, @NotNull Integer amount) {
      target.setMaxLandsModifier(target.getMaxLandsModifier() - amount);
    }

    @Override
    public boolean canRefund(Kingdom target, @NotNull Integer amount) {
      return true;
    }

    @Override
    public void forceRefund(Kingdom target, @NotNull Integer amount) {
      target.setMaxLandsModifier(target.getMaxLandsModifier() + amount);
    }

  }

  public static class NationBankCurrency implements Currency<Nation, Long> {

    @NotNull
    @Override
    public String getKey() {
      return "nation-bank";
    }

    @NotNull
    @Override
    public Class<Nation> getTargetClass() {
      return Nation.class;
    }

    @Override
    public CurrencyEntry<Nation, Long> getAmount(String amountString) {
      return new CurrencyEntry<>(this, Long.parseLong(amountString));
    }

    @Override
    public CurrencyEntry<Nation, Long> getAmount(Object amount) {
      Long i = NumberUtils.parseLong(amount);
      if (i != null) {
        return new CurrencyEntry<>(this, i);
      }
      return null;
    }

    @Override
    public boolean canExpend(Nation target, Long amount) {
      return target.getBank().has(amount);
    }

    @Override
    public void forceExpend(Nation target, Long amount) {
      target.getBank().subtract(amount);
    }

    @Override
    public boolean canRefund(Nation target, Long amount) {
      return true;
    }

    @Override
    public void forceRefund(Nation target, Long amount) {
      target.getBank().add(amount);
    }

  }

  public static class NationResourcePointsCurrency implements Currency<Nation, Long> {

    private NationResourcePointsCurrency() {
      super();
    }

    @NotNull
    @Override
    public String getKey() {
      return "nation-resource-points";
    }

    @NotNull
    @Override
    public Class<Nation> getTargetClass() {
      return Nation.class;
    }

    @Override
    public CurrencyEntry<Nation, Long> getAmount(String amountString) {
      return new CurrencyEntry<>(this, Long.parseLong(amountString));
    }

    @Override
    public CurrencyEntry<Nation, Long> getAmount(Object amount) {
      Long i = NumberUtils.parseLong(amount);
      if (i != null) {
        return new CurrencyEntry<>(this, i);
      }
      return null;
    }

    @Override
    public boolean canExpend(Nation target, Long amount) {
      return target.getResourcePoints().has(amount);
    }

    @Override
    public void forceExpend(Nation target, Long amount) {
      target.getResourcePoints().subtract(amount);
    }

    @Override
    public boolean canRefund(Nation target, Long amount) {
      return true;
    }

    @Override
    public void forceRefund(Nation target, Long amount) {
      target.getResourcePoints().add(amount);
    }

  }

  public static class RelatedKingdomsWarPoints implements Currency<RelatedKingdoms, Double> {

    @NotNull
    @Override
    public String getKey() {
      return "related-kingdoms-war-points";
    }

    @NotNull
    @Override
    public Class<RelatedKingdoms> getTargetClass() {
      return RelatedKingdoms.class;
    }

    @Override
    public CurrencyEntry<RelatedKingdoms, Double> getAmount(String amountString) {
      return new CurrencyEntry<>(this, Double.parseDouble(amountString));
    }

    @Override
    public CurrencyEntry<RelatedKingdoms, Double> getAmount(Object amount) {
      Double i = NumberUtils.parseDouble(amount);
      if (i != null) {
        return new CurrencyEntry<>(this, i);
      }
      return null;
    }

    @Override
    public boolean canExpend(RelatedKingdoms target, Double amount) {
      return WarPoint.hasWarPoints(target.getActiveKingdom(), target.getPassiveKingdom(), amount);
    }

    @Override
    public void forceExpend(RelatedKingdoms target, Double amount) {
      WarPoint.addWarPoints(target.getActiveKingdom(), target.getPassiveKingdom(), -amount);
    }

    @Override
    public boolean canRefund(RelatedKingdoms target, Double amount) {
      return true;
    }

    @Override
    public void forceRefund(RelatedKingdoms target, Double amount) {
      WarPoint.addWarPoints(target.getActiveKingdom(), target.getPassiveKingdom(), amount);
    }

  }

}
