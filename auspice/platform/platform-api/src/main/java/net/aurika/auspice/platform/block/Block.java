package net.aurika.auspice.platform.block;

import net.aurika.auspice.platform.block.data.BlockData;
import net.aurika.auspice.platform.block.data.BlockDataAware;
import net.aurika.auspice.platform.block.type.BlockType;
import net.aurika.auspice.platform.block.type.BlockTypeAware;
import net.aurika.auspice.platform.location.Grid3Location;
import net.aurika.auspice.platform.location.Grid3LocationAware;
import net.aurika.auspice.platform.material.Material;
import net.aurika.auspice.platform.material.MaterialAware;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface Block extends BlockTypeAware, BlockDataAware, MaterialAware, Grid3LocationAware, BlockAware, Examinable {

  @ApiStatus.Experimental
  String VAL_LOCATION = "location";
  @ApiStatus.Experimental
  String VAL_TYPE = "type";
  @ApiStatus.Experimental
  String VAL_DATA = "data";
  @ApiStatus.Experimental
  String VAL_MATERIAL = "material";

  @Override
  @ExaminablePropertyGetter(VAL_TYPE)
  @NotNull BlockType blockType();

  @Override
  @ExaminablePropertyGetter(VAL_DATA)
  @NotNull BlockData blockData();

  /**
   * Sets the block data.
   *
   * @param blockData the new block data
   * @throws ClassCastException when the type of block data is wrong
   */
  void blockData(@NotNull BlockData blockData) throws ClassCastException;

  @Override
  @ExaminablePropertyGetter(VAL_MATERIAL)
  @NotNull Material material();

  @Override
  @NotNull World world();

  @Override
  int gridX();

  @Override
  int gridY();

  @Override
  int gridZ();

  @Override
  @ExaminablePropertyGetter(VAL_LOCATION)
  default @NotNull Grid3Location grid3Location() {
    return Grid3LocationAware.super.grid3Location();
  }

  @Override
  default @NotNull Block block() {
    return this;
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_LOCATION, grid3Location()),        // location
        ExaminableProperty.of(VAL_TYPE, blockType()),                // type
        ExaminableProperty.of(VAL_DATA, blockData()),                // data
        ExaminableProperty.of(VAL_MATERIAL, material())              // material
    );
  }

}
