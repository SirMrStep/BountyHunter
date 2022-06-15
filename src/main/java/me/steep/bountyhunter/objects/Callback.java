package me.steep.bountyhunter.objects;

public interface Callback<V extends Object, T extends Throwable> {
    public void call(V result, T thrown);
}
