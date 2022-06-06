package com.spring.inventory.repositories

import com.spring.inventory.entities.Contract
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface ContractRepository: JpaRepository<Contract, Int> {
}
