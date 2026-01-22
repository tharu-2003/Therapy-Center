# 🏥 Therapy Center Management System

A comprehensive management solution developed for a Therapy Center to streamline operations, manage patient records, handle therapist schedules, and automate billing processes. This project is built using **JavaFX** for the frontend and **Hibernate ORM** for database management.

## 🚀 Features

* **User Management:** Secure Sign-In and Sign-Up with password encryption (BCrypt).
* **Patient Management:** Effortless tracking of patient history and details.
* **Therapist Management:** Manage therapist profiles and specializations.
* **Session Scheduling:** Organize and track therapy sessions and programs.
* **Payment Tracking:** Automated billing and payment history management.
* **Layered Architecture:** Built using DAO and BO design patterns for high maintainability.

---

## 📸 User Interface Preview

### 🔐 Authentication
|                        Sign In                         | Sign Up |
|:------------------------------------------------------:| :---: |
| ![Sign In](/src/main/resources/screenshots/singIn.png) | ![Sign Up](/src/main/resources/screenshots/singUp.png) |

### 👥 User & Staff Management
| User Management | Therapist Management |
| :---: | :---: |
| ![User](/src/main/resources/screenshots/user.png) | ![Therapist](/src/main/resources/screenshots/therapist.png) |

### 📅 Therapy Planning
| Therapy Programs | Therapy Sessions |
| :---: | :---: |
| ![Program](/src/main/resources/screenshots/therapyProgram.png) | ![Session](/src/main/resources/screenshots/therapySession.png) |

### 💳 Patients & Billing
| Patient Management | Payment Details |
| :---: | :---: |
| ![Patient](/src/main/resources/screenshots/patient.png) | ![Payment](/src/main/resources/screenshots/payment.png) |

---

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** JavaFX (OpenJFX)
* **ORM:** Hibernate 6.0.0.Final
* **Database:** MySQL 9.0.0
* **Build Tool:** Maven
* **Security:** jBCrypt (Password Hashing)
* **Reporting:** JasperReports
* **UI Components:** JFoenix

---

## 🏗️ Project Architecture

The project follows a **Layered Architecture** to ensure separation of concerns:
1.  **UI Layer (Controller/View):** Handles user interactions and FXML displays.
2.  **BO Layer (Business Objects):** Contains business logic and interacts with the DTOs.
3.  **DAO Layer (Data Access Objects):** Handles low-level data operations and Hibernate queries.
4.  **Entity Layer:** Maps Java objects to Database tables via Hibernate annotations.

---

## ⚙️ Setup & Installation

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/tharu-2003/Therapy-Center.git](https://github.com/tharu-2003/Therapy-Center.git)
    ```

2.  **Database Configuration:**
    * Create a MySQL database named `TherapyCenter`.
    * Update the `hibernate.properties` file in `src/main/resources` with your MySQL credentials:
        ```properties
        hibernate.connection.username=your_username
        hibernate.connection.password=your_password
        hibernate.connection.url=jdbc:mysql://localhost:3306/TherapyCenter
        ```

3.  **Build the Project:**
    ```bash
    mvn clean install
    ```

4.  **Run the Application:**
    ```bash
    mvn javafx:run
    ```

---

## 👨‍💻 Developer
**Tharusha sandaruwan** *Software Engineering Student at IJSE*

---