package com.company.common.services.jersey;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.*;

@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({PARAMETER, METHOD})
public @interface ResourceList {

}
