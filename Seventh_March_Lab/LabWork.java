package org.Game.Seventh_March_Lab;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class LabWork {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()){
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                Account account = Account.builder()
                        .username("ivan@gmail.com")
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .birthDate(LocalDate.of(2000, 1, 19))
                        .age(20)
                        .inn(12345L)
                        .build();
                session.persist(account);
                Bank bank = Bank.builder()
                        .title("MadeInChina")
                        .build();
                session.persist(bank);
                Card card1 = Card.builder()
                        .pan("00000000")
                        .expDate(LocalDate.now())
                        .cvv("333")
                        .account(account)
                        .bank(bank)
                        .build();
                session.persist(card1);
                session.persist(bank);
                Card card2 = Card.builder()
                        .pan("00111111")
                        .expDate(LocalDate.now())
                        .cvv("332")
                        .account(account)
                        .bank(bank)
                        .build();
                session.persist(card2);

                Resident resident1 = Resident.builder().countryName("Russia").build();
                Resident resident2 = Resident.builder().countryName("USA").build();
                session.persist(resident1);
                session.persist(resident2);
                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                SimpleApplicationRepository simpleApplicationRepository = new SimpleApplicationRepository(session);
                ApplicationRepEx applicationRepEx = new ApplicationRepEx(session);
                session.beginTransaction();

                simpleApplicationRepository.createTransaction(1L, 2L, BigDecimal.valueOf(250L),
                        LocalDateTime.now(), "something");
                List<Card> cards = simpleApplicationRepository.getCardsByBank(1L);
                System.out.println("cards from first bank: " + cards);
                cards.forEach(a -> System.out.println(a.getPan()));

                List<Resident> residents = simpleApplicationRepository.getResidentsWithAccountHavingCard();
                residents.forEach(a -> System.out.println(a.getCountryName()));

                simpleApplicationRepository.addResidencyToAccount(1L, 2L);
                session.getTransaction().commit();

                session.beginTransaction();

                List<Object[]> txData = applicationRepEx.getTransactionsByAccountId(1L);
                System.out.println("Transactions for account 1:");
                for (Object[] row : txData) {
                    System.out.println("From: " + ((Card)row[0]).getPan() + ", To: " + ((Card)row[1]).getPan() +
                            ", Amount: " + row[2] + ", Date: " + row[3]);
                }

                // 8.5
                List<Bank> banks = applicationRepEx.getBanksInvolvedInTransactionsBetweenCountries(1L, 2L);
                System.out.println("Banks involved in transactions between countries 1 and 2:");
                banks.forEach(b -> System.out.println(b.getTitle()));

                session.getTransaction().commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
