package com.FactoryManager.Repository;

import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.Entity.CentralOfficeRequest;
import com.FactoryManager.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentralOfficeRequestRepository extends JpaRepository<CentralOfficeRequest, Long> {
    List<CentralOfficeRequest> findByRequestStatus(RequestStatus requestStatus);

    @Query("SELECT r FROM CentralOfficeRequest r WHERE r.requestStatus = 'PENDING' OR r.approvedBy = :user")
    Page<CentralOfficeRequest> findAllPendingOrApprovedBy(User user, Pageable pageable);

    Page<CentralOfficeRequest> findByRequestStatusAndApprovedBy(RequestStatus status, User user, Pageable pageable);

    Page<CentralOfficeRequest> findByRequestStatus(RequestStatus status, Pageable pageable);

}
