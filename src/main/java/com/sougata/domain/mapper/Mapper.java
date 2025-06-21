package com.sougata.domain.mapper;

import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.shared.MasterEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final EntityDtoMapping entityDtoMapping;
    private final EntityManager entityManager;

    public MasterEntity toEntity(MasterDto dto) {
        if (dto == null) {
            return null;
        }

        try {
            Class<? extends MasterEntity> entityClass = entityDtoMapping.getDtoToEntityMap().get(dto.getClass());
            MasterEntity entity = entityClass.getDeclaredConstructor().newInstance();

            for (Field entityField : entity.getClass().getDeclaredFields()) {
                entityField.setAccessible(true);

                Field dtoField = dto.getClass().getDeclaredField(entityField.getName());
                dtoField.setAccessible(true);

                if (Collection.class.isAssignableFrom(entityField.getType())) {
                    Set<MasterEntity> set = new HashSet<>();
                    Object dtoValue = dtoField.get(dto);
                    if (dtoValue instanceof Collection) {
                        for (Object item : (Collection<?>) dtoValue) {
                            if (item instanceof MasterDto) {
                                MasterEntity itemEntity = (MasterEntity) entityManager.getReference(item.getClass(), ((MasterDto) item).getId());
                                set.add(itemEntity);
                            }
                        }
                    }
                    entityField.set(entity, set);
                } else if (isComplex(entityField)) {
                    Object dtoValue = dtoField.get(dto);
                    if (dtoValue == null) {
                        entityField.set(entity, null);
                    } else {
                        if (dtoValue instanceof MasterDto) {
                            Class<? extends MasterEntity> objectClass = entityDtoMapping.getDtoToEntityMap().get(dtoValue.getClass());
                            if (((MasterDto) dtoValue).getId() == null) {
                                entityField.set(entity, objectClass.getDeclaredConstructor().newInstance());
                            } else {
                                MasterEntity relation = entityManager.getReference(objectClass, ((MasterDto) dtoValue).getId());
                                entityField.set(entity, relation);
                            }
                        }
                    }

                } else if (!isComplex(entityField)) {
                    Object dtoValue = dtoField.get(dto);
                    if (dtoValue != null) {
                        entityField.set(entity, dtoValue);
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MasterDto toDto(MasterEntity entity) {
        if (entity == null) {
            return null;
        }

        try {
            Class<? extends MasterDto> dtoClass;
            if (entity instanceof HibernateProxy) {
                Class<? extends MasterEntity> actualClass = Hibernate.getClass(entity);
                dtoClass = entityDtoMapping.getEntityToDtoMap().get(actualClass);
            } else {
                dtoClass = entityDtoMapping.getEntityToDtoMap().get(entity.getClass());
            }
            MasterDto dto = dtoClass.getDeclaredConstructor().newInstance();

            for (Field dtoField : dto.getClass().getDeclaredFields()) {
                dtoField.setAccessible(true);

                Field entityField;
                if (entity instanceof HibernateProxy) {
                    entity = (MasterEntity) Hibernate.unproxy(entity);
                    entityField = getDeclaredField(entity.getClass(), dtoField.getName());
                } else {
                    entityField = entity.getClass().getDeclaredField(dtoField.getName());
                }
                entityField.setAccessible(true);
                if (Collection.class.isAssignableFrom(dtoField.getType())) {
                    Set<MasterEntity> set = new HashSet<>();
                    Object entityValues = entityField.get(entity);
                    if (entityValues instanceof Collection) {
                        for (Object item : (Collection<?>) entityValues) {
                            if (item instanceof MasterEntity) {
                                Object mappedItem = mapObject(item, entityDtoMapping.getEntityToDtoMap().get(dtoField.getClass()));
                                set.add((MasterEntity) mappedItem);
                            }
                        }
                    }
                    entityField.set(entity, set);
                } else if (isComplex(dtoField)) {
                    Object entityValue = entityField.get(entity);
                    if (entityValue != null) {
                        Object mappedDto = mapObject(entityValue, dtoField.getClass());
                        dtoField.set(dto, mappedDto);
                    }
                } else if (!isComplex(dtoField)) {
                    Object entityValue = entityField.get(entity);
                    if (entityValue != null) {
                        dtoField.set(dto, entityValue);
                    }
                }
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MasterEntity merge(MasterEntity nu, MasterEntity og) {
        if (nu == null) return og;
        if (og == null) return null;

        try {
            for (Field ogField : og.getClass().getDeclaredFields()) {
                ogField.setAccessible(true);

                Field nuField = nu.getClass().getDeclaredField(ogField.getName());
                nuField.setAccessible(true);

                if (Collection.class.isAssignableFrom(ogField.getType())) {
                    Object nuValue = nuField.get(nu);
                    Object ogValue = ogField.get(nu);

                    if (nuValue != null) {
                        //noinspection unchecked
                        Collection<MasterEntity> nuCollection = (Collection<MasterEntity>) nuValue;
                        //noinspection unchecked
                        Collection<MasterEntity> ogCollection = (Collection<MasterEntity>) ogValue;

                        Map<Long, MasterEntity> nuMap = nuCollection.stream().collect(Collectors.toMap(MasterEntity::getId, e -> e));
                        Map<Long, MasterEntity> ogMap = ogCollection.stream().collect(Collectors.toMap(MasterEntity::getId, e -> e));

                        HashSet<MasterEntity> insertSet = new HashSet<>();
                        for (MasterEntity obj : nuCollection) {
                            if (obj != null) {
                                if (!ogMap.containsKey(obj.getId())) {
                                    MasterEntity managedEntity = entityManager.getReference(obj.getClass(), obj.getId());
                                    if (managedEntity != null) {
                                        insertSet.add(managedEntity);
                                    }
                                }
                            }
                        }

                        ogCollection.addAll(insertSet);

                        // delete
                        HashSet<MasterEntity> deleteSet = new HashSet<>();
                        for (MasterEntity obj : ogCollection) {
                            if (obj != null) {
                                if (!nuMap.containsKey(obj.getId())) {
                                    deleteSet.add(obj);
                                }
                            }
                        }
                        deleteSet.forEach(ogCollection::remove);
                    }
                } else if (isComplex(ogField)) {
                    MasterEntity relation = (MasterEntity) nuField.get(nu);
                    if (relation != null) {
                        if (relation.getId() == null) {
                            ogField.set(nu, null);
                        } else if (relation.getId() != null) {
                            Object managedEntity = entityManager.getReference(ogField.getType(), relation.getId());
                            if (managedEntity instanceof MasterEntity) {
                                ogField.set(og, managedEntity);
                            }
                        }
                    }
                } else if (!isComplex(ogField)) {
                    Object nuValue = nuField.get(nu);
                    if (nuValue != null) {
                        ogField.set(nu, nuValue);
                    }
                }
            }
            return og;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private <T, S> T mapObject(S source, Class<T> targetClass) {
        if (source == null) return null;

        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            for (Field targetField : targetClass.getDeclaredFields()) {
                targetField.setAccessible(true);
                Field sourceField = source.getClass().getDeclaredField(targetField.getName());
                sourceField.setAccessible(true);

                if (Collection.class.isAssignableFrom(targetField.getType())) {
                    targetField.set(target, new HashSet<>());
                } else if (isComplex(targetField)) {
                    targetField.set(target, null);
                } else if (!isComplex(targetField)) {
                    Object sourceValue = sourceField.get(source);
                    if (sourceValue != null) {
                        targetField.set(target, sourceValue);
                    }
                }
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isComplex(Field f) {
        return !Integer.class.isAssignableFrom(f.getType()) && !Long.class.isAssignableFrom(f.getType()) &&
                !Double.class.isAssignableFrom(f.getType()) && !Boolean.class.isAssignableFrom(f.getType()) &&
                !Float.class.isAssignableFrom(f.getType()) && !String.class.isAssignableFrom(f.getType()) &&
                !LocalDateTime.class.isAssignableFrom(f.getType()) && !Timestamp.class.isAssignableFrom(f.getType()) &&
                !Date.class.isAssignableFrom(f.getType()) && !Enum.class.isAssignableFrom(f.getType());
    }

    private static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
