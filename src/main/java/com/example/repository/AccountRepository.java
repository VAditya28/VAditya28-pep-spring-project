package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.*;
import java.util.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//boolean userExists (  String username);
Account findByUsername(String username);
}
