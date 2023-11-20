package com.example.demo.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntitySample is a Querydsl query type for EntitySample
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntitySample extends EntityPathBase<EntitySample> {

    private static final long serialVersionUID = -654895303L;

    public static final QEntitySample entitySample = new QEntitySample("entitySample");

    public final DateTimePath<java.time.LocalDateTime> createdDt = createDateTime("createdDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath roles = createString("roles");

    public QEntitySample(String variable) {
        super(EntitySample.class, forVariable(variable));
    }

    public QEntitySample(Path<? extends EntitySample> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntitySample(PathMetadata metadata) {
        super(EntitySample.class, metadata);
    }

}

