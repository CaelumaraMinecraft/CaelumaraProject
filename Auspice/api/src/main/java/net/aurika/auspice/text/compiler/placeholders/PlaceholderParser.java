package net.aurika.auspice.text.compiler.placeholders;

import kotlin.ranges.IntRange;
import net.aurika.auspice.configs.messages.placeholders.FunctionalPlaceholder;
import net.aurika.auspice.configs.messages.placeholders.KingdomsPlaceholderTranslator;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.compiler.builders.TextObjectBuilder;
import net.aurika.auspice.text.compiler.placeholders.PlaceholderLexer.CharTokenType;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderFunctionData;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderFunctionParameters;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderNamedFunctionParameters;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderPositionalFunctionParameters;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import net.aurika.auspice.text.compiler.placeholders.types.ExternalOrLocalPlaceholder;
import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.auspice.text.compiler.placeholders.types.PermissionPlaceholder;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.util.collection.CursorList;
import net.aurika.util.unsafe.fn.Fn;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.*;

public final class PlaceholderParser {
    private boolean a = false;
    private boolean b;
    private String c;
    private String d;
    private String e;
    private PlaceholderFunctionData f;
    private final List<PlaceholderModifier> g = new ArrayList<>();
    private final PlaceholderLexer h;
    private PlaceholderLexer.Token i;
    private final CursorList<PlaceholderLexer.Token> j;
    private final a k = new a();

    public Phase getPhase() {
        if (!this.g.isEmpty()) {
            return PlaceholderParser.Phase.MODIFIER;
        } else if (this.f != null) {
            return PlaceholderParser.Phase.FUNCTION;
        } else {
            return this.c != null ? PlaceholderParser.Phase.POINTER : PlaceholderParser.Phase.NONE;
        }
    }

    public int getEndIndex() {
        return this.h.getIndex();
    }

    public String getFullString() {
        return this.h.getFullString();
    }

    private PlaceholderParser(PlaceholderLexer var1) {
        this.h = var1;
        this.j = new CursorList<>(var1.getTokens());
    }

    public static Placeholder parse(String var0) {
        return parse(var0, false);
    }

    public static Placeholder parse(String var0, boolean var1) {
        PlaceholderParser var2 = parse(0, var0.toCharArray(), false);
        var2.b = var1;
        return var2.getPlaceholder();
    }

    public static PlaceholderParser parse(int indexOfChars, char[] chars, boolean var2) {
        PlaceholderLexer var3 = new PlaceholderLexer(indexOfChars, chars, var2);
        return switch (var3.getResult()) {
            case NOT_A_PLACEHOLDER, CLOSING_CLOSURE_NOT_FOUND -> null;
            default -> new PlaceholderParser(var3);
        };
    }

    public String collectSeparatedStrings() {
        if (!this.j.hasNext()) {
            this.a(this.j.current(), "Placeholder ended unexpectedly, can't collect.");
        }

        StringBuilder var1 = new StringBuilder(50);
        boolean var2 = false;

        while (this.j.hasNext()) {
            PlaceholderLexer.Token var3;
            if ((var3 = this.j.next()) instanceof PlaceholderLexer.StringToken) {
                if (var2) {
                    this.a(this.j.previous(), "Expected a separator after string");
                }

                var1.append(((PlaceholderLexer.StringToken) var3).getString());
                var2 = true;
            } else {
                PlaceholderLexer.CharToken var4 = (PlaceholderLexer.CharToken) var3;
                var2 = false;
                if (var4.getType() != CharTokenType.SEPARATOR) {
                    this.j.previous();
                    break;
                }

                var1.append('_');
            }
        }

        return var1.toString();
    }

    public void collectModifiers() {
        while (true) {
            int var1 = this.j.getCursor();
            if (!(this.j.current() instanceof PlaceholderLexer.StringToken)) {
                return;
            }

            String var2 = this.collectSeparatedStrings();
            CharTokenType var4 = CharTokenType.MODIFIER;
            if (!this.a(var4, null)) {
                this.j.setCursor(var1);
                return;
            }

            PlaceholderModifier var3 = PlaceholderModifier.get(var2);
            this.k.b = new IntRange(var1, this.j.getCursor());
            if (var3 != null) {
                this.g.add(var3);
            } else {
                this.a(this.i, "Unknown placeholder modifier: " + var2);
            }
        }
    }

    private <T extends PlaceholderLexer.Token> T a(Class<T> var1) {
        if (!this.j.hasNext()) {
            return null;
        } else {
            T var2 = Fn.safeCast(this.i = this.j.next(), var1);
            if (var2 == null) {
                this.j.previous();
            }

            return var2;
        }
    }

    private String a(String var1) {
        PlaceholderLexer.StringToken var2 = this.a(PlaceholderLexer.StringToken.class);
        if (var2 == null && var1 != null) {
            this.a(this.i, var1);
        }

        return var2 == null ? null : var2.getString();
    }

    private boolean a(CharTokenType var1, String var2) {
        PlaceholderLexer.CharToken var3;
        if ((var3 = this.a(PlaceholderLexer.CharToken.class)) == null && var2 != null) {
            this.a(this.i, var2);
        }

        if (var3 != null && var3.getType() == var1) {
            return true;
        } else {
            this.j.previous();
            return false;
        }
    }

    private RuntimeException a(PlaceholderLexer.Token var1, String var2) {
        throw new PlaceholderParseException(var2 + " for token " + var1 + " at " + var1.getStartIndex() + " in placeholder '" + this.h.getFullString() + '\'');
    }

    private void a(int var1, int var2, String var3) {
        throw new PlaceholderParseException(var3 + " at " + var1 + " '" + this.h.getFullString().substring(var1, var2) + "' in placeholder '" + this.h.getFullString() + '\'');
    }

    private void a(boolean var1) {
        int var2 = this.j.getCursor();
        String var3;
        if ((var3 = this.a((String) null)) != null) {
            if (var3.equals("other")) {
                this.c = "other";
                if (!var1) {
                    this.a(this.j.previous(), "'other' modifier not expected here");
                }

                this.a(CharTokenType.SEPARATOR, "Expected placeholder ID after legacy pointer");
            } else {
                Optional<PlaceholderModifier> var4 = PlaceholderModifier.getRegistered().values().stream().filter((var1x) -> var1x.getName().equals(var3)).findFirst();
                if (var4.isPresent()) {
                    PlaceholderModifier var5 = var4.get();
                    this.g.add(var5);
                    this.a(CharTokenType.SEPARATOR, "Expected placeholder ID after legacy modifier");
                } else {
                    this.j.setCursor(var2);
                }
            }
        }
    }

    private <T extends Placeholder> T b(Class<T> var1) {
        String var2 = this.h.getFullString();
        if (var1 == ExternalOrLocalPlaceholder.class) {
            return Fn.cast(new ExternalOrLocalPlaceholder(var2, this.a, this.d, this.e, this.c, this.g));
        } else if (var1 == PermissionPlaceholder.class) {
            if (this.a) {
                this.a(this.k.a, "Permission placeholders can't be relational");
            }

            if (this.e == null) {
                this.a(this.j.current(), "No permission specified for permission placeholder");
            }

            return Fn.cast(new PermissionPlaceholder(var2, this.e, this.c, this.g));
        } else if (var1 == KingdomsPlaceholder.class) {
            if (this.a) {
                this.a(this.k.a, "Kingdoms placeholders should not have relational prefix");
            }

            KingdomsPlaceholderTranslator var3;
            if ((var3 = KingdomsPlaceholderTranslator.getByName(this.e)) == null) {
                this.a(this.j.previous(), "Unknown kingdoms placeholder with parameter '" + this.e + '\'');
            }

            return Fn.cast(new KingdomsPlaceholder(var2, this.c, this.g, var3, this.f));
        } else {
            throw this.a(this.j.previous(), "Unknown requested placeholder type '" + var1 + '\'');
        }
    }

    private Placeholder a() {
        boolean var1 = this.b || this.d.equals("kingdoms");
        if (this.b && this.e == null) {
            this.a(this.j.previous(), "Can't assume kingdoms placeholder with no parameter set, id=" + this.d);
        }

        if (this.e == null && this.d.equals("perm")) {
            this.a(this.j.previous(), "Missing permission placeholder parameters");
        }

        if (var1 && this.e != null) {
            return this.b(KingdomsPlaceholder.class);
        } else {
            return this.d.equals("perm") ? this.b(PermissionPlaceholder.class) : this.b(ExternalOrLocalPlaceholder.class);
        }
    }

    private void b(String var1) {
        if (!this.j.hasNext()) {
            this.a(this.j.previous(), var1);
        }
    }

    public Placeholder getPlaceholder() {
        String var3 = this.a("Placeholder starts with unexpected character");
        String var8;
        Placeholder var10000;
        if (!this.j.hasNext()) {
            if (this.b) {
                this.d = "kingdoms";
                this.e = var3;
            } else {
                this.d = var3;
            }

            var10000 = this.a();
        } else {
            this.j.setCursor(0);
            int var6;
            String var7;
            CharTokenType var10;
            String var10001;
            if (!this.j.hasNext(2)) {
                var10001 = null;
            } else {
                var6 = this.j.getCursor();
                var7 = this.collectSeparatedStrings();
                var10 = CharTokenType.POINTER;
                if (!this.a(var10, null)) {
                    this.j.setCursor(var6);
                    var10001 = null;
                } else {
                    var10001 = var7;
                }
            }

            this.c = var10001;
            if (this.c != null && !this.c.equals("other")) {
                this.a(this.j.get(0), "Unknown pointer for target '" + this.c + "' only 'other' is supported.");
            }

            this.b("No ID specified for the placeholder");
            this.collectModifiers();
            this.b("No ID specified for the placeholder");
            var6 = this.j.getCursor();
            boolean var28;
            if ((var7 = this.a((String) null)) == null) {
                var28 = false;
            } else {
                this.k.a = (PlaceholderLexer.StringToken) this.i;
                if (var7.equals("rel")) {
                    var10 = CharTokenType.SEPARATOR;
                    if (!this.a(var10, null)) {
                        this.j.setCursor(var6);
                        var28 = false;
                    } else {
                        var28 = true;
                    }
                } else {
                    this.j.setCursor(var6);
                    var28 = false;
                }
            }

            label195:
            {
                this.a = var28;
                this.a(false);
                boolean var4;
                if (this.b) {
                    var4 = true;
                    this.d = "kingdoms";
                } else {
                    this.d = this.a("No ID specified for the placeholder");
                    if (!(var4 = this.d.equals("kingdoms")) && this.d.equalsIgnoreCase("kingdoms")) {
                        this.a(this.i, "Kingdoms placeholders must be 'kingdoms' not '" + this.d + "' (case-sensitive)");
                    }

                    if (!this.j.hasNext()) {
                        break label195;
                    }

                    var10 = CharTokenType.SEPARATOR;
                    this.a(var10, null);
                }

                if (var4) {
                    this.a(true);
                }

                if (this.j.hasNext()) {
                    this.e = this.collectSeparatedStrings();
                    if (this.j.hasNext()) {
                        if (var4) {
                            PlaceholderParser var5 = this;
                            var10 = CharTokenType.FUNCTION_START;
                            if (this.a(var10, null)) {
                                String var21 = this.a("Expected a placeholder function name");
                                int var24 = this.j.getCursor();
                                var8 = this.a("Expected placeholder function parameters");
                                var10 = CharTokenType.FUNCTION_ARGUMENT_SEPARATOR;
                                boolean var1;
                                if (this.a(var10, null)) {
                                    var1 = true;
                                } else {
                                    var10 = CharTokenType.FUNCTION_ARGUMENT_EQUAL;
                                    if (!this.a(var10, null) && this.j.hasNext()) {
                                        throw this.a(this.i, "Unexpected character in function parameters '" + var21 + "' - '" + var8 + "' - " + this.j.current());
                                    }

                                    var1 = false;
                                }

                                this.j.setCursor(var24);
                                PlaceholderFunctionParameters var11;
                                String var15;
                                if (var1) {
                                    ArrayList<String> var14 = new ArrayList<>();
                                    var11 = new PlaceholderPositionalFunctionParameters(var14);

                                    while (true) {
                                        var15 = var5.a("Expected parameter value");
                                        var14.add(var15);
                                        if (!var5.j.hasNext()) {
                                            break;
                                        }

                                        var10 = CharTokenType.FUNCTION_ARGUMENT_SEPARATOR;
                                        if (!var5.a(var10, null)) {
                                            var10 = CharTokenType.FUNCTION_ARGUMENT_EQUAL;
                                            if (var5.a(var10, null)) {
                                                throw var5.a(var5.i, "Cannot use mix named and positional placeholder function parameters");
                                            }
                                            break;
                                        }
                                    }
                                } else {
                                    LinkedHashMap<String, String> var13 = new LinkedHashMap<>();
                                    var11 = new PlaceholderNamedFunctionParameters(var13);

                                    while (true) {
                                        var15 = var5.a("Expected parameter name");
                                        var10 = CharTokenType.FUNCTION_ARGUMENT_EQUAL;
                                        if (!var5.a(var10, null)) {
                                            var10 = CharTokenType.FUNCTION_ARGUMENT_SEPARATOR;
                                            if (var5.a(var10, null)) {
                                                throw var5.a(var5.i, "Cannot use mix named and positional placeholder function parameters");
                                            }

                                            throw var5.a(var5.i, "Expected parameter value assignment");
                                        }

                                        var7 = var5.collectSeparatedStrings();
                                        var13.put(var15, var7);
                                        if (!var5.j.hasNext()) {
                                            break;
                                        }

                                        var10 = CharTokenType.FUNCTION_ARGUMENT_SEPARATOR;
                                        if (!var5.a(var10, null)) {
                                            if (var5.j.current() instanceof PlaceholderLexer.StringToken) {
                                                var5.a(var5.j.current(), "Missing comma for function parameter");
                                            }
                                            break;
                                        }
                                    }
                                }

                                var5.f = new PlaceholderFunctionData(var21, var11);
                            }
                        }

                        if (this.j.hasNext() && this.j.hasNext()) {
                            this.a(this.j.next(), "Unknown final placeholder part");
                        }
                    }
                }
            }

            var10000 = this.a();
        }

        Placeholder var12 = var10000;
        PlaceholderParser var2 = this;
        if (this.c != null && !PlaceholderLexer.isNormal(this.c)) {
            this.a(this.j.get(0), "Unexpected character in placeholder pointer");
        }

        Optional<PlaceholderModifier> var17 = this.g.stream().filter((var0) -> !var0.isSupported(Boolean.class)).findFirst();
        if (this.d.equals("perm") && var17.isPresent()) {
            this.a(this.j.previous(), "Modifier '" + var17.get().getName() + "' is not supported for permission placeholders");
        }

        KingdomsPlaceholder var19;
        if (this.f != null && var12 instanceof KingdomsPlaceholder && (var19 = (KingdomsPlaceholder) var12).identifier.getFunctions() != null) {
            FunctionalPlaceholder.CompiledFunction var16 = var19.identifier.getFunctions().get(this.f.getFunctionName());
            if (var16 == null) {
                this.a(this.j.previous(), "Unknown function '" + this.f.getFunctionName() + "' for kingdoms placeholder '" + var19.identifier + '\'');
            }

            if (this.f.getParameters() instanceof PlaceholderNamedFunctionParameters var22) {

                for (FunctionalPlaceholder.Parameter parameter : var16.getParameters()) {
                    FunctionalPlaceholder.Parameter var26;
                    if (!(var26 = parameter).getOptional() && !var26.getType().isInternal() && !var22.getParameters().containsKey(var26.getName())) {
                        var2.a(var2.j.previous(), "Missing parameter '" + var26.getName() + '\'');
                    }
                }
            }
        }

        if (!var2.g.isEmpty()) {
            PlaceholderModifier var20 = null;
            Set<PlaceholderModifier> var18 = var12.getExpectedModifiers();

            for (PlaceholderModifier var27 : var2.g) {
                if (var18 != null && !var18.contains(var27)) {
                    var2.a(var2.k.b.getStart(), var2.k.b.getEndInclusive(), "Unexpected modifier '" + var27.getName() + '\'');
                }

                if (var20 == null) {
                    var20 = var27;
                } else {
                    var8 = var20.getName() + " (" + var20.getOutputType() + ") -> " + var27.getName() + " (" + var27.getOutputType() + ')';
                    switch (var20.compareCompatibilityWith(var27)) {
                        case COMPATIBLE:
                        case BEFORE:
                        default:
                            break;
                        case INCOMPATIBLE:
                            var2.a(var2.k.b.getStart(), var2.k.b.getEndInclusive(), "Incompatible placeholder modifiers " + var8);
                        case AFTER:
                            var2.a(var2.k.b.getStart(), var2.k.b.getEndInclusive(), "Incompatible placeholder modifiers, the order must be switched " + var8);
                    }
                }
            }
        }

        return var12;
    }

    public String toString() {
        return "PlaceholderParser{rel=" + this.a + ", pointer='" + this.c + '\'' + ", id='" + this.d + '\'' + ", parameter='" + this.e + '\'' + ", function=" + this.f + ", modifiers=" + this.g + '}';
    }

    @Nullable
    public static Map<String, Object> serializeVariables(boolean checkSpecialType, @Nullable Map<String, Object> container, @NonNull Object[] edits) {
        if (edits.length != 0) {
            if (container == null) {
                container = new HashMap<>(edits.length / 2);
            }

            int max = edits.length - 1;

            for (int indexToStr = 0; indexToStr < max; indexToStr += 2) {
                Object object = edits[indexToStr + 1];
                Objects.requireNonNull(object);
                if (!checkSpecialType && !object.getClass().isPrimitive() && !(object instanceof Messenger) && !(object instanceof TextObjectBuilder)) {
                    object = PlaceholderTranslationContext.withDefaultContext(object);
                }

                container.put(String.valueOf(edits[indexToStr]), object);
            }
        }
        return container;
    }

    public enum Phase {
        NONE(0),
        POINTER(1),
        FUNCTION(2),
        MODIFIER(3);
        private final int stage;

        Phase(int stage) {
            this.stage = stage;
        }

        public boolean hasPassed(Phase other) {
            return this.stage > other.stage;
        }
    }

    private static final class a {
        PlaceholderLexer.StringToken a;
        IntRange b;

        private a() {
        }
    }
}

