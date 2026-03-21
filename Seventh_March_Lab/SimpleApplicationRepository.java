package org.Game.Seventh_March_Lab;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleApplicationRepository {
    private  EntityManager em;


    public void createTransaction(Long fromCardId, Long toCardId, BigDecimal ammount,
                                  LocalDateTime dateTime, String description) {
        Card from = em.find(Card.class, fromCardId);
        Card to = em.find(Card.class, toCardId);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Card not found");
        }

        Transaction transaction = Transaction.builder()
                .ammount(ammount)
                .fromCard(from)
                .toCard(to)
                .build();

        TransactionDetails details = TransactionDetails.builder()
                .dateTime(dateTime)
                .description(description)
                .transaction(transaction)
                .build();

        transaction.setDetails(details);
        em.persist(transaction);
    }

    public List<Card> getCardsByBank(Long bankId) {
        return em.createQuery("SELECT c FROM Card c WHERE c.bank.id = :bankId", Card.class)
                .setParameter("bankId", bankId)
                .getResultList();
    }

    public List<Resident> getResidentsWithAccountHavingCard() {
        String jpql = "SELECT DISTINCT r FROM Resident r " +
                "WHERE EXISTS (SELECT 1 FROM Account a JOIN a.cards c WHERE a MEMBER OF r.accounts)";
        return em.createQuery(jpql, Resident.class).getResultList();
    }

    public void addResidencyToAccount(Long accountId, Long residentId) {
        Account account = em.find(Account.class, accountId);
        Resident resident = em.find(Resident.class, residentId);
        if (account == null || resident == null) {
            throw new IllegalArgumentException("Account or Resident not found");
        }
        account.getResidents().add(resident);
        em.merge(account);
    }
}