package com.serjiosoft.themefrost.builder_intent;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by autoexec on 01.03.2017.
 */

@SuppressWarnings("unchecked")
public abstract class FragmentBuilder<I extends FragmentBuilder<I, F>, F> extends Builder {

    protected Bundle args;

    public FragmentBuilder() {
        args = new Bundle();
    }

    public abstract F build();

    public I arg(Bundle map) {
        args.putAll(map);
        return (I) this;
    }

    public I arg(String key, boolean value) {
        args.putBoolean(key, value);
        return (I) this;
    }

    public I arg(String key, byte value) {
        args.putByte(key, value);
        return (I) this;
    }

    public I arg(String key, char value) {
        args.putChar(key, value);
        return (I) this;
    }

    public I arg(String key, short value) {
        args.putShort(key, value);
        return (I) this;
    }

    public I arg(String key, int value) {
        args.putInt(key, value);
        return (I) this;
    }

    public I arg(String key, long value) {
        args.putLong(key, value);
        return (I) this;
    }

    public I arg(String key, float value) {
        args.putFloat(key, value);
        return (I) this;
    }

    public I arg(String key, double value) {
        args.putDouble(key, value);
        return (I) this;
    }

    public I arg(String key, String value) {
        args.putString(key, value);
        return (I) this;
    }

    public I arg(String key, CharSequence value) {
        args.putCharSequence(key, value);
        return (I) this;
    }

    public I arg(String key, Parcelable value) {
        args.putParcelable(key, value);
        return (I) this;
    }

    public I arg(String key, Parcelable[] value) {
        args.putParcelableArray(key, value);
        return (I) this;
    }

    public I parcelableArrayListArg(String key, ArrayList<? extends Parcelable> value) {
        args.putParcelableArrayList(key, value);
        return (I) this;
    }

    public I arg(String key, SparseArray<? extends Parcelable> value) {
        args.putSparseParcelableArray(key, value);
        return (I) this;
    }

    public I integerArrayListArg(String key, ArrayList<Integer> value) {
        args.putIntegerArrayList(key, value);
        return (I) this;
    }

    public I stringArrayListArg(String key, ArrayList<String> value) {
        args.putStringArrayList(key, value);
        return (I) this;
    }

    public I arg(String key, Serializable value) {
        args.putSerializable(key, value);
        return (I) this;
    }

    public I arg(String key, boolean[] value) {
        args.putBooleanArray(key, value);
        return (I) this;
    }

    public I arg(String key, byte[] value) {
        args.putByteArray(key, value);
        return (I) this;
    }

    public I arg(String key, short[] value) {
        args.putShortArray(key, value);
        return (I) this;
    }

    public I arg(String key, char[] value) {
        args.putCharArray(key, value);
        return (I) this;
    }

    public I arg(String key, int[] value) {
        args.putIntArray(key, value);
        return (I) this;
    }

    public I arg(String key, long[] value) {
        args.putLongArray(key, value);
        return (I) this;
    }

    public I arg(String key, float[] value) {
        args.putFloatArray(key, value);
        return (I) this;
    }

    public I arg(String key, double[] value) {
        args.putDoubleArray(key, value);
        return (I) this;
    }

    public I arg(String key, String[] value) {
        args.putStringArray(key, value);
        return (I) this;
    }

    public I arg(String key, Bundle value) {
        args.putBundle(key, value);
        return (I) this;
    }

    public Bundle args() {
        return args;
    }
}
