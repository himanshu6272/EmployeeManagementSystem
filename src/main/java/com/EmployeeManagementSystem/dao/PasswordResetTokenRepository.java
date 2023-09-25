package com.EmployeeManagementSystem.dao;

import com.EmployeeManagementSystem.forgotCredential.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    public PasswordResetToken findByOtp(String otp);

    @Transactional
    @Modifying
    @Query("delete from PasswordResetToken p where p.user.id= :id")
    void deleteByUserId(@Param("id") int id);
}
