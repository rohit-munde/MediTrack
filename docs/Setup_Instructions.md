# MediTrack - Setup Instructions

## Environment Setup Guide

### Prerequisites
- macOS 10.13 or later
- 2GB RAM minimum
- 500MB disk space

### Step 1: Install Java Development Kit (JDK)

#### Option A: Using Homebrew (Recommended)
```bash
# Install Homebrew if not already installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java JDK 17 (LTS)
brew install openjdk@17

# Set JAVA_HOME environment variable
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
source ~/.zshrc
```

#### Option B: Direct Download
1. Visit [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/)
2. Download JDK 17 (or later) for macOS
3. Run the installer (.dmg file)
4. Follow the installation wizard

### Step 2: Verify Java Installation

```bash
# Check Java version
java -version

# Expected output:
# openjdk version "17.0.x" 2023-xx-xx
# OpenJDK Runtime Environment ...
# OpenJDK 64-Bit Server VM ...

# Check JDK path
echo $JAVA_HOME

# Should output something like: /Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home
```

### Step 3: Clone or Setup MediTrack Project

```bash
# Navigate to projects directory
cd ~/IdeaProjects

# If cloning from Git
git clone https://github.com/yourusername/MediTrack.git
cd MediTrack

# Or if already present
cd ~/IdeaProjects/MediTrack
```

### Step 4: Project Structure Verification

```bash
# Verify the project structure
ls -la src/com/airtribe/meditrack/

# You should see:
# - enitity/      (entity classes)
# - service/      (service classes)
# - util/         (utility classes)
# - exception/    (custom exceptions)
# - interfaces/   (interface definitions)
# - constants/    (constants file)
# - test/         (test runner)
# - Main.java     (main entry point)
```

### Step 5: Compile the Project

```bash
# Create output directory
mkdir -p bin

# Compile all Java files
javac -d bin src/com/airtribe/meditrack/**/*.java

# Or use find command for better compatibility
find src -name "*.java" | xargs javac -d bin

# Verify compilation (should see no errors)
echo "Compilation successful!"
```

### Step 6: Run the Application

```bash
# Navigate to project root
cd ~/IdeaProjects/MediTrack

# Run the main application
java -cp bin com.airtribe.meditrack.Main

# Or with data loading flag
java -cp bin com.airtribe.meditrack.Main --loadData

# Run tests
java -cp bin com.airtribe.meditrack.test.TestRunner
```

### Step 7: Configure IDE (Optional - IntelliJ IDEA)

1. Open IntelliJ IDEA
2. Click **File > Open**
3. Navigate to `/Users/rohitmunde/IdeaProjects/MediTrack`
4. Click **Open**
5. Wait for indexing to complete
6. Click **File > Project Structure**
   - Set **Project SDK**: Java 17
   - Set **Project Language Level**: 17
7. Right-click on `src` folder → **Mark Directory as > Sources Root**
8. Right-click on project → **Build > Build Project**

### Step 8: Environment Variables (Optional)

Add to your shell profile (`~/.zshrc` or `~/.bash_profile`):

```bash
# Java Home
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

# Project Home
export MEDITRACK_HOME=~/IdeaProjects/MediTrack

# Classpath
export CLASSPATH=$MEDITRACK_HOME/bin:$CLASSPATH
```

Then reload: `source ~/.zshrc`

---

## Troubleshooting

### Problem: "java: command not found"
**Solution**: 
```bash
# Install Java or set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### Problem: "javac: command not found"
**Solution**: JDK is not installed. Run:
```bash
brew install openjdk@17
```

### Problem: Compilation errors in DataStore
**Solution**: Ensure JDK version is 8 or higher. Check:
```bash
javac -version
```

### Problem: Scanner input not working in terminal
**Solution**: Use the full input mode in terminal or IDE

### Problem: Cannot write CSV files
**Solution**: Ensure proper permissions:
```bash
chmod 755 ~/IdeaProjects/MediTrack
```

---

## JVM Paths and Setup

### macOS Default JDK Locations

```bash
# List installed JDKs
/usr/libexec/java_home -V

# Get path to specific version
/usr/libexec/java_home -v 17
```

### Typical Installation Paths

- **Homebrew**: `/usr/local/opt/openjdk@17/libexec/openjdk.jdk`
- **Oracle JDK**: `/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home`
- **AdoptOpenJDK**: `/Library/Java/JavaVirtualMachines/adoptopenjdk-17.jdk/Contents/Home`

---

## Verification Checklist

- [ ] Java JDK 17+ installed
- [ ] JAVA_HOME environment variable set
- [ ] MediTrack project cloned/downloaded
- [ ] Project structure verified
- [ ] Code compiles without errors
- [ ] Application runs and shows main menu
- [ ] Tests run successfully

---

## Additional Resources

- [Java Documentation](https://docs.oracle.com/en/java/)
- [JVM Specification](https://docs.oracle.com/javase/specs/jvms/se17/html/)
- [Maven Setup (Optional)](https://maven.apache.org/guides/getting-started/)
- [Git Guide](https://git-scm.com/book/en/v2)

---

**Last Updated**: February 21, 2026
**Java Version**: 17 LTS
**IDE Recommended**: IntelliJ IDEA Community Edition

