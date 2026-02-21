# MediTrack - JVM Report

## Java Virtual Machine (JVM) Internals & Understanding

---

## 1. JVM Architecture Overview

The Java Virtual Machine is an abstract computing machine that enables a computer to run Java programs and applications written in other languages that are compiled to Java bytecode.

```
┌─────────────────────────────────────────────────────────┐
│                    JAVA CODE                            │
│         (SourceFile.java)                               │
└────────────────┬────────────────────────────────────────┘
                 │ javac (Java Compiler)
                 ↓
┌─────────────────────────────────────────────────────────┐
│                  BYTECODE                               │
│         (SourceFile.class)                              │
└────────────────┬────────────────────────────────────────┘
                 │ java (JVM)
                 ↓
┌─────────────────────────────────────────────────────────┐
│            MACHINE CODE / NATIVE CODE                   │
│         (Executed by CPU/OS)                            │
└─────────────────────────────────────────────────────────┘
```

---

## 2. Class Loader (ClassLoader)

The Class Loader is responsible for loading Java classes during runtime. It follows a **Delegation Model** with three types:

### 2.1 Types of Class Loaders

#### **A. Bootstrap Class Loader (Primordial Class Loader)**
- **Purpose**: Loads core JDK classes from `JDK_HOME/lib`
- **Classes Loaded**: 
  - `java.lang.Object`
  - `java.lang.String`
  - `java.lang.System`
  - `java.util.*`
- **Implementation**: Written in native code (C/C++)
- **Parent**: None (it's the root)

#### **B. Extension Class Loader (Platform Class Loader)**
- **Purpose**: Loads extended classes from `JDK_HOME/lib/ext`
- **Classes Loaded**: Optional packages, security extensions
- **Parent**: Bootstrap Class Loader
- **Example**: `javax.crypto.*`, `javax.xml.*`

#### **C. Application Class Loader (System Class Loader)**
- **Purpose**: Loads classes from the classpath/CLASSPATH
- **Classes Loaded**: User application classes
- **Parent**: Extension Class Loader
- **Example**: Our MediTrack application classes

### 2.2 Class Loading Process (Delegation Model)

```
Application Class Loader
         │
         ├─→ Check if class already loaded
         │
         └─→ Ask Extension Class Loader
              │
              ├─→ Check if class already loaded
              │
              └─→ Ask Bootstrap Class Loader
                   │
                   ├─→ Check if class already loaded
                   │
                   └─→ If not found, ClassNotFoundException
```

### 2.3 Example in MediTrack

```java
// When we load Main class:
// 1. Application ClassLoader loads "com.airtribe.meditrack.Main"
// 2. It delegates to Extension ClassLoader
// 3. Which delegates to Bootstrap ClassLoader
// 4. Bootstrap loads java.lang.Object (superclass)
// 5. Then Application ClassLoader loads our Main class

import com.airtribe.meditrack.service.DoctorService;  // User-defined
import java.util.Scanner;                              // JDK class
```

---

## 3. Runtime Data Areas

The JVM organizes memory into several distinct areas:

```
┌────────────────────────────────────────────────────────────┐
│              MEMORY AREAS (RAM)                            │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │           METHOD AREA / CLASS AREA                   │  │
│  │  • Class structures (name, fields, methods)          │  │
│  │  • Method code (bytecode)                            │  │
│  │  • Runtime constant pool                             │  │
│  │  • Static variables                                  │  │
│  │  • Field data                                        │  │
│  │  • Shared among all threads                          │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              HEAP (Garbage Collected)                │  │
│  │  • All objects created via 'new'                     │  │
│  │  • Doctor doc = new Doctor(...)  ← goes to HEAP     │  │
│  │  • Shared among all threads                          │  │
│  │  • GC cleans unreferenced objects                    │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  STACK (Per-Thread, Auto-deallocated)                │  │
│  │  ┌──────────────────────────────────────────────────┐│  │
│  │  │ Thread 1 Stack:                                  ││  │
│  │  │ • main() frame                                   ││  │
│  │  │ • displayMainMenu() frame                        ││  │
│  │  │ • addDoctor() frame                              ││  │
│  │  │ • Local variables (String name, int age, etc.)   ││  │
│  │  └──────────────────────────────────────────────────┘│  │
│  │  ┌──────────────────────────────────────────────────┐│  │
│  │  │ Thread 2 Stack: (if multi-threaded)              ││  │
│  │  │ • Method frames for Thread 2                     ││  │
│  │  │ • Thread 2 local variables                       ││  │
│  │  └──────────────────────────────────────────────────┘│  │
│  └──────────────────────────────────────────────────────┘  │
│                                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │     PROGRAM COUNTER (PC) REGISTER (Per-Thread)       │  │
│  │  • Current instruction being executed               │  │
│  │  • If executing native method: undefined            │  │
│  │  • Very small memory footprint                       │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  NATIVE METHOD STACK (Per-Thread)                    │  │
│  │  • Stack for native (C/C++) method calls             │  │
│  │  • Example: System.out.println() uses native code   │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### 3.1 Detailed Breakdown

#### **METHOD AREA (Class Area)**
- **Size**: Shared, fixed at JVM startup
- **Content**: Class structures, bytecode, constant pool
- **Example from MediTrack**:
  ```java
  // When Doctor.class is loaded:
  public class Doctor extends Person {
      // Class structure stored in Method Area
      // - Class name: "com.airtribe.meditrack.enitity.Doctor"
      // - Fields: name, age, email, contactNo, specialization, fees
      // - Methods: getters, setters, toString(), constructors
      // - Static data (if any)
  }
  ```
- **Garbage Collection**: Cleaned when class is unloaded (rarely)
- **Size Limitation**: Configurable with `-XX:MaxMetaspaceSize=256m`

#### **HEAP**
- **Size**: Largest memory area, grows dynamically
- **Garbage Collection**: Automatic cleanup of unreferenced objects
- **Example from MediTrack**:
  ```java
  public static void main(String[] args) {
      Doctor doc = new Doctor(...);  // Object allocated in HEAP
      Patient pat = new Patient(...); // Object allocated in HEAP
      // When doc or pat go out of scope, GC marks for cleanup
  }
  ```
- **Heap Configuration**:
  ```bash
  java -Xms256m -Xmx1024m com.airtribe.meditrack.Main
  # -Xms: Initial heap size (256MB)
  # -Xmx: Maximum heap size (1024MB)
  ```

#### **STACK (Execution Stack)**
- **Size**: Typically 1MB per thread
- **Memory Management**: Automatic (LIFO - Last In First Out)
- **Example from MediTrack**:
  ```java
  public void displayMainMenu() {           // Stack Frame 1
      System.out.println("Menu:");          // Local variables stored
      int choice = getIntInput();           // Local variable: choice
  }
  
  private static int getIntInput() {        // Stack Frame 2
      int value = sc.nextInt();             // Local variable: value
      return value;
  }
  // When getIntInput() returns, Frame 2 is popped
  // When displayMainMenu() returns, Frame 1 is popped
  ```
- **Stack Overflow**: Occurs with deep recursion
  ```bash
  java -Xss512k com.airtribe.meditrack.Main
  # -Xss: Stack size per thread
  ```

#### **PROGRAM COUNTER (PC) REGISTER**
- **Size**: Very small (4-8 bytes)
- **Purpose**: Points to the current JVM instruction
- **For Native Methods**: PC register is undefined
- **Per Thread**: Each thread has its own PC

#### **NATIVE METHOD STACK**
- **Purpose**: Executes native code (C/C++)
- **Used By**: JNI (Java Native Interface) calls
- **Example from MediTrack**:
  ```java
  System.out.println("Hello");  // println() may use native code
  ```

---

## 4. Execution Engine

The Execution Engine executes the bytecode. It consists of three main components:

### 4.1 Components

```
┌────────────────────────────────────────────────────┐
│           EXECUTION ENGINE                         │
├────────────────────────────────────────────────────┤
│                                                    │
│  ┌──────────────────────────────────────────────┐ │
│  │     1. INTERPRETER                           │ │
│  │  • Reads bytecode instructions               │ │
│  │  • Interprets line-by-line                   │ │
│  │  • Slower execution                          │ │
│  │  • Direct execution without optimization     │ │
│  └──────────────────────────────────────────────┘ │
│                                                    │
│  ┌──────────────────────────────────────────────┐ │
│  │  2. JIT COMPILER (Just-In-Time)              │ │
│  │  • Compiles frequently used code at runtime  │ │
│  │  • Converts bytecode to native machine code  │ │
│  │  • Faster execution after compilation        │ │
│  │  • Adaptive optimization                     │ │
│  └──────────────────────────────────────────────┘ │
│                                                    │
│  ┌──────────────────────────────────────────────┐ │
│  │  3. GARBAGE COLLECTOR (GC)                   │ │
│  │  • Manages automatic memory cleanup          │ │
│  │  • Reclaims unused objects from heap         │ │
│  │  • Runs concurrently with application        │ │
│  └──────────────────────────────────────────────┘ │
│                                                    │
└────────────────────────────────────────────────────┘
```

---

## 5. JIT Compiler vs Interpreter

### 5.1 Comparison Table

| Aspect | Interpreter | JIT Compiler |
|--------|-------------|--------------|
| **Initial Speed** | Fast | Slow (compilation overhead) |
| **Runtime Speed** | Slow | Fast (optimized native code) |
| **Memory Usage** | Low | High (compiled code stored) |
| **Optimization** | Minimal | Aggressive (inlining, loop unrolling) |
| **Best For** | Short-lived scripts | Long-running applications |
| **Decision Point** | Every execution | After hot spot detection |

### 5.2 Tiered Compilation (Modern JVMs)

Modern Java uses **Tiered Compilation** strategy:

```
Step 1: INTERPRETED EXECUTION
        (Application starts)
        ↓
Step 2: MONITOR EXECUTION
        (Collect statistics on hot code)
        ↓
Step 3: INITIAL JIT COMPILATION (C1 Compiler)
        (Fast compilation, basic optimizations)
        ↓
Step 4: FULL JIT COMPILATION (C2 Compiler)
        (Aggressive optimizations, inline analysis)
        ↓
Step 5: OPTIMIZED EXECUTION
        (Native code execution at maximum speed)
```

### 5.3 Example from MediTrack

```java
public void scheduleAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime) {
    // First execution: INTERPRETED
    // Statistics collected about this method being called
    
    // After 10,000 calls (hot spot): JIT COMPILATION
    // C1 Compiler: Basic optimization
    
    // After 20,000 calls: C2 Compiler
    // Aggressive optimization: inlining method calls, loop unrolling
    
    // Final execution: Native code, maximum speed
    if (isDoctorAvailable(doctor, dateTime) && isPatientAvailable(patient, dateTime)) {
        Appointment appointment = new Appointment(doctor, patient, dateTime);
        // ... rest of code
    }
}
```

---

## 6. "Write Once, Run Anywhere" (WORA/WORE)

### 6.1 How Java Achieves Platform Independence

```
┌────────────────────────────────────────────────────────┐
│  Java Source Code (Platform Independent)              │
│  └─→ public class Main { ... }                        │
└────────────────┬───────────────────────────────────────┘
                 │ javac compiler (produces platform-independent)
                 ↓
┌────────────────────────────────────────────────────────┐
│  Java Bytecode (Platform Independent)                 │
│  └─→ .class files (hexadecimal instruction set)       │
└────────────────┬───────────────────────────────────────┘
                 │ JVM (different for each OS)
                 ├──→ JVM on Windows
                 ├──→ JVM on macOS ← MediTrack runs here
                 ├──→ JVM on Linux
                 └──→ JVM on any OS with JVM installed
```

### 6.2 Key Components for WORA

1. **Bytecode Format**: Platform-independent intermediate representation
2. **JVM Implementation**: OS-specific, converts bytecode to native instructions
3. **Standard Library**: Consistent across all platforms

### 6.3 MediTrack WORA Example

```bash
# Compile once on macOS
javac src/com/airtribe/meditrack/**/*.java

# Run on macOS
java -cp bin com.airtribe.meditrack.Main

# Same .class files can run on Windows
# Just install JVM for Windows and run:
# java -cp bin com.airtribe.meditrack.Main

# Same .class files can run on Linux
# Just install JVM for Linux and run:
# java -cp bin com.airtribe.meditrack.Main

# Zero code changes needed! This is WORA.
```

---

## 7. JVM Startup Process

### 7.1 Sequence Diagram

```
1. OS Starts JVM Process
   └─→ JVM Initialization
   
2. Load Bootstrap Classes
   └─→ java.lang.Object, java.lang.String, etc.
   
3. Load System Classes
   └─→ java.lang.System, java.io.*, etc.
   
4. Create Runtime Environment
   └─→ Garbage Collector starts
   └─→ Thread scheduler starts
   └─→ Exception handling initialized
   
5. Find and Load Main Class
   └─→ ClassLoader loads "com.airtribe.meditrack.Main"
   
6. Locate main() Method
   └─→ public static void main(String[] args)
   
7. Create Main Thread
   └─→ Execute main() method
   
8. Shutdown Sequence
   └─→ Exit main() → JVM shutdown hooks
   └─→ Graceful termination
```

### 7.2 MediTrack Startup

```java
public static void main(String[] args) {
    // Step 1: Static fields initialized
    private static DoctorService doctorService = new DoctorService();
    
    // Step 2: Static initializers executed
    // (if any exist)
    
    // Step 3: main() method body executes
    System.out.println("Welcome to MediTrack");
    
    // Step 4: Program runs (while loop)
    while (running) {
        // Event loop
    }
    
    // Step 5: JVM exits
    System.exit(0);  // or naturally return from main()
}
```

---

## 8. Memory Management & Garbage Collection

### 8.1 GC Phases

```
┌───────────────────────────────────────────────────┐
│         GARBAGE COLLECTION PROCESS                │
├───────────────────────────────────────────────────┤
│                                                   │
│ PHASE 1: MARK                                     │
│ ├─ Find all reachable objects from root          │
│ ├─ Mark them as "in use"                         │
│ └─ Everything else is garbage                    │
│                                                   │
│ PHASE 2: SWEEP                                    │
│ ├─ Remove unmarked objects from heap             │
│ ├─ Reclaim memory                                │
│ └─ Compact remaining objects (optional)          │
│                                                   │
│ PHASE 3: COMPACT (Optional)                       │
│ ├─ Reorganize remaining objects                  │
│ ├─ Eliminate fragmentation                       │
│ └─ Improve memory locality                       │
│                                                   │
└───────────────────────────────────────────────────┘
```

### 8.2 GC in MediTrack Context

```java
public void createDoctor() {
    Doctor doc = new Doctor(...);  // Created in HEAP
    doctorService.createDoctor(...);  // Stored in DataStore
    
    // If 'doc' local reference is lost (e.g., method ends)
    // and object is not referenced elsewhere, GC marks it
    // Next GC cycle: Object reclaimed from HEAP
}

// Example of creating many objects:
public void scheduleMultipleAppointments() {
    for (int i = 0; i < 10000; i++) {
        Appointment apt = new Appointment(...);
        // Each iteration creates new object
        // Previous iteration's 'apt' becomes garbage
        // (unless stored in list/collection)
    }
    // After loop: Many objects eligible for GC
}
```

---

## 9. Configuration & Tuning

### 9.1 Common JVM Arguments

```bash
# Memory Configuration
java -Xms256m -Xmx1024m com.airtribe.meditrack.Main
# -Xms: Initial heap size
# -Xmx: Maximum heap size

# Stack Configuration
java -Xss512k com.airtribe.meditrack.Main
# -Xss: Stack size per thread

# GC Configuration
java -XX:+UseG1GC com.airtribe.meditrack.Main
# -XX:+UseG1GC: Use G1 Garbage Collector (modern)
# -XX:+UseSerialGC: Serial GC (single-threaded)
# -XX:+UseParallelGC: Parallel GC (multi-threaded)

# Enable Debugging
java -verbose:class com.airtribe.meditrack.Main
# Shows class loading information

# Performance Analysis
java -XX:+PrintGCDetails com.airtribe.meditrack.Main
# Shows GC activity
```

### 9.2 Recommended for MediTrack

```bash
java -Xms512m -Xmx2g -XX:+UseG1GC \
     -Xss1m -XX:+PrintGCDetails \
     -cp bin com.airtribe.meditrack.Main

# Explanation:
# -Xms512m: Start with 512MB heap
# -Xmx2g: Allow up to 2GB heap
# -XX:+UseG1GC: Modern garbage collector
# -Xss1m: 1MB stack per thread
# -XX:+PrintGCDetails: Show GC information
```

---

## 10. Monitoring & Profiling

### 10.1 Tools Available

- **jps**: List running Java processes
- **jvisualvm**: Visual monitor and profiler
- **jconsole**: JVM monitoring tool
- **jstat**: JVM statistics tool
- **jmap**: Memory dump utility
- **jstack**: Thread dump utility

### 10.2 Example Usage

```bash
# List running Java processes
jps -l
# Output: 12345 com.airtribe.meditrack.Main

# View JVM statistics
jstat -gc -h10 12345 1000
# Updates every 1000ms, header every 10 lines

# Create thread dump
jstack 12345 > thread_dump.txt

# Create heap dump
jmap -dump:live,format=b,file=heap.bin 12345
```

---

## Summary

| Component | Purpose | Key Features |
|-----------|---------|--------------|
| **ClassLoader** | Load classes at runtime | Delegation model, 3 types |
| **Runtime Data Areas** | Memory organization | Heap, Stack, Method Area, etc. |
| **Execution Engine** | Execute bytecode | Interpreter + JIT Compiler + GC |
| **JIT Compiler** | Optimize hot code | Adaptive compilation, tiering |
| **WORA** | Platform independence | Bytecode + JVM abstraction |
| **GC** | Memory management | Mark, sweep, compact |

---

## Glossary

- **Bytecode**: Intermediate platform-independent code
- **Hotspot**: Frequently executed code
- **GC**: Garbage Collector (automatic memory cleanup)
- **JIT**: Just-In-Time compilation
- **Method Area**: Shared memory for class structures
- **WORA**: Write Once, Run Anywhere
- **ClassLoader**: Loads .class files at runtime
- **Execution Engine**: Interprets/compiles and executes bytecode

---

**Document Version**: 1.0
**Last Updated**: February 21, 2026
**Java Version**: 17 LTS
**Author**: MediTrack Development Team

