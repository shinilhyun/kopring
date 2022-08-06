package shin.aiden.kopring.config.auth

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDSLConfiguration {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Bean
    fun jpaQueryFactory():JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}