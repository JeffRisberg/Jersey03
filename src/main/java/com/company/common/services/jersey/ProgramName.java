package com.company.common.services.jersey;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ PARAMETER, METHOD })
public @interface ProgramName {
}
