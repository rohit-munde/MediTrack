#!/bin/bash
# MediTrack - GitHub Publishing Script

PROJECT_DIR="/Users/rohitmunde/IdeaProjects/MediTrack"
cd "$PROJECT_DIR"

echo "================================================"
echo "    MediTrack - GitHub Publishing Script"
echo "================================================"
echo ""

# Step 1: Check if GitHub username is provided
if [ -z "$1" ]; then
    echo "❌ ERROR: GitHub username required"
    echo ""
    echo "Usage: ./publish_to_github.sh YOUR_GITHUB_USERNAME"
    echo ""
    echo "Example: ./publish_to_github.sh rohitmunde"
    echo ""
    exit 1
fi

GITHUB_USERNAME=$1
REPO_URL="https://github.com/${GITHUB_USERNAME}/MediTrack.git"

echo "📋 Publishing Details:"
echo "   GitHub Username: $GITHUB_USERNAME"
echo "   Repository URL: $REPO_URL"
echo "   Project Path: $PROJECT_DIR"
echo ""

# Step 2: Verify Git is initialized
if [ ! -d ".git" ]; then
    echo "❌ ERROR: Git repository not found"
    exit 1
fi

echo "✅ Git repository found"
echo ""

# Step 3: Check current commits
echo "📊 Current commits:"
git log --oneline | head -3
echo ""

# Step 4: Add remote
echo "🔗 Adding GitHub remote..."
git remote add origin "$REPO_URL" 2>/dev/null || {
    echo "⚠️  Remote already exists, updating..."
    git remote remove origin
    git remote add origin "$REPO_URL"
}

# Step 5: Verify remote
echo "✅ Remote configured:"
git remote -v
echo ""

# Step 6: Push commits
echo "📤 Pushing commits to GitHub..."
echo ""
git push -u origin main 2>&1

echo ""
echo "================================================"
echo "    ✅ Publishing Complete!"
echo "================================================"
echo ""
echo "Your repository is now available at:"
echo "🌐 https://github.com/${GITHUB_USERNAME}/MediTrack"
echo ""
echo "Next steps:"
echo "1. Verify on GitHub.com"
echo "2. Share the link with your academy"
echo "3. All commits and files are now synced!"
echo ""

