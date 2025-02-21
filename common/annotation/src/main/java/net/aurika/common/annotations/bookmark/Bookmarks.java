package net.aurika.common.annotations.bookmark;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE
})
public @interface Bookmarks {
    Bookmark[] value();
}
