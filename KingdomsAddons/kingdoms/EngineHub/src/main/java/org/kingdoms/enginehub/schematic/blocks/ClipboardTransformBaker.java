/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.kingdoms.enginehub.schematic.blocks;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.transform.BlockTransformExtent;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.math.transform.CombinedTransform;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Helper class to 'bake' a transform into a clipboard.
 *
 * @see Clipboard
 * @see Transform
 */
public class ClipboardTransformBaker {
  private final Clipboard original;
  private final Transform transform;

  /**
   * Create a new instance.
   *
   * @param original  the original clipboard
   * @param transform the transform
   */
  private ClipboardTransformBaker(Clipboard original, Transform transform) {
    checkNotNull(original);
    checkNotNull(transform);
    this.original = original;
    this.transform = transform;
  }

  /**
   * Get the transformed region.
   *
   * @return the transformed region
   */
  private Region getTransformedRegion() {
    Region region = original.getRegion();
    Vector3 minimum = region.getMinimumPoint().toVector3();
    Vector3 maximum = region.getMaximumPoint().toVector3();

    Transform transformAround =
        new CombinedTransform(
            new AffineTransform().translate(original.getOrigin().multiply(-1)),
            transform,
            new AffineTransform().translate(original.getOrigin()));

    Vector3[] corners = new Vector3[]{
        minimum,
        maximum,
        minimum.withX(maximum.getX()),
        minimum.withY(maximum.getY()),
        minimum.withZ(maximum.getZ()),
        maximum.withX(minimum.getX()),
        maximum.withY(minimum.getY()),
        maximum.withZ(minimum.getZ())
    };

    for (int i = 0; i < corners.length; i++) {
      corners[i] = transformAround.apply(corners[i]);
    }

    Vector3 newMinimum = corners[0];
    Vector3 newMaximum = corners[0];

    for (int i = 1; i < corners.length; i++) {
      newMinimum = newMinimum.getMinimum(corners[i]);
      newMaximum = newMaximum.getMaximum(corners[i]);
    }

    // After transformation, the points may not really sit on a block,
    // so we should expand the region for edge cases
    newMinimum = newMinimum.floor();
    newMaximum = newMaximum.ceil();

    return new CuboidRegion(newMinimum.toBlockPoint(), newMaximum.toBlockPoint());
  }

  /**
   * Create an operation to copy from the original clipboard to the given extent.
   *
   * @param target the target
   * @return the operation
   */
  private Operation copyTo(Extent target) {
    BlockTransformExtent extent = new BlockTransformExtent(original, transform);
    ForwardExtentCopy copy = new ForwardExtentCopy(extent, original.getRegion(), original.getOrigin(), target, original.getOrigin());
    copy.setTransform(transform);
    if (original.hasBiomes()) {
      copy.setCopyingBiomes(true);
    }
    return copy;
  }

  /**
   * Create a new instance to bake the transform with.
   *
   * @param original  the original clipboard
   * @param transform the transform
   * @return a builder
   * @throws WorldEditException if an error occurred during copy
   */
  public static Clipboard bakeTransform(Clipboard original, Transform transform) throws WorldEditException {
    if (transform.isIdentity()) {
      return original;
    }
    ClipboardTransformBaker baker = new ClipboardTransformBaker(original, transform);
    Clipboard target = new BlockArrayClipboard(baker.getTransformedRegion());
    target.setOrigin(original.getOrigin());
    Operations.complete(baker.copyTo(target));

    return target;
  }

  public static Clipboard bakeTransform(ClipboardHolder holder) throws WorldEditException {
    return bakeTransform(holder.getClipboard(), holder.getTransform());
  }

}
