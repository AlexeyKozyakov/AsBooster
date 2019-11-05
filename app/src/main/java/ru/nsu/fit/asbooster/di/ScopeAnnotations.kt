package ru.nsu.fit.asbooster.di

import javax.inject.Scope

/**
 * Scope bound to activity lifecycle.
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScoped

/**
 * Scope bound to fragment lifecycle.
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScoped