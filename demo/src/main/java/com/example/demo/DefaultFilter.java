package com.example.demo;

import com.example.demo.core.validate.etc.DefaultValidateImpl;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class DefaultFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        boolean equals = metadataReader.getClassMetadata().getClass().equals(DefaultValidateImpl.class);
        return equals;
    }
}
