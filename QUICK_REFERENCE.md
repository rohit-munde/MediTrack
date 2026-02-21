# MediTrack - Quick Reference Guide

## 🚀 Getting Started in 5 Minutes

### Step 1: Compile
```bash
cd ~/IdeaProjects/MediTrack
find src -name "*.java" | xargs javac -d bin
```

### Step 2: Run
```bash
java -cp bin com.airtribe.meditrack.Main
```

### Step 3: Explore the Menu
- Create a doctor
- Register a patient
- Schedule an appointment
- Generate a bill

---

## 📱 Main Menu Commands

| Option | Feature | Submenu Items |
|--------|---------|---------------|
| **1** | Doctor Management | Add, View, Search (name/spec), Update, Delete, Avg Fees |
| **2** | Patient Management | Register, View, Search (name/age/symptom), Update, Delete |
| **3** | Appointment Management | Schedule, View, Confirm, Cancel, Reschedule |
| **4** | Billing & Revenue | Generate Bill, View Bills, Patient Bills, Doctor Revenue, Totals |
| **5** | AI Features | Doctor Recommendations, Slots, Affordable, Top Doctors |
| **6** | Reports | Statistics Dashboard |
| **7** | Tests | Run comprehensive test suite |
| **8** | Exit | Save data and exit |

---

## 🧬 Class Structure at a Glance

### Entity Classes
```
Person (base)
├── Doctor (specialization, fees)
└── Patient (symptoms)

Appointment (doctor, patient, time, status)
Bill (appointment, amount, tax)
BillSummary (immutable summary)
```

### Service Layer
```
DoctorService      → CRUD + search doctors
PatientService     → CRUD + search patients
AppointmentService → CRUD + appointment logic
BillService        → Bill generation + analytics
```

### Utilities
```
DataStore<T>    → Generic storage
IdGenerator     → Thread-safe ID generation
Validator       → Input validation
DateUtil        → Date parsing/formatting
CSVUtil         → File persistence
AIHelper        → Smart recommendations
```

---

## 📊 Key Methods

### DoctorService
```java
createDoctor(name, age, email, contact, spec, fees)
getDoctorById(id)
searchDoctorByName(name)
searchDoctorBySpecialization(spec)
updateDoctor(id, ...)
deleteDoctor(id)
getAverageFees()
```

### PatientService
```java
createPatient(name, age, email, contact, symptoms)
getPatientById(id)
searchPatientByName(name)
searchPatientByAge(age)
searchPatientBySymptom(symptom)
updatePatient(id, ...)
deletePatient(id)
```

### AppointmentService
```java
scheduleAppointment(doctor, patient, dateTime)
getAppointmentById(id)
getAppointmentsByDoctor(doctorId)
getAppointmentsByPatient(patientId)
confirmAppointment(id)
cancelAppointment(id)
updateAppointment(id, newDateTime)
```

### AIHelper
```java
recommendDoctorsBySymptoms(symptoms)
suggestAppointmentSlots()
getAffordableDoctors()
getTopDoctors(limit)
```

---

## 🔐 Data Validation Rules

| Field | Rule | Example |
|-------|------|---------|
| **Age** | 1-120 | 45 |
| **Name** | Not empty | "Dr. John" |
| **Email** | Must contain @ | "john@hospital.com" |
| **Contact** | Min 10 digits | "9876543210" |
| **Fees** | 100-10000 | 500 |
| **Date/Time** | Format yyyy-MM-dd HH:mm:ss | "2026-03-21 14:00:00" |

---

## 💾 Data Persistence

### Automatic CSV Files Created
- `doctors.csv` - All doctor records
- `patients.csv` - All patient records

### Load Persisted Data
```bash
java -cp bin com.airtribe.meditrack.Main --loadData
```

### CSV Format
```
doctors.csv:
ID,Name,Age,Email,ContactNo,Specialization,Fees
2000,Dr. John,45,john@hospital.com,9876543210,CARDIOLOGIST,500

patients.csv:
ID,Name,Age,Email,ContactNo,Symptoms
3000,Rahul,28,rahul@email.com,9123456789,Fever|Cough
```

---

## 🤖 AI Doctor Recommendations

Symptoms automatically matched to specializations:

| Symptom | Specialist |
|---------|-----------|
| Chest pain, Heart | Cardiologist |
| Skin, Rash, Acne | Dermatologist |
| Headache, Migraine | Neurologist |
| Child, Baby | Pediatrician |
| Depression, Anxiety | Psychiatrist |
| X-ray, Scan | Radiologist |
| Surgery, Fracture | Surgeon |

---

## 🧪 Testing

### Run All Tests
```bash
java -cp bin com.airtribe.meditrack.test.TestRunner
```

### What Tests Check
- ✅ Doctor CRUD operations
- ✅ Patient CRUD operations
- ✅ Appointment scheduling
- ✅ Bill generation
- ✅ Date utilities
- ✅ AI recommendations
- ✅ Generic collections
- ✅ Search operations

---

## 📋 Enumerations

### Specialization (8 types)
- GENERAL
- CARDIOLOGIST
- DERMATOLOGIST
- NEUROLOGIST
- PEDIATRICIAN
- PSYCHIATRIST
- RADIOLOGIST
- SURGEON

### AppointmentStatus (4 states)
- PENDING (initial)
- CONFIRMED (user confirms)
- CANCELLED (user cancels)
- COMPLETED (appointment done)

---

## 💡 Code Examples

### Create a Doctor
```java
Doctor doc = doctorService.createDoctor(
    "Dr. John",           // name
    45,                   // age
    "john@hospital.com",  // email
    "9876543210",         // contact
    Specialization.CARDIOLOGIST,  // specialization
    500.0                 // fees
);
```

### Create a Patient
```java
List<String> symptoms = Arrays.asList("Chest pain", "Shortness of breath");
Patient pat = patientService.createPatient(
    "Rahul Singh",        // name
    28,                   // age
    "rahul@email.com",    // email
    "9123456789",         // contact
    symptoms              // symptoms
);
```

### Schedule Appointment
```java
LocalDateTime time = DateUtil.parseDateTime("2026-03-21 14:00:00");
Appointment apt = appointmentService.scheduleAppointment(
    doctor,  // Doctor object
    patient, // Patient object
    time     // DateTime
);
```

### Get AI Recommendations
```java
List<String> symptoms = Arrays.asList("chest pain", "heart issues");
List<Doctor> recommended = aiHelper.recommendDoctorsBySymptoms(symptoms);
// Returns cardiologists based on symptoms
```

---

## 🐛 Common Issues & Solutions

### Issue: "No doctors found"
**Solution**: Create doctors first using menu option 1

### Issue: Scanner not accepting input
**Solution**: Make sure you're running from terminal/IDE properly

### Issue: CSV not found
**Solution**: Run save operations first to create CSV files

### Issue: Date parsing error
**Solution**: Use format `yyyy-MM-dd HH:mm:ss` (e.g., `2026-03-21 14:00:00`)

### Issue: "Invalid option"
**Solution**: Enter valid menu numbers (1-8 for main menu)

---

## 📚 File Locations

```
MediTrack/
├── src/                     # Source code
│   └── com/airtribe/meditrack/
│       ├── enitity/        # Entity classes (8)
│       ├── service/        # Service classes (4)
│       ├── util/           # Utility classes (6)
│       ├── exception/      # Exception classes (2)
│       ├── interfaces/     # Interfaces (2)
│       ├── constants/      # Constants (1)
│       ├── test/           # Tests (1)
│       └── Main.java       # Entry point
│
├── bin/                     # Compiled .class files
├── docs/                    # Documentation
│   ├── Setup_Instructions.md
│   └── JVM_Report.md
│
├── README.md                # Project overview
├── COMPLETION_SUMMARY.md    # Assignment status
└── QUICK_REFERENCE.md       # This file
```

---

## 🎯 Workflow Example

1. **Start Application**
   ```bash
   java -cp bin com.airtribe.meditrack.Main
   ```

2. **Add Doctors** (Menu 1 → 1)
   - Add "Dr. John Cardiologist" (Specialization: Cardiologist, Fees: 500)
   - Add "Dr. Sarah Dermatologist" (Specialization: Dermatologist, Fees: 400)

3. **Register Patients** (Menu 2 → 1)
   - Register "Rahul Singh" (Age: 28, Symptoms: Fever, Cough)
   - Register "Priya Sharma" (Age: 32, Symptoms: Chest Pain, Shortness of Breath)

4. **Schedule Appointments** (Menu 3 → 1)
   - Schedule Rahul with Dr. Sarah (Date: 2026-03-21 10:00:00)
   - Schedule Priya with Dr. John (Date: 2026-03-21 14:00:00)

5. **Generate Bills** (Menu 4 → 1)
   - Create bills for each appointment
   - Tax automatically calculated (18%)

6. **View Reports** (Menu 6)
   - See total doctors, patients, appointments, revenue

7. **Exit & Save** (Menu 8)
   - Data automatically saved to CSV files

---

## 🔧 Advanced Usage

### JVM Memory Configuration
```bash
java -Xms512m -Xmx2g -cp bin com.airtribe.meditrack.Main
# -Xms512m: Start with 512MB heap
# -Xmx2g: Max 2GB heap
```

### Enable Garbage Collection Details
```bash
java -XX:+PrintGCDetails -cp bin com.airtribe.meditrack.Main
```

### Verbose Class Loading
```bash
java -verbose:class -cp bin com.airtribe.meditrack.Main
```

---

## 📞 Help & Resources

- **Setup Help**: See `docs/Setup_Instructions.md`
- **JVM Details**: See `docs/JVM_Report.md`
- **Full Docs**: See `README.md`
- **Test Examples**: Run `TestRunner.java`
- **Source Code**: Check `src/` directory

---

## ✨ Features at a Glance

```
✅ 8 Medical Specializations
✅ 4 Appointment States
✅ Multi-criteria Search
✅ Automatic Tax (18%)
✅ AI Recommendations
✅ CSV Persistence
✅ Revenue Analytics
✅ Thread-Safe IDs
✅ Type-Safe Generics
✅ Custom Exceptions
✅ Full CRUD Operations
✅ Comprehensive Testing
```

---

**Happy Coding! 🚀**

For detailed information, see:
- Full documentation: `README.md`
- Setup instructions: `docs/Setup_Instructions.md`
- JVM details: `docs/JVM_Report.md`
- Completion status: `COMPLETION_SUMMARY.md`

