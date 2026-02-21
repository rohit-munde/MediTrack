# Git Commit Setup & Troubleshooting Guide

## **Quick Fix - Step by Step**

### **Step 1: Configure Git (If Not Already Done)**
```bash
# Set your name
git config --global user.name "Rohit Munde"

# Set your email
git config --global user.email "rohitmunde@example.com"

# Verify configuration
git config --global user.name
git config --global user.email
```

### **Step 2: Navigate to Project**
```bash
cd ~/IdeaProjects/MediTrack
```

### **Step 3: Check Current Status**
```bash
git status
```

You should see:
```
On branch main
Your branch is up to date with 'origin/main'.

Changes not staged for commit:
  modified:   [list of files]

Untracked files:
  [list of new files]
```

### **Step 4: Stage All Changes**
```bash
# Stage all modified and new files
git add .

# Or stage specific files
git add src/ docs/ *.md
```

### **Step 5: Verify Staging**
```bash
git status

# You should see:
# Changes to be committed:
#   new file: src/...
#   modified: ...
```

### **Step 6: Commit Changes**
```bash
git commit -m "Complete MediTrack Healthcare Management System

- Added 25 Java source files
- Implemented CRUD operations for Doctors, Patients, Appointments, Billing
- Added AI recommendations engine
- Created comprehensive documentation (5000+ lines)
- Included complete test suite
- All OOP principles and design patterns implemented
- Java 8+ features utilized throughout"
```

### **Step 7: Push to Remote (GitHub)**
```bash
# If you have GitHub remote set up
git push origin main

# Or if you want to set upstream
git push -u origin main
```

---

## **If You Get Errors - Solutions**

### **Error 1: "fatal: not a git repository"**
```bash
# Initialize Git if not already done
cd ~/IdeaProjects/MediTrack
git init

# Add remote if needed
git remote add origin https://github.com/yourusername/MediTrack.git
```

### **Error 2: "Author identity unknown"**
```bash
# Configure Git user
git config --global user.name "Rohit Munde"
git config --global user.email "your-email@example.com"
```

### **Error 3: "nothing to commit, working tree clean"**
```bash
# This means everything is already committed
# Or there are no changes to commit
git status  # Check what's going on
```

### **Error 4: "Permission denied (publickey)"**
```bash
# SSH key issue with GitHub
# Check if SSH key exists
ls -la ~/.ssh/id_rsa

# If not, generate one
ssh-keygen -t rsa -b 4096 -C "your-email@example.com"

# Add key to ssh-agent
ssh-add ~/.ssh/id_rsa

# Test connection
ssh -T git@github.com
```

### **Error 5: "failed to push some refs"**
```bash
# Pull latest changes first
git pull origin main

# Then push
git push origin main
```

---

## **Complete Commit Workflow**

```bash
# 1. Configure Git (one time)
git config --global user.name "Rohit Munde"
git config --global user.email "rohitmunde@example.com"

# 2. Navigate to project
cd ~/IdeaProjects/MediTrack

# 3. Check status
git status

# 4. Stage changes
git add .

# 5. Verify staging
git status

# 6. Commit
git commit -m "Complete MediTrack assignment with all features and documentation"

# 7. Push to GitHub (if you have remote)
git push origin main

# 8. Verify
git log --oneline -3
```

---

## **What to Commit**

### **Include These Files:**
✅ `src/` - All 25 Java files
✅ `bin/` - Compiled .class files
✅ `docs/` - Documentation folder
✅ `README.md` - Project overview
✅ `COMPLETION_SUMMARY.md` - Assignment status
✅ `*.md` - All other documentation

### **Exclude These (Already in .gitignore):**
❌ `.idea/` - IDE configuration
❌ `.DS_Store` - macOS files
❌ `*.log` - Log files

---

## **Verify Your Commit**

```bash
# See commit history
git log --oneline -5

# See all commits
git log

# See what's in latest commit
git show HEAD

# See changes made
git diff HEAD~1 HEAD
```

---

## **If Remote Repository (GitHub) Setup Needed**

### **Create GitHub Repository:**
1. Go to https://github.com/new
2. Name it: `MediTrack`
3. Click "Create repository"

### **Add Remote and Push:**
```bash
cd ~/IdeaProjects/MediTrack

# Add remote (replace USERNAME)
git remote add origin https://github.com/USERNAME/MediTrack.git

# Rename branch if needed
git branch -M main

# Push all commits
git push -u origin main
```

### **Verify Remote:**
```bash
git remote -v
# Should show:
# origin  https://github.com/USERNAME/MediTrack.git (fetch)
# origin  https://github.com/USERNAME/MediTrack.git (push)
```

---

## **Common Commit Messages for This Project**

```bash
# Initial commit
git commit -m "Initial MediTrack project setup"

# Feature complete
git commit -m "Complete MediTrack Healthcare Management System

- 25 Java files with full OOP implementation
- 4 service classes with CRUD operations
- 6 utility classes including AI recommendations
- Comprehensive documentation (5000+ lines)
- 100+ test scenarios with 100% pass rate
- Design patterns: Singleton, Factory, Strategy
- Java 8+ features: Streams, Lambdas, Generics"

# Documentation update
git commit -m "Add comprehensive documentation and setup guide"

# Test addition
git commit -m "Add complete test suite with 100+ test scenarios"

# Final submission
git commit -m "Final submission: MediTrack complete and tested"
```

---

## **Troubleshooting Checklist**

- [ ] Git is installed: `git --version`
- [ ] User configured: `git config --global user.name`
- [ ] Email configured: `git config --global user.email`
- [ ] In project directory: `pwd` shows `/Users/rohitmunde/IdeaProjects/MediTrack`
- [ ] Git initialized: `.git` folder exists
- [ ] Files ready: `git status` shows changes
- [ ] Changes staged: `git add .` executed
- [ ] Commit message ready: `git commit -m "message"`
- [ ] Remote added: `git remote -v` shows origin
- [ ] Ready to push: `git push origin main`

---

## **Quick Command - Copy & Paste Ready**

```bash
# All in one (after initial setup)
cd ~/IdeaProjects/MediTrack && \
git add . && \
git commit -m "Complete MediTrack Healthcare Management System - All requirements met" && \
git push origin main
```

---

**Need more help?** Let me know what error you're seeing!

