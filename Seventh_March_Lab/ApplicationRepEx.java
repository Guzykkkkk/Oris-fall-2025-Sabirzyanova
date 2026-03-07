package org.Game.Seventh_March_Lab;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
public class ApplicationRepEx {

    private  EntityManager entityManager;

    // 8.1
    public  List<Object[]> getTransactionsByAccountId(Long accountId) {
        String jpql = "SELECT t.fromCard, t.toCard, t.ammount, t.details.dateTime " +
                "FROM Transaction t " +
                "JOIN t.fromCard fc " +
                "JOIN t.toCard tc " +
                "WHERE fc.account.id = :accId OR tc.account.id = :accId";
        return entityManager.createQuery(jpql, Object[].class)
                .setParameter("accId", accountId)
                .getResultList();
        }

        // 8.2
        public List<Account> getAccountsWithTransactionAbove(BigDecimal threshold) {
            String jpql = "SELECT DISTINCT a FROM Account a " +
                    "WHERE EXISTS (SELECT 1 FROM Transaction t " +
                    "              WHERE (t.fromCard.account = a OR t.toCard.account = a) " +
                    "                AND t.ammount > :threshold)";
            return entityManager.createQuery(jpql, Account.class)
                    .setParameter("threshold", threshold)
                    .getResultList();
        }

        // 8.3
        public List<Account> getAccountsNotResidentOf(Long residentId) {
            String jpql = "SELECT a FROM Account a " +
                    "WHERE NOT EXISTS (SELECT 1 FROM a.residents r WHERE r.id = :resId)";
            return entityManager.createQuery(jpql, Account.class)
                    .setParameter("resId", residentId)
                    .getResultList();
        }

        // 8.4
        public List<Bank> getBanksWithCardWithoutTransactions() {
            String jpql = "SELECT DISTINCT b FROM Bank b JOIN b.cards c " +
                    "WHERE NOT EXISTS (SELECT 1 FROM Transaction t " +
                    "                  WHERE t.fromCard = c OR t.toCard = c)";
            return entityManager.createQuery(jpql, Bank.class).getResultList();
        }

        // 8.5
        public List<Bank> getBanksInvolvedInTransactionsBetweenCountries(Long countryId1, Long countryId2) {
            Resident country1 = entityManager.find(Resident.class, countryId1);
            Resident country2 = entityManager.find(Resident.class, countryId2);
            if (country1 == null || country2 == null) {
                throw new IllegalArgumentException("Country not found");
            }

            String jpql = "SELECT DISTINCT b FROM Bank b JOIN b.cards c " +
                    "WHERE EXISTS (SELECT 1 FROM Transaction t " +
                    "              WHERE (t.fromCard = c AND :c1 MEMBER OF t.fromCard.account.residents " +
                    "                           AND :c2 MEMBER OF t.toCard.account.residents) " +
                    "                 OR (t.toCard = c AND :c1 MEMBER OF t.toCard.account.residents " +
                    "                           AND :c2 MEMBER OF t.fromCard.account.residents))";
            return entityManager.createQuery(jpql, Bank.class)
                    .setParameter("c1", country1)
                    .setParameter("c2", country2)
                    .getResultList();
        }
}
