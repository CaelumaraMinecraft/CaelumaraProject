package top.auspice.api.annotations.bookmark;

import java.lang.annotation.*;

@Target({
        ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Bookmarks {
    Bookmark[] value();
}
