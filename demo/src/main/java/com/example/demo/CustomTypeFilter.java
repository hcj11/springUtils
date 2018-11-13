package com.example.demo;

import com.example.demo.core.validate.etc.DefaultValidateImpl;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于实体的扫描注入方式。
 * Create by houchunjian on 2018/10/21 0021
 */
public class CustomTypeFilter implements TypeFilter {

    private static final List<Object> lists = new ArrayList<>();

    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(metadataReader.getClassMetadata().getClassName());
            Object o = aClass.newInstance();
            Object isValidator = aClass.getMethod("isValidator").invoke(o);
            // 是校验器，
            if ((Boolean) isValidator) {
                lists.add(o);
                // 注入到容器中
                return true;
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
        }
        // 将实现类注入到DefaultValidateImpl 的list中
        boolean equals = metadataReader.getClassMetadata().getClassName().equals(DefaultValidateImpl.class.getName());

        if (equals) {
            CachingMetadataReaderFactory cachingMetadataReaderFactory = (CachingMetadataReaderFactory) metadataReaderFactory;

            GenericApplicationContext context = (GenericApplicationContext) cachingMetadataReaderFactory.getResourceLoader();
            RootBeanDefinition beanDefinition = new RootBeanDefinition();

            // 构建beanDefinition
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(lists);
            // 根类
            beanDefinition.setBeanClass(DefaultValidateImpl.class);

            if (aClass != null) {
                context.registerBeanDefinition(aClass.getSimpleName(), beanDefinition);
            }
        }
        return equals;

    }


}
