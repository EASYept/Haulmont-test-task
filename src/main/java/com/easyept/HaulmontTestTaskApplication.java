package com.easyept;

import com.easyept.dao.BankRepository;
import com.easyept.dao.ClientRepository;
import com.easyept.dao.CreditOfferRepository;
import com.easyept.dao.CreditRepository;
import com.easyept.entity.Bank;
import com.easyept.entity.Credit;
import com.easyept.entity.CreditOffer;
import com.easyept.entity.Client;
import org.apache.commons.compress.utils.Sets;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class HaulmontTestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaulmontTestTaskApplication.class, args);
	}

	@Profile("initDB")
	@Bean
	CommandLineRunner initRecords(
			BankRepository bankService,
			ClientRepository clientService,
			CreditRepository creditService,
			CreditOfferRepository creditOfferService
	) {
		Client client1 = new Client("1111", "1111", "1111", "1111", "1111", "1111");
		Client client2 = new Client("2222", "2222", "2222", "2222", "2222", "2222");
		Client client3 = new Client("3333", "3333", "3333", "3333", "3333", "3333");
		Client client4 = new Client("4444", "4444", "4444", "4444", "4444", "4444");
		Client client5 = new Client("5555", "5555", "5555", "5555", "5555", "5555");
		Client client6 = new Client("6666", "6666", "6666", "6666", "6666", "6666");
		Client client7 = new Client("7777", "7777", "7777", "7777", "7777", "7777");
		Client client8 = new Client("8888", "8888", "8888", "8888", "8888", "8888");
		Client client9 = new Client("9999", "9999", "9999", "9999", "9999", "9999");
		Client client10 = new Client("11110", "11110", "11110", "11110", "11110", "11110");
		Client client11 = new Client("12221", "12221", "12221", "12221", "12221", "12221");
		Client client12 = new Client("13332", "13332", "13332", "13332", "13332", "13332");
		Client client13 = new Client("14443", "14443", "14443", "14443", "14443", "14443");
		Client client14 = new Client("15554", "15554", "15554", "15554", "15554", "15554");
		Client client15 = new Client("16665", "16665", "16665", "16665", "16665", "16665");
		Client client16 = new Client("17776", "17776", "17776", "17776", "17776", "17776");
		Client client17 = new Client("18887", "18887", "18887", "18887", "18887", "18887");
		Client client18 = new Client("19998", "19998", "19998", "19998", "19998", "19998");
		Client client19 = new Client("21109", "21109", "21109", "21109", "21109", "21109");
		Client client20 = new Client("22220", "22220", "22220", "22220", "22220", "22220");


		Credit credit1 = new Credit(100000, 25);
		Credit credit2 = new Credit(150000, 20.1f);
		Credit credit3 = new Credit(200000, 15);
		Credit credit4 = new Credit(250000, 10.2f);
		Credit credit5 = new Credit(300000, 8.1f);
		Credit credit6 = new Credit(350000, 7.4f);
		Credit credit7 = new Credit(400000, 5.3f);
		Credit credit8 = new Credit(1000000, 3.99f);

		Bank bank1 = new Bank("Kappa");
		Bank bank2 = new Bank("Kappa123");
		Bank bank3 = new Bank("KEKW");

		bank1.setClients(Sets.newHashSet(
				client1,
				client2,
				client3,
				client4,
				client5
		));
		bank2.setClients(Sets.newHashSet(
				client6,
				client7,
				client8,
				client9,
				client10
		));
		bank3.setClients(Sets.newHashSet(
				client11,
				client12,
				client13,
				client14,
				client15
		));

		bank1.setCredits(Sets.newHashSet(credit8,credit2));
		bank2.setCredits(Sets.newHashSet(credit3,credit7));
		bank3.setCredits(Sets.newHashSet(credit5,credit6));



		CreditOffer creditOffer1 = new CreditOffer(client1, credit1, 100000, 24, LocalDate.of(2021, 9, 25));
		CreditOffer creditOffer2 = new CreditOffer(client5, credit7, 222000, 12, LocalDate.of(2021, 8, 30));
		CreditOffer creditOffer3 = new CreditOffer(client4, credit3, 100000, 36, LocalDate.of(2021, 9, 30));
		CreditOffer creditOffer4 = new CreditOffer(client9, credit4, 105000, 75, LocalDate.of(2021, 5, 5));
		CreditOffer creditOffer5 = new CreditOffer(client20, credit8, 900000, 120, LocalDate.of(2021, 9, 13));


		return args -> {
			clientService.save(client16);
			clientService.saveAll(Arrays.asList(client17, client18, client19, client20));

			creditService.saveAll(Arrays.asList(credit1, credit4));

			bankService.save(bank1);
			bankService.save(bank2);
			bankService.save(bank3);
			creditOfferService.save(creditOffer1);
			creditOfferService.save(creditOffer2);
			creditOfferService.save(creditOffer3);
			creditOfferService.save(creditOffer4);
			creditOfferService.save(creditOffer5);
		};
	}

}
