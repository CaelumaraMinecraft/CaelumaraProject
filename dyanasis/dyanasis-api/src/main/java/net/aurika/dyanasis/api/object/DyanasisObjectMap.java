package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public interface DyanasisObjectMap extends DyanasisObjectSupport {

    /**
     * Gets the size of the dyanasis map.
     *
     * @return the size
     */
    default int size() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the pair at the {@code index}.
     *
     * @param index the index
     * @return the pair
     * @throws IndexOutOfBoundsException when the {@code index} is out of the bound of the map
     */
    default @NotNull DyanasisObjectPair atIndex(int index) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException();
    }

    default @NotNull DyanasisObject get(@NotNull DyanasisObject key) throws IndexOutOfBoundsException {
        LinkedHashMap<DyanasisObject, DyanasisObject> map = new LinkedHashMap<>();
        throw new UnsupportedOperationException();
    }

    @Override
    default @NotNull SupportType supportType() {
        return SupportType.MAP;
    }

    @Override
    @NotNull Object valueAsJava();
}
