package com.kalgooksoo.user.specification;

import com.kalgooksoo.user.domain.User;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static <T> Specification<T> usernameEquals(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<User> usernameContains(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> nameContains(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<User> emailIdContains(String emailId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email").get("id"), "%" + emailId + "%");
    }

    public static Specification<User> contactNumberContains(String contactNumber) {
        return (root, query, criteriaBuilder) -> {
            Path<Object> first = root.get("contactNumber").get("first");
            Path<Object> middle = root.get("contactNumber").get("middle");
            Path<Object> last = root.get("contactNumber").get("last");
            Expression<String> concat = criteriaBuilder.concat(criteriaBuilder.concat(first.as(String.class), middle.as(String.class)), last.as(String.class));
            return criteriaBuilder.like(concat, "%" + contactNumber + "%");
        };
    }

}
