package com.devfill.checkman_mvp.base.dagger;

public interface ActivityComponent<A> {
    void inject(A activity);
}
