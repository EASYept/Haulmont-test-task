# Haulmont-test-task

Test Task
=========

Prerequisites
-------------

* [Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)

Build and Run
-------------

1. Run in the command line:
	```
	mvn package
	mvn jetty:run
	```

2. Open `http://localhost:8080` in a web browser.

Описание задачи
Разработать веб-приложение, включая следующие сущности и атрибуты:
* Клиент
	* ФИО
	* Номер телефона
	* Электронная почта
	* Номер паспорта
* Банк
	* Список кредитов
	* Список клиентов
* Кредит
	* Лимит по кредиту
	* Процентная ставка
* Кредитное предложение
	* Клиент
	* Кредит
	* Сумма кредита
	* График платежей
	* Дата платежа
		* Сумма платежа
		* Сумма гашения тела кредита
		* Сумма гашения процентов

Приложение должно реализовывать следующие функции:
* Добавление, редактирование и удаление сущностей.
* Процесс оформления кредита на клиента с созданием графика платежей и расчетом необходимых сумм:
	* Автоматический расчет итоговой суммы процентов по кредиту;
	* Автоматический расчет суммы ежемесячного платежа с учетом процентной ставки.
