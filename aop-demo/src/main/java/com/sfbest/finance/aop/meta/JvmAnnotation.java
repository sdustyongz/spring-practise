package com.sfbest.finance.aop.meta;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

public class JvmAnnotation {

    public  static void main(String[] args) {
        for (Annotation anno : MetaTest.class.getAnnotations()) {
            System.out.println(anno);
        }
        for (Annotation declaredAnnotation : MetaTest.class.getDeclaredAnnotations()) {
            System.out.println(declaredAnnotation);
        }

        StandardAnnotationMetadata mete = new StandardAnnotationMetadata(MetaTest.class);
        for (MergedAnnotation<Annotation> annotation : mete.getAnnotations()) {
            System.out.println(annotation);
        }
    }
}
