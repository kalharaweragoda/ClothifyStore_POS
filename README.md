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

## ✨ Main Features
* 🎨 **Modern UI:** Intuitive graphical user interface built with JavaFX and JFoenix components.
* 📦 **Inventory Management:** Comprehensive tracking of stock levels, categories, and fashion items.
* 📑 **Professional Reporting:** Generate high-quality bills and sales reports using JasperReports.
* 🔐 **Secure Data:** Password encryption and secure credential management using Jasypt.
* 📊 **Export Capabilities:** Support for exporting data to Excel formats via Apache POI.

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

## ⚙️ Setup & Installation

Follow these steps to get the project running on your local machine:

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/kalharaweragoda/ClothifyStore_POS.git
    ```
2.  **Database Configuration:**
    Update the database name, username, and password in the configuration files located in `src/main/resources` to match your local MySQL setup.
3.  **Build & Run:**
    Open the project in your preferred IDE (IntelliJ IDEA or Eclipse), let Maven download the dependencies, and run the `AppInitializer.java` file.

---

## 🤝 Contributions
Contributions are welcome! If you encounter any bugs or have feature suggestions, please open an **Issue** or submit a **Pull Request**.

---

