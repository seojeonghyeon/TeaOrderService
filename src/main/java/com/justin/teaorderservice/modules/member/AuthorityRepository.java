package com.justin.teaorderservice.modules.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
