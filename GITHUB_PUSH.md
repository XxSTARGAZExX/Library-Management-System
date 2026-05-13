# Pushing to GitHub

Follow these steps to push your Library Management System project to GitHub:

## Step 1: Create a GitHub Repository

1. Go to [GitHub](https://github.com/new)
2. Click "New repository"
3. Enter repository name: `library-management-system`
4. Choose Public or Private
5. **DO NOT** initialize with README, .gitignore, or license (we already have them)
6. Click "Create repository"

## Step 2: Add Remote and Push

After creating the repository, GitHub will show you commands. Run these:

### Option A: Using HTTPS (simpler, but requires token)
```bash
cd c:\Users\aakas\Desktop\LMS\library-management-system

# Add remote repository
git remote add origin https://github.com/XxSTARGAZExX/library-management-system.git

# Rename branch to main (if needed)
git branch -M main

# Push to GitHub
git push -u origin main
```

### Option B: Using SSH (more secure)
```bash
cd c:\Users\aakas\Desktop\LMS\library-management-system

# Add remote repository
git remote add origin git@github.com:XxSTARGAZExX/library-management-system.git

# Rename branch to main
git branch -M main

# Push to GitHub
git push -u origin main
```

## Step 3: Verify

1. Go to your GitHub repository
2. Verify all files are there
3. Check the commit history

## If You Get Authentication Errors (HTTPS)

### For Windows:
1. Git will prompt you for credentials
2. Use your GitHub username
3. For password, use a **Personal Access Token** (not your password):
   - Go to GitHub Settings > Developer settings > Personal access tokens
   - Create a new token with `repo` scope
   - Use this token as password

### For SSH:
1. Generate SSH key: `ssh-keygen -t ed25519 -C "your.email@example.com"`
2. Add key to GitHub: Settings > SSH and GPG keys > New SSH key
3. Paste your public key

## Next Steps

After pushing to GitHub, share the repository link:
```
https://github.com/XxSTARGAZExX/library-management-system
```

## Useful Git Commands

```bash
# Check remote
git remote -v

# View commit history
git log --oneline

# Create a new branch
git checkout -b feature/new-feature

# Push a branch
git push origin feature/new-feature

# Pull latest changes
git pull origin main
```

## Congratulations! 🎉

Your Library Management System is now on GitHub and ready for sharing!
