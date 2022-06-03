package com.spring.inventory.repositories

import com.spring.inventory.entities.SourceOfFunds
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SourceOfFundsRepository: JpaRepository<SourceOfFunds, Int> {
}
