package com.spring.inventory.services

import com.spring.inventory.entities.Fix
import com.spring.inventory.repositories.FixRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FixService {
    @Autowired
    lateinit var fixRepository: FixRepository

    fun addFix(fix: Fix): Fix {
        return fixRepository.saveAndFlush(fix)
    }

    fun getFixes(): MutableList<Fix> {
        return fixRepository.findAll()
    }

    fun saveFix(fix: Fix): Fix {
        return fixRepository.save(fix)
    }

    fun findFixById(id: Int): Fix {
        return fixRepository.findById(id).orElse(null)
    }

    fun deleteById(id: Int) {
        return fixRepository.deleteById(id)
    }
}
