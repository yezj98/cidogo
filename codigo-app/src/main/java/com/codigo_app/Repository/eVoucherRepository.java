package com.codigo_app.Repository;

import com.codigo_app.Model.eVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface eVoucherRepository extends JpaRepository<eVoucher, Long> {
}
