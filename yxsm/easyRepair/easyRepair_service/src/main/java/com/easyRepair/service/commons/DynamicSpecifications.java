package com.easyRepair.service.commons;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DynamicSpecifications {
    public static <T> Specification<T> bySearchFilter(final Collection<SpecificationUtil> filters, final Class<T> entityClazz) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (filters.size()>0) {
                    List<Predicate> predicates = Lists.newArrayList();
                    for (SpecificationUtil filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.operator) {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case NEQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;

                            case BEG:
                                ParameterExpression<Date> param = builder.parameter(Date.class,filter.value.toString());
                                predicates.add(builder.lessThanOrEqualTo(expression,param));
                                break;

                            case AFT:
                                ParameterExpression<Date> param2 = builder.parameter(Date.class,filter.value.toString());
                                predicates.add(builder.lessThanOrEqualTo(expression,param2));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case NL:
                                predicates.add(builder.notLike(expression, "%" + filter.value + "%"));
                                break;
                            case NU:
                                predicates.add(builder.isEmpty(expression));
                                break;
                            case NN:
                                predicates.add(builder.isNotEmpty(expression));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }


    /*public static <T> Predicate bySearchFilterTest(final Collection<SpecificationUtil> filters,CriteriaBuilder criteriaBuilder final Class<T> entityClazz) {
                if (filters.size()>0) {
                    List<Predicate> predicates = Lists.newArrayList();
                    for (SpecificationUtil filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);

                        // logic operator
                        switch (filter.operator) {
                            case EQ:
                                predicates.add(criteriaBuilder.equal(expression, filter.value));
                                break;
                            case NEQ:
                                predicates.add(criteriaBuilder.equal(expression, filter.value));
                                break;
                            case BEG:
                                ParameterExpression<Date> param = criteriaBuilder.parameter(Date.class,filter.value.toString());
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(expression,param));
                                break;

                            case AFT:
                                ParameterExpression<Date> param2 = criteriaBuilder.parameter(Date.class,filter.value.toString());
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(expression,param2));
                                break;
                            case LIKE:
                                predicates.add(criteriaBuilder.like(expression, "%" + filter.value + "%"));
                                break;
                            case NL:
                                predicates.add(criteriaBuilder.notLike(expression, "%" + filter.value + "%"));
                                break;
                            case NU:
                                predicates.add(criteriaBuilder.isEmpty(expression));
                                break;
                            case NN:
                                predicates.add(criteriaBuilder.isNotEmpty(expression));
                                break;
                            case GT:
                                predicates.add(criteriaBuilder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(criteriaBuilder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return criteriaBuilder.conjunction();
            }
    }*/
}