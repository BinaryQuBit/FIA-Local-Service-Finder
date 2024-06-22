package com.FIA.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostServiceRepository extends JpaRepository<PostService, Long> {
}
