# MediTrack - Healthcare Management System

A comprehensive Java-based healthcare management system demonstrating advanced OOP principles, design patterns, and Java features.

![Version](https://img.shields.io/badge/version-1.0-blue.svg)
![Java](https://img.shields.io/badge/java-17+-green.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

---

## 📋 Table of Contents

1. [Features](#features)
2. [Project Structure](#project-structure)
3. [Installation & Setup](#installation--setup)
4. [Usage Guide](#usage-guide)
5. [Architecture & Design](#architecture--design)
6. [Key Learnings](#key-learnings)
7. [Testing](#testing)
8. [Documentation](#documentation)
9. [Contributing](#contributing)

---

## ✨ Features

### Core Features
- ✅ **Doctor Management**: Add, update, delete, and search doctors
- ✅ **Patient Management**: Register patients, manage medical history
- ✅ **Appointment Scheduling**: Book, confirm, and reschedule appointments
- ✅ **Billing System**: Generate bills with automatic tax calculation
- ✅ **Specialization Tracking**: 8 medical specializations (Cardiology, Dermatology, etc.)
- ✅ **Appointment Status**: Track appointment states (Pending, Confirmed, Cancelled, Completed)

### Advanced Features
- 🤖 **AI Doctor Recommendations**: Symptom-based doctor suggestions
- 📊 **Analytics & Reporting**: Revenue tracking, statistics dashboard
- 💾 **Data Persistence**: CSV export/import functionality
- 🔍 **Advanced Search**: Multi-criteria search (by name, age, specialization, symptoms)
- 🧵 **Thread-Safe**: Synchronized ID generation
- 📝 **Immutable Classes**: BillSummary for data integrity

### Java Features Demonstrated
- **OOP Concepts**: Inheritance, polymorphism, encapsulation, abstraction
- **Collections**: ArrayList, HashMap, Stream API, Lambdas
- **Generics**: Type-safe DataStore<T>
- **Enums**: Specialization, AppointmentStatus
- **Exception Handling**: Custom exceptions (AppointmentNotFoundException, InvalidDataException)
- **File I/O**: CSV read/write with try-with-resources
- **Design Patterns**: Singleton (IdGenerator), Factory, Strategy
- **Java 8+**: Streams, lambdas, method references
- **Concurrency**: Synchronized methods, thread-safe operations

---

## 📁 Project Structure

```
MediTrack/
├── src/com/airtribe/meditrack/
│   ├── enitity/                    # Entity Classes
│   │   ├── Person.java             # Base person class
│   │   ├── Doctor.java             # Doctor entity with specialization
│   │   ├── Patient.java            # Patient entity with symptoms
│   │   ├── Appointment.java        # Appointment with status
│   │   ├── Bill.java               # Billing information
│   │   ├── BillSummary.java        # Immutable bill summary
│   │   ├── Specialization.java     # Enum for doctor specializations
│   │   └── AppointmentStatus.java  # Enum for appointment states
│   │
│   ├── service/                    # Service Classes (Business Logic)
│   │   ├── DoctorService.java      # Doctor CRUD & search operations
│   │   ├── PatientService.java     # Patient CRUD & search operations
│   │   ├── AppointmentService.java # Appointment management
│   │   └── BillService.java        # Billing operations
│   │
│   ├── util/                       # Utility Classes
│   │   ├── DataStore.java          # Generic data storage (HashMap-based)
│   │   ├── IdGenerator.java        # Thread-safe ID generation (Singleton)
│   │   ├── Validator.java          # Input validation
│   │   ├── DateUtil.java           # Date/time parsing and formatting
│   │   ├── CSVUtil.java            # CSV file operations
│   │   └── AIHelper.java           # AI recommendations engine
│   │
│   ├── exception/                  # Custom Exceptions
│   │   ├── AppointmentNotFoundException.java
│   │   └── InvalidDataException.java
│   │
│   ├── interfaces/                 # Interface Definitions
│   │   ├── Searchable.java         # Searchable interface
│   │   └── Payable.java            # Payable interface
│   │
│   ├── constants/                  # Constants
│   │   └── Constants.java          # Application-wide constants
│   │
│   ├── test/                       # Testing
│   │   └── TestRunner.java         # Comprehensive test suite
│   │
│   └── Main.java                   # Main entry point with menu system
│
├── docs/                           # Documentation
│   ├── Setup_Instructions.md       # Installation guide
│   └── JVM_Report.md               # JVM architecture details
│
├── bin/                            # Compiled bytecode (generated)
├── *.csv                           # Persistent data files (auto-generated)
└── README.md                       # This file
```

---

## 🚀 Installation & Setup

### Prerequisites
- **Java JDK 17+** (LTS version recommended)
- **macOS 10.13+** (or any OS with Java support)
- **2GB RAM** minimum
- **Git** (optional, for version control)

### Quick Start

1. **Clone or download the project**
   ```bash
   cd ~/IdeaProjects/MediTrack
   ```

2. **Compile the project**
   ```bash
   mkdir -p bin
   javac -d bin src/com/airtribe/meditrack/**/*.java
   ```

3. **Run the application**
   ```bash
   java -cp bin com.airtribe.meditrack.Main
   ```

4. **Run tests**
   ```bash
   java -cp bin com.airtribe.meditrack.test.TestRunner
   ```

### With Persistent Data Loading
```bash
java -cp bin com.airtribe.meditrack.Main --loadData
```

---

## 📖 Usage Guide

### Main Menu Options

```
┌────────────── MAIN MENU ──────────────┐
│ 1. Doctor Management                   │
│ 2. Patient Management                  │
│ 3. Appointment Management              │
│ 4. Billing & Revenue                   │
│ 5. AI Features (Recommendations)       │
│ 6. Reports & Analytics                 │
│ 7. Run Tests                           │
│ 8. Exit MediTrack                      │
└────────────────────────────────────────┘
```

### 1. Doctor Management
- **Add Doctor**: Register new doctor with specialization and fees
- **View All Doctors**: Display all registered doctors
- **Search by Name**: Find doctors by name
- **Search by Specialization**: Filter doctors by medical specialty
- **Update Doctor**: Modify doctor information
- **Delete Doctor**: Remove doctor from system
- **View Average Fees**: See average consultation fees

### 2. Patient Management
- **Register Patient**: Add new patient with medical history
- **View All Patients**: List all registered patients
- **Search by Name**: Find patients by name
- **Search by Age**: Filter patients by age
- **Search by Symptom**: Find patients with specific symptoms
- **Update Patient**: Modify patient details
- **Delete Patient**: Remove patient from system

### 3. Appointment Management
- **Schedule Appointment**: Book appointment with date/time validation
- **View All Appointments**: See all scheduled appointments
- **View Doctor Appointments**: Show appointments for specific doctor
- **View Patient Appointments**: Show appointments for specific patient
- **Confirm Appointment**: Mark appointment as confirmed
- **Cancel Appointment**: Cancel and mark as cancelled
- **Reschedule Appointment**: Change appointment date/time

### 4. Billing & Revenue
- **Generate Bill**: Create bill for completed appointment
- **View All Bills**: Display all bills with details
- **View Patient Bills**: Show bills for specific patient
- **View Doctor Revenue**: Calculate revenue per doctor
- **Total Revenue & Tax**: View financial summary

### 5. AI Features
- **Doctor Recommendations**: Get AI-suggested doctors based on symptoms
- **Appointment Slots**: View available appointment times
- **Affordable Doctors**: Find doctors below average fees
- **Top Doctors**: View top doctors by fees

### 6. Reports & Analytics
- Total doctors in system
- Total patients in system
- Total appointments scheduled
- Total bills generated
- Average doctor fees
- Total revenue
- Total tax collected

---

## 🏗️ Architecture & Design

### Design Patterns Used

#### 1. **Singleton Pattern** (IdGenerator)
```java
// Thread-safe ID generation
public class IdGenerator {
    private static int appointmentId = 1000;
    
    public static synchronized int generateAppointmentId() {
        return appointmentId++;
    }
}
```

#### 2. **Factory Pattern** (Service Classes)
Services act as factories creating entities:
```java
Doctor doc = doctorService.createDoctor(name, age, email, ...);
Patient pat = patientService.createPatient(name, age, email, ...);
```

#### 3. **Strategy Pattern** (Multiple Search Strategies)
```java
// Different search strategies
doctorService.searchDoctorByName(name);
doctorService.searchDoctorBySpecialization(spec);
patientService.searchPatientByAge(age);
patientService.searchPatientBySymptom(symptom);
```

#### 4. **Generic Collection Pattern** (DataStore<T>)
```java
public class DataStore<T> {
    private HashMap<Integer, T> data;
    
    public void create(int id, T item) { ... }
    public T read(int id) { ... }
    public void update(int id, T item) { ... }
    public void delete(int id) { ... }
}
```

### OOP Principles

#### **Encapsulation**
- Private fields with public getters/setters
- Validation in setters
- Data hiding and controlled access

#### **Inheritance**
```java
public class Doctor extends Person { ... }
public class Patient extends Person { ... }
```

#### **Polymorphism**
- Method overloading (search methods)
- Method overriding (inherited methods)
- Dynamic dispatch

#### **Abstraction**
- Interfaces (Searchable, Payable)
- Abstract concepts (BillSummary)
- Hiding complex operations

---

## 📚 Key Learnings

### Java 8+ Features
- **Streams API**: Filtering, mapping, collecting data
- **Lambda Expressions**: Functional programming style
- **Method References**: Concise code with `::` operator
- **Default Methods**: In interfaces

### Collections & Generics
- **Type Safety**: Generic DataStore<T>
- **ArrayList & HashMap**: Dynamic data structures
- **Stream Operations**: map(), filter(), collect()
- **Comparators & Sorting**

### Exception Handling
- **Custom Exceptions**: AppointmentNotFoundException, InvalidDataException
- **Exception Chaining**: Preserving stack traces
- **Try-with-Resources**: Automatic resource management

### File I/O
- **CSV Parsing**: String.split() for parsing
- **Buffered I/O**: Efficient file reading/writing
- **Try-with-Resources**: Safe resource handling

### Concurrency
- **Synchronized Methods**: Thread-safe ID generation
- **Atomic Operations**: Safe counter increments
- **Thread Safety**: DataStore operations

---

## ✅ Testing

### Running Tests
```bash
java -cp bin com.airtribe.meditrack.test.TestRunner
```

### Test Coverage
- ✓ Doctor Service CRUD operations
- ✓ Patient Service CRUD operations
- ✓ Appointment scheduling and management
- ✓ Bill generation and calculation
- ✓ DateUtil parsing and formatting
- ✓ AI Helper doctor recommendations
- ✓ Generic collections (DataStore<T>)
- ✓ Search operations
- ✓ Exception handling

### Manual Testing
Run the interactive menu system and perform operations through the UI.

---

## 📚 Documentation

### Setup Instructions
See [docs/Setup_Instructions.md](docs/Setup_Instructions.md) for:
- Java installation guide
- Project compilation steps
- IDE configuration
- Troubleshooting tips

### JVM Report
See [docs/JVM_Report.md](docs/JVM_Report.md) for:
- JVM architecture overview
- Class Loader mechanics
- Runtime Data Areas (Heap, Stack, etc.)
- Execution Engine details
- JIT Compiler vs Interpreter
- Garbage Collection process
- "Write Once, Run Anywhere" explanation

---

## 💡 Example Usage

### Add a Doctor
```
1. Select option: 1 (Doctor Management)
2. Select option: 1 (Add New Doctor)
3. Enter name: Dr. John Cardiologist
4. Enter age: 45
5. Enter email: john@hospital.com
6. Enter contact: 9876543210
7. Enter fees: 500
8. Select specialization: 1 (Cardiologist)
```

### Schedule an Appointment
```
1. Select option: 3 (Appointment Management)
2. Select option: 1 (Schedule Appointment)
3. Enter doctor ID: 2000
4. Enter patient ID: 3000
5. Enter appointment date: 2026-03-21 14:00:00
```

### Get AI Recommendations
```
1. Select option: 5 (AI Features)
2. Select option: 1 (Doctor Recommendations)
3. Enter symptoms: chest pain, shortness of breath
→ AI suggests 1 cardiologist
```

---

## 🔧 Advanced Configuration

### Compile with Options
```bash
# Verbose output
javac -d bin -verbose src/com/airtribe/meditrack/**/*.java

# Lint warnings
javac -d bin -Xlint src/com/airtribe/meditrack/**/*.java
```

### Run with JVM Options
```bash
# Increase heap size
java -Xms512m -Xmx2g -cp bin com.airtribe.meditrack.Main

# Enable garbage collection details
java -XX:+PrintGCDetails -cp bin com.airtribe.meditrack.Main

# Enable assertions
java -ea -cp bin com.airtribe.meditrack.Main
```

---

## 📊 Data Persistence

### CSV File Format

**doctors.csv**
```
ID,Name,Age,Email,ContactNo,Specialization,Fees
2000,Dr. John,45,john@hospital.com,9876543210,CARDIOLOGIST,500
2001,Dr. Sarah,35,sarah@hospital.com,9876543211,DERMATOLOGIST,400
```

**patients.csv**
```
ID,Name,Age,Email,ContactNo,Symptoms
3000,Rahul,28,rahul@email.com,9123456789,Fever|Cough
3001,Priya,32,priya@email.com,9123456790,Chest Pain|Shortness of Breath
```

### Loading Persisted Data
```bash
java -cp bin com.airtribe.meditrack.Main --loadData
# Automatically loads from CSV files
```

---

## 🎓 Learning Outcomes

After completing this project, you will understand:

✅ Java compilation and execution flow
✅ JVM architecture and memory management
✅ OOP principles (abstraction, encapsulation, inheritance, polymorphism)
✅ Collections framework (ArrayList, HashMap, Streams)
✅ Exception handling and custom exceptions
✅ File I/O and CSV parsing
✅ Design patterns (Singleton, Factory, Strategy)
✅ Java 8+ features (Lambdas, Streams, default methods)
✅ Thread safety and synchronization
✅ How to structure a real-world Java application

---

## 🐛 Troubleshooting

### Issue: "javac: command not found"
**Solution**: Install Java JDK or add JAVA_HOME to PATH

### Issue: "Scanner not reading input"
**Solution**: Run from terminal or IDE with proper input stream setup

### Issue: CSV files not found during load
**Solution**: Ensure CSVUtil can write to current directory or configure path

### Issue: OutOfMemoryException
**Solution**: Increase heap size with `-Xmx` flag

---

## 📝 Future Enhancements

- [ ] Database integration (SQL)
- [ ] REST API (Spring Boot)
- [ ] Web UI (JavaFX)
- [ ] Email notifications
- [ ] SMS reminders
- [ ] Mobile app integration
- [ ] Multi-language support
- [ ] Advanced reporting (PDF generation)

---

## 👥 Contributors

**Project Author**: MediTrack Development Team
**Version**: 1.0
**Last Updated**: February 21, 2026

---

## 📄 License

MIT License - Feel free to use, modify, and distribute.

---

## 📞 Support

For questions or issues:
1. Check [Setup_Instructions.md](docs/Setup_Instructions.md)
2. Review [JVM_Report.md](docs/JVM_Report.md)
3. Examine test cases in TestRunner.java
4. Review example code in Main.java

---

## 🎯 Key Achievements

✅ 100% object-oriented design
✅ 8 medical specializations
✅ 5 appointment statuses
✅ AI-powered recommendations
✅ 2 design patterns
✅ Type-safe generics
✅ Full CRUD operations
✅ Advanced search capabilities
✅ Comprehensive test suite
✅ Detailed documentation

**Happy Coding! 🚀**

