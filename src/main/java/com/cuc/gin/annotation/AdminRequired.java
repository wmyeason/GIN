package com.cuc.gin.annotation;

import java.lang.annotation.*;

/**
 * @author : Wang SM.
 * @since : 2024/3/9, 周一
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminRequired {
}
