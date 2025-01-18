package top.auspice.utils.number;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public final class NumberProcessor {
    @NotNull
    private final String string;
    @NotNull
    private final Set<NumberFailReason> failedConstraints;
    @NotNull
    private Set<? extends NumberConstraint> constraints;
    @NotNull
    private Set<? extends NumberDecorator> decorators;
    private AnyNumber _number;
    private boolean processed;
    @NotNull
    private static final Set<NumberDecorator> ALL_DECORATORS = ArraysKt.toSet(NumberDecorator.values());

    public NumberProcessor(@NotNull String string) {
        Objects.requireNonNull(string, "string");
        this.string = string;
        this.failedConstraints = new HashSet<>();
        this.constraints = new HashSet<>();
        this.decorators = new HashSet<>();
    }

    @NotNull
    public String getString() {
        return this.string;
    }

    @NotNull
    public Set<NumberFailReason> getFailedConstraints() {
        return this.failedConstraints;
    }

    @NotNull
    public AnyNumber getNumber() {
        if (!this.failedConstraints.isEmpty()) {
            throw new IllegalArgumentException("Number processor has failed");
        } else {
            if (this._number == null) {
                throw new IllegalStateException("lateinit property " + "_number" + " has not been initialized");
            }

            return this._number;
        }
    }

    @NotNull
    public NumberProcessor withConstraints(@NotNull NumberConstraint... constraints) {
        Objects.requireNonNull(constraints);
        this.constraints = ArraysKt.toSet(constraints);
        return this;
    }

    @NotNull
    public NumberProcessor withAllDecorators() {
        this.decorators = ALL_DECORATORS;
        return this;
    }

    @NotNull
    public NumberProcessor withDecorators(@NotNull NumberDecorator... decorators) {
        Objects.requireNonNull(decorators);
        this.decorators = ArraysKt.toSet(decorators);
        return this;
    }

    @NotNull
    public NumberFailReason getMostImportantFailure() {
        if ((this.failedConstraints).isEmpty()) {
            throw new IllegalArgumentException("Number processor did not fail");
        } else {
            Iterator<NumberFailReason> iterator$iv = this.failedConstraints.iterator();
            NumberFailReason var10000;
            if (!iterator$iv.hasNext()) {
                var10000 = null;
            } else {
                NumberFailReason minElem$iv = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    var10000 = minElem$iv;
                } else {
                    NumberFailReason it = minElem$iv;
                    int minValue$iv = it.ordinal();

                    do {
                        NumberFailReason e$iv = iterator$iv.next();
                        int v$iv = e$iv.ordinal();
                        if (minValue$iv > v$iv) {
                            minElem$iv = e$iv;
                            minValue$iv = v$iv;
                        }
                    } while(iterator$iv.hasNext());

                    var10000 = minElem$iv;
                }
            }

            Objects.requireNonNull(var10000);
            return var10000;
        }
    }

    public boolean isSuccessful() {
        if (!this.processed) {
            String var2 = this.string + " is not processed yet";
            throw new IllegalArgumentException(var2);
        } else {
            return this.failedConstraints.isEmpty();
        }
    }

    private void fail(NumberFailReason reason) {
        this.failedConstraints.add(reason);
    }

    public void process() {
        if (this.processed) {
            String var7 = this.string + " is already processed";
            throw new IllegalArgumentException(var7);
        } else {
            this.processed = true;
            Number mod = null;
            String it = this.string;
            String str = it;
            if (this.decorators.contains(NumberDecorator.SUFFIX)) {
                String var10000;
                if (StringsKt.endsWith(str, 'K', true)) {
                    mod = 1000;
                    var10000 = str.substring(0, str.length() - 1);
                    Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
                } else {
                    var10000 = str;
                }

                str = var10000;
            }

            if (this.decorators.contains(NumberDecorator.COMMA)) {
                str = StringsKt.replace(str, ",", "", false);
            }

            it = str;
            AnyNumber var8 = NumberType.INT.parseString(it);
            if (var8 == null) {
                var8 = NumberType.DOUBLE.parseString(it);
            }

            AnyNumber _number = var8;
            if (_number == null) {
                this.fail(NumberFailReason.NOT_A_NUMBER);
            } else {
                if (mod == null) {
                    this._number = _number;
                } else {
                    this._number = _number.times(AnyNumber.getAbstractNumber(mod.intValue()));
                }

                if (_number.getType() != NumberType.INT && this.constraints.contains(NumberConstraint.INTEGER_ONLY)) {
                    this.fail(NumberFailReason.INTEGER_ONLY);
                }

                if (_number.compareTo(AnyNumber.of(_number.getType().getMinValue())) <= 0 || _number.compareTo(AnyNumber.of(_number.getType().getMaxValue())) >= 0) {
                    this.fail(NumberFailReason.OUT_OF_BOUNDS);
                }

                if (this.constraints.contains(NumberConstraint.POSITIVE) && _number.compareTo(AnyNumber.of(0)) <= 0) {
                    this.fail(NumberFailReason.POSITIVE);
                }

                if (this.constraints.contains(NumberConstraint.ZERO_OR_POSITIVE) && _number.compareTo(AnyNumber.of(0)) < 0) {
                    this.fail(NumberFailReason.ZERO_OR_POSITIVE);
                }

            }
        }
    }

}
