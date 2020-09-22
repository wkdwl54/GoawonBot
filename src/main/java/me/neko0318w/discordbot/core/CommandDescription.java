package me.neko0318w.discordbot.core;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandDescription {

    String name();

    String description() default "";

    String[] triggers();

    long[] access() default {};
}