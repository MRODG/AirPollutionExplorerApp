package com.mariosodigie.apps.airpollutionexplore.utils


@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

/**
 * Annotation for classes to be used in mocks for testing.
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting