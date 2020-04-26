package io.emeraldpay.pjc.scale.reader;

import io.emeraldpay.pjc.scale.ScaleReader;
import io.emeraldpay.pjc.scale.ScaleCodecReader;
import io.emeraldpay.pjc.scale.UnionValue;

import java.util.*;

public class UnionReader<T> implements ScaleReader<UnionValue<T>> {

    private List<ScaleReader<Object>> mapping;

    public UnionReader(List<ScaleReader<Object>> mapping) {
        this.mapping = mapping;
    }

    @SuppressWarnings("unchecked")
    public UnionReader(ScaleReader<Object>... mapping) {
        this(Arrays.asList(mapping));
    }

    @Override
    @SuppressWarnings("unchecked")
    public UnionValue<T> read(ScaleCodecReader rdr) {
        int index = rdr.readUByte();
        if (mapping.size() <= index) {
            throw new IllegalStateException("Unknown type index: " + index);
        }
        T value = (T) mapping.get(index).read(rdr);
        return new UnionValue<>(index, value);
    }
}
