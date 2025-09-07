package com.sougata.domain.mapper;

import com.sougata.domain.domain.status.enums.WorkFlowActionType;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.shared.MasterEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RelationalMapper {
    private final EntityDtoMapping map;
    private final EntityManager entityManager;

    public MasterEntity mapToEntity(MasterDto dto) {
        if (dto == null) return null;

        try {
            MasterEntity res = null;

            Queue<ChildParentPair<MasterDto, MasterEntity>> queue = new LinkedList<>();
            Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());

            queue.add(new ChildParentPair<>(dto, null));
            visited.add(dto);
            while (!queue.isEmpty()) {
                ChildParentPair<MasterDto, MasterEntity> u = queue.poll();

                Class<? extends MasterEntity> entityClass = map.getDtoToEntityMap().get(u.child().getClass());
                MasterEntity e = entityClass.getDeclaredConstructor().newInstance();

                for (Field ef : e.getClass().getDeclaredFields()) {
                    ef.setAccessible(true);
                    Field df = u.child().getClass().getDeclaredField(ef.getName());
                    df.setAccessible(true);

                    if (Collection.class.isAssignableFrom(ef.getType())) {
                        Object dfValue = df.get(u.child());
                        if (dfValue != null) {
                            Collection<?> collection = (Collection<?>) dfValue;
                            for (Object obj : collection) {
                                if (obj instanceof MasterDto) {
                                    if (!visited.contains(obj)) {
                                        visited.add((MasterDto) obj);
                                        queue.add(new ChildParentPair<>((MasterDto) obj, e));
                                    }
                                }
                            }

                            // initialize an empty collection so that corresponding relation could be added here.
                            if (Set.class.isAssignableFrom(df.getType())) {
                                ef.set(e, new HashSet<>());
                            } else if (ArrayList.class.isAssignableFrom(df.getType())) {
                                ef.set(e, new ArrayList<>());
                            }
                        }
                    } else if (isComplex(df)) {
                        // setting manyToOne | oneToOne relations if any.
                        MasterDto dfValue = (MasterDto) df.get(u.child());
                        if (dfValue != null) {
                            MasterEntity relation = mapToEntity(dfValue);
                            ef.set(e, relation);
                        }
                    } else if (!isComplex(df)) {
                        // setting the simple data fields
                        Object dfValue = df.get(u.child());
                        if (dfValue != null) {
                            ef.set(e, dfValue);
                        }
                    }
                }

                // add the child relation to the parent's collection.
                if (u.parent() != null) {
                    for (Field pf : u.parent().getClass().getDeclaredFields()) {
                        pf.setAccessible(true);

                        if (Collection.class.isAssignableFrom(pf.getType())) {
                            Type genericType = pf.getGenericType();
                            if (genericType instanceof ParameterizedType) {
                                Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                                if (actualType == e.getClass()) {
                                    //noinspection unchecked
                                    Collection<MasterEntity> collection = (Collection<MasterEntity>) pf.get(u.parent());
                                    collection.add(e);
                                }
                            }
                        }
                    }
                } else {
                    // setting the converted entity reference to the result.
                    res = e;
                }
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MasterDto mapToDto(MasterEntity entity) {
        if (entity == null) return null;
        MasterDto res = null;
        try {
            Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
            Queue<ChildParentPair<MasterEntity, MasterDto>> queue = new LinkedList<>();

            queue.add(new ChildParentPair<>(entity, null));
            visited.add(entity);

            while (!queue.isEmpty()) {
                ChildParentPair<MasterEntity, MasterDto> u = queue.poll();
                Class<? extends MasterDto> dto = null;
                if (u.child() instanceof HibernateProxy) {
                    Class<? extends MasterEntity> actualClass = Hibernate.getClass(u.child());
                    dto = map.getEntityToDtoMap().get(actualClass);
                } else {
                    dto = map.getEntityToDtoMap().get(u.child().getClass());
                }
                MasterDto d = dto.getDeclaredConstructor().newInstance();

                for (Field df : d.getClass().getDeclaredFields()) {
                    df.setAccessible(true);
                    Field ef;
                    MasterEntity childClass = u.child();
                    if (u.child() instanceof HibernateProxy) {
                        childClass = (MasterEntity) Hibernate.unproxy(u.child());
                        ef = getDeclaredField(childClass.getClass(), df.getName());
                    } else {
                        ef = childClass.getClass().getDeclaredField(df.getName());
                    }
                    ef.setAccessible(true);

                    if (Collection.class.isAssignableFrom(df.getType())) {
                        Object efValue = ef.get(childClass);
                        if (efValue != null) {
                            Collection<?> collection = (Collection<?>) efValue;
                            for (Object obj : collection) {
                                if (obj instanceof MasterEntity) {
                                    if (!visited.contains(obj)) {
                                        visited.add((MasterEntity) obj);
                                        queue.offer(new ChildParentPair<>((MasterEntity) obj, d));
                                    }
                                }
                            }
                        }

                        if (Set.class.isAssignableFrom(df.getType())) {
                            df.set(d, new HashSet<>());
                        } else if (ArrayList.class.isAssignableFrom(df.getType())) {
                            ef.set(d, new ArrayList<>());
                        }
                    } else if (isComplex(df)) {
                        System.out.println("+++++++++++++++");
                        Object relation = ef.get(childClass);
                        if (relation instanceof HibernateProxy) {
                            relation = Hibernate.unproxy(relation);
                        }
                        System.out.println("RELATION=" + relation);
                        MasterDto relationInstance = null;
                        if (relation != null) {
                            relationInstance = (MasterDto) df.getType().getDeclaredConstructor().newInstance();
                            System.out.println("Relation Instance = " + relationInstance);
                            for (Field rif : relationInstance.getClass().getDeclaredFields()) {
                                Field rf = relation.getClass().getDeclaredField(rif.getName());
                                rf.setAccessible(true);
                                rif.setAccessible(true);
                                if (Collection.class.isAssignableFrom(rif.getType())) {
                                    rif.set(relationInstance, null);
                                } else if (isComplex(rif)) {
                                    rif.set(relationInstance, null);
                                } else if (!isComplex(rif)) {
                                    Object rfValue = rf.get(relation);
                                    rif.set(relationInstance, rfValue);
                                }
                            }
                        }
                        System.out.println("+++++++++++++++");
                        df.set(d, relationInstance);
                    } else if (!isComplex(df)) {
                        Object efValue = ef.get(childClass);
                        if (efValue != null) {
                            df.set(d, efValue);
                        }
                    }
                }

                if (u.parent() != null) {
                    for (Field pf : u.parent().getClass().getDeclaredFields()) {
                        pf.setAccessible(true);
                        if (Collection.class.isAssignableFrom(pf.getType())) {
                            Type genericType = pf.getGenericType();
                            if (genericType instanceof ParameterizedType) {
                                Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                                if (actualType == d.getClass()) {
                                    //noinspection unchecked
                                    Collection<MasterDto> collection = (Collection<MasterDto>) pf.get(u.parent());
                                    collection.add(d);
                                }
                            }
                        }
                    }
                } else {
                    res = d;
                }
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MasterEntity merge(MasterEntity nu, MasterEntity og) {
        if (og == null) return null;
        try {
            for (Field ogf : og.getClass().getDeclaredFields()) {
                ogf.setAccessible(true);
                Field nuf = nu.getClass().getDeclaredField(ogf.getName());
                nuf.setAccessible(true);

                if (Collection.class.isAssignableFrom(ogf.getType())) {
                    Object nufValue = nuf.get(nu);
                    Object ogfValue = ogf.get(og);
                    if (nufValue != null) {
                        //noinspection unchecked
                        Collection<MasterEntity> nuCollection = (Collection<MasterEntity>) nufValue;
                        //noinspection unchecked
                        Collection<MasterEntity> ogCollection = (Collection<MasterEntity>) ogfValue;

                        // pre process [insert, delete]
                        Map<Long, MasterEntity> nuMap = nuCollection.stream().collect(Collectors.toMap(MasterEntity::getId, e -> e));
                        Map<Long, MasterEntity> ogMap = ogCollection.stream().collect(Collectors.toMap(MasterEntity::getId, e -> e));

                        // insert
                        HashSet<MasterEntity> insertSet = new HashSet<>();
                        for (MasterEntity obj : nuCollection) {
                            if (obj != null) {
                                if (!ogMap.containsKey(obj.getId())) {
                                    try {
                                        MasterEntity managedEntity = entityManager.getReference(obj.getClass(), obj.getId());
                                        if (managedEntity != null) {
                                            insertSet.add(managedEntity);
                                        }
                                    } catch (EntityNotFoundException e) {
                                        System.err.printf("Entity not found: Class=%s, Id=%s\n", obj.getClass(), obj.getId());
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
                } else if (isComplex(ogf)) {
                    try {
                        MasterEntity relation = (MasterEntity) nuf.get(nu);
                        if (relation != null) {
                            if (relation.getId() == null) {
                                ogf.set(og, null);
                            } else if (relation.getId() != null) {
                                Object managedEntity = entityManager.getReference(ogf.getType(), relation.getId());
                                System.out.println("managedEntity = " + managedEntity);
                                if (managedEntity instanceof MasterEntity) {
                                    ogf.set(og, managedEntity);
                                }
                            }
                        }
                    } catch (EntityNotFoundException e) {
                        System.err.printf("Entity not found: Class=%s, Id=%s\n", ogf.getClass(), nu.getId());
                    }
                } else if (!isComplex(nuf)) {
                    Object nufValue = nuf.get(nu);
                    if (nufValue != null) {
                        ogf.set(og, nufValue);
                    }
                }
            }
            return og;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isComplex(Field f) {
        return !Integer.class.isAssignableFrom(f.getType()) && !Long.class.isAssignableFrom(f.getType()) &&
                !Double.class.isAssignableFrom(f.getType()) && !Boolean.class.isAssignableFrom(f.getType()) &&
                !Float.class.isAssignableFrom(f.getType()) && !String.class.isAssignableFrom(f.getType()) &&
                !LocalDateTime.class.isAssignableFrom(f.getType()) && !Timestamp.class.isAssignableFrom(f.getType()) &&
                !Date.class.isAssignableFrom(f.getType()) && !Enum.class.isAssignableFrom(f.getType()) &&
                !WorkFlowMovement.class.isAssignableFrom(f.getType()) &&
                !WorkFlowActionType.class.isAssignableFrom(f.getType());
    }

    private Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
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
