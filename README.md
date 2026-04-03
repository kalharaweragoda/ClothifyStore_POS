# 👕 ClothifyStore - Fashion Shop POS System

ClothifyStore is a specialized **Point of Sale (POS)** application designed for fashion retail outlets. Built using **JavaFX** and following a **Layered Architecture**, it provides a streamlined experience for managing sales, inventory, and order tracking.

---

## 🚀 Key Enhancements & Design Patterns
To ensure high quality, scalability, and maintainability, the following patterns and principles were implemented:

* **Layered Architecture:** Clear separation of concerns (Controller, Service, Repository) for easier maintenance.
* **Factory Design Pattern:** Used to decouple the layers and manage object creation efficiently.
* **Strategy Design Pattern:** Implemented to allow flexible business logic execution.
* **Dependency Injection:** Managed via the **Google Guice** library for better component management.
* **Hibernate ORM:** Integrated for robust and efficient database interactions and mapping.

---

### 🌟 Features & Integrations

* **[✓] User-Friendly UI** – Modern, responsive graphical interface built using **JFoenix** and **JavaFX**.
* **[✓] Maven Build Tool** – Comprehensive dependency management and automated project builds.
* **[✓] Hibernate ORM** – Robust database interactions and object-relational mapping for data persistence.
* **[✓] Lombok Integration** – Significantly reduces boilerplate code (Getters, Setters, Constructors) for cleaner entity classes.
* **[✓] Data Encryption** – Secure password and credential management using the **Jasypt** library.
* **[✓] Transaction Management** – Ensures data integrity by handling **JDBC Transactions** effectively across service layers.

---

## 🛠️ Technologies Used
* **Language:** Java (JDK 11+)
* **Framework:** JavaFX
* **Build Tool:** Maven
* **Database:** MySQL
* **ORM:** Hibernate
* **Key Libraries:** * Lombok (Reduces boilerplate code)
    * Jasypt (Encryption)
    * ModelMapper (Object mapping)
    * Log4j (Logging)
    * JasperReports (Reporting)
    * Google Guice (Dependency Injection)

---

## ⚙️ Getting Started

## 📋 Prerequisites

To run this application smoothly, ensure the following software is installed on your local machine:

* **[☕] JDK 11+** – Java Development Kit required to compile and run the project.
* **[🗄️] MySQL Server** – Required for managing the application's database.
* **[🔨] Maven** – For handling project dependencies and build automation (usually bundled with modern IDEs).

Follow these steps to get the project running on your local machine:

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/kalharaweragoda/ClothifyStore_POS.git
    ```
2.  **Database Configuration:**
    Update the database name, username, and password in the configuration files located in `src/main/resources` to match your local MySQL setup.


---

## 🤝 Contributions
Contributions are welcome! If you encounter any bugs or have feature suggestions, please open an **Issue** or submit a **Pull Request**.

---

