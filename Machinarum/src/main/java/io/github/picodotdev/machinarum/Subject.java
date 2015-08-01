package io.github.picodotdev.machinarum;

public interface Subject<S> {

    S getState();
    void setState(S status);
}