package com.game.itstar.dialect;


import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.engine.jdbc.dialect.internal.DialectResolverSet;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

/**
 * @Author 朱斌
 * @Date 2019/9/24  10:18
 * @Desc SQLite工具
 */
public class SQLiteMetadataBuilderInitializer implements MetadataBuilderInitializer {


    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry serviceRegistry) {
        DialectResolver dialectResolver = serviceRegistry.getService(DialectResolver.class);

        if (!(dialectResolver instanceof DialectResolverSet)) {
            return;
        }

        ((DialectResolverSet) dialectResolver).addResolver(resolver);
    }

    static private final SQLiteDialect dialect = new SQLiteDialect();

    static private final DialectResolver resolver = (DialectResolver) info -> {
        if (info.getDatabaseName().equals("SQLite")) {
            return dialect;
        }

        return null;
    };

}
